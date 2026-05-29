package vbonedra.shattered_baubles.items;

import baubles.api.BaubleSlotHelper;
import baubles.api.BaubleType;
import net.minecraft.EntityLivingBase;
import net.minecraft.EntityPlayer;
import net.minecraft.ItemStack;
import net.minecraft.Material;
import vbonedra.shattered_baubles.SBItems;
import vbonedra.shattered_baubles.SBItem;
import vbonedra.shattered_baubles.util.IPlayerNBT;

import static vbonedra.shattered_baubles.util.ConfigShatteredBaubles.*;

public class RingOfPride extends SBItem {
    public RingOfPride(int id) {
        super(id, Material.gold, "ring_of_pride");
    }
    public String formatTextWithConfigValues(String text) {
        return text.formatted(
                Math.round(ring_of_pride_EXPERIENCE_ADDITIONAL_PERCENT.getDoubleValue()*100),
                ring_of_pride_EXPERIENCE_PUNISHMENT_MULTIPLIER.getDoubleValue()
                );
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.RING;
    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
        if (player == null) return;
        if (!player.worldObj.isRemote) {
            player.worldObj.playSoundAtEntity(player, "random.orb", 0.5F, 1.5F);
        }
    }

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
        if (player == null) return;
        if (!player.worldObj.isRemote) {
            player.worldObj.playSoundAtEntity(player, "random.orb", 0.5F, 0.5F);

            if (player instanceof EntityPlayer) {
                IPlayerNBT pridePlayer = (IPlayerNBT) player;
                int sharedExp = pridePlayer.shatteredBaubles$getRingOfPrideSharedExperience();
                pridePlayer.shatteredBaubles$setRingOfPrideSharedExperience(0);
                if (sharedExp >= 0) {
                    ((EntityPlayer) player).addExperience(-sharedExp);
                }
            }
        }
    }

    public int getAdditionalExperience(int xp, EntityPlayer player) {
        xp = (int) (xp * ring_of_pride_EXPERIENCE_ADDITIONAL_PERCENT.getDoubleValue() * BaubleSlotHelper.countRingsOfType(player, SBItems.ring_of_pride));
        IPlayerNBT pridePlayer = (IPlayerNBT) player;
        pridePlayer.shatteredBaubles$addRingOfPrideSharedExperience((int) (xp * ring_of_pride_EXPERIENCE_PUNISHMENT_MULTIPLIER.getDoubleValue()));
        return xp;
    }
}
