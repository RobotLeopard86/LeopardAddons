package net.rl86.leopardaddons.content;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.shared.common.AbstractContainerBlockEntity;
import dan200.computercraft.shared.container.BasicWorldlyContainer;
import net.minecraft.core.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
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
	private final CardDevicePeripheral peripheral;

	public CardDeviceBE(BlockPos pos, BlockState state) {
		super(BETypeRegistry.cardBE.get(), pos, state);
		peripheral = new CardDevicePeripheral(this);
	}

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
	public void loadAdditional(CompoundTag tag, HolderLookup.Provider p) {
		super.loadAdditional(tag, p);
		ContainerHelper.loadAllItems(tag, inventory, p);
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider p ) {
		super.saveAdditional(tag, p);
		ContainerHelper.saveAllItems(tag, inventory, p);
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
				itm.set(DataComponents.CUSTOM_NAME, Component.literal(name));
			}
			inventory.set(0, itm);
			level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(CardDeviceBlock.empty, false));
			super.setChanged();
		}
	}

	public ItemInteractionResult use(Player player) {
		return use(player, ItemStack.EMPTY, InteractionHand.MAIN_HAND);
	}

	public ItemInteractionResult use(Player player, ItemStack stack, InteractionHand hand) {
		if(inventory.get(0).isEmpty()) {
			if(!(stack.getItem() instanceof CardItem)) {
				return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
			} else {
				inventory.set(0, stack);
				if(player.isCreative()) player.setItemInHand(hand, ItemStack.EMPTY);
				level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(CardDeviceBlock.empty, false));
				super.setChanged();
				return ItemInteractionResult.SUCCESS;
			}
		} else {
			if(stack.getItem() instanceof CardItem) {
				player.setItemInHand(hand, inventory.get(0));
				inventory.set(0, stack);
				super.setChanged();
				return ItemInteractionResult.SUCCESS;
			} else if(stack.isEmpty()){
				player.setItemInHand(hand, inventory.get(0));
				inventory.set(0, ItemStack.EMPTY);
				level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(CardDeviceBlock.empty, true));
				super.setChanged();
				return ItemInteractionResult.SUCCESS;
			} else {
				return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
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

	@Override
	public NonNullList<ItemStack> getItems() {
		return NonNullList.of(ItemStack.EMPTY);
	}

	@Override
	protected void setItems(NonNullList<ItemStack> p_332640_) {}
}
