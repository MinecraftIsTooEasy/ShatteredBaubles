package vbonedra.shattered_baubles.mixin;


import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import vbonedra.shattered_baubles.SBItems;
import vbonedra.shattered_baubles.items.*;


@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {
    // Looting
    @ModifyReturnValue(method = "getLootingModifier", at = @At("RETURN"))
    private static int getLootingModifier(int original, @Local(argsOnly = true) EntityLivingBase par0EntityLivingBase) {
        if (par0EntityLivingBase instanceof EntityPlayer player) return original
                +  ((HunterHat) SBItems.hunter_hat).getLootingModifierAdditional(player)
                ;
        return original;
    }
    // Butchering
    @ModifyReturnValue(method = "getButcheringModifier", at = @At("RETURN"))
    private static int getButcheringModifier(int original, @Local(argsOnly = true) EntityLivingBase par0EntityLivingBase) {
        if (par0EntityLivingBase instanceof EntityPlayer player) return original
                +  ((HunterHat) SBItems.hunter_hat).getButcheringModifierAdditional(player)
                ;
        return original;
    }
    // Respiration
    @ModifyReturnValue(method = "getRespiration", at = @At("RETURN"))
    private static int getRespiration(int original, @Local(argsOnly = true) EntityLivingBase par0EntityLivingBase) {
        if (par0EntityLivingBase instanceof EntityPlayer player) return original
                +  ((DivingHelmet) SBItems.diving_helmet).getRespirationModifierAdditional(player)
                ;
        return original;
    }
}
/*
getKnockbackModifier        Knockback
getFireAspectModifier       FireAspect
getEfficiencyModifier       Efficiency
getSilkTouchModifier        SilkTouch
getFortuneModifier          Fortune
getLootingModifier          Looting
getAquaAffinityModifier     AquaAffinity
getStunModifier             Stun
getFishingFortuneModifier   FishingFortune
getFertilityModifier        Fertility
getTreeFellingModifier      TreeFelling
getSpeedModifier            Speed
getRegenerationModifier     Regeneration
getFreeActionModifier       FreeAction
getButcheringModifier       Butchering
getEnduranceModifier        Endurance
*/