package net.skeletoncrew.bonezone.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

public class BasicBoneBlock extends Block {

    public static final Properties BONE = Properties.of().mapColor(MapColor.SAND).requiresCorrectToolForDrops().strength(2.0F).sound(SoundType.BONE_BLOCK);
    public static final Properties WITHER = Properties.of().mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(2.0F).sound(SoundType.BONE_BLOCK);
    public static final Properties STRAY = Properties.of().mapColor(MapColor.SNOW).requiresCorrectToolForDrops().strength(2.0F).sound(SoundType.BONE_BLOCK);

    public BasicBoneBlock(Properties properties) {

        super(properties);
    }

    public static Block bone() {

        return new BasicBoneBlock(BONE);
    }

    public static Block wither() {

        return new BasicBoneBlock(WITHER);
    }

    public static Block stray() {

        return new BasicBoneBlock(STRAY);
    }
}