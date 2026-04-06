package com.koponat.mditter.gui;

import com.koponat.mditter.network.PacketBlinker;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;

public class MditterScreen extends Screen {
    public MditterScreen() {
        super(Text.literal("MDITTER v1.4"));
    }

    @Override
    protected void init() {
        int x = this.width / 2 - 100;
        int y = this.height / 2 - 40;

        this.addDrawableChild(ButtonWidget.builder(getToggleText(), b -> {
            PacketBlinker.toggle(MinecraftClient.getInstance());
            b.setMessage(getToggleText());
        }).dimensions(x, y, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(getLangButtonText(), b -> {
            PacketBlinker.toggleLanguage();
            if (this.client != null) {
                this.client.setScreen(new MditterScreen());
            }
        }).dimensions(x, y + 25, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
            Text.literal(PacketBlinker.getLang("mditter用户使用条款", "Mditter Terms of Service")), 
            b -> {
                if (this.client != null) {
                    this.client.setScreen(new MditterTermsScreen(this));
                }
            }
        ).dimensions(x, y + 50, 200, 20).build());
    }

    private Text getToggleText() {
        String status = PacketBlinker.isActive() ? 
            PacketBlinker.getLang("灵魂出窍 (拦截开启)", "SOUL MODE (ON)") : 
            PacketBlinker.getLang("肉身禁锢 (正常模式)", "BODY MODE (OFF)");
        return Text.literal(PacketBlinker.getLang("当前状态: ", "Status: ") + status);
    }

    private Text getLangButtonText() {
        return Text.literal(PacketBlinker.getLang("语言: 简体中文", "Language: English"));
    }

    @Override
    public void render(DrawContext dc, int mx, int my, float d) {
        this.renderBackground(dc); 
        dc.drawCenteredTextWithShadow(this.textRenderer, "MDITTER v1.4 (1.20.2)", this.width / 2, 20, 0x00FF00);
        super.render(dc, mx, my, d);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
