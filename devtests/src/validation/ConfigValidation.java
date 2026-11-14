package validation;

import de.teutonstudio.toolbeltunlimited.BeltLayout;
import de.teutonstudio.toolbeltunlimited.BeltRows;
import de.teutonstudio.toolbeltunlimited.ToolBeltUnlimitedClientConfig;
import de.teutonstudio.toolbeltunlimited.client.BeltGridScreen;
import de.teutonstudio.toolbeltunlimited.client.RowEyeButton;
import dev.gigaherz.toolbelt.BeltFinder;
import dev.gigaherz.toolbelt.ConfigData;
import dev.gigaherz.toolbelt.belt.ToolBeltItem;
import dev.gigaherz.toolbelt.slot.BeltAttachment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.nio.file.Files;
import java.util.List;
import java.util.Set;

import static validation.UiValidation.*;

final class ConfigValidation {
    private static int time;
    private static int lastFrame = -1;

    private static void clientConfig(String key, Object value) {
        ((ModConfigSpec.ConfigValue) ToolBeltUnlimitedClientConfig.SPEC.getValues().get(key)).set(value);
    }

    private static void point(Minecraft mc, int slot, int local) {
        double r = menu(mc).radiusIn + (local + 0.5) * 28;
        move(mc, mc.screen.width / 2 + Math.cos(BeltLayout.angle(slot)) * r,
                mc.screen.height / 2 + Math.sin(BeltLayout.angle(slot)) * r);
    }

    private static List<RowEyeButton> eyes(Minecraft mc) {
        return mc.screen.children().stream().filter(RowEyeButton.class::isInstance).map(RowEyeButton.class::cast).toList();
    }

