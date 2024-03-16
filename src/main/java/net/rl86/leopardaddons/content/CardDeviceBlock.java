package net.rl86.leopardaddons.content;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;

public class CardDeviceBlock extends Block implements EntityBlock {

	public CardDeviceBlock() {
		super(Properties.of());
	}
	
	public static final DirectionProperty facing = HorizontalDirectionalBlock.FACING;
	public static final BooleanProperty empty = BooleanProperty.create("empty");
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> stateBuilder) {
		super.createBlockStateDefinition(stateBuilder.add(facing, empty));
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState baseState = super.getStateForPlacement(context);
		baseState = baseState.setValue(facing, context.getHorizontalDirection());
		baseState = baseState.setValue(empty, true);
		return baseState;
	}

	@Override
	public MutableComponent getName() {
		return Component.translatable("block.leopardaddons.card_device");
	}
	
	@Override
    public final InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		return world.getBlockEntity(pos) instanceof CardDeviceBE dev ? dev.use(player, hand) : InteractionResult.PASS;
    }

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new CardDeviceBE(pos, state);
	}

}
