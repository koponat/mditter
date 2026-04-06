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

        // 按钮 1: 灵魂视角开关 (原网络拦截)
        this.addDrawableChild(ButtonWidget.builder(getToggleText(), b -> {
            PacketBlinker.toggle(MinecraftClient.getInstance());
            b.setMessage(getToggleText());
        }).dimensions(x, y, 200, 20).build());

        // 按钮 2: 语言切换
        this.addDrawableChild(ButtonWidget.builder(getLangButtonText(), b -> {
            PacketBlinker.toggleLanguage();
            // 重新初始化界面以刷新所有文字
            this.clearAndInit();
        }).dimensions(x, y + 25, 200, 20).build());

        // 按钮 3: 用户使用条款 (位于最下方)
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
            PacketBlinker.getLang("灵魂出窍 (拦截中)", "SOUL MODE (BLOCKING)") : 
            PacketBlinker.getLang("肉身禁锢 (正常)", "BODY MODE (NORMAL)");
        return Text.literal(PacketBlinker.getLang("当前状态: ", "Status: ") + status);
    }

    private Text getLangButtonText() {
        return Text.literal(PacketBlinker.getLang("语言: 简体中文", "Language: English"));
    }

    @Override
    public void render(DrawContext dc, int mx, int my, float d) {
        // 1.20.1 的渲染背景方法
        this.renderBackground(dc);
        
        // 标题渲染
        dc.drawCenteredTextWithShadow(this.textRenderer, "MDITTER v1.4", this.width / 2, 20, 0x00FF00);
        
        // 版本提示
        String hint = PacketBlinker.getLang("适配版本: 1.20.1", "Compatible: 1.20.1");
        dc.drawCenteredTextWithShadow(this.textRenderer, hint, this.width / 2, 35, 0xAAAAAA);
        
        super.render(dc, mx, my, d);
    }

    @Override
    public boolean shouldPause() {
        // 打开菜单时是否暂停游戏（单机有效）
        return false;
    }
}
