package net.rl86.leopardaddons.registries;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.rl86.leopardaddons.LeopardAddons;
import net.rl86.leopardaddons.content.CardDeviceBlock;
import net.rl86.leopardaddons.content.ComputerKioskBlock;

public class BlockRegistry {
	
	public static final DeferredRegister<Block> registry = DeferredRegister.create(ForgeRegistries.BLOCKS, LeopardAddons.modId);
	
	public static final RegistryObject<Block> computerKiosk = registry.register("kiosk", () -> new ComputerKioskBlock());
	public static final RegistryObject<Block> cardDevice = registry.register("card_device", () -> new CardDeviceBlock());
}