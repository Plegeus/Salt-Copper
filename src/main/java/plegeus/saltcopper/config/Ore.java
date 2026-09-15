package plegeus.saltcopper.config;

import java.util.ArrayList;
import java.util.List;

import com.electronwill.nightconfig.core.CommentedConfig;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome.ClimateSettings;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.registries.DeferredHolder;
import plegeus.saltcopper.SaltCopper;

public class Ore {

    public final String TAG;
    public final List<String> FEATURES;
    public final List<String> BIOMES;
    public final float MIN_TEMP;
    public final float MAX_TEMP;
    public final float MIN_RAIN;
    public final float MAX_RAIN;

    // FIXME: Are these values correct?
    public final static float DEFAULT_MIN_TEMP = -1.0f; 
    public final static float DEFAULT_MAX_TEMP = 1.0f;
    public final static float DEFAULT_MIN_RAIN = 0.0f;
    public final static float DEFAULT_MAX_RAIN = 1.0f;

    Ore(CommentedConfig table) throws Exception {

        
        TAG = table.get("tag");
        ArrayList<String> features = table.get("features");
        if (features == null) {
            features = defaultFeatures(TAG);
        }
        FEATURES = features;
        
        ArrayList<String> biomes = table.get("biomes");
        if (biomes == null) {
            biomes = new ArrayList<>();
        }
        BIOMES = biomes;
        
        ArrayList<Double> temp = table.get("temp");
        if (temp == null) {
            temp = new ArrayList<>();
            temp.add((double) DEFAULT_MIN_TEMP);
            temp.add((double) DEFAULT_MAX_TEMP);
        } 
        if (temp.size() != 2) {
            throw new Exception("");
        }   
        MIN_TEMP = (float) Math.clamp(temp.get(0), DEFAULT_MIN_TEMP, DEFAULT_MAX_TEMP);
        MAX_TEMP = (float) Math.clamp(temp.get(1), DEFAULT_MIN_TEMP, DEFAULT_MAX_TEMP);
        

        ArrayList<Double> rain = table.get("rain");
        if (rain == null) {
            rain = new ArrayList<>();
            rain.add((double) DEFAULT_MIN_RAIN);
            rain.add((double) DEFAULT_MAX_RAIN);
        } 
        if (rain.size() != 2) {
            throw new Exception("");
        }   
        MIN_RAIN = (float) Math.clamp(rain.get(0), DEFAULT_MIN_RAIN, DEFAULT_MAX_RAIN);
        MAX_RAIN = (float) Math.clamp(rain.get(1), DEFAULT_MIN_RAIN, DEFAULT_MAX_RAIN);

        SaltCopper.LOGGER.info("ORE FINISHED");
        
    }

    ArrayList<String> defaultFeatures(String tag) {
        // FIXME: implement.
        return new ArrayList<>();
    }

    public boolean matchesClimate(ClimateSettings climate) {
        
        float rain = climate.downfall();
        float temp = climate.temperature();
    
        return (
            (rain > MIN_RAIN && rain < MAX_RAIN) &&
            (temp > MIN_TEMP && temp < MAX_TEMP)
        );
    }

    public List<Holder<PlacedFeature>> getFeatures(HolderGetter<PlacedFeature> registry) {
        //Registry<PlacedFeature> registry = SaltCopper.registries.registryOrThrow(Registries.PLACED_FEATURE);
        ArrayList<Holder<PlacedFeature>> list = new ArrayList<>();
        for (String f : FEATURES) {
            ResourceLocation r = ResourceLocation.parse(f);
            list.add(registry.get(ResourceKey.create(Registries.PLACED_FEATURE, r)).get());
        }
        return list;
    }

}
