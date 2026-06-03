package vbonedra.shattered_baubles.items;

import baubles.api.BaubleSlotHelper;
import baubles.api.BaubleType;
import net.minecraft.EntityPlayer;
import net.minecraft.ItemStack;
import net.minecraft.Material;
import vbonedra.shattered_baubles.SBItem;
import vbonedra.shattered_baubles.SBItems;

import static vbonedra.shattered_baubles.SBConfig.*;
import static vbonedra.shattered_baubles.util.SBSoundMaster.playRandomizedSoundAtPlayer;

public class FeatherBoots extends SBItem {
    public FeatherBoots(int id) {super(id, Material.meat, "feather_boots");}
    public String formatDescriptionWithConfigValues(String text) {
        return text.formatted(
                Math.round(-feather_boots_FALL_DAMAGE_ADDITIONAL_PERCENT.getDoubleValue()*100),
                Math.round((feather_boots_DETECT_RANGE_MULRIPLIER.getDoubleValue()-1)*100)
        );
    }

    @Override public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.FEET;
    }


    public float getFallDamageAdditionalPercent(EntityPlayer player, float damage) {
        return damage * (float) (BaubleSlotHelper.hasFeetOfType(player, SBItems.feather_boots) ? (feather_boots_FALL_DAMAGE_ADDITIONAL_PERCENT.getDoubleValue()) : 0);
    }
    public double getDetectRangeAdditionalPercent(EntityPlayer player) {
        return (BaubleSlotHelper.hasFeetOfType(player, SBItems.feather_boots) ? Math.pow((feather_boots_DETECT_RANGE_MULRIPLIER.getDoubleValue()),2) : 0);
    }
}