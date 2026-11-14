package validation;

import de.teutonstudio.toolbeltunlimited.BeltLayout;
import de.teutonstudio.toolbeltunlimited.ToolBeltUnlimitedConfig;
import de.teutonstudio.toolbeltunlimited.client.BeltGridScreen;
import dev.gigaherz.toolbelt.BeltFinder;
import dev.gigaherz.toolbelt.ConfigData;
import dev.gigaherz.toolbelt.belt.ToolBeltItem;
import dev.gigaherz.toolbelt.client.RadialMenuScreen;
import dev.gigaherz.toolbelt.client.radial.GenericRadialMenu;
import dev.gigaherz.toolbelt.client.radial.ItemStackRadialMenuItem;
import dev.gigaherz.toolbelt.common.BeltContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Screenshot;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.NeoForge;

/** Drives native menus and packets in a disposable flat world. Never included in the release JAR. */
@Mod(value = "toolbelt_ui_validation", dist = Dist.CLIENT)
public final class UiValidation {
    private static int tick;
    private static boolean creating;
    private static boolean failed;

    public UiValidation() { NeoForge.EVENT_BUS.addListener(UiValidation::tick); }

    static void require(boolean condition, String label) {
        if (!condition) throw new IllegalStateException(label);
        System.out.println("UI_VERIFIED " + label);
    }

    static void server(Minecraft mc, java.util.function.Consumer<net.minecraft.server.level.ServerPlayer> action) {
        var server = mc.getSingleplayerServer();
        server.submit(() -> action.accept(server.getPlayerList().getPlayers().getFirst())).join();
    }

    static void config(String key, Object value) {
        ((ModConfigSpec.ConfigValue) ToolBeltUnlimitedConfig.SPEC.getValues().get(key)).set(value);
    }

    static void prepare(Minecraft mc, int slots, boolean sparse, boolean grid) {
        mc.setScreen(null);
        server(mc, player -> {
            player.closeContainer();
            player.getInventory().clearContent();
            var belt = ToolBeltItem.of(slots);
            belt.set(DataComponents.CUSTOM_NAME, Component.literal("Grid & Rings"));
            var handler = ToolBeltItem.getComponentItemHandler(belt);
            for (int i = 0; i < slots; i++) {
                if (sparse && i != 0 && i != 9 && i != 18 && i != 19) continue;
                var tool = new ItemStack(Items.DIAMOND_PICKAXE);
                tool.set(DataComponents.CUSTOM_NAME, Component.literal("Tool " + i));
                handler.setStackInSlot(i, tool);
            }
            player.getInventory().selected = grid ? 8 : 0;
            dev.gigaherz.toolbelt.slot.BeltAttachment.get(player).setContents(grid ? ItemStack.EMPTY : belt);
            player.getInventory().setItem(8, grid ? belt : ItemStack.EMPTY);
            player.inventoryMenu.broadcastChanges();
        });
        mc.player.getInventory().selected = grid ? 8 : 0;
    }

    static void openGrid(Minecraft mc) {
        server(mc, player -> {
            player.setShiftKeyDown(true);
            player.getMainHandItem().use(player.level(), player, InteractionHand.MAIN_HAND);
            player.setShiftKeyDown(false);
        });
    }

    static GenericRadialMenu menu(Minecraft mc) {
        try {
            var field = RadialMenuScreen.class.getDeclaredField("menu");
            field.setAccessible(true);
            return (GenericRadialMenu) field.get(mc.screen);
        } catch (ReflectiveOperationException exception) { throw new RuntimeException(exception); }
    }

    // Hold the menu open independently of physical keyboard input; explicitly test release below.
    static final class HeldRadialScreen extends RadialMenuScreen {
        private int renderedTick = -1;
        int renderSerial;
        HeldRadialScreen(BeltFinder.BeltGetter getter) { super(getter); }
        @Override public void tick() { menu(Minecraft.getInstance()).tick(); }
        @Override public void render(net.minecraft.client.gui.GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
            super.render(graphics, mouseX, mouseY, partialTick);
            renderedTick = UiValidation.tick;
            renderSerial++;
        }
        void releaseKey() { super.tick(); }
    }

