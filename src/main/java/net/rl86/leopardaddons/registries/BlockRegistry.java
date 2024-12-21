package net.rl86.leopardaddons.registries;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.rl86.leopardaddons.LeopardAddons;
import net.rl86.leopardaddons.content.CardDeviceBlock;
import net.rl86.leopardaddons.content.ComputerKioskBlock;

public class BlockRegistry {
	
	public static final DeferredRegister<Block> registry = DeferredRegister.create(BuiltInRegistries.BLOCK, LeopardAddons.modId);
	
	public static final DeferredHolder<Block, ComputerKioskBlock> computerKiosk = registry.register("kiosk", ComputerKioskBlock::new);
	public static final DeferredHolder<Block, CardDeviceBlock> cardDevice = registry.register("card_device", CardDeviceBlock::new);
}