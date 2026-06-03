package vbonedra.shattered_baubles.items;


import baubles.api.BaubleSlotHelper;
import baubles.api.BaubleType;
import net.minecraft.EntityLivingBase;
import net.minecraft.EntityPlayer;
import net.minecraft.ItemStack;
import net.minecraft.Material;
import vbonedra.shattered_baubles.SBItems;
import vbonedra.shattered_baubles.SBItem;
import vbonedra.shattered_baubles.util.MathAdditional;
import vbonedra.shattered_baubles.util.SBSoundMaster;

import static vbonedra.shattered_baubles.event.SBSounds.*;
import static vbonedra.shattered_baubles.SBConfig.*;


public class AncientGauntlet extends SBItem {
    public AncientGauntlet(int id) {
        super(id, Material.ancient_metal, "ancient_gauntlet");
    }
    public String formatDescriptionWithConfigValues(String text) {
        return text.formatted(
                Math.round(ancient_gauntlet_DAMAGE_ADDITIONAL_PERCENT.getDoubleValue()*100),
                Math.round(ancient_gauntlet_DAMAGE_ADDITIONAL_CAP.getDoubleValue()),
                Math.round(ancient_gauntlet_MINING_SPEED_ADDITIONAL_PERCENT.getDoubleValue()*100),
                Math.round(ancient_gauntlet_MINING_SPEED_ADDITIONAL_CAP.getDoubleValue())
        );
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.HAND;
    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
        if (player == null || player.worldObj == null || player.worldObj.isRemote) return;
        SBSoundMaster.playRandomizedSoundAtPlayer(player, EQUIP_IRON, 0.5, 1.0);
        SBSoundMaster.playRandomizedSoundAtPlayer(player, EQUIP_CHAIN, 0.5, 1.0);
    }
    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
        if (player == null || player.worldObj == null || player.worldObj.isRemote) return;
        SBSoundMaster.playRandomizedSoundAtPlayer(player, EQUIP_IRON, 0.5, 0.75);
        SBSoundMaster.playRandomizedSoundAtPlayer(player, EQUIP_CHAIN, 0.5, 0.75);
    }


    public float getDamageAdditional(float original, EntityPlayer player) {
        return MathAdditional.minAbs(original * ancient_gauntlet_DAMAGE_ADDITIONAL_PERCENT.getDoubleValue(), ancient_gauntlet_DAMAGE_ADDITIONAL_CAP.getDoubleValue())
                * BaubleSlotHelper.countHandsOfType(player, SBItems.ancient_gauntlet);
    }
    public float getMiningSpeedAdditional(float original, EntityPlayer player) {
        return MathAdditional.minAbs(original * ancient_gauntlet_MINING_SPEED_ADDITIONAL_PERCENT.getDoubleValue(), ancient_gauntlet_MINING_SPEED_ADDITIONAL_CAP.getDoubleValue())
                * BaubleSlotHelper.countHandsOfType(player, SBItems.ancient_gauntlet);
    }

}
