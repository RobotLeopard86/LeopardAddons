package net.rl86.leopardaddons.content;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.rl86.leopardaddons.util.VoxelUtils;
import org.jetbrains.annotations.NotNull;

public class SwiperBlock extends Block /*implements EntityBlock*/ {

	public SwiperBlock() {
		super(Properties.of().noCollission().instabreak().pushReaction(PushReaction.DESTROY));
	}
	
	public static final DirectionProperty facing = HorizontalDirectionalBlock.FACING;
	public static final BooleanProperty vertical = BooleanProperty.create("vertical");
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> stateBuilder) {
		super.createBlockStateDefinition(stateBuilder.add(facing, vertical));
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState baseState = super.getStateForPlacement(context);
		baseState = baseState.setValue(vertical, context.getClickedFace() != Direction.UP && context.getClickedFace() != Direction.DOWN);
		Direction[] lookings = context.getNearestLookingDirections();
		for(Direction direction : lookings) {
			if(direction.getAxis().isHorizontal()) {
				baseState = baseState.setValue(facing, direction);
				if (baseState.canSurvive(context.getLevel(), context.getClickedPos())) {
					return baseState;
				}
			}
		}
		return baseState;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext collision) {
		return VoxelUtils.rotateVoxel(Direction.WEST, state.getValue(facing), state.getValue(vertical) ? Shapes.box(0.0, 0.125, 0.3125, 0.25, 0.875, 0.6875) : Shapes.box(0.125, 0.0, 0.3125, 0.875, 0.25, 0.6875));
	}
	
	/*@Override
    protected final @NotNull InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
		return world.getBlockEntity(pos) instanceof CardDeviceBE dev ? dev.use(player).result() : InteractionResult.PASS;
    }

	@Override
	protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		return world.getBlockEntity(pos) instanceof CardDeviceBE dev ? dev.use(player, stack, hand) : ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new CardDeviceBE(pos, state);
	}*/

}
