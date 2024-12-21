package net.rl86.leopardaddons.content;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.shared.common.AbstractContainerBlockEntity;
import dan200.computercraft.shared.container.BasicWorldlyContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.rl86.leopardaddons.registries.BETypeRegistry;
import net.rl86.leopardaddons.registries.ItemRegistry;

public class CardDeviceBE extends AbstractContainerBlockEntity implements BasicWorldlyContainer, Position {
	private static final int[] SLOTS = { 0 };
	private final NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);
	private CardDevicePeripheral peripheral;

	public CardDeviceBE(BlockPos pos, BlockState state) {
		super(BETypeRegistry.cardBE.get(), pos, state);
		peripheral = new CardDevicePeripheral(this);
	}

	@Override
	public NonNullList<ItemStack> getContents() {
		return inventory;
	}

	@Override
	public int[] getSlotsForFace(Direction dir) {
		return SLOTS;
	}

	@Override
	protected AbstractContainerMenu createMenu(int p_58627_, Inventory p_58628_) {
		return null;
	}
	
	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		ContainerHelper.loadAllItems(tag, inventory);
	}

	@Override
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		ContainerHelper.saveAllItems(tag, inventory);
	}
	
	public void forceDrop() {
		if(!inventory.get(0).isEmpty()) {
			DefaultDispenseItemBehavior.spawnItem(getLevel(), inventory.get(0), 2, this.getBlockState().getValue(CardDeviceBlock.facing), this);
			inventory.set(0, ItemStack.EMPTY);
			level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(CardDeviceBlock.empty, true));
			super.setChanged();
		}
	}
	
	public void generateCard(String name) {
		if(inventory.get(0).isEmpty()) {
			ItemStack itm = new ItemStack(ItemRegistry.card.get());
			if(!name.equalsIgnoreCase("")) {
				itm.setHoverName(Component.literal(name));
			}
			inventory.set(0, itm);
			level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(CardDeviceBlock.empty, false));
			super.setChanged();
		}
	}

	public InteractionResult use(Player player, InteractionHand hand) {
		if(inventory.get(0).isEmpty()) {
			if(!(player.getItemInHand(hand).getItem() instanceof CardItem)) {
				return InteractionResult.PASS;
			} else {
				inventory.set(0, player.getItemInHand(hand));
				if(player.isCreative()) player.setItemInHand(hand, ItemStack.EMPTY);
				level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(CardDeviceBlock.empty, false));
				super.setChanged();
				return InteractionResult.SUCCESS;
			}
		} else {
			if(player.getItemInHand(hand).getItem() instanceof CardItem) {
				ItemStack pItem = player.getItemInHand(hand);
				player.setItemInHand(hand, inventory.get(0));
				inventory.set(0, pItem);
				super.setChanged();
				return InteractionResult.SUCCESS;
			} else if(player.getItemInHand(hand).isEmpty()){
				player.setItemInHand(hand, inventory.get(0));
				inventory.set(0, ItemStack.EMPTY);
				level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(CardDeviceBlock.empty, true));
				super.setChanged();
				return InteractionResult.SUCCESS;
			} else {
				return InteractionResult.PASS;
			}
		}
	}

	public IPeripheral getCCPeripheral() {
		return peripheral;
	}

	@Override
	public double x() {
		if(getBlockState().getValue(CardDeviceBlock.facing) == Direction.WEST || getBlockState().getValue(CardDeviceBlock.facing) == Direction.EAST) {
			return this.getBlockPos().getX() + (getBlockState().getValue(CardDeviceBlock.facing) == Direction.WEST ? -1 : 1);
		}
		return this.getBlockPos().getX();
	}

	@Override
	public double y() {
		return this.getBlockPos().getY();
	}

	@Override
	public double z() {
		if(getBlockState().getValue(CardDeviceBlock.facing) == Direction.NORTH || getBlockState().getValue(CardDeviceBlock.facing) == Direction.SOUTH) {
			return this.getBlockPos().getZ() + (getBlockState().getValue(CardDeviceBlock.facing) == Direction.SOUTH ? -1 : 1);
		}
		return this.getBlockPos().getZ();
	}

}
