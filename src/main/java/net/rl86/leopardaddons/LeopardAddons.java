package net.rl86.leopardaddons;

import com.mojang.logging.LogUtils;
import dan200.computercraft.api.peripheral.PeripheralCapability;
import net.minecraft.server.MinecraftServer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.rl86.leopardaddons.registries.BETypeRegistry;
import net.rl86.leopardaddons.registries.BlockRegistry;
import net.rl86.leopardaddons.registries.ItemRegistry;
import net.rl86.leopardaddons.registries.TabRegistry;
import org.slf4j.Logger;

@Mod(LeopardAddons.modId)
public class LeopardAddons
{
    public static final String modId = "leopardaddons";
    public static final Logger logger = LogUtils.getLogger();
    
    public static MinecraftServer server;

    public LeopardAddons(IEventBus modEventBus) {
        BlockRegistry.registry.register(modEventBus);
        ItemRegistry.registry.register(modEventBus);
        BETypeRegistry.registry.register(modEventBus);
        TabRegistry.registry.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerCapabilities);
        NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        logger.info("Leopard Addons says ¡hola! from common setup!");
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(PeripheralCapability.get(), BETypeRegistry.cardBE.get(), (be, side) -> be.getCCPeripheral());
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        logger.info("Leopard Addons says ¡hola! from server startup!");
        server = event.getServer();
    }

    @EventBusSubscriber(modid=modId, value=Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            logger.info("Leopard Addons says ¡hola! from client setup!");
        }
    } 
}
