package vbonedra.shattered_baubles.mixin;

import net.minecraft.Block;
import net.minecraft.BlockBreakInfo;
import net.minecraft.EntityPlayer;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import vbonedra.shattered_baubles.SBItems;
import vbonedra.shattered_baubles.items.RingOfPride;

@Mixin(Block.class)
public abstract class BlockMixin {
    @Shadow protected abstract void dropXpOnBlockBreak(World par1World, int par2, int par3, int par4, int par5);

    // Ring of Pride xp bonus
    @Inject(method = "dropBlockAsEntityItem(Lnet/minecraft/BlockBreakInfo;IIIF)I",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/Block;dropXpOnBlockBreak(Lnet/minecraft/World;IIII)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void moreXpDrop(BlockBreakInfo info, int id_dropped, int subtype, int quantity, float chance, CallbackInfoReturnable<Integer> cir, int damage, int num_drops, int xp_reward_per_unit) {
        if (info.responsible_entity instanceof EntityPlayer entityPlayer) {
            this.dropXpOnBlockBreak(
                    info.world,
                    info.x,
                    info.y,
                    info.z,
                    ((RingOfPride) SBItems.ring_of_pride).getAdditionalExperience(xp_reward_per_unit * num_drops, entityPlayer)
            );
        }
    }
}