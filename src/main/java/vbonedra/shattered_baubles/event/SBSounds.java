package vbonedra.shattered_baubles.event;


import net.minecraft.ResourceLocation;
import net.xiaoyu233.fml.reload.event.SoundsRegisterEvent;

import vbonedra.shattered_baubles.ShatteredBaubles;


public class SBSounds {
    public static final ResourceLocation EQUIP_GENERIC = new ResourceLocation(ShatteredBaubles.RESOURCE_ID + "item.interaction.equip_generic");
    public static final ResourceLocation EQUIP_LEATHER = new ResourceLocation(ShatteredBaubles.RESOURCE_ID + "item.interaction.equip_leather");
    public static final ResourceLocation EQUIP_CHAIN = new ResourceLocation(ShatteredBaubles.RESOURCE_ID + "item.interaction.equip_chain");
    public static final ResourceLocation EQUIP_IRON = new ResourceLocation(ShatteredBaubles.RESOURCE_ID + "item.interaction.equip_iron");
    public static final ResourceLocation EQUIP_COPPER = new ResourceLocation(ShatteredBaubles.RESOURCE_ID + "item.interaction.equip_copper");

    public static void register(SoundsRegisterEvent event) {
        event.registerSound(EQUIP_GENERIC, 6);
        event.registerSound(EQUIP_LEATHER, 6);
        event.registerSound(EQUIP_CHAIN, 6);
        event.registerSound(EQUIP_IRON, 6);
        event.registerSound(EQUIP_COPPER, 6);
    }
}