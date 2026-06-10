package vbonedra.shattered_baubles.mixin;

import baubles.api.BaubleSlotHelper;
import net.minecraft.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.shattered_baubles.SBItems;

@Mixin(RenderPlayer.class)
public abstract class RenderPlayerMixin {

    @Shadow private ModelBiped modelBipedMain;
    @Shadow private ModelBiped modelArmorChestplate;

    @Unique
    private static final ResourceLocation LEATHER_GLOVE_TEXTURE =
            new ResourceLocation("shattered_baubles", "textures/models/armor/leather_glove_layer_1.png");

    @Unique
    private final ModelBiped gloveHUDModel = new ModelBiped(0.15F);

    @Inject(
            method = "renderFirstPersonArm(Lnet/minecraft/EntityPlayer;)V",
            at = @At("TAIL")
    )
    private void renderGloveAndArmorOnHUDHand(EntityPlayer player, CallbackInfo ci) {

        GL11.glPushMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        GL11.glScalef(1.001F, 1.001F, 1.001F);

        if (BaubleSlotHelper.hasHandOfType(player, SBItems.leather_glove)) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(LEATHER_GLOVE_TEXTURE);
            syncArmAngles(this.modelBipedMain, this.gloveHUDModel);
            this.gloveHUDModel.bipedRightArm.render(0.0625F);
        }

        // maybe no need for it
        ItemStack chestplateStack = player.inventory.armorItemInSlot(2);
        if (chestplateStack == null || !(chestplateStack.getItem() instanceof ItemArmor)) {
            chestplateStack = player.inventory.armorItemInSlot(1);
        }

        if (chestplateStack != null && chestplateStack.getItem() instanceof ItemArmor) {
            ItemArmor armorItem = (ItemArmor) chestplateStack.getItem();

            ResourceLocation armorTexture = RenderBiped.func_110857_a(armorItem, 0);

            if (armorTexture != null) {
                Minecraft.getMinecraft().getTextureManager().bindTexture(armorTexture);

                syncArmAngles(this.modelBipedMain, this.modelArmorChestplate);

                if (armorItem.getArmorMaterial() == Material.leather) {
                    int color = armorItem.getColor(chestplateStack);
                    float r = (float)(color >> 16 & 255) / 255.0F;
                    float g = (float)(color >> 8 & 255) / 255.0F;
                    float b = (float)(color & 255) / 255.0F;
                    GL11.glColor3f(r, g, b);
                } else {
                    GL11.glColor3f(1.0F, 1.0F, 1.0F);
                }

                this.modelArmorChestplate.bipedRightArm.showModel = true;
                this.modelArmorChestplate.bipedRightArm.render(0.0625F);
            }
        }

        GL11.glPopMatrix();
    }

    @Unique
    private void syncArmAngles(ModelBiped source, ModelBiped target) {
        target.bipedRightArm.rotateAngleX = source.bipedRightArm.rotateAngleX;
        target.bipedRightArm.rotateAngleY = source.bipedRightArm.rotateAngleY;
        target.bipedRightArm.rotateAngleZ = source.bipedRightArm.rotateAngleZ;

        target.bipedRightArm.rotationPointX = source.bipedRightArm.rotationPointX;
        target.bipedRightArm.rotationPointY = source.bipedRightArm.rotationPointY;
        target.bipedRightArm.rotationPointZ = source.bipedRightArm.rotationPointZ;
    }
}
