package net.rl86.leopardaddons.content;

import javax.annotation.Nullable;

import dan200.computercraft.api.lua.Coerced;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.component.CustomData;

public class CardDevicePeripheral implements IPeripheral {
	private final CardDeviceBE physicalDevice;
	
	public CardDevicePeripheral(CardDeviceBE device) {
		this.physicalDevice = device;
	}
	
	@Override
	public String getType() {
		return "card_device";
	}

	@Override
	public boolean equals(@Nullable IPeripheral other) {
		return this == other || (other instanceof CardDevicePeripheral otherP && otherP.physicalDevice == physicalDevice);
	}
	
	@LuaFunction(mainThread=true)
	public final String readCard() throws LuaException {
		if(physicalDevice.getContents().get(0).isEmpty()) throw new LuaException("Card device is empty!");
		if(!(physicalDevice.getContents().get(0).getItem() instanceof CardItem)) throw new LuaException("Card device contains something that isn't a card... somehow... what have you done?");
		
		CompoundTag data = physicalDevice.getContents().get(0).get(DataComponents.CUSTOM_DATA).copyTag();
		return (data.contains("Data") ? data.get("Data").getAsString() : "");
	}
	
	@LuaFunction(mainThread=true)
	public final void writeToCard(Coerced<String> value) throws LuaException {
		if(physicalDevice.getContents().get(0).isEmpty()) throw new LuaException("Card device is empty!");
		if(!(physicalDevice.getContents().get(0).getItem() instanceof CardItem)) throw new LuaException("Card device contains something that isn't a card... somehow... what have you done?");
		
		CompoundTag data = physicalDevice.getContents().get(0).get(DataComponents.CUSTOM_DATA).copyTag();
		data.putString("Data", value.value());
		physicalDevice.getContents().get(0).set(DataComponents.CUSTOM_DATA, CustomData.of(data));
	}
	
	@LuaFunction(mainThread=true)
	public final void ejectCard() throws LuaException {
		if(physicalDevice.getContents().get(0).isEmpty()) throw new LuaException("Card device is empty!");
		
		physicalDevice.forceDrop();
	}
	
	@LuaFunction(mainThread=true)
	public final boolean hasCard() {
		return physicalDevice.getContents().get(0).isEmpty();
	}
	
	@LuaFunction(mainThread=true)
	public final void generateNamedCard(Coerced<String> itemName) throws LuaException {
		if(!physicalDevice.getContents().get(0).isEmpty()) throw new LuaException("Card device is not empty!");
		
		physicalDevice.generateCard(itemName.value());
	}
	
	@LuaFunction(mainThread=true)
	public final void generateCard() throws LuaException {
		generateNamedCard(new Coerced<String>(""));
	}
}
