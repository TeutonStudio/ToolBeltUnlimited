package de.teutonstudio.toolbeltunlimited.mixin

import de.teutonstudio.toolbeltunlimited.ToolBeltUnlimitedConfig
import dev.gigaherz.toolbelt.integration.SewingUpgradeRecipe
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.Constant
import org.spongepowered.asm.mixin.injection.ModifyConstant

@Mixin(value = [SewingUpgradeRecipe::class], remap = false)
abstract class SewingUpgradeRecipeMixin {
    @ModifyConstant(method = ["assemble"], constant = [Constant(intValue = 9)])
    private fun `toolbeltunlimited$removeSewingUpgradeClamp`(originalMaximum: Int): Int =
        ToolBeltUnlimitedConfig.maxSlots()
}
