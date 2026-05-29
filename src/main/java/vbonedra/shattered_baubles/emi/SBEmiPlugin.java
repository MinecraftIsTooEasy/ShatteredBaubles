package vbonedra.shattered_baubles.emi;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiInfoRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import vbonedra.shattered_baubles.SBItem;
import vbonedra.shattered_baubles.ShatteredBaubles;
import net.minecraft.ItemStack;
import net.minecraft.ResourceLocation;
import net.minecraft.Translator;
import shims.java.net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SBEmiPlugin implements EmiPlugin {
    // TODO: might lead into unwanted formatting? just seen weird thing, not sure that those are the problem
    String descriptionName = "§f§l";
    String descriptionSourceColor = "§7";

    @Override
    public void register(EmiRegistry registry) {
        for (SBItem item : SBItem.allShatteredBaubles) { addShatteredBaubleInfo(registry, item); }
    }

    private void addShatteredBaubleInfo(EmiRegistry registry, SBItem item) {
        String name = item.texture;
        List<EmiIngredient> stacks = Collections.singletonList(EmiStack.of(new ItemStack(item)));
        List<Text> text = new ArrayList<>(Arrays.asList(
                Text.literal(descriptionName + Translator.get("item.%s.name".formatted(name))),
                item.getEmiDescription(),
                Text.literal(descriptionSourceColor + Translator.get("emi.obtain.source"))
        ));
        for (String chestName : new String[]{
                "DesertPyramid",
                "JunglePyramid",
                "Fortress",
                "Mineshaft",
                "StrongholdCorridor",
                "StrongholdCrossing",
                "StrongholdLibrary",
                "SwampHut",
                "BlackSmith",
                "DungeonOverworld",
                "DungeonUnderworld"}
        ) {
            if (item.getChestLoot(chestName)[3] > 0)
                text.add(Text.literal(" " + descriptionSourceColor + Translator.get("emi.obtain.chests."+chestName).formatted((float) item.getChestLoot(chestName)[4] / 100)));
        }
//        if (text.size() > 3) {
//            text.add(3, Text.literal("  "+descriptionSourceColor + Translator.get("emi.obtain.chests")));
//        }
        registry.addRecipe(new EmiInfoRecipe(
            stacks,
            text,
                new ResourceLocation(ShatteredBaubles.NAMESPACE, "info/%s".formatted(name))
        ));
    }

}