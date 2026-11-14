package de.teutonstudio.toolbeltunlimited.mixin.client;

import de.teutonstudio.toolbeltunlimited.client.BeltRingMenu;
import de.teutonstudio.toolbeltunlimited.ToolBeltUnlimitedClientConfig;
import de.teutonstudio.toolbeltunlimited.client.RingMenuAccess;
import dev.gigaherz.toolbelt.client.RadialMenuScreen;
import dev.gigaherz.toolbelt.client.radial.GenericRadialMenu;
import dev.gigaherz.toolbelt.client.radial.RadialMenuItem;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = GenericRadialMenu.class, remap = false)
public abstract class GenericRadialMenuMixin implements RingMenuAccess {
    @Shadow @Final private List<RadialMenuItem> items;
    @Shadow private void updateAnimationState(float partialTicks) { throw new AssertionError(); }
    @Shadow private void setMousePosition(double x, double y) { throw new AssertionError(); }
    @Unique private BeltRingMenu toolbeltunlimited$rings;

    @Unique
    private BeltRingMenu toolbeltunlimited$rings() {
        if (toolbeltunlimited$rings == null) {
            toolbeltunlimited$rings = new BeltRingMenu((GenericRadialMenu) (Object) this, items, this::setMousePosition);
        }
        return toolbeltunlimited$rings;
    }

    @Inject(method = "draw", at = @At("HEAD"), cancellable = true)
    private void toolbeltunlimited$drawRings(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY, CallbackInfo ci) {
        if (ToolBeltUnlimitedClientConfig.useRings() && ((GenericRadialMenu) (Object) this).host.getScreen() instanceof RadialMenuScreen) {
            updateAnimationState(partialTicks);
            toolbeltunlimited$rings().draw(graphics, mouseX, mouseY);
            ci.cancel();
        }
    }

    @Inject(method = "moveMouseToItem", at = @At("HEAD"), cancellable = true)
    private void toolbeltunlimited$cycleRings(int which, int count, CallbackInfo ci) {
        if (ToolBeltUnlimitedClientConfig.useRings() && ((GenericRadialMenu) (Object) this).host.getScreen() instanceof RadialMenuScreen) {
            toolbeltunlimited$rings().moveTo(which);
            ci.cancel();
        }
    }

    @Inject(method = "cycleNext", at = @At("HEAD"), cancellable = true)
    private void toolbeltunlimited$nextEnabled(CallbackInfo ci) {
        if (ToolBeltUnlimitedClientConfig.useRings() && ((GenericRadialMenu) (Object) this).host.getScreen() instanceof RadialMenuScreen) {
            toolbeltunlimited$rings().cycle(1);
            ci.cancel();
        }
    }

    @Inject(method = "cyclePrevious", at = @At("HEAD"), cancellable = true)
    private void toolbeltunlimited$previousEnabled(CallbackInfo ci) {
        if (ToolBeltUnlimitedClientConfig.useRings() && ((GenericRadialMenu) (Object) this).host.getScreen() instanceof RadialMenuScreen) {
            toolbeltunlimited$rings().cycle(-1);
            ci.cancel();
        }
    }

    @Override
    public boolean toolbeltunlimited$scrollRings(double amount) {
        return ToolBeltUnlimitedClientConfig.useRings() && toolbeltunlimited$rings().scroll(amount);
    }
}
