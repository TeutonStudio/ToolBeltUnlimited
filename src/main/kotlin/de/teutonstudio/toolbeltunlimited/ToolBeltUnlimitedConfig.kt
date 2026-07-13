package de.teutonstudio.toolbeltunlimited

import net.neoforged.neoforge.common.ModConfigSpec

object ToolBeltUnlimitedConfig {
    const val DEFAULT_MAX_SLOTS = 20

    private val allowCommonTools: ModConfigSpec.BooleanValue
    private val maxSlots: ModConfigSpec.IntValue

    @JvmField
    val SPEC: ModConfigSpec

    init {
        val builder = ModConfigSpec.Builder()

        allowCommonTools = builder
            .comment("Allow all items tagged with #c:tools to be stored in Tool Belts.")
            .define("allowCommonTools", true)

        maxSlots = builder
            .comment("Maximum Tool Belt slot count. Vanilla Tool Belt upgrades are unchanged up to 9 slots.")
            .defineInRange("maxSlots", DEFAULT_MAX_SLOTS, 9, Int.MAX_VALUE)

        SPEC = builder.build()
    }

    fun allowCommonTools(): Boolean = allowCommonTools.asBoolean

    fun maxSlots(): Int = maxSlots.asInt
}
