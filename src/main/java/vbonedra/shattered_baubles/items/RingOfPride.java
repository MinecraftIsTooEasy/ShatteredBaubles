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
import vbonedra.shattered_baubles.util.SBSoundMaster;

import static vbonedra.shattered_baubles.event.SBSounds.EQUIP_LEATHER;
import static vbonedra.shattered_baubles.SBConfig.*;

public class RingOfPride extends SBItem {
    public RingOfPride(int id) {
        super(id, Material.mithril, "ring_of_pride", BaubleType.RING, "ring_gem/mithril/emerald");
    }
    public String formatDescriptionWithConfigValues(String text) {
        return text.formatted(
                Math.round((ring_of_pride_EXPERIENCE_MULTIPLIER.getDoubleValue()-1)*100),
                ring_of_pride_EXPERIENCE_PUNISHMENT_MULTIPLIER.getDoubleValue()
                );
    }


    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
        if (player == null || player.worldObj == null || player.worldObj.isRemote) return;
        SBSoundMaster.playRandomizedSoundAtPlayer(player, EQUIP_LEATHER, 0.5, 1.0);
        SBSoundMaster.playRandomizedSoundAtPlayer(player, "random.orb", 0.25, 0.1);
    }
    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
        if (player == null || player.worldObj == null || player.worldObj.isRemote) return;
        SBSoundMaster.playRandomizedSoundAtPlayer(player, EQUIP_LEATHER, 0.5, 0.75);
        SBSoundMaster.playRandomizedSoundAtPlayer(player, "random.orb", 0.25, 0.25);

        if (player instanceof EntityPlayer) {
            IPlayerNBT pridePlayer = (IPlayerNBT) player;
            int sharedExp = pridePlayer.shatteredBaubles$getRingOfPrideSharedExperience();
            pridePlayer.shatteredBaubles$setRingOfPrideSharedExperience(0);
            if (sharedExp >= 0) {
                ((EntityPlayer) player).addExperience(-sharedExp);
            }
        }
    }


    public int getAdditionalExperience(int xp, EntityPlayer player) {
        if (player != null) {
            xp = (int) (xp * (ring_of_pride_EXPERIENCE_MULTIPLIER.getDoubleValue() - 1) * BaubleSlotHelper.countRingsOfType(player, SBItems.ring_of_pride));
            IPlayerNBT pridePlayer = (IPlayerNBT) player;
            pridePlayer.shatteredBaubles$addRingOfPrideSharedExperience((int) (xp * ring_of_pride_EXPERIENCE_MULTIPLIER.getDoubleValue()));
        }
        return xp;
    }
}
