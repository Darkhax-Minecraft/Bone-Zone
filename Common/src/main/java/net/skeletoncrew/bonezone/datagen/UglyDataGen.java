package net.skeletoncrew.bonezone.datagen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    private static List<String> ALL = new ArrayList<>();
    private static final String EMPTY = "empty";

    static {
        ALL.addAll(Arrays.stream(CROSS_PLANTS).toList());
        ALL.add("bamboo");
        ALL.add("mangrove_propagule");
        ALL.add("cactus");
        ALL.add("azalea");
        ALL.add(EMPTY); //empty
        ALL.add("flowering_azalea");
        ALL.add("fern");
    }

    public static void generateCrossBlockstate(String prefix) {

        File blockstatesDir = new File("output/assets/bonezone/blockstates");
        blockstatesDir.mkdirs();
        for (String type : ALL) {
            for (int i = 0; i < 2; i++) {
                String flip = (i == 0 ? "" : "_flipped");
                String empty = "";
                String pot = "_potted_";
                if (type.equals(EMPTY)) {
                    empty = "empty_";
                    pot = "_pot";
                    type = "";
                }
                try (FileWriter writer = new FileWriter(new File(blockstatesDir, empty + prefix + pot + type + flip + ".json"))) {
                    if (type.isBlank()) type = EMPTY;
                    writer.append("{\n" +
                            "  \"variants\": {\n" +
                            "    \"facing=north\": {\n" +
                            "      \"model\": \"bonezone:block/skullpot/" + prefix + "/" + type + flip + "\"\n" +
                            "    },\n" +
                            "    \"facing=south\": {\n" +
                            "      \"model\": \"bonezone:block/skullpot/" + prefix + "/" + type + flip + "\"\n," +
                            "      \"y\": 180\n" +
                            "    },\n" +
                            "    \"facing=east\": {\n" +
                            "      \"model\": \"bonezone:block/skullpot/" + prefix + "/" + type + flip + "\"\n," +
                            "      \"y\": 90\n" +
                            "    },\n" +
                            "    \"facing=west\": {\n" +
                            "      \"model\": \"bonezone:block/skullpot/" + prefix + "/" + type + flip + "\"\n," +
                            "      \"y\": 270\n" +
                            "    }\n" +
                            "  }\n" +
                            "}");
                } catch (IOException e) {

                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void generateModels(String prefix, String particle, String entity, String face) {
        File modelsDir = new File("output/assets/bonezone/models/block/skullpot/" + prefix);
        modelsDir.mkdirs();

        String texEntity = "minecraft:entity/" + entity;

        for (String type : CROSS_PLANTS) {
            make(modelsDir, type, particle, texEntity, face, "mob_head_pot_cross", "    \"plant\": \"minecraft:block/" + type + "\"\n");
        }

        make(modelsDir, "azalea", particle, texEntity, face, "mob_head_pot_azalea_bush", "\"top\": \"minecraft:block/potted_azalea_bush_top\",\n", "\"side\": \"minecraft:block/potted_azalea_bush_side\"");
        make(modelsDir, "bamboo", particle, texEntity, face, "mob_head_pot_bamboo");
        make(modelsDir, "cactus", particle, texEntity, face, "mob_head_pot_cactus");
        make(modelsDir, "empty", particle, texEntity, face, "mob_head_pot");
        make(modelsDir, "fern", particle, texEntity, face, "mob_head_pot_cross_tinted");
        make(modelsDir, "flowering_azalea", particle, texEntity, face, "mob_head_pot_azalea_bush", "\"top\": \"minecraft:block/potted_flowering_azalea_bush_top\",\n", "\"side\": \"minecraft:block/potted_flowering_azalea_bush_side\"");
        make(modelsDir, "mangrove_propagule", particle, texEntity, face, "mob_head_pot_mangrove_propagule");

    }

    private static void make(File modelsDir, String type, String particle, String texEntity, String face, String model, String... extra) {
        for (int i = 0; i < 2; i++) {
            try (FileWriter writer = new FileWriter(new File(modelsDir, type + (i == 0 ? "" : "_flipped") + ".json"))) {
                writer.append("{\n" +
                        "  \"parent\": \"bonezone:block/skullpot/" + (i == 0 ? "" : "flipped_") + model + "\",\n" +
                        "  \"textures\": {\n" +
                        "    \"particle\": \"" + particle + "\",\n" +
                        "    \"head_north\": \"" + face + "\",\n" +
                        "    \"head_east\": \"" + texEntity + "\",\n" +
                        "    \"head_south\": \"" + texEntity + "\",\n" +
                        "    \"head_west\": \"" + texEntity + "\",\n" +
                        "    \"head_top\": \"" + texEntity + "\",\n" +
                        "    \"head_bottom\": \"" + texEntity + "\"");
                if (extra != null && extra.length > 0) {
                    for (String s : extra) {
                        writer.append(",\n");
                        writer.append(s);
                    }
                }
                writer.append("\n");
                writer.append("  }\n}");

            } catch (IOException e) {

                throw new RuntimeException(e);
            }
        }
    }
}