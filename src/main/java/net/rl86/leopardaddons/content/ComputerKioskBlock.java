package net.rl86.leopardaddons.content;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.rl86.leopardaddons.util.VoxelUtils;

public class ComputerKioskBlock extends Block implements EntityBlock {
	
	public static final DirectionProperty facing = HorizontalDirectionalBlock.FACING;

	public ComputerKioskBlock() {
		super(BlockBehaviour.Properties.of().strength(2).isRedstoneConductor((block, level, blockPos) -> false));
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> stateBuilder) {
		super.createBlockStateDefinition(stateBuilder.add(facing));
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext collision) {
		return VoxelUtils.rotateVoxel(Direction.NORTH, state.getValue(facing), Shapes.box(0.09375, 0, 0.21875, 0.90625, 1.3125, 0.78125));
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState baseState = super.getStateForPlacement(context);
		baseState = baseState.setValue(facing, context.getHorizontalDirection());
		return baseState;
	}

	@Override
	public MutableComponent getName() {
		return Component.translatable("block.leopardaddons.kiosk");
	}
	
	@Override
    public final InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        return world.getBlockEntity(pos) instanceof KioskBE kiosk ? kiosk.use(player, hand) : InteractionResult.PASS;
    }

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new KioskBE(pos, state);
	}

}
