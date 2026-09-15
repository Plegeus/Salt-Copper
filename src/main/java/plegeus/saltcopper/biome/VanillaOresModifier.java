package plegeus.saltcopper.biome;

import java.util.List;
import java.util.Optional;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.ClimateSettings;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;
import plegeus.saltcopper.Config;
import plegeus.saltcopper.Ore;

/**
 * VanillaOresModifier
 * @param biomes
 * A list of biomes to apply this modifer to.
 * 
 */
public record VanillaOresModifier(HolderSet<Biome> biomes) implements BiomeModifier {

    @Override
    public MapCodec<? extends BiomeModifier> codec() {
        return DeferredModifier.OVERRIDE_VANILLA_ORES.get();
    }

    @Override
    public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
        if (phase == Phase.AFTER_EVERYTHING) {
            
            // Removes all features from the given step, so no ores!
            List<Holder<PlacedFeature>> features = builder.getGenerationSettings().getFeatures(Decoration.UNDERGROUND_ORES);
            if (!Config.KEEP_VANILLA_ORES.getAsBoolean()) {
                features.clear();
            }
            
            // Biome info, set to builder values as default in case it was set by other modifiers.
            ClimateSettings climate = builder.getClimateSettings().build();
            Optional<Biome> maybe_biome = biome.unwrap().right();
            if (maybe_biome.isPresent()) {
                climate = maybe_biome.get().getModifiedClimateSettings();
            }

            // Based on temperature and downfall, predict geology.
            for (Ore ore : Config.ORES.ORES) {
                if (ore.matchesClimate(climate)) {
                    
                }
            }

        }
    }    

}


