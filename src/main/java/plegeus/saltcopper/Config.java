package plegeus.saltcopper;


import net.neoforged.neoforge.common.ModConfigSpec;


public class Config {
    
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue KEEP_VANILLA_ORES = BUILDER
        .comment("If false removes all vanilla ore generation.")
        .define("keepVanillaOres", false);
    
    static final ModConfigSpec SPEC = BUILDER.build();


    public static final OreConfig ORES = new OreConfig();

}


