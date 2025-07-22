package net.rl86.leopardaddons.registries;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rl86.leopardaddons.LeopardAddons;
import net.rl86.leopardaddons.content.CardItem;
import net.rl86.leopardaddons.content.KioskLinkerItem;

public class ItemRegistry {
	
	public static final DeferredRegister<Item> registry = DeferredRegister.create(BuiltInRegistries.ITEM, LeopardAddons.modId);
	
	public static final DeferredHolder<Item, BlockItem> computerKiosk = registry.register("kiosk", () -> new BlockItem(BlockRegistry.computerKiosk.get(), new Item.Properties()));
	public static final DeferredHolder<Item, BlockItem> cardDevice = registry.register("card_device", () -> new BlockItem(BlockRegistry.cardDevice.get(), new Item.Properties()));
	public static final DeferredHolder<Item, KioskLinkerItem> kioskLinker = registry.register("linker", KioskLinkerItem::new);
	public static final DeferredHolder<Item, CardItem> card = registry.register("card", CardItem::new);

	public static final DeferredHolder<Item, BlockItem> railItm = registry.register("rail", () -> new BlockItem(BlockRegistry.rail.get(), new Item.Properties()));
	public static final DeferredHolder<Item, BlockItem> poweredItm = registry.register("powered_rail", () -> new BlockItem(BlockRegistry.powered.get(), new Item.Properties()));
	public static final DeferredHolder<Item, BlockItem> detectorItm = registry.register("detector_rail", () -> new BlockItem(BlockRegistry.detector.get(), new Item.Properties()));
	public static final DeferredHolder<Item, BlockItem> activatorItm = registry.register("activator_rail", () -> new BlockItem(BlockRegistry.activator.get(), new Item.Properties()));

	public static final DeferredHolder<Item, BlockItem> chargedBlockItm = registry.register("charged_dust_block", () -> new BlockItem(BlockRegistry.chargedDustBlock.get(), new Item.Properties()));
	public static final DeferredHolder<Item, BlockItem> hardenedBlockItm = registry.register("hardened_charged_dust_block", () -> new BlockItem(BlockRegistry.hardenedDustBlock.get(), new Item.Properties()));
	public static final DeferredHolder<Item, Item> chargedDust = registry.register("charged_dust", () -> new Item(new Item.Properties()));
	public static final DeferredHolder<Item, Item> refinedDust = registry.register("refined_dust", () -> new Item(new Item.Properties()));
	public static final DeferredHolder<Item, Item> invisidust = registry.register("invisidust", () -> new Item(new Item.Properties().fireResistant()));
}