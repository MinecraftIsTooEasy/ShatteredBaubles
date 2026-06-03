package vbonedra.shattered_baubles.mixin;

import baubles.api.BaubleSlotHelper;
import net.minecraft.EntityPlayer;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.*;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.shattered_baubles.SBItems;
import vbonedra.shattered_baubles.items.*;
import vbonedra.shattered_baubles.util.PlayerEatingTracker;

import java.util.List;

import static vbonedra.shattered_baubles.util.SBSoundMaster.playRandomizedSoundAtPlayer;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin extends Entity {
    public EntityLivingBaseMixin(World par1World) {
        super(par1World);
    }
    @Shadow public abstract int getExperienceValue();
    @Shadow protected EntityPlayer attackingPlayer;

    // Ring of Pride xp bonus
    @Redirect(method = "onDeathUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/EntityLivingBase;getExperienceValue()I"))
    public int modifiedXpDrop(EntityLivingBase entityLivingBase) {
        int xp = this.getExperienceValue();
        return xp
                + ((HunterHat) SBItems.hunter_hat).getAdditionalExperience(xp, attackingPlayer)
                + ((RingOfPride) SBItems.ring_of_pride).getAdditionalExperience(xp, attackingPlayer)
                ;
    }

    // Golden Egg no food effects
    @Inject(method = "addPotionEffect", at = @At("HEAD"), cancellable = true)
    private void onAddPotionEffect(PotionEffect effect, CallbackInfo ci) {
        if (PlayerEatingTracker.isEatingFood) {
            if (BaubleSlotHelper.hasCharmOfType((EntityPlayer) (Object) this, SBItems.golden_egg)) {
                ci.cancel();
            }
        }
    }

    // Flower Boots and Climbing Pick wall climbing
    @Inject(method = "moveEntityWithHeading(FF)V", at = @At("TAIL"))
    private void onMoveEntityWithHeadingTail(float par1, float par2, CallbackInfo ci) {
        if (!((Object) this instanceof EntityPlayer)) return;

        EntityPlayer player = (EntityPlayer) (Object) this;
        if (player.capabilities.isFlying) return;
        // TODO: maybe replace getCollidingBoundingBoxes with different checks
        // TODO: maybe check for player wearing baubles before blocks checks
        int playerX = MathHelper.floor_double(player.posX);
        int playerZ = MathHelper.floor_double(player.posZ);
        AxisAlignedBB checkAABB = player.boundingBox.expand(0.05D, 0.0D, 0.05D);
        List boundingBoxes = player.worldObj.getCollidingBoundingBoxes(player, checkAABB);

        if (boundingBoxes != null && !boundingBoxes.isEmpty()) {
            int minX = MathHelper.floor_double(checkAABB.minX);
            int maxX = MathHelper.floor_double(checkAABB.maxX);
            int minY = MathHelper.floor_double(checkAABB.minY);
            int maxY = MathHelper.floor_double(checkAABB.maxY);
            int minZ = MathHelper.floor_double(checkAABB.minZ);
            int maxZ = MathHelper.floor_double(checkAABB.maxZ);

            searchBlock:
            for (int x = minX; x <= maxX; ++x) {
                for (int y = minY; y <= maxY; ++y) {
                    for (int z = minZ; z <= maxZ; ++z) {
                        if (x == playerX || z == playerZ) {
                            Block block = player.worldObj.getBlock(x, y, z);
                            if (block != null) {
                                Material material = block.blockMaterial;
                                if (((ClimbingPick) SBItems.climbing_pick).climbWall(player, material) || ((FlowerBoots) SBItems.flower_boots).climbWall(player, material)) {
                                    playRandomizedSoundAtPlayer(
                                            player,
                                            block.stepSound.getBreakSound(),
                                            0.75, 0.1,
                                            0.75, 0.1
                                    );
                                    break searchBlock;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // how far mobs can see player
    @ModifyVariable(
            method = "canEntityBeSeenFrom(DDDDZ)Z",
            at = @At("HEAD"),
            ordinal = 3,
            argsOnly = true,
            remap = false
    )
    private double increaseMaxRangeSq(double maxRangeSq) {
        if ((Object) this instanceof EntityPlayer player) {
            if (BaubleSlotHelper.hasCharmOfType(player, SBItems.salt_cube)) {
                return maxRangeSq
                        * ((FeatherBoots) SBItems.feather_boots).getDetectRangeAdditionalPercent(player)
                        ;
            }
        }
        return maxRangeSq;
    }

}