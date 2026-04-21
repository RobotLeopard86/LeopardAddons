package net.rl86.leopardaddons.content;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.PeripheralCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Position;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.rl86.leopardaddons.registries.BETypeRegistry;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class RemotePeripheralBE extends BlockEntity implements Position {
	private int lpx, lpy, lpz;
	private Direction lpDir;
	private boolean configured = false;

	public RemotePeripheralBE(BlockPos pos, BlockState state) {
		super(BETypeRegistry.remotePeripheralBE.get(), pos, state);
	}

	public void configureOnPlace(BlockPos pos, Direction dir) {
		lpx = pos.getX();
		lpy = pos.getY();
		lpz = pos.getZ();
		lpDir = dir;
		configured = true;
		super.setChanged();
	}

	@Override
	public void loadAdditional(CompoundTag data, HolderLookup.Provider p) {
		lpx = data.getInt("LinkedX");
		lpy = data.getInt("LinkedY");
		lpz = data.getInt("LinkedZ");
		lpDir = switch(data.getInt("LinkedDir")) {
			case 0 -> Direction.DOWN;
			case 1 -> Direction.UP;
			case 2 -> Direction.NORTH;
			case 3 -> Direction.SOUTH;
			case 4 -> Direction.EAST;
			case 5 -> Direction.WEST;
            default -> throw new IllegalStateException("Unexpected value");
        };
		super.loadAdditional(data, p);
	}

	@Override
	protected void saveAdditional(CompoundTag data, HolderLookup.Provider p) {
		data.put("LinkedX", IntTag.valueOf(lpx));
		data.put("LinkedY", IntTag.valueOf(lpy));
		data.put("LinkedZ", IntTag.valueOf(lpz));
		data.put("LinkedDir", IntTag.valueOf(switch(lpDir) {
			case Direction.DOWN -> 0;
			case Direction.UP -> 1;
			case Direction.NORTH -> 2;
			case Direction.SOUTH -> 3;
			case Direction.EAST -> 4;
			case Direction.WEST -> 5;
		}));
		super.saveAdditional(data, p);
	}

	public IPeripheral getCCPeripheral() {
		if(!configured) {
			return new IPeripheral() {
				@Override
				public @NotNull String getType() {
					return "rpdead";
				}

				@Override
				public boolean equals(@Nullable IPeripheral other) {
					return this == other;
				}
			};
		}
		BlockPos linked = new BlockPos(lpx, lpy, lpz);
		IPeripheral peripheral = level.getCapability(PeripheralCapability.get(), linked, lpDir);
		if(peripheral != null) return peripheral;
		return new IPeripheral() {
			@Override
			public @NotNull String getType() {
				return "rpdead";
			}

			@Override
			public boolean equals(@Nullable IPeripheral other) {
				return this == other;
			}
		};
	}

	@Override
	public double x() {
		if(getBlockState().getValue(RemotePeripheralBlock.facing) == Direction.WEST || getBlockState().getValue(RemotePeripheralBlock.facing) == Direction.EAST) {
			return this.getBlockPos().getX() + (getBlockState().getValue(RemotePeripheralBlock.facing) == Direction.WEST ? -1 : 1);
		}
		return this.getBlockPos().getX();
	}

	@Override
	public double y() {
		return this.getBlockPos().getY();
	}

	@Override
	public double z() {
		if(getBlockState().getValue(RemotePeripheralBlock.facing) == Direction.NORTH || getBlockState().getValue(RemotePeripheralBlock.facing) == Direction.SOUTH) {
			return this.getBlockPos().getZ() + (getBlockState().getValue(RemotePeripheralBlock.facing) == Direction.SOUTH ? -1 : 1);
		}
		return this.getBlockPos().getZ();
	}
}
