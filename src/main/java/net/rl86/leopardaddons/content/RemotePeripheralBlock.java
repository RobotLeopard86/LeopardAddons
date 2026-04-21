package net.rl86.leopardaddons.content;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.rl86.leopardaddons.util.VoxelUtils;
import org.jetbrains.annotations.NotNull;

public class RemotePeripheralBlock extends Block implements EntityBlock {
    public RemotePeripheralBlock() {
        super(Properties.of().noCollission().instabreak().pushReaction(PushReaction.DESTROY));
    }

    public static final DirectionProperty facing = DirectionalBlock.FACING;

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        super.createBlockStateDefinition(stateBuilder.add(facing));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState baseState = super.getStateForPlacement(context);
        baseState = baseState.setValue(facing, context.getClickedFace().getOpposite());
        return baseState;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext collision) {
        if(state.getValue(facing) == Direction.UP || state.getValue(facing) == Direction.DOWN) return Shapes.or(
                Shapes.box(0.125000, 0.000000, 0.187500, 0.187500, 0.062500, 0.812500),
                Shapes.box(0.812500, 0.000000, 0.187500, 0.875000, 0.062500, 0.812500),
                Shapes.box(0.187500, 0.000000, 0.125000, 0.812500, 0.062500, 0.875000),
                Shapes.box(0.656250, 0.062500, 0.656250, 0.718750, 0.375000, 0.718750)
        );
        return VoxelUtils.rotateVoxel(Direction.NORTH, state.getValue(facing), Shapes.or(
                Shapes.box(0.187500, 0.000000, 0.187500, 0.250000, 0.062500, 0.812500),
                Shapes.box(0.187500, 0.687500, 0.187500, 0.250000, 0.750000, 0.812500),
                Shapes.box(0.187500, 0.062500, 0.125000, 0.250000, 0.687500, 0.875000),
                Shapes.box(0.000000, 0.125000, 0.250000, 0.062500, 0.625000, 0.750000),
                Shapes.box(0.062500, 0.500000, 0.312500, 0.187500, 0.562500, 0.375000),
                Shapes.box(0.062500, 0.500000, 0.625000, 0.187500, 0.562500, 0.687500),
                Shapes.box(0.062500, 0.187500, 0.312500, 0.187500, 0.250000, 0.375000),
                Shapes.box(0.062500, 0.187500, 0.625000, 0.187500, 0.250000, 0.687500),
                Shapes.box(0.125000, 0.562500, 0.312500, 0.187500, 0.875000, 0.375000)
        ));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RemotePeripheralBE(pos, state);
    }
}
