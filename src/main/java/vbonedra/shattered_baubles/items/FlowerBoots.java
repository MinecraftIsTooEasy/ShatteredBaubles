package vbonedra.shattered_baubles.items;

import baubles.api.BaubleSlotHelper;
import baubles.api.BaubleType;
import net.minecraft.*;
import vbonedra.shattered_baubles.SBItem;
import vbonedra.shattered_baubles.SBItems;
import vbonedra.shattered_baubles.util.SBSoundMaster;

import java.util.Set;

import static vbonedra.shattered_baubles.event.SBSounds.EQUIP_GENERIC;
import static vbonedra.shattered_baubles.SBConfig.*;
import static vbonedra.shattered_baubles.util.SBSoundMaster.playRandomizedSoundAtPlayer;

public class FlowerBoots extends SBItem {
    public FlowerBoots(int id) {super(id, Material.tree_leaves, "flower_boots", BaubleType.FEET);}
    public String formatDescriptionWithConfigValues(String text) {
        return text.formatted(
                Math.round((flower_boots_MOVEMENT_SPEED_MULTIPLIER.getDoubleValue()-1)*100),
                Math.round((flower_boots_FIRE_DAMAGE_MULTIPLIER.getDoubleValue()-1)*100)
        );
    }


    private static final Set<Material> plantMaterials = Set.of(
            Material.grass,
            Material.pumpkin,
            Material.cactus,
            Material.tree_leaves
    );
    public float getFireDamageAdditionalPercent(EntityPlayer player) {
        return (float) (BaubleSlotHelper.hasFeetOfType(player, SBItems.flower_boots) ? (flower_boots_FIRE_DAMAGE_MULTIPLIER.getDoubleValue()) - 1 : 0);
    }
    public float getMovementSpeedAdditionalPercent(EntityPlayer player) {
        if (!BaubleSlotHelper.hasFeetOfType(player, SBItems.flower_boots)) return 0.0F;

        Material material = player.worldObj.getBlockMaterial(
                (int) (player.posX),
                (int) (player.getFootPosY() - 0.5),
                (int) (player.posZ)
        );
        if (material != null && plantMaterials.contains(material)) return (float) flower_boots_MOVEMENT_SPEED_MULTIPLIER.getDoubleValue() - 1;

        return 0.0F;
    }
    public boolean climbWall(EntityPlayer player, Material material){
        if (!BaubleSlotHelper.hasFeetOfType(player, SBItems.flower_boots)) return false;
        if (plantMaterials.contains(material)) {
            float climbingSpeed = (float) (player.getClimbingSpeed() * flower_boots_CLIMBING_SPEED_MULTIPLIER.getDoubleValue());
            if (!player.onGround) {
                player.fallDistance = 0.0F;
                if (player.rotationPitch < -45.0F) {
                    player.motionY = Math.max(player.motionY,climbingSpeed);
                } else if (player.rotationPitch > 45.0F) {
                    player.motionY = Math.max(player.motionY,-climbingSpeed);
                } else if (player.isSneaking()) {
                    player.motionY = 0;
                }
                return player.ticksExisted % 10 == 0 && (player.motionY != 0 || player.moveForward != 0 || player.moveStrafing != 0);
            }
            return false;
        }
        return false;
    }
}