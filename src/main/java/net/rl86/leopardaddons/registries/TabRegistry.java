package net.rl86.leopardaddons.registries;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rl86.leopardaddons.LeopardAddons;

import java.util.function.Supplier;

public class TabRegistry {
	
	public static final DeferredRegister<CreativeModeTab> registry = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, LeopardAddons.modId);

	public static final Supplier<CreativeModeTab> tab = registry.register("leopardtab", () -> CreativeModeTab.builder()
			.title(Component.translatable("item_group." + LeopardAddons.modId + ".leopardtab"))
			.icon(() -> new ItemStack(ItemRegistry.card.get()))
			.displayItems((params, out) -> {
				out.accept(ItemRegistry.computerKiosk.get());
				out.accept(ItemRegistry.kioskLinker.get());
				out.accept(ItemRegistry.card.get());
				out.accept(ItemRegistry.cardDevice.get());
				out.accept(ItemRegistry.railItm.get());
				out.accept(ItemRegistry.poweredItm.get());
				out.accept(ItemRegistry.detectorItm.get());
				out.accept(ItemRegistry.activatorItm.get());
				out.accept(ItemRegistry.chargedDust.get());
				out.accept(ItemRegistry.refinedDust.get());
				out.accept(ItemRegistry.invisidust.get());
				out.accept(ItemRegistry.chargedBlockItm.get());
			})
	.build());
}