package vbonedra.shattered_baubles.util;

import fi.dy.masa.malilib.config.ConfigTab;
import fi.dy.masa.malilib.config.SimpleConfigs;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.config.options.ConfigDouble;
import vbonedra.shattered_baubles.ShatteredBaubles;

import java.util.*;
import java.util.stream.Collectors;

public class ConfigShatteredBaubles extends SimpleConfigs {
    private static final ConfigShatteredBaubles Instance;

    public static final List<ConfigBase<?>> DropProbabilities;
    public static final List<ConfigDouble> ChestLoot;
    public static final List<ConfigBase<?>> OtherSettings;
    public static final List<ConfigBase<?>> Total;
    public static final List<ConfigTab> tabs;

    public ConfigShatteredBaubles(String name, List<ConfigBase<?>> values) {
        super(name, null, values);
    }

    public List<ConfigTab> getConfigTabs() {
        return tabs;
    }

    public static ConfigShatteredBaubles getInstance() {
        return Instance;
    }

    public static ConfigDouble probabilityChest(String description, double probability) {
        return new ConfigDouble(description, probability,0.0,0.1);
    }


    public static final ConfigDouble salt_cube_METABOLISM_MULTIPLIER = new ConfigDouble(
            "Salt Cube: Metabolism Multiplier",0.5,0.0,1.0,false,"");


    public static final ConfigDouble bottle_of_ghoul_blood_HEALTH_LIMIT_ADDITIONAL_PERCENT = new ConfigDouble(
            "Bottle of Ghoul Blood: Health Additional Percent",-0.25,-1.0,0.0,false,"");
    public static final ConfigDouble bottle_of_ghoul_blood_HEALTH_LIMIT_ADDITIONAL_CAP = new ConfigDouble(
            "Bottle of Ghoul Blood: Health Additional Cap",-100.0,-100.0,0.0,false,"");
    public static final ConfigDouble bottle_of_ghoul_blood_REGENERATION_MULTIPLIER = new ConfigDouble(
            "Bottle of Ghoul Blood: Regeneration Multiplier",0.5,0.0,1.0,false,"");
    public static final ConfigDouble bottle_of_ghoul_blood_PROBABILITY_Ghoul = new ConfigDouble(
            "Bottle of Ghoul Blood: Probability when Ghoul Dies",0.005,0.0,1.0,false,"");


    public static final ConfigDouble leather_glove_MINING_SPEED_ADDITIONAL_PERCENT = new ConfigDouble(
            "Leather Glove: Mining Speed Additional Percent",0.15,0.0,1.0,false,"");
    public static final ConfigDouble leather_glove_MINING_SPEED_ADDITIONAL_CAP = new ConfigDouble(
            "Leather Glove: Mining Speed Additional Cap",5.0,0.0,100.0,false,"");
    public static final ConfigDouble leather_glove_DAMAGE_ADDITIONAL_PERCENT = new ConfigDouble(
            "Leather Glove: Damage Additional Percent",-0.1,-1.0,0.0,false,"");
    public static final ConfigDouble leather_glove_DAMAGE_ADDITIONAL_CAP = new ConfigDouble(
            "Leather Glove: Damage Additional Cap",-1.0,-100.0,0.0,false,"");


    public static final ConfigDouble ancient_gauntlet_MINING_SPEED_ADDITIONAL_PERCENT = new ConfigDouble(
            "Ancient Gauntlet: Mining Speed Additional Percent",-0.1,-1.0,0.0,false,"");
    public static final ConfigDouble ancient_gauntlet_MINING_SPEED_ADDITIONAL_CAP = new ConfigDouble(
            "Ancient Gauntlet: Mining Speed Additional Cap",-4,-100.0,0.0,false,"");
    public static final ConfigDouble ancient_gauntlet_DAMAGE_ADDITIONAL_PERCENT = new ConfigDouble(
            "Ancient Gauntlet: Damage Additional Percent",0.2,0.0,1.0,false,"");
    public static final ConfigDouble ancient_gauntlet_DAMAGE_ADDITIONAL_CAP = new ConfigDouble(
            "Ancient Gauntlet: Damage Additional Cap",2.0,0.0,100.0,false,"");


    public static final ConfigDouble ring_of_pride_EXPERIENCE_ADDITIONAL_PERCENT = new ConfigDouble(
            "Ring of Pride: Experience Additional Percent",0.5,0.0,1.0,false,"");
    public static final ConfigDouble ring_of_pride_EXPERIENCE_PUNISHMENT_MULTIPLIER = new ConfigDouble(
            "Ring of Pride: Experience Punishment Multiplier",1.5,1.0,2.0,false,"");


    public static final ConfigDouble golden_egg_PROBABILITY_ChickenLayEgg = new ConfigDouble(
            "Golden Egg: Probability when Chicken Lays Egg",1.0/(16*9),0.0,1.0,false,"");


