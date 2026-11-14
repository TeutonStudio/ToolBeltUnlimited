package de.teutonstudio.toolbeltunlimited.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import de.teutonstudio.toolbeltunlimited.BeltLayout;
import de.teutonstudio.toolbeltunlimited.BeltRows;
import de.teutonstudio.toolbeltunlimited.ToolBeltUnlimitedClientConfig;
import dev.gigaherz.toolbelt.ConfigData;
import dev.gigaherz.toolbelt.client.radial.GenericRadialMenu;
import dev.gigaherz.toolbelt.client.radial.ItemStackRadialMenuItem;
import dev.gigaherz.toolbelt.client.radial.RadialMenuItem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.function.BiConsumer;

/** Rendering and hit testing use the actual belt slot, never its index among visible tools. */
public final class BeltRingMenu {
    private static final int RING_WIDTH = 28;
    private final GenericRadialMenu menu;
    private final List<RadialMenuItem> items;
    private final BiConsumer<Double, Double> moveMouse;
    private final RingWindow window = new RingWindow();
    private RadialMenuItem keyboardItem;
    private int lastMouseX;
    private int lastMouseY;
    private boolean mouseInitialized;

    public BeltRingMenu(GenericRadialMenu menu, List<RadialMenuItem> items, BiConsumer<Double, Double> moveMouse) {
        this.menu = menu;
        this.items = items;
        this.moveMouse = moveMouse;
    }

    private int ringCount() {
        int slots = 0;
        for (RadialMenuItem item : items) {
            if (item instanceof ItemStackRadialMenuItem slot) slots = Math.max(slots, slot.getSlot() + 1);
        }
        return BeltLayout.rows(slots);
    }

    private int innerRadius() {
        return Math.max(24, (int) Math.ceil(BeltLayout.columns() * 18 / (Math.PI * 2) - RING_WIDTH / 2.0));
    }

    private void refreshWindow() {
        var belt = ((BeltSelectionAccess) menu.host.getScreen()).toolbeltunlimited$getBelt();
        window.update(ringCount(), BeltRows.hidden(belt));
        if (keyboardItem instanceof ItemStackRadialMenuItem item && !window.contains(BeltLayout.row(item.getSlot()))) {
            keyboardItem.setHovered(false);
            keyboardItem = null;
        }
    }

    private int capacity() {
        var screen = menu.host.getScreen();
        int physical = Math.max(1, (Math.min(screen.width, screen.height) / 2 - 12 - innerRadius()) / RING_WIDTH);
        return Math.min(physical, ToolBeltUnlimitedClientConfig.maxVisibleRings());
    }

    private int visibleRings() { return window.visibleCount(capacity()); }

    private int localRing(ItemStackRadialMenuItem item) {
        return window.localIndex(BeltLayout.row(item.getSlot()), capacity());
    }

    private double radius(ItemStackRadialMenuItem item) {
        return innerRadius() + (localRing(item) + 0.5) * RING_WIDTH;
    }

    public boolean scroll(double amount) {
        if (amount == 0 || !menu.isReady()) return false;
        refreshWindow();
        if (!window.scroll(amount, capacity())) return false;
        keyboardItem = null;
        menu.setHovered(-1);
        return true;
    }

    public void moveTo(int visibleIndex) {
        List<RadialMenuItem> visible = items.stream().filter(RadialMenuItem::isVisible).toList();
        if (visibleIndex < 0 || visibleIndex >= visible.size()) return;
        refreshWindow();
        select(visible.get(visibleIndex));
    }

    private void select(RadialMenuItem item) {
        if (item instanceof ItemStackRadialMenuItem stack && !window.contains(BeltLayout.row(stack.getSlot()))) return;
        keyboardItem = item;
        if (item instanceof ItemStackRadialMenuItem stack) window.focus(BeltLayout.row(stack.getSlot()), capacity());
        for (RadialMenuItem entry : items) entry.setHovered(entry == item);
    }

