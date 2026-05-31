package de.teutonstudio.toolbeltunlimited.mixin;

import de.teutonstudio.toolbeltunlimited.ToolBeltUnlimitedConfig;
import dev.gigaherz.toolbelt.ConfigData;
import dev.gigaherz.toolbelt.belt.ToolBeltItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ConfigData.class, remap = false)
public abstract class ConfigDataMixin {

    @Inject(
            method = "isItemStackAllowed",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void toolbeltunlimited$allowCommonTools(
            ItemStack stack,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (stack.isEmpty()) {
            return;
        }

        if (!ToolBeltUnlimitedConfig.allowCommonTools()) {
            return;
        }

        if (stack.getItem() instanceof ToolBeltItem) {
            return;
        }

        if (stack.is(Tags.Items.TOOLS)) {
            cir.setReturnValue(true);
        }
    }
}