    public static final ConfigDouble flower_boots_MOVEMENT_SPEED_ADDITIONAL_PERCENT = new ConfigDouble(
            "Flower Boots: Movement Speed Additional Percent",0.15,0.0,1.0,false,"");
    public static final ConfigDouble flower_boots_FIRE_DAMAGE_ADDITIONAL_PERCENT = new ConfigDouble(
            "Flower Boots: Fire Damage Additional Percent",1.0,0.0,1.0,false,"");
    public static final ConfigDouble flower_boots_CLIMBING_SPEED_MULTIPLIER = new ConfigDouble(
            "Flower Boots: Climbing Speed Multiplier",0.5,0.0,1.0,false,"");


    public static final ConfigDouble climbing_pick_FALL_DAMAGE_ADDITIONAL_PERCENT = new ConfigDouble(
            "Climbing Pick: Fall Damage Additional Percent",1.0,0.0,1.0,false,"");
    public static final ConfigDouble climbing_pick_CLIMBING_SPEED_MULTIPLIER = new ConfigDouble(
            "Climbing Pick: Climbing Speed Multiplier",0.25,0.0,1.0,false,"");

    // TODO: add function that would automatically add translated description (ig hard to do since it would mean that we pull items which triggers classes that use uninitialized things)
    public static final Map<String, Map<String, ConfigDouble>> PROBABILITY_ChestName = new HashMap<>()
    {{
        put("ancient_gauntlet", new HashMap<>() {{
            put("DesertPyramid",        probabilityChest("Ancient Gauntlet: DesertPyramid",                 0/67.0));
            put("JunglePyramid",        probabilityChest("Ancient Gauntlet: JunglePyramid",                 0/62.0));
            put("Fortress",             probabilityChest("Ancient Gauntlet: Fortress",                      2/66.0));
            put("Mineshaft",            probabilityChest("Ancient Gauntlet: Mineshaft",                     0/168.0));
            put("StrongholdCorridor",   probabilityChest("Ancient Gauntlet: StrongholdCorridor",            0/189.0));
            put("StrongholdCrossing",   probabilityChest("Ancient Gauntlet: StrongholdCrossing",            1/61.0));
            put("StrongholdLibrary",    probabilityChest("Ancient Gauntlet: StrongholdLibrary",             0/42.0));
            put("SwampHut",             probabilityChest("Ancient Gauntlet: SwampHut",                      0/98.0));
            put("BlackSmith",           probabilityChest("Ancient Gauntlet: BlackSmith",                    0/302.0));
            put("DungeonOverworld",     probabilityChest("Ancient Gauntlet: DungeonOverworld",              0/155.0));
            put("DungeonUnderworld",    probabilityChest("Ancient Gauntlet: DungeonUnderworld",             4/73.0));
        }});
        put("bottle_of_ghoul_blood", new HashMap<>() {{
            put("DesertPyramid",        probabilityChest("Bottle of Ghoul Blood: DesertPyramid",            2/67.0));
            put("JunglePyramid",        probabilityChest("Bottle of Ghoul Blood: JunglePyramid",            2/62.0));
            put("Fortress",             probabilityChest("Bottle of Ghoul Blood: Fortress",                 0/66.0));
            put("Mineshaft",            probabilityChest("Bottle of Ghoul Blood: Mineshaft",                0/168.0));
            put("StrongholdCorridor",   probabilityChest("Bottle of Ghoul Blood: StrongholdCorridor",       0/189.0));
            put("StrongholdCrossing",   probabilityChest("Bottle of Ghoul Blood: StrongholdCrossing",       0/61.0));
            put("StrongholdLibrary",    probabilityChest("Bottle of Ghoul Blood: StrongholdLibrary",        0/42.0));
            put("SwampHut",             probabilityChest("Bottle of Ghoul Blood: SwampHut",                 1/98.0));
            put("BlackSmith",           probabilityChest("Bottle of Ghoul Blood: BlackSmith",               3/302.0));
            put("DungeonOverworld",     probabilityChest("Bottle of Ghoul Blood: DungeonOverworld",         5/155.0));
            put("DungeonUnderworld",    probabilityChest("Bottle of Ghoul Blood: DungeonUnderworld",        3/73.0));
        }});
        put("leather_glove", new HashMap<>() {{
            put("DesertPyramid",        probabilityChest("Leather Glove: DesertPyramid",                    0/67.0));
            put("JunglePyramid",        probabilityChest("Leather Glove: JunglePyramid",                    0/62.0));
            put("Fortress",             probabilityChest("Leather Glove: Fortress",                         0/66.0));
            put("Mineshaft",            probabilityChest("Leather Glove: Mineshaft",                        3/168.0));
            put("StrongholdCorridor",   probabilityChest("Leather Glove: StrongholdCorridor",               3/189.0));
            put("StrongholdCrossing",   probabilityChest("Leather Glove: StrongholdCrossing",               0/61.0));
            put("StrongholdLibrary",    probabilityChest("Leather Glove: StrongholdLibrary",                0/42.0));
            put("SwampHut",             probabilityChest("Leather Glove: SwampHut",                         1/98.0));
            put("BlackSmith",           probabilityChest("Leather Glove: BlackSmith",                       5/302.0));
            put("DungeonOverworld",     probabilityChest("Leather Glove: DungeonOverworld",                 1/155.0));
            put("DungeonUnderworld",    probabilityChest("Leather Glove: DungeonUnderworld",                1/73.0));
        }});
        put("ring_of_pride", new HashMap<>() {{
            put("DesertPyramid",        probabilityChest("Ring of Pride: DesertPyramid",                    3/67.0));
            put("JunglePyramid",        probabilityChest("Ring of Pride: JunglePyramid",                    3/62.0));
            put("Fortress",             probabilityChest("Ring of Pride: Fortress",                         0/66.0));
            put("Mineshaft",            probabilityChest("Ring of Pride: Mineshaft",                        1/168.0));
            put("StrongholdCorridor",   probabilityChest("Ring of Pride: StrongholdCorridor",               0/189.0));
            put("StrongholdCrossing",   probabilityChest("Ring of Pride: StrongholdCrossing",               0/61.0));
            put("StrongholdLibrary",    probabilityChest("Ring of Pride: StrongholdLibrary",                1/42.0));
            put("SwampHut",             probabilityChest("Ring of Pride: SwampHut",                         0/98.0));
            put("BlackSmith",           probabilityChest("Ring of Pride: BlackSmith",                       1/302.0));
            put("DungeonOverworld",     probabilityChest("Ring of Pride: DungeonOverworld",                 0/155.0));
            put("DungeonUnderworld",    probabilityChest("Ring of Pride: DungeonUnderworld",                1/73.0));
        }});
        put("salt_cube", new HashMap<>() {{
            put("DesertPyramid",        probabilityChest("Salt Cube: DesertPyramid",                        0/67.0));
            put("JunglePyramid",        probabilityChest("Salt Cube: JunglePyramid",                        1/62.0));
            put("Fortress",             probabilityChest("Salt Cube: Fortress",                             0/66.0));
            put("Mineshaft",            probabilityChest("Salt Cube: Mineshaft",                            4/168.0));
            put("StrongholdCorridor",   probabilityChest("Salt Cube: StrongholdCorridor",                   2/189.0));
            put("StrongholdCrossing",   probabilityChest("Salt Cube: StrongholdCrossing",                   0/61.0));
            put("StrongholdLibrary",    probabilityChest("Salt Cube: StrongholdLibrary",                    0/42.0));
            put("SwampHut",             probabilityChest("Salt Cube: SwampHut",                             3/98.0));
            put("BlackSmith",           probabilityChest("Salt Cube: BlackSmith",                           5/302.0));
            put("DungeonOverworld",     probabilityChest("Salt Cube: DungeonOverworld",                     3/155.0));
            put("DungeonUnderworld",    probabilityChest("Salt Cube: DungeonUnderworld",                    1/73.0));
        }});
        put("golden_egg", new HashMap<>() {{
            put("DesertPyramid",        probabilityChest("Golden Egg: DesertPyramid",                       0/67.0));
            put("JunglePyramid",        probabilityChest("Golden Egg: JunglePyramid",                       1/62.0));
            put("Fortress",             probabilityChest("Golden Egg: Fortress",                            0/66.0));
            put("Mineshaft",            probabilityChest("Golden Egg: Mineshaft",                           4/168.0));
            put("StrongholdCorridor",   probabilityChest("Golden Egg: StrongholdCorridor",                  2/189.0));
            put("StrongholdCrossing",   probabilityChest("Golden Egg: StrongholdCrossing",                  0/61.0));
            put("StrongholdLibrary",    probabilityChest("Golden Egg: StrongholdLibrary",                   0/42.0));
            put("SwampHut",             probabilityChest("Golden Egg: SwampHut",                            3/98.0));
            put("BlackSmith",           probabilityChest("Golden Egg: BlackSmith",                          5/302.0));
            put("DungeonOverworld",     probabilityChest("Golden Egg: DungeonOverworld",                    3/155.0));
            put("DungeonUnderworld",    probabilityChest("Golden Egg: DungeonUnderworld",                   1/73.0));
        }});
        put("flower_boots", new HashMap<>() {{
            put("DesertPyramid",        probabilityChest("Flower Boots: DesertPyramid",                     0/67.0));
            put("JunglePyramid",        probabilityChest("Flower Boots: JunglePyramid",                     5/62.0));
            put("Fortress",             probabilityChest("Flower Boots: Fortress",                          0/66.0));
            put("Mineshaft",            probabilityChest("Flower Boots: Mineshaft",                         3/168.0));
            put("StrongholdCorridor",   probabilityChest("Flower Boots: StrongholdCorridor",                0/189.0));
            put("StrongholdCrossing",   probabilityChest("Flower Boots: StrongholdCrossing",                0/61.0));
            put("StrongholdLibrary",    probabilityChest("Flower Boots: StrongholdLibrary",                 0/42.0));
            put("SwampHut",             probabilityChest("Flower Boots: SwampHut",                          2/98.0));
            put("BlackSmith",           probabilityChest("Flower Boots: BlackSmith",                        1/302.0));
            put("DungeonOverworld",     probabilityChest("Flower Boots: DungeonOverworld",                  0/155.0));
            put("DungeonUnderworld",    probabilityChest("Flower Boots: DungeonUnderworld",                 1/73.0));
        }});
        put("climbing_pick", new HashMap<>() {{
            put("DesertPyramid",        probabilityChest("Climbing Pick: DesertPyramid",                    0/67.0));
            put("JunglePyramid",        probabilityChest("Climbing Pick: JunglePyramid",                    0/62.0));
            put("Fortress",             probabilityChest("Climbing Pick: Fortress",                         1/66.0));
            put("Mineshaft",            probabilityChest("Climbing Pick: Mineshaft",                        4/168.0));
            put("StrongholdCorridor",   probabilityChest("Climbing Pick: StrongholdCorridor",               0/189.0));
            put("StrongholdCrossing",   probabilityChest("Climbing Pick: StrongholdCrossing",               1/61.0));
            put("StrongholdLibrary",    probabilityChest("Climbing Pick: StrongholdLibrary",                0/42.0));
            put("SwampHut",             probabilityChest("Climbing Pick: SwampHut",                         0/98.0));
            put("BlackSmith",           probabilityChest("Climbing Pick: BlackSmith",                       3/302.0));
            put("DungeonOverworld",     probabilityChest("Climbing Pick: DungeonOverworld",                 0/155.0));
            put("DungeonUnderworld",    probabilityChest("Climbing Pick: DungeonUnderworld",                0/73.0));
        }});
    }};
    public static List<ConfigDouble> getAllChestLoot() {
        return PROBABILITY_ChestName.values().stream()
                .flatMap(structureMap -> structureMap.values().stream())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


    static {
        DropProbabilities = List.of(
                golden_egg_PROBABILITY_ChickenLayEgg,
                bottle_of_ghoul_blood_PROBABILITY_Ghoul
        );
        ChestLoot = getAllChestLoot();
        OtherSettings = List.of(
                bottle_of_ghoul_blood_HEALTH_LIMIT_ADDITIONAL_PERCENT,
                bottle_of_ghoul_blood_HEALTH_LIMIT_ADDITIONAL_CAP,
                bottle_of_ghoul_blood_REGENERATION_MULTIPLIER,

                leather_glove_MINING_SPEED_ADDITIONAL_PERCENT,
                leather_glove_MINING_SPEED_ADDITIONAL_CAP,
                leather_glove_DAMAGE_ADDITIONAL_PERCENT,
                leather_glove_DAMAGE_ADDITIONAL_CAP,

                ancient_gauntlet_MINING_SPEED_ADDITIONAL_PERCENT,
                ancient_gauntlet_MINING_SPEED_ADDITIONAL_CAP,
                ancient_gauntlet_DAMAGE_ADDITIONAL_PERCENT,
                ancient_gauntlet_DAMAGE_ADDITIONAL_CAP,

                ring_of_pride_EXPERIENCE_ADDITIONAL_PERCENT,
                ring_of_pride_EXPERIENCE_PUNISHMENT_MULTIPLIER,

                flower_boots_MOVEMENT_SPEED_ADDITIONAL_PERCENT,
                flower_boots_FIRE_DAMAGE_ADDITIONAL_PERCENT,
                flower_boots_CLIMBING_SPEED_MULTIPLIER,

                climbing_pick_FALL_DAMAGE_ADDITIONAL_PERCENT,
                climbing_pick_CLIMBING_SPEED_MULTIPLIER,

                salt_cube_METABOLISM_MULTIPLIER
        );

        Total = new ArrayList<>();
        Total.addAll(DropProbabilities);
        Total.addAll(ChestLoot);
        Total.addAll(OtherSettings);

        tabs = new ArrayList<>();
        tabs.add(new ConfigTab("drops", DropProbabilities));
        tabs.add(new ConfigTab("chests", ChestLoot));
        tabs.add(new ConfigTab("other", OtherSettings));

        Instance = new ConfigShatteredBaubles(ShatteredBaubles.MODID, Total);
    }
}
