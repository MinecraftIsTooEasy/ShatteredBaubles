package vbonedra.shattered_baubles.items;


import baubles.api.BaubleSlotHelper;
import baubles.api.BaubleType;
import net.minecraft.*;
import vbonedra.shattered_baubles.SBItem;
import vbonedra.shattered_baubles.SBItems;
import vbonedra.shattered_baubles.util.SBSoundMaster;

import static vbonedra.shattered_baubles.SBConfig.*;
import static vbonedra.shattered_baubles.event.SBSounds.EQUIP_IRON;
import static vbonedra.shattered_baubles.event.SBSounds.EQUIP_LEATHER;


public class DivingHelmet extends SBItem {
    public DivingHelmet(int id) {
        super(id, Material.leather, "diving_helmet", BaubleType.HEAD);
    }
    public String formatDescriptionWithConfigValues(String text) {
        return text.formatted(
                StatCollector.translateToLocal("enchantment.level." + diving_helmet_RESPIRATION_ADDITIONAL_VALUE.getIntegerValue()),
                Math.round(diving_helmet_UNEQUIP_DAMAGE_PERCENT.getDoubleValue()*100)
        );
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
        if (player.isInWater()) player.attackEntityFrom(new Damage(DamageSource.generic, (float) (player.getMaxHealth()*diving_helmet_UNEQUIP_DAMAGE_PERCENT.getDoubleValue())));
    }


    public int getRespirationModifierAdditional(EntityPlayer player) {
        return BaubleSlotHelper.hasHeadOfType(player, SBItems.diving_helmet) ? diving_helmet_RESPIRATION_ADDITIONAL_VALUE.getIntegerValue() : 0;
    }

}
