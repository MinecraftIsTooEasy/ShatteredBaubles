package vbonedra.shattered_baubles.mixin.RingOfPride;


import net.minecraft.EntityPlayer;
import net.minecraft.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.shattered_baubles.util.IPlayerNBT;


@Mixin(EntityPlayer.class)
public abstract class PlayerNBT implements IPlayerNBT {
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
}