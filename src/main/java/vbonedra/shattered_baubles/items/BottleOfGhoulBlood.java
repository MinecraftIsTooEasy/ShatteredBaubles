package vbonedra.shattered_baubles.items;

import baubles.api.BaubleSlotHelper;
import baubles.api.BaubleType;
import net.minecraft.*;
import vbonedra.shattered_baubles.SBItem;
import vbonedra.shattered_baubles.SBItems;
import vbonedra.shattered_baubles.util.MathAdditional;
import vbonedra.shattered_baubles.util.SBSoundMaster;

import static vbonedra.shattered_baubles.util.SBConfig.*;

public class BottleOfGhoulBlood extends SBItem {
    public BottleOfGhoulBlood(int id) {
        super(id, Material.glass, "bottle_of_ghoul_blood");
    }
    public String formatTextWithConfigValues(String text) {
        return text.formatted(
                Math.round(bottle_of_ghoul_blood_REGENERATION_MULTIPLIER.getDoubleValue()*100),
                Math.round(bottle_of_ghoul_blood_HEALTH_LIMIT_ADDITIONAL_PERCENT.getDoubleValue()*100)
        );
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.AMULET;
    }


    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
        if (player == null || player.worldObj == null || player.worldObj.isRemote) return;
        SBSoundMaster.playRandomizedSoundAtPlayer(player, "mob.chicken.plop", 0.5, 1.0);
        SBSoundMaster.playRandomizedSoundAtPlayer(player, "random.drink", 0.25, 0.75);
    }
    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
        if (player == null || player.worldObj == null || player.worldObj.isRemote) return;
        SBSoundMaster.playRandomizedSoundAtPlayer(player, "mob.chicken.plop", 0.5, 0.75);
    }

    public float getHealthLimitAdditional(float original, EntityPlayer player) {
        return MathAdditional.minAbs(original * bottle_of_ghoul_blood_HEALTH_LIMIT_ADDITIONAL_PERCENT.getDoubleValue(), bottle_of_ghoul_blood_HEALTH_LIMIT_ADDITIONAL_CAP.getDoubleValue())
                * (BaubleSlotHelper.hasAmuletOfType(player, SBItems.bottle_of_ghoul_blood) ? 1 : 0);
    }
    public float getRegenerationAdditional(float baseIncrement, EntityPlayer player) {
        return baseIncrement * (float) (bottle_of_ghoul_blood_REGENERATION_MULTIPLIER.getDoubleValue()
                * (BaubleSlotHelper.hasAmuletOfType(player, SBItems.bottle_of_ghoul_blood) ? 1 : 0));
    }
}
