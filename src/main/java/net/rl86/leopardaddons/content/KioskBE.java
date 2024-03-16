package net.rl86.leopardaddons.content;

import dan200.computercraft.shared.computer.blocks.AbstractComputerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.rl86.leopardaddons.registries.BETypeRegistry;

public class KioskBE extends BlockEntity {
	
	private int lcx, lcy, lcz;

	public KioskBE(BlockPos pos, BlockState state) {
		super(BETypeRegistry.kioskBE.get(), pos, state);
		lcx = pos.getX();
		lcy = pos.getY();
		lcz = pos.getZ();
	}
	
	public InteractionResult use(Player player, InteractionHand hand) {
		if(player.isCrouching()) return InteractionResult.PASS;
		if(player.getItemInHand(hand).getItem() instanceof KioskLinkerItem) return InteractionResult.PASS;
		
		BlockPos linkedPos = new BlockPos(lcx, lcy, lcz);
		if(!(getLevel().getBlockEntity(linkedPos) instanceof AbstractComputerBlockEntity)) return InteractionResult.FAIL;
		
		((AbstractComputerBlockEntity)getLevel().getBlockEntity(linkedPos)).use(player, hand);
		
		return InteractionResult.SUCCESS;
	}
	
	public void linkToComputer(int x, int y, int z) {
		lcx = x;
		lcy = y;
		lcz = z;
		setChanged();
	}

	@Override
	public void load(CompoundTag data) {
		lcx = data.getInt("LinkedX");
		lcy = data.getInt("LinkedY");
		lcz = data.getInt("LinkedZ");
		super.load(data);
	}

	@Override
	protected void saveAdditional(CompoundTag data) {
		data.put("LinkedX", IntTag.valueOf(lcx));
		data.put("LinkedY", IntTag.valueOf(lcy));
		data.put("LinkedZ", IntTag.valueOf(lcz));
		super.saveAdditional(data);
	}

}