    static void openRings(Minecraft mc) {
        mc.setScreen(new HeldRadialScreen(BeltFinder.findBelt(mc.player).orElseThrow()));
    }

    static void move(Minecraft mc, double x, double y) {
        org.lwjgl.glfw.GLFW.glfwSetCursorPos(mc.getWindow().getWindow(), x * mc.getWindow().getGuiScale(), y * mc.getWindow().getGuiScale());
        try {
            var fx = net.minecraft.client.MouseHandler.class.getDeclaredField("xpos");
            var fy = net.minecraft.client.MouseHandler.class.getDeclaredField("ypos");
            fx.setAccessible(true);
            fy.setAccessible(true);
            fx.setDouble(mc.mouseHandler, x * mc.getWindow().getGuiScale());
            fy.setDouble(mc.mouseHandler, y * mc.getWindow().getGuiScale());
        } catch (ReflectiveOperationException exception) { throw new RuntimeException(exception); }
    }

    static void pointAt(Minecraft mc, int slot, int firstRing) {
        double radius = 24 + (BeltLayout.row(slot) - firstRing + 0.5) * 28;
        move(mc, mc.screen.width / 2 + Math.cos(BeltLayout.angle(slot)) * radius,
                mc.screen.height / 2 + Math.sin(BeltLayout.angle(slot)) * radius);
    }

    static void hovered(Minecraft mc, int expected) {
        var hovered = menu(mc).getHoveredItem();
        require(hovered instanceof ItemStackRadialMenuItem item && item.getSlot() == expected,
                "ring hit selects actual slot " + expected + " (actual="
                        + (hovered instanceof ItemStackRadialMenuItem item ? item.getSlot() : hovered) + ")");
    }

    static void shot(Minecraft mc, String name) {
        Screenshot.grab(mc.gameDirectory, name + ".png", mc.getMainRenderTarget(), ignored -> System.out.println("UI_SHOT " + name));
    }

    private static void checkGrid(Minecraft mc, int slots) {
        require(mc.screen instanceof BeltGridScreen, "grid screen for " + slots + " slots");
        var menu = (BeltContainer) mc.player.containerMenu;
        require(menu.beltSlots == slots, "grid preserves slot count " + slots);
        for (int i = 0; i < slots; i++) {
            var slot = menu.getSlot(i);
            require(slot.x == 8 + i % 9 * 18 && slot.y == 20 + i / 9 * 18, "grid coordinates " + slots + "/" + i);
        }
        require(menu.getSlot(slots).y == 51 + (BeltLayout.rows(slots) - 1) * 18, "player inventory below belt rows");
        server(mc, player -> {
            var serverMenu = (BeltContainer) player.containerMenu;
            require(serverMenu.getSlot(slots - 1).y == 20 + (slots - 1) / 9 * 18, "server grid agrees");
            require(!serverMenu.getSlot(slots + 35).mayPickup(player), "held belt remains locked");
        });
    }

