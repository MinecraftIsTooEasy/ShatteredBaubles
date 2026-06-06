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

import static vbonedra.shattered_baubles.event.SBSounds.EQUIP_IRON;
import static vbonedra.shattered_baubles.SBConfig.*;

public class Flippers extends SBItem {
    public Flippers(int id) {
        super(id, Material.leather, "flippers", BaubleType.FEET);
    }


    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
        if (player == null || player.worldObj == null || player.worldObj.isRemote) return;
        SBSoundMaster.playRandomizedSoundAtPlayer(player, EQUIP_IRON, 0.5, 1.0);
    }
    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
        if (player == null || player.worldObj == null || player.worldObj.isRemote) return;
        SBSoundMaster.playRandomizedSoundAtPlayer(player, EQUIP_IRON, 0.5, 0.75);
    }
    // TODO: finish item
//    public float getMovementSpeedAdditionalPercent(EntityPlayer player) {
//        return (float) (flippers_MOVEMENT_SPEED_MULTIPLIER.getDoubleValue() * (BaubleSlotHelper.hasFeetOfType(player, SBItems.flippers) ? 1 : 0));
//    }
//    public float getSwimmingSpeedAdditionalPercent(EntityPlayer player) {
//        return (float) (flippers_SWIMMING_SPEED_MULRIPLIER.getDoubleValue() * (BaubleSlotHelper.hasFeetOfType(player, SBItems.flippers) ? 1 : 0));
//    }
}
