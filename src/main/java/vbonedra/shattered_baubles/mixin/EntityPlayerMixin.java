package vbonedra.shattered_baubles.mixin;


import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import moddedmite.rustedironcore.api.event.Handlers;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.shattered_baubles.SBItems;
import vbonedra.shattered_baubles.items.*;
import vbonedra.shattered_baubles.util.IPlayerNBT;


@Mixin(EntityPlayer.class)
public abstract class EntityPlayerMixin implements IPlayerNBT {
    // NBT: Ring of Pride mainly
    @Unique
    private int ringOfPrideSharedExperience = 0;

    @Override
    public int shatteredBaubles$getRingOfPrideSharedExperience() {
        return this.ringOfPrideSharedExperience;
    }

    @Override
    public void shatteredBaubles$setRingOfPrideSharedExperience(int value) {
        this.ringOfPrideSharedExperience = value;
    }

    @Override
    public void shatteredBaubles$addRingOfPrideSharedExperience(int value) {
        this.ringOfPrideSharedExperience += value;
    }

    @Inject(method = "writeEntityToNBT", at = @At("RETURN"))
    private void writeRingOfPrideSharedExperience(NBTTagCompound nbt, CallbackInfo ci) {
        if (nbt != null) {
            nbt.setInteger("RingOfPrideSharedExperience", this.ringOfPrideSharedExperience);
        }
    }

    @Inject(method = "readEntityFromNBT", at = @At("RETURN"))
    private void readRingOfPrideSharedExperience(NBTTagCompound nbt, CallbackInfo ci) {
        if (nbt != null && nbt.hasKey("RingOfPrideSharedExperience")) {
            this.ringOfPrideSharedExperience = nbt.getInteger("RingOfPrideSharedExperience");
        }
        else {
            this.ringOfPrideSharedExperience = 0;
        }
    }
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
                    + ((BraceletOfMight) SBItems.bracelet_of_might).getHealthLimitAdditional(original, player)
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
                            + ((CopperFlippers) SBItems.copper_flippers).getMovementSpeedAdditionalPercent(player)
                    )
            );
        }
        return original;
    }
    // on player attack
//    @Inject(method = "attackTargetEntityWithCurrentItem(Lnet/minecraft/Entity;)V", at = @At("HEAD"))
//    private void onAttackTarget(Entity target, CallbackInfo ci) {
//        if ((Object) this instanceof EntityPlayer player) {
//            if (true) {
//                ItemStack item = player.getHeldItemStack();
//                if (item != null) {
//                    item.tryDamageItem(
//                            DamageSource.acid,
//                            50,
//                            player
//                    );
//                }
//            }
//        }
//    }
    // mob attacks player
    @Inject(method = "attackEntityFrom(Lnet/minecraft/Damage;)Lnet/minecraft/EntityDamageResult;", at = @At("RETURN"))
    public void onPlayerAttacked(Damage damage, CallbackInfoReturnable<EntityDamageResult> cir) {
        EntityDamageResult result = cir.getReturnValue();
        if (result == null) return;

        EntityPlayer player = (EntityPlayer) (Object) this;

        if (!player.worldObj.isRemote) {
            Entity attackerEntity = damage.getSource().getResponsibleEntity();
            if (attackerEntity instanceof EntityLivingBase) {
                ((DriedTentacle) SBItems.dried_tentacle).applySlownessEffect(player, (EntityLivingBase) attackerEntity);
            }
        }
    }
}