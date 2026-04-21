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
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.rl86.leopardaddons.registries.BETypeRegistry;
import net.rl86.leopardaddons.registries.ItemRegistry;

import java.util.Arrays;

public class SwiperBE extends BlockEntity implements Position {
	private final SwiperPeripheral peripheral;
	private String data = new String();
	private boolean fresh = false;

	public SwiperBE(BlockPos pos, BlockState state) {
		super(BETypeRegistry.swiperBE.get(), pos, state);
		peripheral = new SwiperPeripheral(this);
	}
	
	@Override
	public void loadAdditional(CompoundTag tag, HolderLookup.Provider p) {
		data = tag.get("content").getAsString();
		fresh = tag.getBoolean("fresh");
		super.loadAdditional(tag, p);
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider p) {
		tag.putString("content", data);
		tag.putBoolean("fresh", fresh);
		super.saveAdditional(tag, p);
	}

	public ItemInteractionResult use(Player player, ItemStack stack, InteractionHand hand) {
		if(stack.getItem() instanceof CardItem) {
			CustomData custom = stack.get(DataComponents.CUSTOM_DATA);
			if(custom != null) {
				data = custom.copyTag().get("Data").getAsString();
				fresh = true;
			}
			super.setChanged();
			return ItemInteractionResult.SUCCESS;
		} else {
			return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
		}
	}

	public IPeripheral getCCPeripheral() {
		return peripheral;
	}

	public String getData() {
		fresh = false;
		return data;
	}

	public boolean isFresh() {
		return fresh;
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
