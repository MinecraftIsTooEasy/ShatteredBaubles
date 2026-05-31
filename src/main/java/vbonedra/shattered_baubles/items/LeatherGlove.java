package vbonedra.shattered_baubles.items;

import baubles.api.BaubleSlotHelper;
import baubles.api.BaubleType;
import net.minecraft.EntityLivingBase;
import net.minecraft.EntityPlayer;
import net.minecraft.ItemStack;
import net.minecraft.Material;
import vbonedra.shattered_baubles.SBItem;
import vbonedra.shattered_baubles.SBItems;
import vbonedra.shattered_baubles.util.MathAdditional;
import vbonedra.shattered_baubles.util.SBSoundMaster;

import static vbonedra.shattered_baubles.event.SBSounds.EQUIP_LEATHER;
import static vbonedra.shattered_baubles.util.SBConfig.*;

public class LeatherGlove extends SBItem {

    public LeatherGlove(int id) {
        super(id, Material.leather, "leather_glove");
    }
    public String formatTextWithConfigValues(String text) {
        return text.formatted(
                Math.round(leather_glove_MINING_SPEED_ADDITIONAL_PERCENT.getDoubleValue()*100),
                Math.round(leather_glove_MINING_SPEED_ADDITIONAL_CAP.getDoubleValue()),
                Math.round(leather_glove_DAMAGE_ADDITIONAL_PERCENT.getDoubleValue()*100),
                Math.round(leather_glove_DAMAGE_ADDITIONAL_CAP.getDoubleValue())
        );
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.HAND;
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

    public float getDamageAdditional(float original, EntityPlayer player) {
        return MathAdditional.minAbs(original * leather_glove_DAMAGE_ADDITIONAL_PERCENT.getDoubleValue(), leather_glove_DAMAGE_ADDITIONAL_CAP.getDoubleValue())
                * BaubleSlotHelper.countHandsOfType(player, SBItems.leather_glove);
    }
    public float getMiningSpeedAdditional(float original, EntityPlayer player) {
        return MathAdditional.minAbs(original * leather_glove_MINING_SPEED_ADDITIONAL_PERCENT.getDoubleValue(), leather_glove_MINING_SPEED_ADDITIONAL_CAP.getDoubleValue())
                * BaubleSlotHelper.countHandsOfType(player, SBItems.leather_glove);
    }

}
