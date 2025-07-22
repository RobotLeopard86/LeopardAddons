package net.rl86.leopardaddons.registries;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.rl86.leopardaddons.LeopardAddons;
import net.rl86.leopardaddons.content.CardDeviceBlock;
import net.rl86.leopardaddons.content.ChargedDustBlock;
import net.rl86.leopardaddons.content.ComputerKioskBlock;

public class BlockRegistry {
	
	public static final DeferredRegister<Block> registry = DeferredRegister.create(BuiltInRegistries.BLOCK, LeopardAddons.modId);
	
	public static final DeferredHolder<Block, ComputerKioskBlock> computerKiosk = registry.register("kiosk", ComputerKioskBlock::new);
	public static final DeferredHolder<Block, CardDeviceBlock> cardDevice = registry.register("card_device", CardDeviceBlock::new);

	public static final DeferredHolder<Block, ChargedDustBlock> chargedDustBlock = registry.register("charged_dust_block", ChargedDustBlock::new);
	public static final DeferredHolder<Block, Block> hardenedDustBlock = registry.register("hardened_charged_dust_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE)));

	public static final DeferredHolder<Block, RailBlock> rail = registry.register("rail", () -> new RailBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RAIL)));
	public static final DeferredHolder<Block, PoweredRailBlock> powered = registry.register("powered_rail", () -> new PoweredRailBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POWERED_RAIL)));
	public static final DeferredHolder<Block, DetectorRailBlock> detector = registry.register("detector_rail", () -> new DetectorRailBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DETECTOR_RAIL)));
	public static final DeferredHolder<Block, PoweredRailBlock> activator = registry.register("activator_rail", () -> new PoweredRailBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ACTIVATOR_RAIL)));
}