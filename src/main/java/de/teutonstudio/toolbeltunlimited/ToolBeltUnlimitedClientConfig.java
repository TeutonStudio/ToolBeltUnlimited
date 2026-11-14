package de.teutonstudio.toolbeltunlimited;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class ToolBeltUnlimitedClientConfig {
    public static final ModConfigSpec SPEC;
    private static final ModConfigSpec.BooleanValue USE_RINGS;
    private static final ModConfigSpec.IntValue MAX_VISIBLE_RINGS;

    static {
        var builder = new ModConfigSpec.Builder();
        USE_RINGS = builder.comment("Use concentric rings. False restores Tool Belt's original single radial menu.")
                .define("useRings", true);
        MAX_VISIBLE_RINGS = builder.comment("Maximum simultaneously visible rings. The screen may fit fewer.",
                        "Scroll down: innermost ring leaves, remaining rings move inward, next ring appears outside.",
                        "Scroll up reverses this. Hidden rows are skipped; scrolling wraps around.")
                .defineInRange("maxVisibleRings", 3, 1, 16);
        SPEC = builder.build();
    }

    private ToolBeltUnlimitedClientConfig() {}

    public static boolean useRings() { return USE_RINGS.get(); }
    public static int maxVisibleRings() { return MAX_VISIBLE_RINGS.get(); }
}
