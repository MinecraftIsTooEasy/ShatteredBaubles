package vbonedra.shattered_baubles.mixin;

import baubles.api.BaubleSlotHelper;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import vbonedra.shattered_baubles.SBItems;
import vbonedra.shattered_baubles.util.BaubleRenderHelper;

@Mixin(RendererLivingEntity.class)
public abstract class RendererLivingEntityMixin {

    @Redirect(
            method = "renderModel(Lnet/minecraft/EntityLivingBase;FFFFFF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/ModelBase;render(Lnet/minecraft/Entity;FFFFFF)V"
            )
    )
    private void redirectMainModelRender(ModelBase mainModel, Entity entity, float par2, float par3, float par4, float par5, float par6, float par7) {

        if (entity instanceof EntityPlayer player && mainModel instanceof ModelBiped mainBiped) {
            mainModel.render(entity, par2, par3, par4, par5, par6, par7);

            if (BaubleSlotHelper.hasFeetOfType(player, SBItems.feather_boots)) {
                BaubleRenderHelper.renderBaubleLayer(player, mainBiped,
                        "textures/models/armor/feather_boots_layer_1.png", 0.15F,
                        false, false, false, true,
                        par2, par3, par4, par5, par6, par7);
            }
            if (BaubleSlotHelper.hasHeadOfType(player, SBItems.diving_helmet)) {
                BaubleRenderHelper.renderBaubleLayer(player, mainBiped,
                        "textures/models/armor/diving_helmet_layer_1.png", 0.75F,
                        true, false, false, false,
                        par2, par3, par4, par5, par6, par7);
            }
            if (BaubleSlotHelper.hasHandOfType(player, SBItems.leather_glove)) {
                BaubleRenderHelper.renderBaubleLayer(player, mainBiped,
                        "textures/models/armor/leather_glove_layer_1.png", 0.15F,
                        false, false, true, false,
                        par2, par3, par4, par5, par6, par7);
            }
        } else {
            mainModel.render(entity, par2, par3, par4, par5, par6, par7);
        }
    }
}
