package net.rl86.leopardaddons.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.rl86.leopardaddons.LeopardAddons;

public class TabRegistry {
	
	public static final DeferredRegister<CreativeModeTab> registry = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LeopardAddons.modId);

	public static final RegistryObject<CreativeModeTab> tab = registry.register("leopardtab", () -> CreativeModeTab.builder()
			.title(Component.translatable("item_group." + LeopardAddons.modId + ".leopardtab"))
			.icon(() -> new ItemStack(ItemRegistry.card.get()))
			.displayItems((params, out) -> {
				out.accept(ItemRegistry.computerKiosk.get());
				out.accept(ItemRegistry.kioskLinker.get());
				out.accept(ItemRegistry.card.get());
				out.accept(ItemRegistry.cardDevice.get());
			})
	.build());
}