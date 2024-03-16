package net.rl86.leopardaddons.registries;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.rl86.leopardaddons.LeopardAddons;
import net.rl86.leopardaddons.content.CardDeviceBE;
import net.rl86.leopardaddons.content.KioskBE;

public class BETypeRegistry {
	
	public static final DeferredRegister<BlockEntityType<?>> registry = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, LeopardAddons.modId);
	
	public static final RegistryObject<BlockEntityType<KioskBE>> kioskBE = registry.register("kiosk_be", () -> BlockEntityType.Builder.of(KioskBE::new, BlockRegistry.computerKiosk.get()).build(null));
	public static final RegistryObject<BlockEntityType<CardDeviceBE>> cardBE = registry.register("card_dev_be", () -> BlockEntityType.Builder.of(CardDeviceBE::new, BlockRegistry.cardDevice.get()).build(null));
}
