package vbonedra.shattered_baubles.mixin;

import baubles.api.BaubleSlotHelper;
import net.minecraft.Entity;
import net.minecraft.EntityPlayer;
import net.minecraft.EntitySenses;
import net.minecraft.EntityLiving;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.shattered_baubles.SBItems;
import vbonedra.shattered_baubles.items.InvisibilityCloak;

@Mixin(EntitySenses.class)
public abstract class EntitySensesMixin {

    @Shadow EntityLiving entityObj;

    // how far mobs can see
    @Inject(
            method = "canSee(Lnet/minecraft/Entity;Z)Z",
            at = @At("RETURN"),
            cancellable = true
    )
    private void canSee(Entity target, boolean ignoreLeaves, CallbackInfoReturnable<Boolean> cir) {
        if (target instanceof EntityPlayer player) {

            double finalVisionRange = (double) ((EntityLivingAccessor) this.entityObj).invokeGetMaxTargettingRange()
                    * ((InvisibilityCloak) SBItems.invisibility_cloak).getDetectRangeMultiplier(player)
                    ;

            if (this.entityObj.getDistanceSqToEntity(player) <= finalVisionRange * finalVisionRange) {
                if (BaubleSlotHelper.hasFeetOfType(player, SBItems.feather_boots)) {
                    cir.setReturnValue(true);
                }
            } else {
                cir.setReturnValue(false);
            }
        }
    }
}
