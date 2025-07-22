package net.rl86.leopardaddons.registries;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rl86.leopardaddons.LeopardAddons;
import net.rl86.leopardaddons.content.CardDeviceBE;
import net.rl86.leopardaddons.content.ChargedDustBlockBE;
import net.rl86.leopardaddons.content.KioskBE;

import java.util.function.Supplier;

public class BETypeRegistry {
	
	public static final DeferredRegister<BlockEntityType<?>> registry = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, LeopardAddons.modId);
	
	public static final Supplier<BlockEntityType<KioskBE>> kioskBE = registry.register("kiosk_be", () -> BlockEntityType.Builder.of(KioskBE::new, BlockRegistry.computerKiosk.get()).build(null));
	public static final Supplier<BlockEntityType<CardDeviceBE>> cardBE = registry.register("card_dev_be", () -> BlockEntityType.Builder.of(CardDeviceBE::new, BlockRegistry.cardDevice.get()).build(null));
	public static final Supplier<BlockEntityType<ChargedDustBlockBE>> chargedDustBE = registry.register("charged_dust_be", () -> BlockEntityType.Builder.of(ChargedDustBlockBE::new, BlockRegistry.chargedDustBlock.get()).build(null));
}
