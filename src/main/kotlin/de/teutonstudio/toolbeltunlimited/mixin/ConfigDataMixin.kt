package de.teutonstudio.toolbeltunlimited.mixin

import de.teutonstudio.toolbeltunlimited.ToolBeltUnlimitedConfig
import dev.gigaherz.toolbelt.ConfigData
import dev.gigaherz.toolbelt.belt.ToolBeltItem
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.common.Tags
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable

@Mixin(value = [ConfigData::class], remap = false)
abstract class ConfigDataMixin {
    private companion object {
        @JvmStatic
        @Inject(method = ["isItemStackAllowed"], at = [At("HEAD")], cancellable = true)
        private fun `toolbeltunlimited$allowCommonTools`(
            stack: ItemStack,
            cir: CallbackInfoReturnable<Boolean>,
        ) {
            if (
                stack.isEmpty ||
                !ToolBeltUnlimitedConfig.allowCommonTools() ||
                stack.item is ToolBeltItem
            ) {
                return
            }

            if (stack.`is`(Tags.Items.TOOLS)) {
                cir.returnValue = true
            }
        }
    }
}
