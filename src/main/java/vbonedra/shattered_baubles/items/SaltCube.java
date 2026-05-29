package vbonedra.shattered_baubles.items;

import baubles.api.BaubleSlotHelper;
import baubles.api.BaubleType;
import net.minecraft.EntityLivingBase;
import net.minecraft.EntityPlayer;
import net.minecraft.ItemStack;
import net.minecraft.Material;
import vbonedra.shattered_baubles.SBItem;
import vbonedra.shattered_baubles.SBItems;

import static vbonedra.shattered_baubles.util.ConfigShatteredBaubles.*;

public class SaltCube extends SBItem {
    public SaltCube(int id) {
        super(id, Material.sugar, "salt_cube");
    }
    public String formatTextWithConfigValues(String text) {
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
        if (!player.worldObj.isRemote) {
            player.worldObj.playSoundAtEntity(player, "dig.gravel", 0.5F, 1.75F);
        }
    }

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
        if (!player.worldObj.isRemote) {
            player.worldObj.playSoundAtEntity(player, "dig.gravel", 0.5F, 1.25F);
        }
    }


    public float getRegenerationAdditional(float baseIncrement, EntityPlayer player) {
        return baseIncrement * (float) (salt_cube_METABOLISM_MULTIPLIER.getDoubleValue()
                * (BaubleSlotHelper.hasCharmOfType(player, SBItems.salt_cube) ? 1 : 0));
    }
}
