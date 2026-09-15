package plegeus.saltcopper.biome;

import java.util.function.Supplier;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import plegeus.saltcopper.SaltCopper;

/**
 * DeferredModifier
 * 
 */
public class DeferredModifier {

    public DeferredModifier(IEventBus modBus) {
        BIOME_MODIFIERS.register(modBus);
    }
    
    private static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIERS =
        DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, SaltCopper.MODID);

    // Template to generate a json file.
    public static final Supplier<MapCodec<VanillaOresModifier>> OVERRIDE_VANILLA_ORES =
        BIOME_MODIFIERS.register(
            "override_vanilla_ores", 
            () -> RecordCodecBuilder.mapCodec(instance -> 
                instance.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(VanillaOresModifier::biomes),
                    RegistryOps.retrieveGetter(Registries.PLACED_FEATURE)
                ).apply(instance, VanillaOresModifier::new)
            )
        );

}
