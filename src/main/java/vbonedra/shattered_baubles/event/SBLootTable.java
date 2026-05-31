package vbonedra.shattered_baubles.event;

import moddedmite.rustedironcore.api.event.listener.ILootTableRegisterListener;
import net.minecraft.Item;
import net.minecraft.ItemStack;
import net.minecraft.WeightedRandomChestContent;
import vbonedra.shattered_baubles.SBItem;

import java.util.List;

public class SBLootTable implements ILootTableRegisterListener {

    void onRegisterOriginalAdd(List<WeightedRandomChestContent> original, String registerName, Item item) {
        Integer[] lootData = ((SBItem) item).getChestLoot(registerName);
        original.add(new WeightedRandomChestContent(new ItemStack(item,lootData[0]),lootData[1],lootData[2],lootData[3]));
    }

    void onRegisterOriginalAddForAll(List<WeightedRandomChestContent> original, String registerName) {
        for (SBItem item : SBItem.allShatteredBaubles) { onRegisterOriginalAdd(original, registerName, item); }
    }


    @Override public void onDesertPyramidRegister(List<WeightedRandomChestContent> original) { onRegisterOriginalAddForAll(original, "DesertPyramid"); }
    @Override public void onJunglePyramidRegister(List<WeightedRandomChestContent> original) { onRegisterOriginalAddForAll(original, "JunglePyramid"); }
    @Override public void onFortressRegister(List<WeightedRandomChestContent> original) { onRegisterOriginalAddForAll(original, "Fortress"); }
    @Override public void onMineshaftRegister(List<WeightedRandomChestContent> original) { onRegisterOriginalAddForAll(original, "Mineshaft"); }
    @Override public void onStrongholdCorridorRegister(List<WeightedRandomChestContent> original) { onRegisterOriginalAddForAll(original, "StrongholdCorridor"); }
    @Override public void onStrongholdCrossingRegister(List<WeightedRandomChestContent> original) { onRegisterOriginalAddForAll(original, "StrongholdCrossing"); }
    @Override public void onStrongholdLibraryRegister(List<WeightedRandomChestContent> original) { onRegisterOriginalAddForAll(original, "StrongholdLibrary"); }
    @Override public void onSwampHutRegister(List<WeightedRandomChestContent> original) { onRegisterOriginalAddForAll(original, "SwampHut"); }
    @Override public void onBlackSmithRegister(List<WeightedRandomChestContent> original) { onRegisterOriginalAddForAll(original, "BlackSmith"); }
    @Override public void onDungeonOverworldRegister(List<WeightedRandomChestContent> original) { onRegisterOriginalAddForAll(original, "DungeonOverworld"); }
    @Override public void onDungeonUnderworldRegister(List<WeightedRandomChestContent> original) { onRegisterOriginalAddForAll(original, "DungeonUnderworld"); }
}