    public void cycle(int direction) {
        if (!menu.isReady()) return;
        refreshWindow();
        var available = items.stream().filter(RadialMenuItem::isVisible)
                .filter(item -> !(item instanceof ItemStackRadialMenuItem stack) || window.contains(BeltLayout.row(stack.getSlot())))
                .toList();
        if (available.isEmpty()) { menu.setHovered(-1); return; }
        int current = available.indexOf(menu.getHoveredItem());
        int next = current < 0 ? (direction > 0 ? 0 : available.size() - 1)
                : Math.floorMod(current + direction, available.size());
        select(available.get(next));
    }

    public void draw(GuiGraphics graphics, int mouseX, int mouseY) {
        if (menu.isClosed()) return;
        var screen = menu.host.getScreen();
        var font = menu.host.getFontRenderer();
        refreshWindow();
        int rings = visibleRings();
        double halfSegment = Math.PI / BeltLayout.columns();
        int x = screen.width / 2;
        int y = screen.height / 2;
        menu.radiusIn = innerRadius();
        menu.radiusOut = innerRadius() + rings * RING_WIDTH;
        menu.itemRadius = innerRadius() + RING_WIDTH / 2f;
        if (menu.isReady()) processMouse(mouseX, mouseY, x, y, rings);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        BufferBuilder buffer = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        float scale = menu.isReady() ? 1 : Math.max(0.001f, menu.animProgress);
        int arcs = 0;
        RadialMenuItem insert = null;
        for (RadialMenuItem entry : items) {
            if (entry instanceof ItemStackRadialMenuItem item) {
                int ring = localRing(item);
                if (ring < 0 || ring >= rings) continue;
                double angle = BeltLayout.angle(item.getSlot());
                int color = item.isHovered() ? 0xA07092B5 : item.isVisible() ? 0xA020252D : 0x6020252D;
                arc(buffer, graphics, x, y, (innerRadius() + ring * RING_WIDTH + 1) * scale,
                        (innerRadius() + (ring + 1) * RING_WIDTH - 1) * scale,
                        angle - halfSegment + 0.018, angle + halfSegment - 0.018, color);
                arcs++;
            } else if (entry.isVisible()) {
                insert = entry;
            }
        }
        if (insert != null) {
            arc(buffer, graphics, x, y, 0, (innerRadius() - 2) * scale, 0, Math.PI * 2,
                    insert.isHovered() ? 0xA07092B5 : 0xA020252D);
            arcs++;
        }
        if (arcs > 0) BufferUploader.drawWithShader(buffer.buildOrThrow());
        else buffer.build();
        RenderSystem.disableBlend();
        if (!menu.isReady()) return;

        for (RadialMenuItem entry : items) {
            if (!(entry instanceof ItemStackRadialMenuItem item)) continue;
            int ring = localRing(item);
            if (ring < 0 || ring >= rings) continue;
            double angle = BeltLayout.angle(item.getSlot());
            int itemX = (int) Math.round(x + Math.cos(angle) * radius(item));
            int itemY = (int) Math.round(y + Math.sin(angle) * radius(item));
            if (!item.getStack().isEmpty()) {
                graphics.renderItem(item.getStack(), itemX - 8, itemY - 8);
                graphics.renderItemDecorations(font, item.getStack(), itemX - 8, itemY - 8);
            } else {
                graphics.drawCenteredString(font, Integer.toString(BeltLayout.column(item.getSlot()) + 1), itemX, itemY - 4,
                        item.isVisible() ? 0xFFBBBBBB : 0xFF666666);
            }
        }

        RadialMenuItem hovered = menu.getHoveredItem();
        Component text = hovered != null && hovered.getCentralText() != null ? hovered.getCentralText() : menu.getCentralText();
        if (insert != null && (hovered == null || hovered == insert)) text = Component.translatable("text.toolbelt.insert");
        if (text == null && rings == 0) text = Component.translatable("text.toolbeltunlimited.all_hidden");
        if (text != null) {
            graphics.pose().pushPose();
            graphics.pose().translate(x, y - 4, 0);
            float textScale = Math.min(1f, 44f / Math.max(1, font.width(text)));
            graphics.pose().scale(textScale, textScale, 1);
            graphics.drawCenteredString(font, text, 0, 0, 0xFFFFFFFF);
            graphics.pose().popPose();
        }
        String rowNumbers = java.util.stream.IntStream.range(0, rings)
                .mapToObj(i -> Integer.toString(window.rowAt(i) + 1)).collect(java.util.stream.Collectors.joining(", "));
        Component range = rings == 0 ? Component.translatable("text.toolbeltunlimited.no_rings")
                : Component.translatable("text.toolbeltunlimited.rings_visible", rowNumbers);
        graphics.drawCenteredString(font, range, x, y + (int) menu.radiusOut + 3, 0xFFFFFFFF);
        if (rings < window.count()) {
            graphics.drawCenteredString(font, Component.translatable("text.toolbeltunlimited.scroll"), x, y - (int) menu.radiusOut - 12, 0xFFFFFFFF);
        }
        if (hovered instanceof ItemStackRadialMenuItem item) {
            var label = Component.translatable("text.toolbeltunlimited.row_ring", BeltLayout.row(item.getSlot()) + 1, BeltLayout.row(item.getSlot()) + 1);
            if (!item.getStack().isEmpty()) {
                menu.host.renderTooltip(graphics, item.getStack(), mouseX, mouseY);
            } else {
                graphics.renderTooltip(font, label, mouseX, mouseY);
            }
        }
    }

