package vbonedra.shattered_baubles.items;

import baubles.api.BaubleSlotHelper;
import baubles.api.BaubleType;
import net.minecraft.*;
import vbonedra.shattered_baubles.SBItem;
import vbonedra.shattered_baubles.SBItems;

import java.util.Set;

import static vbonedra.shattered_baubles.event.SBSounds.EQUIP_LEATHER;
import static vbonedra.shattered_baubles.util.SBConfig.*;
import static vbonedra.shattered_baubles.util.SBSoundMaster.playRandomizedSoundAtPlayer;

public class ClimbingPick extends SBItem {
    public ClimbingPick(int id) {
        super(id, Material.mithril, "climbing_pick");
    }
    public String formatDescriptionWithConfigValues(String text) {
        return text.formatted(
                Math.round(climbing_pick_FALL_DAMAGE_ADDITIONAL_PERCENT.getDoubleValue()*100)
        );
    }


    @Override public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.BELT;
    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
        if (player == null || player.worldObj == null || player.worldObj.isRemote) return;
        playRandomizedSoundAtPlayer(player, EQUIP_LEATHER, 0.5, 1.0);
    }
    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
        if (player == null || player.worldObj == null || player.worldObj.isRemote) return;
        playRandomizedSoundAtPlayer(player, EQUIP_LEATHER, 0.5, 0.75);
    }



    public float getFallDamageAdditionalPercent(EntityPlayer player, float damage) {
        return damage * (float) (BaubleSlotHelper.hasBeltOfType(player, SBItems.climbing_pick) ? climbing_pick_FALL_DAMAGE_ADDITIONAL_PERCENT.getDoubleValue() : 0);
    }
    private static final Set<Material> climbableMaterials = Set.of(
            Material.lapis_lazuli,
            Material.redstone,
            Material.coal,
            Material.gold,
            Material.silver,
            Material.copper,
            Material.iron,
            Material.ancient_metal,
            Material.mithril,
            Material.adamantium,
            Material.obsidian,
            Material.glowstone,
            Material.netherrack,
            Material.stone
    );
    public boolean climbWall(EntityPlayer player, Material material){
        if (!BaubleSlotHelper.hasBeltOfType(player, SBItems.climbing_pick)) return false;
        if (climbableMaterials.contains(material)) {
            float climbingSpeed = (float) (player.getClimbingSpeed() * climbing_pick_CLIMBING_SPEED_MULTIPLIER.getDoubleValue());
            if (player.isSneaking() && !player.onGround) {
                if (player.motionY >= -climbingSpeed*10) {
                    player.fallDistance = 0.0F;
                    if (player.rotationPitch < -45.0F) {
                        player.motionY = Math.max(player.motionY,climbingSpeed);
                    } else if (player.rotationPitch > 45.0F) {
                        player.motionY = Math.min(player.motionY,-climbingSpeed);
                    } else {
                        player.motionY = 0;
                    }
                    return player.ticksExisted % 10 == 0 && (player.motionY != 0 || player.moveForward != 0 || player.moveStrafing != 0);
                } else {
                    player.fallDistance *= 0.9F;
                    player.motionY *= 0.9;
                    return true;
                }
            }
            return false;
        }
        return false;
    }
}
