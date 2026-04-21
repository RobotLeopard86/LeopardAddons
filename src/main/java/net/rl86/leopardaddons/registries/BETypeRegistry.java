package net.rl86.leopardaddons.registries;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rl86.leopardaddons.LeopardAddons;
import net.rl86.leopardaddons.content.*;

import java.util.function.Supplier;

public class BETypeRegistry {
	
	public static final DeferredRegister<BlockEntityType<?>> registry = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, LeopardAddons.modId);
	
	public static final Supplier<BlockEntityType<KioskBE>> kioskBE = registry.register("kiosk_be", () -> BlockEntityType.Builder.of(KioskBE::new, BlockRegistry.computerKiosk.get()).build(null));
	public static final Supplier<BlockEntityType<CardDeviceBE>> cardBE = registry.register("card_dev_be", () -> BlockEntityType.Builder.of(CardDeviceBE::new, BlockRegistry.cardDevice.get()).build(null));
	public static final Supplier<BlockEntityType<SwiperBE>> swiperBE = registry.register("swiper_be", () -> BlockEntityType.Builder.of(SwiperBE::new, BlockRegistry.swiper.get()).build(null));
	public static final Supplier<BlockEntityType<RemotePeripheralBE>> remotePeripheralBE = registry.register("remote_peripheral_be", () -> BlockEntityType.Builder.of(RemotePeripheralBE::new, BlockRegistry.remotePeripheral.get()).build(null));
	public static final Supplier<BlockEntityType<ChargedDustBlockBE>> chargedDustBE = registry.register("charged_dust_be", () -> BlockEntityType.Builder.of(ChargedDustBlockBE::new, BlockRegistry.chargedDustBlock.get()).build(null));
}
