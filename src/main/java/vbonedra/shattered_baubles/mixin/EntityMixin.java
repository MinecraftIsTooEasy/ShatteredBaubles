package vbonedra.shattered_baubles.mixin;

import baubles.api.BaubleSlotHelper;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import vbonedra.shattered_baubles.SBItems;
import vbonedra.shattered_baubles.items.*;

@Mixin(Entity.class)
public abstract class EntityMixin {
    // Lifebuoy swimming
    @ModifyReturnValue(method = "handleWaterMovement()Z", at = @At("RETURN"))
    private boolean modifyWaterMovement(boolean inWater) {
        if (inWater && (Object) this instanceof EntityPlayer player) {

            if (BaubleSlotHelper.hasBeltOfType(player, SBItems.lifebuoy)) {
                if (player.motionY < 0.1D) {
                    player.motionY += 0.1D;
                }
            }

        }
        return inWater;
    }
}
