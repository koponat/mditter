package com.koponat.mditter.gui;

import com.koponat.mditter.network.PacketBlinker;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;

public class MditterScreen extends Screen {
    public MditterScreen() { 
        super(Text.literal("MDITTER")); 
    }

    @Override
    protected void init() {
        int x = this.width / 2 - 100;
        int y = this.height / 2 - 20;

        this.addDrawableChild(ButtonWidget.builder(getToggleText(), b -> {
            PacketBlinker.toggle(MinecraftClient.getInstance());
            b.setMessage(getToggleText());
        }).dimensions(x, y, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(getLangButtonText(), b -> {
            PacketBlinker.toggleLanguage();
            this.clearAndInit();
        }).dimensions(x, y + 25, 200, 20).build());
    }

    private Text getToggleText() {
        String status = PacketBlinker.isActive() ? 
            PacketBlinker.getLang("拦截中", "BLOCKING") : 
            PacketBlinker.getLang("正常", "NORMAL");
        String label = PacketBlinker.getLang("网络状态: ", "Network: ");
        return Text.literal(label + status);
    }

    private Text getLangButtonText() {
        return Text.literal(PacketBlinker.getLang("语言: 简体中文", "Language: English"));
    }

    @Override
    public void render(DrawContext dc, int mx, int my, float d) {
        this.renderBackground(dc);
        String title = PacketBlinker.getLang("欢迎使用 MDITTER", "WELCOME TO MDITTER");
        dc.drawCenteredTextWithShadow(this.textRenderer, title, this.width / 2, 40, 0x00FF00);
        super.render(dc, mx, my, d);
    }

    @Override
    public boolean shouldPause() { return false; }
}
