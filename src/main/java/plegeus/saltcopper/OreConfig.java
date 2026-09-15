package plegeus.saltcopper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.toml.TomlFormat;

import net.neoforged.fml.loading.FMLPaths;

public class OreConfig {

    public final ArrayList<Ore> ORES;
 
    public OreConfig() {
        Path oresPath = FMLPaths.CONFIGDIR.get().resolve(SaltCopper.MODID + "-ores.toml");
        ArrayList<Ore> ores = new ArrayList<>();
        if (init(oresPath)) {
            try (CommentedFileConfig config = CommentedFileConfig.builder(oresPath, TomlFormat.instance()).build()) {
                config.load();
                List<CommentedConfig> oreTables = config.get("ores");
                for (CommentedConfig table : oreTables) {
                    ores.add(new Ore(table));
                }
            } catch (Exception e) {
                SaltCopper.LOGGER.error(e.toString());
            }
        }
        ORES = ores;
    }

    boolean init(Path orePath) {
        
        if (Files.exists(orePath)) {
            return true;
        }

        try {
            Files.writeString(orePath, """
            # Ore Generation Configuration
            # 'ores' is a list of configurations.
            # You can add as many configurations as you want, the same tag may be reused!
            # format: 
            # {
            #   tag = "<namespace>:<block>",                # Required, must be a valid id.
            #   temp = [<min>, <max>],                      # Optional, must be a list of two floats in range (-1, 1). 
            #   rain = [<min>, <max>],                      # Optional, must be a list of two floats in range (0, 1). 
            #   features = [<feature1>, <feature2>, ...]    # Optional, a list of any length referring to a feature in a datapack.
            # }
            #
            # Each biome has a temperature and downfall (resp. temp and rain).
            # If a biome's temperature or downfall is not in range, the configuration will not be added to the biome.
            # If the configuration ommits an optional field, then it is ignored, i.e., the ore will be added.
            # 
            # 

            ores = [
              { tag = "minecraft:coal_ore", temp = [-1.0, 1.0], rain = [0.0, 1.0] },
            ]

            """);
        } catch (IOException e) {
            SaltCopper.LOGGER.error(e.toString());
            return false;
        }


        return true;
    }

}



