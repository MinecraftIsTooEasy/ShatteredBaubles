package vbonedra.shattered_baubles.items;


import baubles.api.BaubleSlotHelper;
import baubles.api.BaubleType;
import net.minecraft.EntityLivingBase;
import net.minecraft.EntityPlayer;
import net.minecraft.ItemStack;
import net.minecraft.Material;
import vbonedra.shattered_baubles.SBItem;
import vbonedra.shattered_baubles.SBItems;
import vbonedra.shattered_baubles.util.SBSoundMaster;

import static vbonedra.shattered_baubles.event.SBSounds.*;
import static vbonedra.shattered_baubles.SBConfig.*;


public class HunterHat extends SBItem {
    public HunterHat(int id) {
        super(id, Material.leather, "hunter_hat");
    }
    public String formatDescriptionWithConfigValues(String text) {
        return text.formatted(
                hunter_hat_BUTCHERING_ADDITIONAL_VALUE.getIntegerValue(),
                hunter_hat_LOOTING_ADDITIONAL_VALUE.getIntegerValue(),
                Math.round(hunter_hat_EXPERIENCE_ADDITIONAL_PERCENT.getDoubleValue()*100)
        );
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.HEAD;
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


    public int getButcheringModifierAdditional(EntityPlayer player) {
        return BaubleSlotHelper.hasHeadOfType(player, SBItems.hunter_hat) ? hunter_hat_BUTCHERING_ADDITIONAL_VALUE.getIntegerValue() : 0;
    }
    public int getLootingModifierAdditional(EntityPlayer player) {
        return BaubleSlotHelper.hasHeadOfType(player, SBItems.hunter_hat) ? hunter_hat_LOOTING_ADDITIONAL_VALUE.getIntegerValue() : 0;
    }
    public int getAdditionalExperience(int xp, EntityPlayer player) {
        return BaubleSlotHelper.hasHeadOfType(player, SBItems.hunter_hat) ? (int) (xp * hunter_hat_EXPERIENCE_ADDITIONAL_PERCENT.getDoubleValue()) : 0;
    }

}
