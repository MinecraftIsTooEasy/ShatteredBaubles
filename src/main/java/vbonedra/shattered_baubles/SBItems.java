package vbonedra.shattered_baubles;

import net.minecraft.FurnaceRecipes;
import net.minecraft.ItemStack;
import net.xiaoyu233.fml.reload.event.RecipeRegistryEvent;
import net.xiaoyu233.fml.reload.utils.IdUtil;
import net.minecraft.Item;
import net.xiaoyu233.fml.reload.event.ItemRegistryEvent;
import vbonedra.shattered_baubles.items.*;


public class SBItems {
    // TODO: maybe add levels to baubles? salt_cube would slow down by 0.2 and then 0.4 and then 0.6 and then 0.8; maybe make salt_cube stackable to 4 and add new count in slot, so its possible to modify it with count?
    // TODO: maybe make some baubles eatable? salt_cube and bottle_of_ghoul_blood would suit greatly
    public static final Item bottle_of_ghoul_blood = new BottleOfGhoulBlood(IdUtil.getNextItemID());
    public static final Item salt_cube = new SaltCube(IdUtil.getNextItemID());
    public static final Item leather_glove = new LeatherGlove(IdUtil.getNextItemID());
    public static final Item ancient_gauntlet = new AncientGauntlet(IdUtil.getNextItemID());
    public static final Item ring_of_pride = new RingOfPride(IdUtil.getNextItemID());
    public static final Item golden_egg = new GoldenEgg(IdUtil.getNextItemID());
    public static final Item flower_boots = new FlowerBoots(IdUtil.getNextItemID());
    public static final Item climbing_pick = new ClimbingPick(IdUtil.getNextItemID());

    // TODO: spiritual quiver - adds chance to save arrow, but bow loses more durability when it happens
    // TODO: old hat - increases bow draw speed but ??? prevents full draw attack OR lowers damage OR increases lose chance
    // TODO: tattered manuscript - increases hand enchantments by 1 level but takes xp on tool use (attack, mine, etc.)
    // TODO: bracelet of might - increases health by % with cap
    // TODO: feather boots - adds fall damage defence, but increases taken knockback
    // TODO: worm king - feasts on players inventory, x2 efficiency of normal worms (its bad because it might eat players food not only manure-transformable things)


    private static void registerItem(ItemRegistryEvent registry, String name, Item item) {
        registry.register(ShatteredBaubles.MOD_ID, ShatteredBaubles.RESOURCE_ID + name, name, item);
    }

    public static void registerItems(ItemRegistryEvent event) {
        for (SBItem item : SBItem.allShatteredBaubles) { registerItem(event, item.texture, item); }
    }

    public static void registerRecipes(RecipeRegistryEvent event) {
        // shaped crafting
        event.registerShapedRecipe(new ItemStack(leather_glove), true, ""+
                "LLL",
                "LPL",
                "SNS",
                'L', Item.leather,
                'S', Item.silk,
                'P', Item.paper,
                'N', Item.coinGold
        );
        event.registerShapedRecipe(new ItemStack(ancient_gauntlet), true, ""+
                "III",
                "CGC",
                "LNL",
                'I', Item.ingotAncientMetal,
                'C', Item.chainAncientMetal,
                'L', Item.leather,
                'N', Item.coinAncientMetal,
                'G', SBItems.leather_glove
        );
        event.registerShapedRecipe(new ItemStack(climbing_pick), true, ""+
                "HII",
                "SL ",
                "NL ",
                'I', Item.ingotMithril,
                'L', Item.leather,
                'N', Item.coinMithril,
                'H', Item.hoeMithril,
                'S', Item.stick
        );
        FurnaceRecipes.smelting().addSmelting(SBItems.ancient_gauntlet.itemID, new ItemStack(Item.ancientMetalNugget,12));
        FurnaceRecipes.smelting().addSmelting(SBItems.ring_of_pride.itemID, new ItemStack(Item.goldNugget,12));
        FurnaceRecipes.smelting().addSmelting(SBItems.golden_egg.itemID, new ItemStack(Item.goldNugget,8));
        FurnaceRecipes.smelting().addSmelting(SBItems.climbing_pick.itemID, new ItemStack(Item.mithrilNugget,12));

    }
}
