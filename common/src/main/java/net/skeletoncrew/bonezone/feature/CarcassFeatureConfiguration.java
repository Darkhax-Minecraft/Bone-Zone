package net.skeletoncrew.bonezone.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record CarcassFeatureConfiguration(HolderSet<Block> blockIds, Block spinalSkull) implements FeatureConfiguration {

    public static Codec<CarcassFeatureConfiguration> CODEC = RecordCodecBuilder.create(
            conf -> conf.group(
                            RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("blockIds").forGetter(CarcassFeatureConfiguration::blockIds),
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("spinalSkull").forGetter(CarcassFeatureConfiguration::spinalSkull)
                    )
                    .apply(conf, CarcassFeatureConfiguration::new));
}
