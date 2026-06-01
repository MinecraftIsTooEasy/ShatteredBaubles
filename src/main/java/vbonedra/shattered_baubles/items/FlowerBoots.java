package vbonedra.shattered_baubles.items;

import baubles.api.BaubleSlotHelper;
import baubles.api.BaubleType;
import net.minecraft.*;
import vbonedra.shattered_baubles.SBItem;
import vbonedra.shattered_baubles.SBItems;
import vbonedra.shattered_baubles.util.SBSoundMaster;

import java.util.Set;

import static vbonedra.shattered_baubles.event.SBSounds.EQUIP_GENERIC;
import static vbonedra.shattered_baubles.util.SBConfig.*;
import static vbonedra.shattered_baubles.util.SBSoundMaster.playRandomizedSoundAtPlayer;

public class FlowerBoots extends SBItem {
    public FlowerBoots(int id) {super(id, Material.tree_leaves, "flower_boots");}
    public String formatTextWithConfigValues(String text) {
        return text.formatted(
                Math.round(flower_boots_MOVEMENT_SPEED_ADDITIONAL_PERCENT.getDoubleValue()*100),
                Math.round(flower_boots_FIRE_DAMAGE_ADDITIONAL_PERCENT.getDoubleValue()*100)
        );
    }

    @Override public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.FEET;
    }


    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
        if (player == null || player.worldObj == null || player.worldObj.isRemote) return;
        SBSoundMaster.playRandomizedSoundAtPlayer(player, EQUIP_GENERIC, 0.5, 1.0);
    }
    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
        if (player == null || player.worldObj == null || player.worldObj.isRemote) return;
        SBSoundMaster.playRandomizedSoundAtPlayer(player, EQUIP_GENERIC, 0.5, 0.75);
    }



    private static final Set<Material> plantMaterials = Set.of(
            Material.grass,
            Material.pumpkin,
            Material.cactus,
            Material.tree_leaves
    );
    public float getFireDamageAdditionalPercent(EntityPlayer player, float damage) {
        return damage * (float) (BaubleSlotHelper.hasFeetOfType(player, SBItems.flower_boots) ? (flower_boots_FIRE_DAMAGE_ADDITIONAL_PERCENT.getDoubleValue()) : 0);
    }
    public float getMovementSpeedMultiplier(EntityPlayer player, float speed) {
        if (!BaubleSlotHelper.hasFeetOfType(player, SBItems.flower_boots)) return 0.0F;

        Material material = player.worldObj.getBlockMaterial(
                (int) (player.posX),
                (int) (player.getFootPosY() - 0.5),
                (int) (player.posZ)
        );
        if (material != null && plantMaterials.contains(material)) return speed * (float) flower_boots_MOVEMENT_SPEED_ADDITIONAL_PERCENT.getDoubleValue();

        return 0.0F;
    }
    public boolean climbWall(EntityPlayer player, Material material){
        if (!BaubleSlotHelper.hasFeetOfType(player, SBItems.flower_boots)) return false;
        if (plantMaterials.contains(material)) {
            float climbingSpeed = (float) (player.getClimbingSpeed() * flower_boots_CLIMBING_SPEED_MULTIPLIER.getDoubleValue());

            if (!player.onGround) {
                player.fallDistance = 0.0F;
                if (player.rotationPitch < -45.0F) {
                    player.motionY = climbingSpeed;
                } else if (player.rotationPitch > 45.0F) {
                    player.motionY = -climbingSpeed;
                } else {
                    player.motionY = 0;
                }
                if (player.motionY != 0 && player.ticksExisted % 10 == 0) {
                    playRandomizedSoundAtPlayer(
                            player,
                            "dig.leave",
                            0.75, 0.1,
                            0.75, 0.1
                    );
                }
            }

            return true;
        }
        return false;
    }
}