package vbonedra.shattered_baubles.items;

import baubles.api.BaubleSlotHelper;
import baubles.api.BaubleType;
import net.minecraft.*;
import vbonedra.shattered_baubles.SBItem;
import vbonedra.shattered_baubles.SBItems;
import vbonedra.shattered_baubles.util.IPlayerNBT;
import vbonedra.shattered_baubles.util.MathAdditional;
import vbonedra.shattered_baubles.util.SBSoundMaster;

import static vbonedra.shattered_baubles.SBConfig.*;
import static vbonedra.shattered_baubles.SBConfig.bottle_of_ghoul_blood_HEALTH_LIMIT_ADDITIONAL_CAP;
import static vbonedra.shattered_baubles.event.SBSounds.EQUIP_IRON;
import static vbonedra.shattered_baubles.event.SBSounds.EQUIP_LEATHER;

public class BraceletOfMight extends SBItem {
    public BraceletOfMight(int id) {
        super(id, Material.gold, "bracelet_of_might", BaubleType.BRACELET);
    }
    public String formatDescriptionWithConfigValues(String text) {
        return text.formatted(
                Math.round((bracelet_of_might_HEALTH_LIMIT_MULTIPLIER.getDoubleValue()-1)*100),
                Math.round((bracelet_of_might_EQUIP_DAMAGE_PERCENT.getDoubleValue())*100)
                );
    }


    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
        if (player == null || player.worldObj == null || player.worldObj.isRemote) return;
        SBSoundMaster.playRandomizedSoundAtPlayer(player, EQUIP_IRON, 0.5, 1.0);
        player.attackEntityFrom(new Damage(DamageSource.generic, (float) (player.getMaxHealth()*bracelet_of_might_EQUIP_DAMAGE_PERCENT.getDoubleValue())));
    }
    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
        if (player == null || player.worldObj == null || player.worldObj.isRemote) return;
        SBSoundMaster.playRandomizedSoundAtPlayer(player, EQUIP_IRON, 0.5, 0.75);
    }


    public float getHealthLimitAdditional(float original, EntityPlayer player) {
        return (float) (original * (bracelet_of_might_HEALTH_LIMIT_MULTIPLIER.getDoubleValue()-1) * BaubleSlotHelper.countBraceletsOfType(player, SBItems.bracelet_of_might));
    }
}