    private static void tick(ClientTickEvent.Post event) {
        if (failed) return;
        var mc = Minecraft.getInstance();
        mc.options.pauseOnLostFocus = false;
        mc.options.tutorialStep = net.minecraft.client.tutorial.TutorialSteps.NONE;
        if (mc.getOverlay() != null) return;
        try {
            if (mc.level == null) {
                if (!creating && mc.screen instanceof TitleScreen) {
                    creating = true;
                    var settings = new net.minecraft.world.level.LevelSettings("Belt UI validation", net.minecraft.world.level.GameType.CREATIVE,
                            false, net.minecraft.world.Difficulty.PEACEFUL, true, new net.minecraft.world.level.GameRules(),
                            net.minecraft.world.level.WorldDataConfiguration.DEFAULT);
                    mc.createWorldOpenFlows().createFreshLevel("BeltUi-" + System.currentTimeMillis(), settings,
                            new net.minecraft.world.level.levelgen.WorldOptions(91L, false, false),
                            registry -> registry.registryOrThrow(net.minecraft.core.registries.Registries.WORLD_PRESET)
                                    .getOrThrow(net.minecraft.world.level.levelgen.presets.WorldPresets.FLAT).createWorldDimensions(), mc.screen);
                }
                return;
            }
            if (Boolean.getBoolean("toolbelt.validation.config")) { ConfigValidation.tick(mc); return; }
            if (Boolean.getBoolean("toolbelt.validation.insert")) { InsertValidation.tick(mc); return; }
            // Catch-up ticks can run without a render in between. Wait for the UI to
            // consume the previous input before advancing this fixture's timeline.
            if (mc.screen instanceof HeldRadialScreen screen) {
                if (screen.renderedTick < tick) return;
                int next = tick + 1;
                if ((next == 310 || next == 420 || next == 490 || next == 610)
                        && (!menu(mc).isReady() || menu(mc).getVisibleItemCount() == 0)) return;
            }
            tick++;
            if (tick == 20) {
                mc.options.guiScale().set(2); mc.resizeDisplay();
                config("maxSlots", 90);
                for (int i = 0; i < 1000; i++) {
                    if (BeltLayout.columnAt(BeltLayout.angle(i)) != i % 9) throw new IllegalStateException("angle round trip " + i);
                }
                require(true, "1000 slot angle round trips");
                require(BeltLayout.rows(Integer.MAX_VALUE) == 238609295, "row count avoids overflow");
                prepare(mc, 20, false, true);
            }
            if (tick == 40) openGrid(mc);
            if (tick == 70) { checkGrid(mc, 20); shot(mc, "grid-20"); }
            if (tick == 80) mc.gameMode.handleInventoryMouseClick(mc.player.containerMenu.containerId, 19, 0, ClickType.PICKUP, mc.player);
            if (tick == 90) {
                require(mc.player.containerMenu.getCarried().getHoverName().getString().equals("Tool 19"), "client picks last grid slot");
                server(mc, player -> require(player.containerMenu.getCarried().getHoverName().getString().equals("Tool 19"), "server picks same slot"));
                mc.gameMode.handleInventoryMouseClick(mc.player.containerMenu.containerId, 19, 0, ClickType.PICKUP, mc.player);
                server(mc, player -> {
                    var container = (BeltContainer) player.containerMenu;
                    var moved = container.quickMoveStack(player, 10);
                    require(!moved.isEmpty() && container.getSlot(10).getItem().isEmpty(), "shift click belt to player inventory");
                    int playerIndex = -1;
                    for (int i = 20; i < container.slots.size(); i++) if (container.getSlot(i).getItem().getHoverName().getString().equals("Tool 10")) playerIndex = i;
                    require(playerIndex >= 20 && !container.quickMoveStack(player, playerIndex).isEmpty(), "shift click player to belt");
                    container.broadcastChanges();
                });
            }
            if (tick == 100) prepare(mc, 2, false, true);
            if (tick == 110) openGrid(mc);
            if (tick == 130) checkGrid(mc, 2);
            if (tick == 140) prepare(mc, 9, false, true);
            if (tick == 150) openGrid(mc);
            if (tick == 170) checkGrid(mc, 9);
            if (tick == 180) prepare(mc, 10, false, true);
            if (tick == 190) openGrid(mc);
            if (tick == 210) checkGrid(mc, 10);
            if (tick == 220) { mc.options.guiScale().set(4); mc.resizeDisplay(); prepare(mc, 90, false, true); }
            if (tick == 230) openGrid(mc);
            if (tick == 250) {
                var screen = (BeltGridScreen) mc.screen;
                for (int i = 0; i < 20; i++) screen.mouseScrolled(screen.getGuiLeft() + 10, screen.getGuiTop() + 20, 0, -1);
                require(!screen.getMenu().getSlot(0).isActive() && screen.getMenu().getSlot(89).isActive(), "grid scroll reaches last row and hides first");
                move(mc, 1, 1);
            }
            if (tick == 260) shot(mc, "grid-90-scrolled");
            if (tick == 280) { mc.options.guiScale().set(2); mc.resizeDisplay(); prepare(mc, 20, true, false); ConfigData.displayEmptySlots = false; }
            if (tick == 295) openRings(mc);
            if (tick == 310) { shot(mc, "rings-20-sparse"); pointAt(mc, 0, 0); }
            if (tick == 320) { hovered(mc, 0); pointAt(mc, 1, 0); }
            if (tick == 325) require(menu(mc).getHoveredItem() == null, "hidden empty slots keep their angular gap");
            if (tick == 330) pointAt(mc, 9, 0);
            if (tick == 340) hovered(mc, 9);
            if (tick == 350) pointAt(mc, 18, 0);
            if (tick == 360) hovered(mc, 18);
            if (tick == 370) pointAt(mc, 19, 0);
            if (tick == 380) { hovered(mc, 19); mc.screen.mouseReleased(0, 0, 0); }
            if (tick == 390) {
                server(mc, player -> require(player.getMainHandItem().getHoverName().getString().equals("Tool 19"), "mouse click packet swaps actual slot 19"));
                prepare(mc, 20, true, false);
                ConfigData.displayEmptySlots = true;
            }
            if (tick == 405) openRings(mc);
            if (tick == 420) pointAt(mc, 10, 0);
            if (tick == 430) { hovered(mc, 10); shot(mc, "rings-20-empty-slots"); }
            if (tick == 440) pointAt(mc, 18, 0);
            if (tick == 450) { hovered(mc, 18); ConfigData.releaseToSwap = true; ((HeldRadialScreen) mc.screen).releaseKey(); }
            if (tick == 460) {
                server(mc, player -> require(player.getMainHandItem().getHoverName().getString().equals("Tool 18"), "key release packet swaps actual slot 18"));
                prepare(mc, 90, false, false); mc.options.guiScale().set(4); mc.resizeDisplay();
            }
            if (tick == 475) openRings(mc);
            if (tick == 490) {
                int visible = Math.round((menu(mc).radiusOut - 24) / 28);
                for (int i = 0; i < BeltLayout.rows(90) - visible; i++) mc.screen.mouseScrolled(1, 1, 0, -1);
                pointAt(mc, 81, BeltLayout.rows(90) - visible);
            }
            if (tick == 500) { hovered(mc, 81); shot(mc, "rings-90-scrolled"); }
            if (tick == 510) { menu(mc).cycleNext(); }
            if (tick == 520) hovered(mc, 82);
            if (tick == 530) { menu(mc).setHovered(0); menu(mc).cyclePrevious(); }
            if (tick == 540) hovered(mc, 89);
            if (tick == 550) { for (int i = 0; i < 27; i++) menu(mc).cyclePrevious(); }
            if (tick == 560) { hovered(mc, 62); shot(mc, "rings-cycle-page"); }
            if (tick == 562) pointAt(mc, 54, 6);
            if (tick == 566) hovered(mc, 54);
            if (tick == 570) {
                prepare(mc, 20, true, false);
                ConfigData.displayEmptySlots = false;
                server(mc, player -> { player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.GOLDEN_PICKAXE)); player.inventoryMenu.broadcastChanges(); });
            }
            if (tick == 590) openRings(mc);
            if (tick == 610) move(mc, mc.screen.width / 2, mc.screen.height / 2);
            if (tick == 620) {
                require(menu(mc).getHoveredItem() != null && !(menu(mc).getHoveredItem() instanceof ItemStackRadialMenuItem), "center insert action remains selectable");
                mc.screen.mouseReleased(0, 0, 0);
            }
            if (tick == 630) {
                server(mc, player -> {
                    require(player.getMainHandItem().isEmpty(), "center action inserts held tool");
                    var belt = BeltFinder.findBelt(player).orElseThrow().getBelt();
                    require(ToolBeltItem.getComponentItemHandler(belt).getStackInSlot(1).is(Items.GOLDEN_PICKAXE), "center insert uses first empty slot");
                });
                config("maxSlots", 20);
                java.nio.file.Files.writeString(mc.gameDirectory.toPath().resolve("ui-validation-success.txt"), "UI_VALIDATION_COMPLETE\n");
                System.out.println("UI_VALIDATION_COMPLETE");
                mc.stop();
            }
        } catch (Throwable throwable) {
            failed = true;
            shot(mc, "validation-failure-" + tick);
            throwable.printStackTrace();
            System.out.println("UI_VALIDATION_FAILED tick=" + tick);
            mc.stop();
        }
    }
}
