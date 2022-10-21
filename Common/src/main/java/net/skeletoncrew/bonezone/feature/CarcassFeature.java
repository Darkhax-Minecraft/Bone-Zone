package net.skeletoncrew.bonezone.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.skeletoncrew.bonezone.Constants;
import net.skeletoncrew.bonezone.block.CarcassBlock;

public class CarcassFeature extends Feature<CarcassFeatureConfiguration> {

    public CarcassFeature() {
        super(CarcassFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<CarcassFeatureConfiguration> context) {
        var rand = context.random();

        if (rand.nextDouble() < 0.9f) //diminish chances
            return false;

        var level = context.level();
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(rand);

        BlockPos origin = context.origin();
        BlockPos placedOn = origin.below();
        BlockPos neighbour = origin.relative(direction);
        BlockPos placedOnNeighbour = neighbour.below();
        BlockPos skull = origin.relative(direction.getOpposite());
        BlockPos skullBelow = skull.below();


        BlockState block = level.getBlockState(origin);
        BlockState blockBelow = level.getBlockState(placedOn);

        BlockState blockNeighbour = level.getBlockState(neighbour);
        BlockState blockBelowNeighbour = level.getBlockState(placedOnNeighbour);

        BlockState blockFront = level.getBlockState(skull);
        BlockState blockFrontBelow = level.getBlockState(skullBelow);

        if (
                blockBelow.is(context.config().blockIds())
                        && block.isAir()
                        && blockNeighbour.isAir()
                        && blockFront.isAir()
                        && blockBelowNeighbour.isFaceSturdy(level, placedOnNeighbour, Direction.UP)
                        && blockFrontBelow.isFaceSturdy(level, skullBelow, Direction.UP)
        ) {
            if (Constants.CARCASS.get() instanceof CarcassBlock carcass) {
                BlockState originState = carcass.defaultBlockState().setValue(CarcassBlock.FACING, direction).setValue(CarcassBlock.HALF, Half.TOP);
                BlockState neighbourState = carcass.defaultBlockState().setValue(CarcassBlock.FACING, direction).setValue(CarcassBlock.HALF, Half.BOTTOM);
                BlockState skullState = context.config().spinalSkull().defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, direction.getOpposite());

                level.setBlock(origin, originState, 3);
                level.setBlock(neighbour, neighbourState, 3);
                level.setBlock(skull, skullState, 3);

                return true;
            }
        }
        return false;
    }
}
