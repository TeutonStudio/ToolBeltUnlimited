package de.teutonstudio.toolbeltunlimited;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class ToolBeltUnlimitedConfig {
    public static final int DEFAULT_MAX_SLOTS = 20;

    public static final ModConfigSpec SPEC;

    private static final ModConfigSpec.BooleanValue ALLOW_COMMON_TOOLS;
    private static final ModConfigSpec.IntValue MAX_SLOTS;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        ALLOW_COMMON_TOOLS = builder
                .comment("Allow all items tagged with #c:tools to be stored in Tool Belts.")
                .define("allowCommonTools", true);

        MAX_SLOTS = builder
                .comment("Maximum Tool Belt slot count. Vanilla Tool Belt upgrades are unchanged up to 9 slots.")
                .defineInRange("maxSlots", DEFAULT_MAX_SLOTS, 9, Integer.MAX_VALUE);

        SPEC = builder.build();
    }

    private ToolBeltUnlimitedConfig() {
    }

    public static boolean allowCommonTools() {
        return ALLOW_COMMON_TOOLS.getAsBoolean();
    }

    public static int maxSlots() {
        return MAX_SLOTS.getAsInt();
    }
}
