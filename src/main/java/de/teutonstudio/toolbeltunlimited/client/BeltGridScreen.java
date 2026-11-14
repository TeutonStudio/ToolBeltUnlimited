package de.teutonstudio.toolbeltunlimited.client;

import de.teutonstudio.toolbeltunlimited.BeltLayout;
import de.teutonstudio.toolbeltunlimited.BeltMenuAccess;
import de.teutonstudio.toolbeltunlimited.BeltRows;
import de.teutonstudio.toolbeltunlimited.SlotLayoutAccess;
import dev.gigaherz.toolbelt.common.BeltContainer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

import java.util.ArrayList;
import java.util.List;

public final class BeltGridScreen extends AbstractContainerScreen<BeltContainer> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("toolbelt", "textures/gui/belt.png");
    private final List<RowEyeButton> eyes = new ArrayList<>();
    private int firstRow;
    private int visibleRows;
    private int firstColumn;
    private int visibleColumns;
    private int contentWidth;

    public BeltGridScreen(BeltContainer menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void init() {
        visibleColumns = Math.min(BeltLayout.columns(), Math.max(9, (width - 64) / 18));
        contentWidth = Math.max(162, visibleColumns * 18);
        imageWidth = contentWidth + 52;
        visibleRows = Math.min(BeltLayout.rows(menu.beltSlots), Math.max(1, (height - 133 - 24) / 18 + 1));
        imageHeight = 133 + (visibleRows - 1) * 18;
        inventoryLabelY = imageHeight - 94;
        inventoryLabelX = 8 + (contentWidth - 162) / 2;
        super.init();
        firstRow = Math.clamp(firstRow, 0, BeltLayout.rows(menu.beltSlots) - visibleRows);
        firstColumn = Math.clamp(firstColumn, 0, BeltLayout.columns() - visibleColumns);
        positionSlots();
        rebuildEyes();
    }

    private void rebuildEyes() {
        if (eyes.contains(getFocused())) setFocused(null);
        eyes.forEach(this::removeWidget);
        eyes.clear();
        for (int local = 0; local < visibleRows; local++) {
            int row = firstRow + local;
            var eye = new RowEyeButton(leftPos + contentWidth + 28, topPos + 19 + local * 18, button -> {
                if (minecraft != null && minecraft.gameMode != null) {
                    minecraft.gameMode.handleInventoryButtonClick(menu.containerId, row);
                }
            });
            eyes.add(addRenderableWidget(eye));
        }
    }

    private void positionSlots() {
        for (int i = 0; i < menu.slots.size(); i++) {
            Slot slot = menu.slots.get(i);
            SlotLayoutAccess layout = (SlotLayoutAccess) slot;
            if (i < menu.beltSlots) {
                int row = BeltLayout.row(i) - firstRow;
                int column = BeltLayout.column(i) - firstColumn;
                layout.toolbeltunlimited$setX(8 + column * 18);
                layout.toolbeltunlimited$setY(20 + row * 18);
                layout.toolbeltunlimited$setVisible(row >= 0 && row < visibleRows && column >= 0 && column < visibleColumns);
            } else {
                int playerSlot = i - menu.beltSlots;
                int y = playerSlot < 27 ? 51 + playerSlot / 9 * 18 : 109;
                layout.toolbeltunlimited$setX(inventoryLabelX + playerSlot % 9 * 18);
                layout.toolbeltunlimited$setY(y + (visibleRows - 1) * 18);
            }
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if ((scrollY != 0 || scrollX != 0) && mouseX >= leftPos && mouseX < leftPos + imageWidth
                && mouseY >= topPos && mouseY < topPos + imageHeight) {
            if (hasShiftDown() || scrollX != 0) {
                double delta = scrollX != 0 ? scrollX : scrollY;
                int next = Math.clamp(firstColumn - (int) Math.signum(delta), 0, BeltLayout.columns() - visibleColumns);
                if (next != firstColumn) {
                    firstColumn = next;
                    positionSlots();
                    return true;
                }
            } else {
                int next = Math.clamp(firstRow - (int) Math.signum(scrollY), 0, BeltLayout.rows(menu.beltSlots) - visibleRows);
                if (next != firstRow) {
                    firstRow = next;
                    positionSlots();
                    rebuildEyes();
                    return true;
                }
            }
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        var hidden = BeltRows.hidden(((BeltMenuAccess) menu).toolbeltunlimited$getBelt(minecraft.player));
        for (int i = 0; i < eyes.size(); i++) {
            int row = firstRow + i;
            boolean off = hidden.contains(row);
            Component message = Component.translatable(off ? "text.toolbeltunlimited.show_row" : "text.toolbeltunlimited.hide_row", row + 1);
            eyes.get(i).setHidden(off);
            if (!eyes.get(i).getMessage().equals(message)) {
                eyes.get(i).setMessage(message);
                eyes.get(i).setTooltip(Tooltip.create(message));
            }
        }
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        graphics.fill(leftPos, topPos, leftPos + imageWidth, topPos + imageHeight, 0xFF373737);
        graphics.fill(leftPos, topPos, leftPos + imageWidth - 2, topPos + imageHeight - 2, 0xFFFFFFFF);
        graphics.fill(leftPos + 2, topPos + 2, leftPos + imageWidth - 2, topPos + imageHeight - 2, 0xFFC6C6C6);
        for (int row = 0; row < visibleRows; row++) {
            int y = topPos + 19 + row * 18;
            int count = Math.min(visibleColumns, menu.beltSlots - (firstRow + row) * BeltLayout.columns() - firstColumn);
            for (int col = 0; col < count; col++) {
                graphics.blit(TEXTURE, leftPos + 7 + col * 18, y, 0, 133, 18, 18);
            }
            graphics.drawString(font, Integer.toString(firstRow + row + 1), leftPos + contentWidth + 12, y + 5, 0xFF404040, false);
        }
        for (int i = menu.beltSlots; i < menu.slots.size(); i++) {
            Slot slot = menu.getSlot(i);
            graphics.blit(TEXTURE, leftPos + slot.x - 1, topPos + slot.y - 1, 0, 133, 18, 18);
        }
        if (visibleColumns < BeltLayout.columns()) {
            graphics.drawString(font, Component.translatable("text.toolbeltunlimited.scroll_columns"), leftPos, topPos - 12, 0xFFFFFFFF);
        } else if (visibleRows < BeltLayout.rows(menu.beltSlots)) {
            graphics.drawString(font, Component.translatable("text.toolbeltunlimited.scroll"), leftPos, topPos - 12, 0xFFFFFFFF);
        }
    }
}
