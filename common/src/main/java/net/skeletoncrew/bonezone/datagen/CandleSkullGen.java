package net.skeletoncrew.bonezone.datagen;

import net.minecraft.world.item.DyeColor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CandleSkullGen {

    private final String type;
    private final String skullTexture;

    public CandleSkullGen(String type, String skullTexture) {
        this.type = type;
        this.skullTexture = skullTexture;
    }

    private static final List<String> CANDLES = new ArrayList<>();

    static {
        CANDLES.addAll(Arrays.stream(DyeColor.values()).map(DyeColor::getName).toList());
        CANDLES.add("regular");
        CANDLES.add("empty");
    }

    public CandleSkullGen genBlockStates() {
        File blockstatesDir = new File("output/assets/bonezone/blockstates");
        blockstatesDir.mkdirs();

        for (String candle : CANDLES) {
            String name = "candle_skull_" + this.type + "_" + candle;
            String model = "bonezone:block/candle_skull/" + this.type + "/" + candle;
            String modelLit = model + "_lit";

            String content = """
                    {
                      "variants": {
                        "facing=north,lit=false": {
                          "model": "$model"
                        },
                        "facing=south,lit=false": {
                          "model": "$model",
                          "y": 180
                        },
                        "facing=east,lit=false": {
                          "model": "$model",
                          "y": 90
                        },
                        "facing=west,lit=false": {
                          "model": "$model",
                          "y": 270
                        }$notEmpty
                      }
                    }""";

            content = content.replace("$notEmpty", candle.equals("empty") ? "" : """
                            ,
                            "facing=north,lit=true": {
                              "model": "$lit"
                            },
                            "facing=south,lit=true": {
                              "model": "$lit",
                              "y": 180
                            },
                            "facing=east,lit=true": {
                              "model": "$lit",
                              "y": 90
                            },
                            "facing=west,lit=true": {
                              "model": "$lit",
                              "y": 270
                            }
                            """)
                    .replace("$model", model)
                    .replace("$lit", modelLit);
            try (FileWriter writer = new FileWriter(new File(blockstatesDir, name + ".json"))) {
                writer.append(content);
            } catch (IOException e) {

            }
        }
        return this;
    }

    public CandleSkullGen genModels() {
        File modelDir = new File("output/assets/bonezone/models/block/candle_skull/" + type);
        modelDir.mkdirs();
        for (String candle : CANDLES) {
            for (int i = 0; i < 2; i++) {
                if (candle.equals("empty") && i == 1)
                    continue;
                String fileName = candle + (i == 0 ? "" : "_lit");
                String parent = "bonezone:block/candle_skull/candle_skull_template";

                String content = """
                        {
                          "parent": "$parent",
                          "textures": {
                            "drip": "bonezone:block/drip/$color",
                            "candle": "minecraft:block/$mccandle$lit",
                            "skull": "$skull"
                          }
                        }
                        """;

                if (candle.equals("empty")) {
                    parent = "bonezone:block/candle_skull/empty_candle_skull";
                    content = """
                            {
                              "parent": "$parent",
                              "textures": {
                                "skull": "$skull"
                              }
                            }
                            """;
                }
                var mcCandle = (candle.equals("regular") ? "" : candle + "_") + "candle";
                content = content.replace("$parent", parent)
                        .replace("$color", candle)
                        .replace("$mccandle", mcCandle)
                        .replace("$skull", skullTexture)
                        .replace("$lit", (i == 0 ? "" : "_lit"));

                try (FileWriter writer = new FileWriter(new File(modelDir, fileName + ".json"))) {
                    writer.append(content);
                } catch (IOException e) {

                }
            }
        }

        return this;
    }

    public CandleSkullGen genItemFile() {
        var name = "candle_skull_" + type + "_empty";
        File itemDir = new File("output/assets/bonezone/models/item");
        itemDir.mkdirs();

        var content = """
                {
                  "parent": "bonezone:block/candle_skull/$type/empty",
                  "textures": {
                      "skull": "$skull"
                    }
                }
                """;
        content = content.replace("$type", type).replace("$skull", skullTexture);

        try (FileWriter writer = new FileWriter(new File(itemDir, name + ".json"))) {
            writer.append(content);
        } catch (IOException e) {

        }


        return this;
    }
}
