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
                Text.literal(item.formatDescriptionWithConfigValues(Translator.get("emi.%s.description".formatted(item.texture))))
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
                text.add(Text.literal(" " +
                        descriptionSourceColor +
                        Translator.get("emi.obtain.chests."+chestName) +
                        Translator.get("emi.obtain.chests.chance").formatted((float) item.getChestLoot(chestName)[4] / 100)
                ));
        }
        // TODO: add optional source info (for example, Chicken laying Golden Egg. would add only if theres additional info, no "emi.item_name.description" in-game)
        if (text.size() > 2) {
            text.add(2, Text.literal(descriptionSourceColor + Translator.get("emi.obtain.source")));
        }
        registry.addRecipe(new EmiInfoRecipe(
            stacks,
            text,
                new ResourceLocation(ShatteredBaubles.MOD_ID, "info/%s".formatted(name))
        ));
    }

}