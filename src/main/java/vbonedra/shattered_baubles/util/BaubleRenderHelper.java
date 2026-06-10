package vbonedra.shattered_baubles.util;

import net.minecraft.Minecraft;
import net.minecraft.ModelBiped;
import net.minecraft.ResourceLocation;
import net.minecraft.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

public class BaubleRenderHelper {

    private static final Map<Float, ModelBiped> MODEL_CACHE = new HashMap<>();

    private static ModelBiped getOrCreateModel(float scale) {
        return MODEL_CACHE.computeIfAbsent(scale, ModelBiped::new);
    }

    public static void renderBaubleLayer(
            EntityPlayer player,
            ModelBiped mainModel,
            String texturePath,
            float scale,
            boolean showHead,
            boolean showBody,
            boolean showArms,
            boolean showLegs,
            float limbSwing,
            float limbSwingAmount,
            float ageInTicks,
            float netHeadYaw,
            float headPitch,
            float renderScale // 1 pixel = 0.0625F
    ) {
        ResourceLocation texture = new ResourceLocation("shattered_baubles", texturePath);
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

        ModelBiped model = getOrCreateModel(scale);

        model.isSneak = mainModel.isSneak;
        model.isRiding = mainModel.isRiding;
        model.isChild = mainModel.isChild;
        model.onGround = mainModel.onGround;

        model.heldItemRight = mainModel.heldItemRight;
        model.heldItemLeft = mainModel.heldItemLeft;
        model.aimedBow = mainModel.aimedBow;

        model.bipedHead.showModel = showHead;
        model.bipedHeadwear.showModel = showHead;
        model.bipedBody.showModel = showBody;
        model.bipedRightArm.showModel = showArms;
        model.bipedLeftArm.showModel = showArms;
        model.bipedRightLeg.showModel = showLegs;
        model.bipedLeftLeg.showModel = showLegs;

        GL11.glPushMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        model.render(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, renderScale);

        GL11.glPopMatrix();
    }
}
