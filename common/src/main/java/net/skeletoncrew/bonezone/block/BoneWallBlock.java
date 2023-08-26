package net.skeletoncrew.bonezone.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallBlock;

public class BoneWallBlock extends WallBlock {

    public BoneWallBlock(Properties properties) {

        super(properties);
    }

    public static Block bone() {

        return new BasicBoneBlock(BasicBoneBlock.BONE);
    }

    public static Block wither() {

        return new BasicBoneBlock(BasicBoneBlock.WITHER);
    }

    public static Block stray() {

        return new BasicBoneBlock(BasicBoneBlock.STRAY);
    }
}