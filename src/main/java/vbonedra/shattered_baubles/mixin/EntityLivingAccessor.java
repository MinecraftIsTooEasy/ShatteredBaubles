package vbonedra.shattered_baubles.mixin;

import net.minecraft.EntityLiving;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntityLiving.class)
public interface EntityLivingAccessor {
    @Invoker("getMaxTargettingRange")
    float invokeGetMaxTargettingRange();
}
