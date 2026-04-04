package com.koponat.mditter.gui;

import com.koponat.mditter.network.PacketBlinker;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;

public class MditterScreen extends Screen {
    public MditterScreen() { super(Text.translatable("menu.mditter.welcome")); }

    @Override
    protected void init() {
        int x = this.width / 2 - 100;
        int y = this.height / 2;
        this.addDrawableChild(ButtonWidget.builder(getToggleText(), b -> {
            PacketBlinker.toggle(MinecraftClient.getInstance());
            b.setMessage(getToggleText());
        }).dimensions(x, y, 200, 20).build());
    }

    private Text getToggleText() {
        String key = PacketBlinker.isActive() ? "menu.mditter.blocking" : "menu.mditter.normal";
        return Text.translatable("menu.mditter.status", Text.translatable(key));
    }

    @Override
    public void render(DrawContext dc, int mx, int my, float d) {
        this.renderBackground(dc);
        dc.drawCenteredTextWithShadow(this.textRenderer, Text.translatable("menu.mditter.welcome").getString(), this.width / 2, 40, 0x00FF00);
        super.render(dc, mx, my, d);
    }

    @Override
    public boolean shouldPause() { return false; }
}
