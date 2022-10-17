package net.skeletoncrew.bonezone;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class UglyDataGen {

    private static String[] CROSS_PLANTS = {
            "acacia_sapling",
            "allium",
            "azure_bluet",
            "birch_sapling",
            "blue_orchid",
            "brown_mushroom",
            "cornflower",
            "crimson_fungus",
            "crimson_roots",
            "dandelion",
            "dark_oak_sapling",
            "dead_bush",
            "jungle_sapling",
            "lily_of_the_valley",
            "oak_sapling",
            "orange_tulip",
            "oxeye_daisy",
            "pink_tulip",
            "poppy",
            "red_mushroom",
            "spruce_sapling",
            "warped_fungus",
            "warped_roots",
            "white_tulip",
            "wither_rose",
            "red_tulip"
    };

    public static void generateCrossBlockstate(String prefix) {

        File blockstatesDir = new File("output/assets/bonezone/blockstates");
        blockstatesDir.mkdirs();

        for (String type : CROSS_PLANTS) {

            try (FileWriter writer = new FileWriter(new File(blockstatesDir, prefix + "_potted_" + type + ".json"))) {

                writer.append("{\n" +
                        "  \"variants\": {\n" +
                        "    \"facing=north\": {\n" +
                        "      \"model\": \"bonezone:block/skullpot/" + prefix + "/" + type + "\"\n" +
                        "    },\n" +
                        "    \"facing=south\": {\n" +
                        "      \"model\": \"bonezone:block/skullpot/" + prefix + "/" + type + "\"\n," +
                        "      \"y\": 180\n" +
                        "    },\n" +
                        "    \"facing=east\": {\n" +
                        "      \"model\": \"bonezone:block/skullpot/" + prefix + "/" + type + "\"\n," +
                        "      \"y\": 90\n" +
                        "    },\n" +
                        "    \"facing=west\": {\n" +
                        "      \"model\": \"bonezone:block/skullpot/" + prefix + "/" + type + "\"\n," +
                        "      \"y\": 270\n" +
                        "    }\n" +
                        "  }\n" +
                        "}");
            }

            catch (IOException e) {

                throw new RuntimeException(e);
            }
        }
    }

    public static void generateCrossModels(String prefix) {

        File modelsDir = new File("output/assets/bonezone/models/block/skullpot/" + prefix);
        modelsDir.mkdirs();

        for (String type : CROSS_PLANTS) {

            try (FileWriter writer = new FileWriter(new File(modelsDir, type + ".json"))) {

                writer.append("{\n" +
                        "  \"parent\": \"bonezone:block/skullpot/mob_head_pot_cross\",\n" +
                        "  \"textures\": {\n" +
                        "    \"particle\": \"minecraft:block/" + "coal_block" + "\"\n" +
                        "    \"head_north\": \"minecraft:block/" + type + "\"\n" +
                        "    \"head_east\": \"minecraft:block/" + type + "\"\n" +
                        "    \"head_south\": \"minecraft:block/" + type + "\"\n" +
                        "    \"head_west\": \"minecraft:block/" + type + "\"\n" +
                        "    \"head_top\": \"minecraft:block/" + type + "\"\n" +
                        "    \"head_bottom\": \"minecraft:block/" + type + "\"\n" +
                        "    \"plant\": \"minecraft:block/" + type + "\"\n" +
                        "  }\n" +
                        "}");
            }

            catch (IOException e) {

                throw new RuntimeException(e);
            }
        }
    }

    public static void generateCrossModels(String prefix, String particle, String entity, String face) {

        File modelsDir = new File("output/assets/bonezone/models/block/skullpot/" + prefix);
        modelsDir.mkdirs();

        for (String type : CROSS_PLANTS) {

            try (FileWriter writer = new FileWriter(new File(modelsDir, type + ".json"))) {

                writer.append("{\n" +
                        "  \"parent\": \"bonezone:block/skullpot/mob_head_pot_cross\",\n" +
                        "  \"textures\": {\n" +
                        "    \"particle\": \"minecraft:block/" + particle + "\",\n" +
                        "    \"head_north\": \"minecraft:entity/" + entity + "\",\n" +
                        "    \"head_east\": \"minecraft:entity/" + entity + "\",\n" +
                        "    \"head_south\": \"minecraft:entity/" + entity + "\",\n" +
                        "    \"head_west\": \"minecraft:entity/" + entity + "\",\n" +
                        "    \"head_top\": \"minecraft:entity/" + entity + "\",\n" +
                        "    \"head_bottom\": \"minecraft:entity/" + entity + "\",\n" +
                        "    \"plant\": \"minecraft:block/" + type + "\"\n" +
                        "  }\n" +
                        "}");
            }

            catch (IOException e) {

                throw new RuntimeException(e);
            }
        }
    }
}