package net.rl86.leopardaddons.registries;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.rl86.leopardaddons.LeopardAddons;
import net.rl86.leopardaddons.content.CardItem;
import net.rl86.leopardaddons.content.KioskLinkerItem;

public class ItemRegistry {
	
	public static final DeferredRegister<Item> registry = DeferredRegister.create(ForgeRegistries.ITEMS, LeopardAddons.modId);
	
	public static final RegistryObject<BlockItem> computerKiosk = registry.register("kiosk", () -> new BlockItem(BlockRegistry.computerKiosk.get(), new Item.Properties()));
	public static final RegistryObject<BlockItem> cardDevice = registry.register("card_device", () -> new BlockItem(BlockRegistry.cardDevice.get(), new Item.Properties()));
	public static final RegistryObject<KioskLinkerItem> kioskLinker = registry.register("linker", () -> new KioskLinkerItem());
	public static final RegistryObject<CardItem> card = registry.register("card", () -> new CardItem());
}