package com.koponat.mditter.gui;

import com.koponat.mditter.network.PacketBlinker;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class MditterScreen extends Screen {
    public MditterScreen() { super(Text.literal("MDITTER Panel")); }

    @Override
    protected void init() {
        int x = this.width / 2 - 100;
        int y = this.height / 2;
        this.addDrawableChild(ButtonWidget.builder(getToggleText(), b -> {
            PacketBlinker.toggle();
            b.setMessage(getToggleText());
        }).dimensions(x, y, 200, 20).build());
    }

    private Text getToggleText() {
        return Text.literal("Network: " + (PacketBlinker.isActive() ? "BLOCKING" : "NORMAL"));
    }

    @Override
    public void render(DrawContext dc, int mx, int my, float d) {
        this.renderBackground(dc);
        dc.drawCenteredTextWithShadow(this.textRenderer, "MDITTER CONTROL PANEL", this.width / 2, 40, 0x00FF00);
        super.render(dc, mx, my, d);
    }

    @Override
    public boolean shouldPause() { return false; }
}
