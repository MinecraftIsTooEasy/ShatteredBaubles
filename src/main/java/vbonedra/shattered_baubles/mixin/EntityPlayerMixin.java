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
    // MeleeDamage
    @ModifyReturnValue(method = "calcRawMeleeDamageVs(Lnet/minecraft/Entity;ZZ)F", at = @At(value = "RETURN"))
    private float onPlayerRawMeleeDamageModify(float original, @Local(argsOnly = true) Entity target, @Local(argsOnly = true, ordinal = 0) boolean critical, @Local(argsOnly = true, ordinal = 1) boolean suspended_in_liquid) {
        if ((Object) this instanceof EntityPlayer player) {
            original = Handlers.Combat.onPlayerRawMeleeDamageModify(player, target, critical, suspended_in_liquid, original);
            System.out.println(original+" "+original
                    + ((LeatherGlove) SBItems.leather_glove).getDamageAdditional(original, player)
                    + ((AncientGauntlet) SBItems.ancient_gauntlet).getDamageAdditional(original, player));
            return original
                    + ((LeatherGlove) SBItems.leather_glove).getDamageAdditional(original, player)
                    + ((AncientGauntlet) SBItems.ancient_gauntlet).getDamageAdditional(original, player)
                    ;
        }
        return original;
    }
    // MiningSpeed
    @ModifyArg(method = "getCurrentPlayerStrVsBlock", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(FF)F"), index = 0)
    private float modifyStrVsBlock(float str_vs_block) {
        if ((Object) this instanceof EntityPlayer player) {
            str_vs_block = Handlers.Combat.onPlayerStrVsBlockModify(player, str_vs_block);
            return str_vs_block
                    + ((LeatherGlove) SBItems.leather_glove).getMiningSpeedAdditional(str_vs_block, player)
                    + ((AncientGauntlet) SBItems.ancient_gauntlet).getMiningSpeedAdditional(str_vs_block, player)
                    ;
        }
        return str_vs_block;
    }
    // HealthLimit
    @ModifyReturnValue(method = "getHealthLimit()F", at = @At("RETURN"))
    private float modifyHealthLimit(float original) {
        if ((Object) this instanceof EntityPlayer player) {
            return original
                    + ((BottleOfGhoulBlood) SBItems.bottle_of_ghoul_blood).getHealthLimitAdditional(original, player)
                    ;
        }
        return original;
    }
    // DamageScale
    @Inject(method = "attackEntityFrom(Lnet/minecraft/Damage;)Lnet/minecraft/EntityDamageResult;", at = @At("HEAD"))
    private void injectDamageScale(Damage damage, CallbackInfoReturnable<EntityDamageResult> cir) {
        if (damage != null && damage.getAmount() > 0.0F) {
            if ((Object) this instanceof EntityPlayer player) {
                float scale = 1.0F;
                if (damage.getSource() == DamageSource.onFire || damage.getSource() == DamageSource.inFire || damage.getSource() == DamageSource.lava) {
                    scale += ((FlowerBoots) SBItems.flower_boots).getFireDamageAdditionalPercent(player);
                }
                if (damage.getSource() == DamageSource.fall) {
                    scale += ((ClimbingPick) SBItems.climbing_pick).getFallDamageAdditionalPercent(player);
                    scale += ((FeatherBoots) SBItems.feather_boots).getFallDamageAdditionalPercent(player);
                }
                if (scale >= 0 && scale != 1.0F) {
                    damage.scaleAmount(scale);
                }
            }
        }
    }
    // MovementSpeed
    @ModifyReturnValue(method = "getAIMoveSpeed()F", at = @At("RETURN"))
    private float injectMovementSpeed(float original) {
        if ((Object) this instanceof EntityPlayer player) {
            return Math.max(0, original * Math.max(0, 1
                            + ((FlowerBoots) SBItems.flower_boots).getMovementSpeedAdditionalPercent(player)
//                            + ((Flippers) SBItems.flippers).getMovementSpeedAdditionalPercent(player)
                    )
            );
        }
        return original;
    }

}