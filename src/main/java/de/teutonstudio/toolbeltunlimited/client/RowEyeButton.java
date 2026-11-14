package de.teutonstudio.toolbeltunlimited.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

/** Pixel-drawn eye, with the normal Button keyboard and narration behavior. */
public final class RowEyeButton extends Button {
    private boolean hidden;

    public RowEyeButton(int x, int y, OnPress onPress) {
        super(x, y, 18, 18, Component.empty(), onPress, DEFAULT_NARRATION);
    }

    public void setHidden(boolean hidden) { this.hidden = hidden; }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        int x = getX();
        int y = getY();
        graphics.fill(x, y, x + width, y + height, isHoveredOrFocused() ? 0xFF7990A5 : 0xFF555B62);
        graphics.fill(x + 1, y + 1, x + width - 1, y + height - 1, 0xFF292D32);
        int color = hidden ? 0xFF888888 : 0xFFE0EAF3;
        graphics.fill(x + 3, y + 7, x + 15, y + 11, color);
        graphics.fill(x + 5, y + 5, x + 13, y + 13, color);
        graphics.fill(x + 7, y + 7, x + 11, y + 11, 0xFF292D32);
        if (hidden) {
            for (int i = 0; i < 12; i++) graphics.fill(x + 3 + i, y + 14 - i, x + 5 + i, y + 15 - i, 0xFFE77A72);
        }
    }
}
