package net.skeletoncrew.bonezone.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class BasicBoneBlock extends Block {

    public static final Properties BONE = Properties.of(Material.STONE, MaterialColor.SAND).requiresCorrectToolForDrops().strength(2.0F).sound(SoundType.BONE_BLOCK);
    public static final Properties WITHER = Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(2.0F).sound(SoundType.BONE_BLOCK);

    public BasicBoneBlock(Properties properties) {

        super(properties);
    }

    public static Block bone() {

        return new BasicBoneBlock(BONE);
    }

    public static Block wither() {

        return new BasicBoneBlock(WITHER);
    }
}