package vbonedra.shattered_baubles.mixin;

import baubles.api.BaubleSlotHelper;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.shattered_baubles.SBItems;
import vbonedra.shattered_baubles.items.*;

@Mixin(Entity.class)
public abstract class EntityMixin {

    // Swimming
    @ModifyReturnValue(method = "handleWaterMovement()Z", at = @At("RETURN"))
    private boolean modifyWaterMovement(boolean inWater) {
        if (inWater && (Object) this instanceof EntityPlayer player) {
            if (BaubleSlotHelper.hasBeltOfType(player, SBItems.lifebuoy)) {
                if (player.motionY < 0.1D) {
                    player.motionY += 0.025D;
                    if (!player.worldObj.isRemote) player.entityFX(EnumEntityFX.splash);
                }
            }
            if (BaubleSlotHelper.hasHeadOfType(player, SBItems.diving_helmet)) {
                if (player.motionY > -0.1D) {
                    player.motionY -= 0.025D;
                }
            }
            if (BaubleSlotHelper.hasFeetOfType(player, SBItems.copper_flippers)) {
                if (player.moveForward != 0.0F || player.moveStrafing != 0.0F) {
                    double currentHorizontalSpeed = Math.sqrt(player.motionX * player.motionX + player.motionZ * player.motionZ);

                    if (currentHorizontalSpeed > 0.01D) {
                        double targetSpeed = 0.15 * ((CopperFlippers) SBItems.copper_flippers).getSwimmingSpeedMultiplier(player);

                        if (currentHorizontalSpeed < targetSpeed) {
                            double speedRatio = currentHorizontalSpeed / targetSpeed;
                            double boostFactor = 0.05D * (1.0D - speedRatio);

                            player.motionX += (player.motionX / currentHorizontalSpeed) * boostFactor;
                            player.motionZ += (player.motionZ / currentHorizontalSpeed) * boostFactor;
                        }
                        else if (currentHorizontalSpeed > targetSpeed + 0.1D) {
                            player.motionX = (player.motionX / currentHorizontalSpeed) * targetSpeed;
                            player.motionZ = (player.motionZ / currentHorizontalSpeed) * targetSpeed;
                        }

                    }
                } else if (player.motionX != 0.0D || player.motionZ != 0.0D) {
                    player.motionX *= 0.9D;
                    player.motionZ *= 0.9D;
                }
            }

        }
        return inWater;
    }
    // trigger: player attacks mob only
    @Inject(method = "onMeleeAttacked(Lnet/minecraft/EntityLivingBase;Lnet/minecraft/EntityDamageResult;)V", at = @At("HEAD"))
    public void onMeleeAttacked(EntityLivingBase attacker, EntityDamageResult result, CallbackInfo ci) {
//        System.out.println("TRIGGERED___________________________");
//        if (attacker != null && this.isEntityPlayer()) {
//
//            EntityPlayer player = (EntityPlayer) (Object) this;
//
//            if (!player.worldObj.isRemote) {
//                attacker.addPotionEffect(new PotionEffect(2, 100, 0, false));
//            }
//        }
    }
}
