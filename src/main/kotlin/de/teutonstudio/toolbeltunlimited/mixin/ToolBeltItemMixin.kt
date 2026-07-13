package de.teutonstudio.toolbeltunlimited.mixin

import de.teutonstudio.toolbeltunlimited.ToolBeltUnlimitedConfig
import dev.gigaherz.toolbelt.belt.ToolBeltItem
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.Constant
import org.spongepowered.asm.mixin.injection.ModifyConstant

@Mixin(value = [ToolBeltItem::class], remap = false)
abstract class ToolBeltItemMixin {
    @ModifyConstant(method = ["forSize"], constant = [Constant(intValue = 9)])
    private fun `toolbeltunlimited$removeForSizeMaximum`(originalMaximum: Int): Int =
        ToolBeltUnlimitedConfig.maxSlots()

    private companion object {
        @JvmStatic
        @ModifyConstant(
            method = ["getSlotsCount", "makeUpgradedStack"],
            constant = [Constant(intValue = 9)],
        )
        private fun `toolbeltunlimited$removeStaticMaximum`(originalMaximum: Int): Int =
            ToolBeltUnlimitedConfig.maxSlots()
    }
}
