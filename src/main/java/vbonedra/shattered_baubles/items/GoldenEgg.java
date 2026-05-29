package vbonedra.shattered_baubles.items;

import baubles.api.BaubleType;
import net.minecraft.EntityLivingBase;
import net.minecraft.ItemStack;
import net.minecraft.Material;
import vbonedra.shattered_baubles.SBItem;

public class GoldenEgg extends SBItem {
    public GoldenEgg(int id) {
        super(id, Material.gold, "golden_egg");
    }
    // TODO: check iTF compatibility (thirst effect from cooked meat)

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.CHARM;
    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
        if (!player.worldObj.isRemote) {
            player.worldObj.playSoundAtEntity(player, "random.pop", 0.5F, 1.25F);
        }
    }

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
        if (!player.worldObj.isRemote) {
            player.worldObj.playSoundAtEntity(player, "random.pop", 0.5F, 0.75F);
        }
    }
}
