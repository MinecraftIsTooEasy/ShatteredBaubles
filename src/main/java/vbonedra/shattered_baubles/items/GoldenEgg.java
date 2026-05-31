package vbonedra.shattered_baubles.items;

import baubles.api.BaubleType;
import net.minecraft.EntityLivingBase;
import net.minecraft.ItemStack;
import net.minecraft.Material;
import vbonedra.shattered_baubles.SBItem;
import vbonedra.shattered_baubles.util.SBSoundMaster;

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
        if (player == null || player.worldObj == null || player.worldObj.isRemote) return;
        SBSoundMaster.playRandomizedSoundAtPlayer(player, "random.pop", 0.5, 1.0);
    }
    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
        if (player == null || player.worldObj == null || player.worldObj.isRemote) return;
        SBSoundMaster.playRandomizedSoundAtPlayer(player, "random.pop", 0.5, 0.75);
    }
}