    static void tick(Minecraft mc) throws Exception {
        if (mc.screen instanceof UiValidation.HeldRadialScreen held) {
            if (held.renderSerial == lastFrame) return;
            lastFrame = held.renderSerial;
            int next = time + 1;
            if ((next == 105 || next == 210 || next == 250) && !menu(mc).isReady()) return;
        } else lastFrame = -1;
        time++;
        if (time == 10) {
            mc.options.guiScale().set(2); mc.resizeDisplay();
            config("maxSlots", 60); config("itemsPerRing", 6);
            clientConfig("useRings", true); clientConfig("maxVisibleRings", 2);
            ConfigData.displayEmptySlots = true;
            prepare(mc, 30, false, true);
        }
        if (time == 30) openGrid(mc);
        if (time == 45) {
            require(mc.screen instanceof BeltGridScreen && BeltLayout.columns() == 6 && BeltLayout.rows(30) == 5, "configured six-slot rows and rings");
            require(eyes(mc).size() == 5, "one eye button per inventory row");
            require(mc.player.containerMenu.getSlot(6).x == 8 && mc.player.containerMenu.getSlot(6).y == 38, "second row starts at slot 6");
            server(mc, p -> { CustomData.update(DataComponents.CUSTOM_DATA, p.getMainHandItem(), tag -> tag.putString("fixture", "preserve")); p.containerMenu.broadcastChanges(); });
        }
        if (time == 50) {
            var eye = eyes(mc).get(1);
            mc.screen.mouseClicked(eye.getX() + 8, eye.getY() + 8, 0);
            mc.screen.mouseReleased(eye.getX() + 8, eye.getY() + 8, 0);
        }
        if (time == 60) {
            server(mc, p -> {
                var belt = p.getMainHandItem();
                require(BeltRows.hidden(belt).equals(Set.of(1)), "eye packet hides only row 2 on server");
                require(belt.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("fixture").equals("preserve"), "eye preserves unrelated custom data");
                require(ToolBeltItem.getComponentItemHandler(belt).getStackInSlot(6).getHoverName().getString().equals("Tool 6"), "hidden inventory contents remain intact");
                require(BeltRows.hidden(ToolBeltItem.makeUpgradedStack(belt)).equals(Set.of(1)), "hidden rows survive belt upgrade");
                var restored = ItemStack.parseOptional(p.registryAccess(), (net.minecraft.nbt.CompoundTag) belt.save(p.registryAccess()));
                require(BeltRows.hidden(restored).equals(Set.of(1)), "hidden rows survive item serialization");
                require(!p.containerMenu.clickMenuButton(p, -1) && !p.containerMenu.clickMenuButton(p, 99), "invalid eye packet rows rejected");
            });
            require(BeltRows.hidden(mc.player.getMainHandItem()).equals(Set.of(1)), "server eye state synchronized to client");
        }
        if (time == 65) server(mc, p -> p.closeContainer());
        if (time == 75) openGrid(mc);
        if (time == 80) { require(BeltRows.hidden(mc.player.getMainHandItem()).contains(1), "eye state retained when inventory reopened"); move(mc, 1, 1); shot(mc, "config-grid-six-eyes"); }
        if (time == 85) {
            mc.setScreen(null);
            server(mc, p -> {
                p.closeContainer(); var belt = p.getInventory().getItem(8);
                p.getInventory().setItem(8, ItemStack.EMPTY); p.getInventory().selected = 0;
                BeltAttachment.get(p).setContents(belt); p.inventoryMenu.broadcastChanges();
            });
        }
        if (time == 95) openRings(mc);
        if (time == 105) { require(menu(mc).radiusOut == 80, "maxVisibleRings=2 enforced"); point(mc, 0, 0); }
        if (time == 110) { hovered(mc, 0); point(mc, 12, 1); }
        if (time == 115) { hovered(mc, 12); shot(mc, "config-rings-skip-row"); }
        if (time == 120) { mc.screen.mouseScrolled(1, 1, 0, -1); point(mc, 12, 0); }
        if (time == 125) { hovered(mc, 12); shot(mc, "config-rings-shift-inward"); }
        if (time == 130) point(mc, 18, 1);
        if (time == 135) hovered(mc, 18);
        if (time == 140) mc.screen.mouseScrolled(1, 1, 0, 1);
        if (time == 145) point(mc, 0, 0);
        if (time == 150) hovered(mc, 0);
        if (time == 155) { for (int i = 0; i < 3; i++) mc.screen.mouseScrolled(1, 1, 0, -1); }
        if (time == 160) point(mc, 24, 0);
        if (time == 165) hovered(mc, 24);
        if (time == 170) point(mc, 0, 1);
        if (time == 175) { hovered(mc, 0); shot(mc, "config-rings-wrapped"); }
        if (time == 180) { menu(mc).setHovered(5); menu(mc).cycleNext(); }
        if (time == 185) hovered(mc, 12);
        if (time == 190) clientConfig("maxVisibleRings", 1);
        if (time == 195) { require(menu(mc).radiusOut == 52, "maxVisibleRings=1 enforced"); mc.screen.mouseScrolled(1, 1, 0, -1); point(mc, 18, 0); }
        if (time == 200) { hovered(mc, 18); shot(mc, "config-rings-limit-one"); }
        if (time == 205) clientConfig("useRings", false);
        if (time == 210) {
            require(menu(mc).radiusIn == 30 && menu(mc).radiusOut == 60, "useRings=false restores native single ring");
            require(!mc.screen.mouseScrolled(1, 1, 0, -1), "ring wheel handler disabled with native menu");
        }
        if (time == 215) {
            double a = -Math.PI / 2 + 6 * Math.PI * 2 / 30;
            move(mc, mc.screen.width / 2 + Math.cos(a) * 45, mc.screen.height / 2 + Math.sin(a) * 45);
        }
        if (time == 220) { hovered(mc, 6); shot(mc, "config-native-single-ring"); }
        if (time == 225) { clientConfig("useRings", true); clientConfig("maxVisibleRings", 2); }
        if (time == 230) require(menu(mc).radiusOut == 80, "ring mode can be re-enabled");
        if (time == 235) {
            mc.setScreen(null);
            server(mc, p -> {
                var belt = BeltFinder.findBelt(p).orElseThrow().getBelt();
                for (int i = 0; i < 5; i++) if (!BeltRows.hidden(belt).contains(i)) BeltRows.toggle(belt, i);
                BeltFinder.sendSync(p);
            });
        }
        if (time == 240) openRings(mc);
        if (time == 250) { require(menu(mc).radiusOut == 24, "all hidden rows produce no selection rings"); menu(mc).cycleNext(); }
        if (time == 255) { require(menu(mc).getHoveredItem() == null, "keyboard cannot select hidden rows"); shot(mc, "config-rings-all-hidden"); }
        if (time == 260) { mc.options.guiScale().set(4); mc.resizeDisplay(); config("itemsPerRing", 24); prepare(mc, 50, false, true); }
        if (time == 270) openGrid(mc);
        if (time == 280) {
            var screen = (BeltGridScreen) mc.screen;
            require(BeltLayout.rows(50) == 3 && mc.player.containerMenu.getSlot(24).y == 38, "24-slot rows retain grouping");
            for (int i = 0; i < 24; i++) screen.mouseScrolled(screen.getGuiLeft() + 10, screen.getGuiTop() + 20, -1, 0);
            require(!screen.getMenu().getSlot(0).isActive() && screen.getMenu().getSlot(23).isActive(), "wide row horizontal scrolling reaches last column");
            move(mc, 1, 1); shot(mc, "config-grid-24-scrolled");
        }
        if (time == 285) { config("itemsPerRing", 1); prepare(mc, 3, false, true); }
        if (time == 295) openGrid(mc);
        if (time == 305) { require(mc.player.containerMenu.getSlot(1).y == 38 && eyes(mc).size() == 3, "one-slot rows supported"); shot(mc, "config-grid-one"); }
        if (time == 310) {
            config("itemsPerRing", 9); config("maxSlots", 20); clientConfig("useRings", true); clientConfig("maxVisibleRings", 3);
            Files.writeString(mc.gameDirectory.toPath().resolve("ui-validation-success.txt"), "UI_VALIDATION_COMPLETE\n");
            System.out.println("CONFIG_UI_VALIDATION_COMPLETE"); System.out.println("UI_VALIDATION_COMPLETE"); mc.stop();
        }
    }
}
