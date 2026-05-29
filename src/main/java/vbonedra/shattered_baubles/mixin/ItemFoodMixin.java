package vbonedra.shattered_baubles.mixin;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.shattered_baubles.util.PlayerEatingTracker;

@Mixin(ItemFood.class)
public class ItemFoodMixin extends Item {
    @Inject(method = "onEaten", at = @At("HEAD"))
    private void beforeEat(ItemStack stack, World world, EntityPlayer player, CallbackInfo ci) {
        PlayerEatingTracker.isEatingFood = true;
    }
    @Inject(method = "onEaten", at = @At("RETURN"))
    private void afterEat(ItemStack stack, World world, EntityPlayer player, CallbackInfo ci) {
        PlayerEatingTracker.isEatingFood = false;
    }
}
