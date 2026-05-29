package vbonedra.shattered_baubles.items;

import baubles.api.BaubleSlotHelper;
import baubles.api.BaubleType;
import net.minecraft.*;
import vbonedra.shattered_baubles.SBItem;
import vbonedra.shattered_baubles.SBItems;

import java.util.Set;

import static vbonedra.shattered_baubles.util.ConfigShatteredBaubles.*;

public class ClimbingPick extends SBItem {
    public ClimbingPick(int id) {
        super(id, Material.mithril, "climbing_pick");
    }
    public String formatTextWithConfigValues(String text) {
        return text.formatted(
                Math.round(climbing_pick_FALL_DAMAGE_ADDITIONAL_PERCENT.getDoubleValue()*100)
        );
    }


    @Override public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.BELT;
    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
        if (!player.worldObj.isRemote) {
            player.worldObj.playSoundAtEntity(player, "dig.stone", 0.5F, 1.25F);
        }
    }
    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
        if (!player.worldObj.isRemote) {
            player.worldObj.playSoundAtEntity(player, "dig.stone", 0.5F, 0.75F);
        }
    }



    public float getFallDamageMultiplier(EntityPlayer player, float damage) {
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
            player.fallDistance = 0.0F;

            if (player.onGround || player.jumpMovementFactor > 0.02) return true;
            if (player.isSneaking() || player.onGround) {
                player.motionY = 0.0D;
            }
            else if (player.moveForward != 0 || player.moveStrafing != 0) {
                player.motionY = climbingSpeed;
            }
            else {
                player.motionY = -climbingSpeed;
            }
            return true;

        }
        return false;
    }
}