    private void processMouse(int mouseX, int mouseY, int x, int y, int rings) {
        boolean moved = mouseInitialized && (mouseX != lastMouseX || mouseY != lastMouseY);
        lastMouseX = mouseX;
        lastMouseY = mouseY;
        mouseInitialized = true;
        // Mouse movement takes over from keyboard focus; stationary coordinates do not.
        if (keyboardItem != null && !moved) {
            for (RadialMenuItem item : items) item.setHovered(item == keyboardItem && item.isVisible());
            return;
        }
        keyboardItem = null;
        double dx = mouseX - x;
        double dy = mouseY - y;
        double distance = Math.hypot(dx, dy);
        int ring = (int) Math.floor((distance - innerRadius()) / RING_WIDTH);
        if (distance >= menu.radiusOut && (ConfigData.clipMouseToCircle || ConfigData.allowClickOutsideBounds)) ring = rings - 1;
        int slot = ring >= 0 && ring < rings
                ? window.rowAt(ring) * BeltLayout.columns() + BeltLayout.columnAt(Math.atan2(dy, dx)) : -1;
        for (RadialMenuItem item : items) {
            boolean hovered = item instanceof ItemStackRadialMenuItem stack
                    ? ring >= 0 && ring < rings && stack.getSlot() == slot
                    : distance < innerRadius() - 2;
            item.setHovered(menu.isReady() && item.isVisible() && hovered);
        }
        if (ConfigData.clipMouseToCircle && distance > menu.radiusOut && distance > 0) {
            moveMouse.accept(x + dx * menu.radiusOut * 0.975 / distance, y + dy * menu.radiusOut * 0.975 / distance);
        }
    }

    private static void arc(BufferBuilder buffer, GuiGraphics graphics, int x, int y, double inner, double outer,
                            double start, double end, int color) {
        int steps = Math.max(1, (int) Math.ceil((end - start) / 0.035));
        var matrix = graphics.pose().last().pose();
        for (int i = 0; i < steps; i++) {
            double a = start + (end - start) * i / steps;
            double b = start + (end - start) * (i + 1) / steps;
            buffer.addVertex(matrix, x + (float) (outer * Math.cos(a)), y + (float) (outer * Math.sin(a)), 0).setColor(color);
            buffer.addVertex(matrix, x + (float) (inner * Math.cos(a)), y + (float) (inner * Math.sin(a)), 0).setColor(color);
            buffer.addVertex(matrix, x + (float) (inner * Math.cos(b)), y + (float) (inner * Math.sin(b)), 0).setColor(color);
            buffer.addVertex(matrix, x + (float) (outer * Math.cos(b)), y + (float) (outer * Math.sin(b)), 0).setColor(color);
        }
    }
}
