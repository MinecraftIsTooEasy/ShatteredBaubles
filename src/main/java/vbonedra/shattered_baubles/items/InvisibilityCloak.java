package vbonedra.shattered_baubles.items;

import baubles.api.BaubleSlotHelper;
import baubles.api.BaubleType;
import net.minecraft.*;
import vbonedra.shattered_baubles.SBItem;
import vbonedra.shattered_baubles.SBItems;
import vbonedra.shattered_baubles.util.SBSoundMaster;

import static vbonedra.shattered_baubles.SBConfig.invisibility_cloak_DETECT_RANGE_MULRIPLIER;
import static vbonedra.shattered_baubles.SBConfig.invisibility_cloak_DETECT_RANGE_SHADOW_MULRIPLIER;
import static vbonedra.shattered_baubles.event.SBSounds.EQUIP_LEATHER;

public class InvisibilityCloak extends SBItem {
    public InvisibilityCloak(int id) {super(id, Material.meat, "invisibility_cloak", BaubleType.BACK);}
    public String formatDescriptionWithConfigValues(String text) {
        return text.formatted(
                Math.round((invisibility_cloak_DETECT_RANGE_MULRIPLIER.getDoubleValue()-1)*100),
                Math.round((invisibility_cloak_DETECT_RANGE_SHADOW_MULRIPLIER.getDoubleValue()-1)*100)
        );
    }


    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
        if (player == null || player.worldObj == null || player.worldObj.isRemote) return;
        SBSoundMaster.playRandomizedSoundAtPlayer(player, EQUIP_LEATHER, 0.5, 1.0);
    }
    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
        if (player == null || player.worldObj == null || player.worldObj.isRemote) return;
        SBSoundMaster.playRandomizedSoundAtPlayer(player, EQUIP_LEATHER, 0.5, 0.75);
    }


    public double getDetectRangeMultiplier(EntityPlayer player) {
        if (BaubleSlotHelper.hasBackOfType(player, SBItems.invisibility_cloak)) {
            double multiplier = invisibility_cloak_DETECT_RANGE_MULRIPLIER.getDoubleValue();

            if (player.worldObj.getBlockLightValue(
                    MathHelper.floor_double(player.posX),
                    MathHelper.floor_double(player.posY),
                    MathHelper.floor_double(player.posZ)
            ) == 0) {
                multiplier = invisibility_cloak_DETECT_RANGE_SHADOW_MULRIPLIER.getDoubleValue();
            }

            return multiplier;
        }
        return 1;
    }
}