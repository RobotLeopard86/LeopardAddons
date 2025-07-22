package net.rl86.leopardaddons.content;

import dan200.computercraft.shared.computer.blocks.AbstractComputerBlockEntity;
import dan200.computercraft.shared.network.container.ComputerContainerData;
import dan200.computercraft.shared.platform.PlatformHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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

	public ItemInteractionResult use(Player player) {
		return use(player, ItemStack.EMPTY, InteractionHand.MAIN_HAND);
	}
	
	public ItemInteractionResult use(Player player, ItemStack stack, InteractionHand hand) {
		if(player.isCrouching()) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
		if(stack.getItem() instanceof KioskLinkerItem) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
		
		BlockPos linkedPos = new BlockPos(lcx, lcy, lcz);
		if(getLevel().getBlockEntity(linkedPos) instanceof AbstractComputerBlockEntity computer) {
			if (!getLevel().isClientSide && computer.isUsable(player)) {
				var serverComputer = computer.createServerComputer();
				serverComputer.turnOn();

				ItemStack _stack = new ItemStack(this.getBlockState().getBlock());
				_stack.applyComponents(computer.collectComponents());

				PlatformHelper.get().openMenu(player, computer.getDisplayName(), computer, new ComputerContainerData(serverComputer, _stack));
			}
			return ItemInteractionResult.sidedSuccess(level.isClientSide);
		} else {
			return ItemInteractionResult.FAIL;
		}
	}
	
	public void linkToComputer(int x, int y, int z) {
		lcx = x;
		lcy = y;
		lcz = z;
		setChanged();
	}

	@Override
	public void loadAdditional(CompoundTag data, HolderLookup.Provider p) {
		lcx = data.getInt("LinkedX");
		lcy = data.getInt("LinkedY");
		lcz = data.getInt("LinkedZ");
		super.loadAdditional(data, p);
	}

	@Override
	protected void saveAdditional(CompoundTag data, HolderLookup.Provider p) {
		data.put("LinkedX", IntTag.valueOf(lcx));
		data.put("LinkedY", IntTag.valueOf(lcy));
		data.put("LinkedZ", IntTag.valueOf(lcz));
		super.saveAdditional(data, p);
	}

}
