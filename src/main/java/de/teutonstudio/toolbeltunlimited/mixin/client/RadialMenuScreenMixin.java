package de.teutonstudio.toolbeltunlimited.mixin.client;

import de.teutonstudio.toolbeltunlimited.client.RingMenuAccess;
import de.teutonstudio.toolbeltunlimited.client.BeltSelectionAccess;
import de.teutonstudio.toolbeltunlimited.ToolBeltUnlimitedClientConfig;
import dev.gigaherz.toolbelt.BeltFinder;
import dev.gigaherz.toolbelt.ConfigData;
import net.minecraft.world.item.ItemStack;
import dev.gigaherz.toolbelt.client.RadialMenuScreen;
import dev.gigaherz.toolbelt.client.radial.GenericRadialMenu;
import dev.gigaherz.toolbelt.client.radial.ItemStackRadialMenuItem;
import dev.gigaherz.toolbelt.client.radial.TextRadialMenuItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.List;

@Mixin(value = RadialMenuScreen.class, remap = false)
public abstract class RadialMenuScreenMixin extends Screen implements BeltSelectionAccess {
    @Shadow @Final private GenericRadialMenu menu;
    @Shadow @Final private BeltFinder.BeltGetter getter;
    @Shadow @Final private List<ItemStackRadialMenuItem> cachedMenuItems;
    @Shadow @Final private TextRadialMenuItem insertMenuItem;

    @Inject(method = "render", at = @At(value = "INVOKE",
            target = "Ldev/gigaherz/toolbelt/client/radial/GenericRadialMenu;draw(Lnet/minecraft/client/gui/GuiGraphics;FII)V"))
    private void toolbeltunlimited$allowDirectInsertion(GuiGraphics graphics, int mouseX, int mouseY,
                                                       float partialTick, CallbackInfo ci) {
        if (!ToolBeltUnlimitedClientConfig.useRings() || !menu.isReady()) return;
        ItemStack held = Minecraft.getInstance().player.getMainHandItem();
        boolean inserting = !held.isEmpty() && ConfigData.isItemStackAllowed(held);
        boolean hasEmpty = false;
        for (ItemStackRadialMenuItem item : cachedMenuItems) {
            boolean empty = item.getStack().isEmpty();
            hasEmpty |= empty;
            // The ring draws every slot. Empty positions must also participate in
            // Tool Belt's visible-item hit testing while holding an item to insert.
            boolean visible = !empty || ConfigData.displayEmptySlots || inserting;
            if (item.isVisible() != visible) item.setVisible(visible);
            item.setCentralText(Component.translatable(inserting
                    ? (empty ? "text.toolbelt.insert" : "text.toolbelt.swap")
                    : (empty ? "text.toolbelt.empty" : "text.toolbelt.extract")));
        }
        // Tool Belt normally infers this from invisible slots. Those slots are now
        // selectable, so retain the existing automatic-insertion action explicitly.
        boolean centerVisible = inserting && hasEmpty && !ConfigData.displayEmptySlots;
        if (insertMenuItem.isVisible() != centerVisible) insertMenuItem.setVisible(centerVisible);
    }

    @Override
    public ItemStack toolbeltunlimited$getBelt() { return getter.getBelt(); }

    protected RadialMenuScreenMixin(Component title) { super(title); }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        return ((RingMenuAccess) menu).toolbeltunlimited$scrollRings(scrollY)
                || super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }
}
