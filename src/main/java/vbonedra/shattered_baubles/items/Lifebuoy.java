package vbonedra.shattered_baubles.items;


import baubles.api.BaubleType;
import net.minecraft.EntityLivingBase;
import net.minecraft.ItemStack;
import net.minecraft.Material;
import vbonedra.shattered_baubles.SBItem;

public class Lifebuoy extends SBItem {
    public Lifebuoy(int id) {
        super(id, Material.leather, "lifebuoy", BaubleType.BELT);
    }


    // TODO: can be unequipped if player is bouncing in water, which mustn't be possible
    @Override
    public boolean canUnequip(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return !entityLivingBase.isInWater();
    }
}
