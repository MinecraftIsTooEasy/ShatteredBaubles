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

public class DriedTentacle extends SBItem {
    public DriedTentacle(int id) {
        super(id, Material.meat, "dried_tentacle", BaubleType.BELT);
    }
    public String formatDescriptionWithConfigValues(String text) {
        return text.formatted(
                StatCollector.translateToLocal("potion.potency." + dried_tentacle_SLOWNESS_LEVEL_VALUE.getIntegerValue()),
                Math.round((float) dried_tentacle_EFFECT_DURATION_VALUE.getIntegerValue() / 20 * 10) / 10
        );
    }


    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
        if (player == null || player.worldObj == null || player.worldObj.isRemote) return;
        SBSoundMaster.playRandomizedSoundAtPlayer(player, EQUIP_LEATHER, 0.5, 1.0);
    }
    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
        if (player == null || player.worldObj == null || player.worldObj.isRemote) return;
        SBSoundMaster.playRandomizedSoundAtPlayer(player, EQUIP_LEATHER, 0.5, 0.75);
    }


    public void applySlownessEffect(EntityPlayer player, EntityLivingBase attackerEntity) {
        if (BaubleSlotHelper.hasBeltOfType(player, SBItems.dried_tentacle) && player.isWet()) {
            attackerEntity.addPotionEffect(new PotionEffect(2, dried_tentacle_EFFECT_DURATION_VALUE.getIntegerValue(), dried_tentacle_SLOWNESS_LEVEL_VALUE.getIntegerValue()-1, false));
        }
    }
}
