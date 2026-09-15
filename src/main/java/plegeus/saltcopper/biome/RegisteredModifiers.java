package plegeus.saltcopper.biome;

import org.openjdk.nashorn.internal.runtime.regexp.joni.constants.RegexState;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import plegeus.saltcopper.SaltCopper;

/**
 * Ores
 * Class to register the biome modifiers themselves.
 */
public class RegisteredModifiers {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
        .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, bootstrap -> {
            bootstrap.register(
                ResourceKey.create(
                    NeoForgeRegistries.Keys.BIOME_MODIFIERS, 
                    ResourceLocation.fromNamespaceAndPath(SaltCopper.MODID, "override_vanilla_ores")
                ), 
                new VanillaOresModifier(
                    bootstrap.lookup(Registries.BIOME)
                        .getOrThrow(Tags.Biomes.IS_OVERWORLD),
                    bootstrap.lookup(Registries.PLACED_FEATURE)
                )
            );
        });

}

