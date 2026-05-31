package vbonedra.shattered_baubles.event;

import com.google.common.eventbus.Subscribe;
import vbonedra.shattered_baubles.SBItems;
import net.xiaoyu233.fml.reload.event.*;

public class FMLEvents {
    @Subscribe
    public void onItemRegister(ItemRegistryEvent event) {
        SBItems.registerItems(event);
    }

    @Subscribe
    public void onRecipeRegister(RecipeRegistryEvent event) {
        SBItems.registerRecipes(event);
    }

    @Subscribe
    public void onSoundsRegister(SoundsRegisterEvent event) {
        SBSounds.register(event);
    }
}