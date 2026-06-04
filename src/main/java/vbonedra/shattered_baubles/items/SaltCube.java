package vbonedra.shattered_baubles.items;

import baubles.api.BaubleSlotHelper;
import baubles.api.BaubleType;
import net.minecraft.*;
import vbonedra.shattered_baubles.SBItem;
import vbonedra.shattered_baubles.SBItems;
import vbonedra.shattered_baubles.util.SBSoundMaster;

import static vbonedra.shattered_baubles.event.SBSounds.EQUIP_GENERIC;
import static vbonedra.shattered_baubles.SBConfig.*;

public class SaltCube extends SBItem {
    public SaltCube(int id) {
        super(id, Material.sugar, "salt_cube");
    }
    public String formatDescriptionWithConfigValues(String text) {
        return text.formatted(
                salt_cube_METABOLISM_MULTIPLIER.getDoubleValue()
        );
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.CHARM;
    }


    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
        if (player == null || player.worldObj == null || player.worldObj.isRemote) return;
        SBSoundMaster.playRandomizedSoundAtPlayer(player, EQUIP_GENERIC, 0.5, 1.0);
        SBSoundMaster.playRandomizedSoundAtPlayer(player, "dig.gravel", 0.25, 1.0);
    }
    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
        if (player == null || player.worldObj == null || player.worldObj.isRemote) return;
        SBSoundMaster.playRandomizedSoundAtPlayer(player, EQUIP_GENERIC, 0.5, 0.75);
        SBSoundMaster.playRandomizedSoundAtPlayer(player, "dig.gravel", 0.25, 0.75);
    }


    public float getMetabolismMultiplier(EntityPlayer player) {
        return (float) ((BaubleSlotHelper.hasCharmOfType(player, SBItems.salt_cube)) ? salt_cube_METABOLISM_MULTIPLIER.getDoubleValue() : 1);
    }
    public float getRegenerationAdditionalPercent(EntityPlayer player) {
        return getMetabolismMultiplier(player) - 1;
    }

}
