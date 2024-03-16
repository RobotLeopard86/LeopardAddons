package net.rl86.leopardaddons;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.rl86.leopardaddons.registries.BETypeRegistry;
import net.rl86.leopardaddons.registries.BlockRegistry;
import net.rl86.leopardaddons.registries.ItemRegistry;
import net.rl86.leopardaddons.registries.TabRegistry;

@Mod(LeopardAddons.modId)
public class LeopardAddons
{
    public static final String modId = "leopardaddons";
    public static final Logger logger = LogUtils.getLogger();
    
    public static MinecraftServer server;

    public LeopardAddons() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        BlockRegistry.registry.register(modEventBus);
        ItemRegistry.registry.register(modEventBus);
        BETypeRegistry.registry.register(modEventBus);
        TabRegistry.registry.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        logger.info("Leopard Addons says ¡hola! from common setup!");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        logger.info("Leopard Addons says ¡hola! from server startup!");
        server = event.getServer();
    }

    @Mod.EventBusSubscriber(modid=modId, bus=Mod.EventBusSubscriber.Bus.MOD, value=Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            logger.info("Leopard Addons says ¡hola! from client setup!");
        }
    } 
}
