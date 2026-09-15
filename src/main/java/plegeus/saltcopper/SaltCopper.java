package plegeus.saltcopper;

import java.util.Set;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.RegistryAccess;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import plegeus.saltcopper.biome.DeferredModifier;
import plegeus.saltcopper.biome.RegisteredModifiers;
import plegeus.saltcopper.config.Config;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(SaltCopper.MODID)
public class SaltCopper {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "saltcopper";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    /**
     * Set at ServerStartEvent for Level.OVERWORLD.
     */
    public static ServerLevel level;
    /**
     * See SaltCopper.level.
     */
    public static RegistryAccess registries;

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public SaltCopper(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::onGatherData);

        new DeferredModifier(modEventBus);


        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (SaltCopper) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        
    }

    public void onGatherData(GatherDataEvent event) {
        
        event.getGenerator()
            .addProvider(
                // Tell generator to run only when server data are generating
                event.includeServer(),
                new DatapackBuiltinEntriesProvider(
                    event.getGenerator().getPackOutput(), 
                    event.getLookupProvider(), 
                    RegisteredModifiers.BUILDER,
                    Set.of(MODID)
                )    
            );
    }

}
