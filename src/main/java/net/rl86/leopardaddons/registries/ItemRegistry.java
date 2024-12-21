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
}