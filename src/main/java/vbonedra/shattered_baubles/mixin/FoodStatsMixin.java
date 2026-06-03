package vbonedra.shattered_baubles.mixin;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.shattered_baubles.items.BottleOfGhoulBlood;
import vbonedra.shattered_baubles.items.SaltCube;
import vbonedra.shattered_baubles.SBItems;

@Mixin(FoodStats.class)
public abstract class FoodStatsMixin {
    @Shadow private int nutrition;
    @Shadow private float heal_progress;
    @Shadow private EntityPlayer player;

    @Inject(method = "onUpdate(Lnet/minecraft/ServerPlayer;)V", at = @At("HEAD"))
    private void sb$onUpdate_head(ServerPlayer par1EntityPlayer, CallbackInfo ci) {
        if (par1EntityPlayer.isGhost() || par1EntityPlayer.isZevimrgvInTournament()) return;
        if (par1EntityPlayer.isDead || par1EntityPlayer.getHealth() <= 0.0F) return;
        if (this.player.isStarving()) return;
        if (!par1EntityPlayer.worldObj.getGameRules().getGameRuleBooleanValue("naturalRegeneration")) return;
        if (!par1EntityPlayer.shouldHeal()) return;

        float baseIncrement = (4.0E-4F + (float)this.nutrition * 2.0E-5F)
                * (par1EntityPlayer.isMalnourished() ? 0.25F : 1.0F)
                * (par1EntityPlayer.inBed() ? 4.0F : 1.0F)
                * EnchantmentHelper.getRegenerationModifier(this.player);

        this.heal_progress += ((BottleOfGhoulBlood) SBItems.bottle_of_ghoul_blood).getRegenerationAdditional(baseIncrement,  this.player);
        this.heal_progress += ((SaltCube) SBItems.salt_cube).getRegenerationAdditional(baseIncrement,  this.player);
    }

    @ModifyVariable(
            method = "addHunger(F)V",
            at = @At("HEAD"),
            argsOnly = true
    )
    private float modifyHungerArgument(float hunger) {
        return hunger
                * ((SaltCube) SBItems.salt_cube).getMetabolismMultiplier(this.player)
                ;
    }
}