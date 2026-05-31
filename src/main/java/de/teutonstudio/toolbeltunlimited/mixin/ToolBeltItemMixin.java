package de.teutonstudio.toolbeltunlimited.mixin;

import de.teutonstudio.toolbeltunlimited.ToolBeltUnlimitedConfig;
import dev.gigaherz.toolbelt.belt.ToolBeltItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = ToolBeltItem.class, remap = false)
public abstract class ToolBeltItemMixin {
    @ModifyConstant(method = "forSize", constant = @Constant(intValue = 9))
    private int toolbeltunlimited$removeForSizeMaximum(int originalMaximum) {
        return ToolBeltUnlimitedConfig.maxSlots();
    }

    @ModifyConstant(method = {"getSlotsCount", "makeUpgradedStack"}, constant = @Constant(intValue = 9))
    private static int toolbeltunlimited$removeStaticMaximum(int originalMaximum) {
        return ToolBeltUnlimitedConfig.maxSlots();
    }
}
