package vbonedra.shattered_baubles.mixin;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import vbonedra.shattered_baubles.SBItems;

import static vbonedra.shattered_baubles.util.ConfigShatteredBaubles.golden_egg_PROBABILITY_ChickenLayEgg;

@Mixin(EntityChicken.class)
public abstract class EntityChickenMixin {
    @Redirect(method = "produceGoods", at = @At(value = "INVOKE", target = "Lnet/minecraft/EntityChicken;dropItem(II)Lnet/minecraft/EntityItem;"))
    private EntityItem redirectEggDrop(EntityChicken chicken, int itemID, int count) {
        if (itemID == Item.egg.itemID && chicken.worldObj.rand.nextFloat() <= golden_egg_PROBABILITY_ChickenLayEgg.getDoubleValue()) {
            return chicken.dropItem(SBItems.golden_egg.itemID, count);
        }
        return chicken.dropItem(itemID, count);
    }
}
