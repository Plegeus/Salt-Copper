package plegeus.saltcopper;

import java.util.ArrayList;
import java.util.List;

import com.electronwill.nightconfig.core.CommentedConfig;

import net.minecraft.world.level.biome.Biome.ClimateSettings;

public class Ore {

    public final String TAG;
    public final List<String> FEATURES;
    public final float MIN_TEMP;
    public final float MAX_TEMP;
    public final float MIN_RAIN;
    public final float MAX_RAIN;

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
        
        float[] temp = table.get("temp");
        if (temp == null) {
            temp = new float[2];
            temp[0] = DEFAULT_MIN_TEMP;
            temp[1] = DEFAULT_MAX_TEMP;
        } 
        if (temp.length != 2) {
            throw new Exception("");
        }   
        MIN_TEMP = Math.clamp(temp[0], DEFAULT_MIN_TEMP, DEFAULT_MAX_TEMP);
        MAX_TEMP = Math.clamp(temp[1], DEFAULT_MIN_TEMP, DEFAULT_MAX_TEMP);

        float[] rain = table.get("rain");
        if (rain == null) {
            rain = new float[2];
            rain[0] = DEFAULT_MIN_RAIN;
            rain[1] = DEFAULT_MAX_RAIN;
        } 
        if (rain.length != 2) {
            throw new Exception("");
        }   
        MIN_RAIN = Math.clamp(rain[0], DEFAULT_MIN_RAIN, DEFAULT_MAX_RAIN);
        MAX_RAIN = Math.clamp(rain[1], DEFAULT_MIN_RAIN, DEFAULT_MAX_RAIN);

    }

    ArrayList<String> defaultFeatures(String tag) {
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

}
