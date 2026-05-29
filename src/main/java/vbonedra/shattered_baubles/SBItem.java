package vbonedra.shattered_baubles;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import baubles.creativetab.BaublesCreativeTab;
import fi.dy.masa.malilib.config.options.ConfigDouble;
import net.minecraft.*;
import shims.java.net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static vbonedra.shattered_baubles.ShatteredBaubles.LOGGER;
import static vbonedra.shattered_baubles.util.ConfigShatteredBaubles.PROBABILITY_ChestName;

public abstract class SBItem extends Item implements IBauble {
    public SBItem(int id, Material material, String texture) {
        super(id, material, texture);
        this.setMaxStackSize(1);
        this.setCreativeTab(BaublesCreativeTab.TAB);
        this.texture = texture;
        allShatteredBaubles.add(this);

        // 1. Безопасно используем локальную переменную 'texture', а не 'this.texture'
        Map<String, ConfigDouble> itemLootMap = PROBABILITY_ChestName.get(texture);

        if (itemLootMap != null) {
            itemLootMap.forEach((structureName, configDouble) -> {
                if (configDouble != null) {
                    putToChestLoots(structureName, configDouble.getDoubleValue());
                }
            });
        } else {
            LOGGER.warn("Item \"{}\" doesn't have chest probability in PROBABILITY_ChestName.", texture);
        }
    }

    public String texture;
    public static ArrayList<SBItem> allShatteredBaubles = new ArrayList<>();
    // TODO: maybe turn Integer[] into map so its easy to understand what those 4 numbers is?
    public final Map<String, Integer[]> chestLoots = new HashMap<String, Integer[]>() {{ // {stack_count, min_count, max_count, weight, estimated_chance_percent}
        put("DesertPyramid",      new Integer[]{0, 0, 0, 0, 0});
        put("JunglePyramid",      new Integer[]{0, 0, 0, 0, 0});
        put("Fortress",           new Integer[]{0, 0, 0, 0, 0});
        put("Mineshaft",          new Integer[]{0, 0, 0, 0, 0});
        put("StrongholdCorridor", new Integer[]{0, 0, 0, 0, 0});
        put("StrongholdCrossing", new Integer[]{0, 0, 0, 0, 0});
        put("StrongholdLibrary",  new Integer[]{0, 0, 0, 0, 0});
        put("SwampHut",           new Integer[]{0, 0, 0, 0, 0});
        put("BlackSmith",         new Integer[]{0, 0, 0, 0, 0});
        put("DungeonOverworld",   new Integer[]{0, 0, 0, 0, 0});
        put("DungeonUnderworld",  new Integer[]{0, 0, 0, 0, 0});
    }};
    public static final Map<String, Integer> chestVanillaWeights = new HashMap<String, Integer>() {{
        put("DesertPyramid",       67);
        put("JunglePyramid",       62);
        put("Fortress",            66);
        put("Mineshaft",           168);
        put("StrongholdCorridor",  189);
        put("StrongholdCrossing",  61);
        put("StrongholdLibrary",   42);
        put("SwampHut",            98);
        put("BlackSmith",          302);
        put("DungeonOverworld",    155);
        put("DungeonUnderworld",   73);
    }};

    public Integer[] getChestLoot (String registerName) {
        Integer[] lootData = chestLoots.get(registerName);
        if (lootData != null && lootData[0] > 0) return chestLoots.get(registerName);
        return new Integer[]{0, 0, 0, 0, 0};
    }
    public void putToChestLoots (String chestName, double chance) { putToChestLoots(chestName, (float) chance); }
    public void putToChestLoots (String chestName, float chance) {
        if (chance > 0.0) {
            int weight = (int) Math.max(1, chestVanillaWeights.get(chestName) * chance);
            int estimated_chance_percent = (int) (weight * 10000 / chestVanillaWeights.get(chestName));
            chestLoots.put(chestName, new Integer[]{1, 1, 1, weight, estimated_chance_percent});
        }
    }

    public Text getEmiDescription() {
        return Text.literal(formatTextWithConfigValues(Translator.get("emi.%s.description".formatted(this.texture))));
    }
    // formatTextWithConfigValues must be modified if inserting values in emi.{this.texture}.description via "%s"
    public String formatTextWithConfigValues(String text) {
        return text;
    }


    @Override
    public abstract BaubleType getBaubleType(ItemStack itemstack);

    @Override
    public boolean onItemRightClick(EntityPlayer player, float partial_tick, boolean ctrl_is_down) {
        InventoryBaubles baubles = PlayerHandler.getPlayerBaubles(player);
        ItemStack item_stack = player.getHeldItemStack();
        for (int i = 0; i < baubles.getSizeInventory(); i++) {
            if (baubles.getStackInSlot(i) == null && baubles.isItemValidForSlot(i, item_stack)) {
                if (player.onServer()) {
                    baubles.setInventorySlotContents(i, item_stack.copy());
                    if (!player.capabilities.isCreativeMode) {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                    }
                    onEquipped(item_stack, player);
                }
                return true;
            }
        }

        return false;
    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
        if (!player.worldObj.isRemote) {
            player.worldObj.playSoundAtEntity(player, "random.orb", 0.1F, 1.3f);
        }
    }

    @Override
    public void onUnequipped(ItemStack itemStack, EntityLivingBase entityLivingBase) {

    }

    @Override
    public void addInformationBeforeEnchantments(ItemStack item_stack, EntityPlayer player, List info, boolean extended_info, Slot slot) {
        if (extended_info) {
            String text = I18n.getString("item." + this.texture + ".description");
            String[] words = text.split(" ");
            String line = "";
            int min_line_length = 48;
            for (String word : words) {
                line += word+" ";
                if (line.length() > min_line_length){
                    if (line.endsWith(" ")) line = line.substring(0, line.length() - 1);
                    info.add(line);
                    line = "";
                }
            }
            if (line.endsWith(" ")) line = line.substring(0, line.length() - 1);
            if (line != "") info.add(line);
        }
    }

    @Override
    public void onWornTick(ItemStack itemStack, EntityLivingBase entityLivingBase) {

    }

    @Override
    public boolean canEquip(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return true;
    }
}
