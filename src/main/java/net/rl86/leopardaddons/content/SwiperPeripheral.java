package net.rl86.leopardaddons.content;

import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class SwiperPeripheral implements IPeripheral {
	private final SwiperBE physicalDevice;

	public SwiperPeripheral(SwiperBE device) {
		this.physicalDevice = device;
	}
	
	@Override
	public @NotNull String getType() {
		return "swiper";
	}

	@Override
	public boolean equals(@Nullable IPeripheral other) {
		return this == other || (other instanceof SwiperPeripheral otherP && otherP.physicalDevice == physicalDevice);
	}
	
	@LuaFunction(mainThread=true)
	public final String getLastReadData() throws LuaException {
		return physicalDevice.getData();
	}

	@LuaFunction(mainThread=true)
	public final boolean isFresh() throws LuaException {
		return physicalDevice.isFresh();
	}

}
