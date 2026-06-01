package vbonedra.shattered_baubles.mixin;


import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import moddedmite.rustedironcore.api.event.Handlers;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.shattered_baubles.SBItems;
import vbonedra.shattered_baubles.items.*;


@Mixin(EntityPlayer.class)
public abstract class EntityPlayerMixin {
    // PlayerNBT
    @ModifyReturnValue(method = "calcRawMeleeDamageVs(Lnet/minecraft/Entity;ZZ)F", at = @At(value = "RETURN"))
    private float onPlayerRawMeleeDamageModify(float original, @Local(argsOnly = true) Entity target, @Local(argsOnly = true, ordinal = 0) boolean critical, @Local(argsOnly = true, ordinal = 1) boolean suspended_in_liquid) {
        original = Handlers.Combat.onPlayerRawMeleeDamageModify((EntityPlayer) (Object) this, target, critical, suspended_in_liquid, original);
        return original
                + ((LeatherGlove) SBItems.leather_glove).getDamageAdditional(original, (EntityPlayer) (Object) this)
                + ((AncientGauntlet) SBItems.ancient_gauntlet).getDamageAdditional(original, (EntityPlayer) (Object) this)
                ;
    }
    // MiningSpeed
    @ModifyArg(method = "getCurrentPlayerStrVsBlock", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(FF)F"), index = 0)
    private float modifyStrVsBlock(float str_vs_block) {
        str_vs_block = Handlers.Combat.onPlayerStrVsBlockModify((EntityPlayer) (Object) this, str_vs_block);
        return str_vs_block
                + ((LeatherGlove) SBItems.leather_glove).getMiningSpeedAdditional(str_vs_block, (EntityPlayer) (Object) this)
                + ((AncientGauntlet) SBItems.ancient_gauntlet).getMiningSpeedAdditional(str_vs_block, (EntityPlayer) (Object) this)
                ;
    }
    // HealthLimit
    @ModifyReturnValue(method = "getHealthLimit()F", at = @At("RETURN"))
    private float modifyHealthLimit(float original) {
        return original
                + ((BottleOfGhoulBlood) SBItems.bottle_of_ghoul_blood).getHealthLimitAdditional(original, (EntityPlayer) (Object) this)
                ;
    }
    // DamageScale
    @Inject(method = "attackEntityFrom(Lnet/minecraft/Damage;)Lnet/minecraft/EntityDamageResult;", at = @At("HEAD"))
    private void injectDamageScale(Damage damage, CallbackInfoReturnable<EntityDamageResult> cir) {
        if (
                        damage != null &&
                        damage.getAmount() > 0.0F
        ) {
            EntityPlayer player = (EntityPlayer) (Object) this;
            float scale = 1.0F;
            if (damage.getSource() == DamageSource.onFire || damage.getSource() == DamageSource.inFire || damage.getSource() == DamageSource.lava) {
                scale += ((FlowerBoots) SBItems.flower_boots).getFireDamageAdditionalPercent(player, scale);
            }
            if (damage.getSource() == DamageSource.fall) {
                scale += ((ClimbingPick) SBItems.climbing_pick).getFallDamageAdditionalPercent(player, scale);
                scale += ((FeatherBoots) SBItems.feather_boots).getFallDamageAdditionalPercent(player, scale);
            }
            if (scale != 1.0F && scale >= 0) {
                damage.scaleAmount(scale);
            }
        }
    }
    // MovementSpeed
    @ModifyReturnValue(method = "getAIMoveSpeed()F", at = @At("RETURN"))
    private float injectMovementSpeed(float original) {
        return original
                + ((FlowerBoots) SBItems.flower_boots).getMovementSpeedMultiplier((EntityPlayer) (Object) this, original)
                ;
    }

}