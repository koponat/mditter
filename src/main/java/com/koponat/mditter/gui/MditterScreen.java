package com.koponat.mditter.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

public class MditterScreen extends Screen {
    private static boolean isEnglish = false;

    public MditterScreen() {
        super(Text.literal("MDITTER v1.5"));
    }

    @Override
    protected void init() {
        int x = this.width / 2 - 100;
        int y = this.height / 2 - 60;

        // 功能按钮示例
        this.addDrawableChild(ButtonWidget.builder(getLangText("数据包拦截: 关闭", "Packet Blocker: OFF"), b -> {
        }).dimensions(x, y, 200, 20).build());

        // 访问仓库
        this.addDrawableChild(ButtonWidget.builder(getLangText("访问 GitHub", "Visit GitHub"), b -> {
            Util.getOperatingSystem().open("https://github.com/koponat/mditter");
        }).dimensions(x, y + 25, 200, 20).build());

        // 切换语言按钮
        this.addDrawableChild(ButtonWidget.builder(getLangText("语言切换", "Switch Language"), b -> {
            isEnglish = !isEnglish;
            this.clearAndInit(); // 刷新界面显示
        }).dimensions(x, y + 50, 200, 20).build());
    }

    private Text getLangText(String zh, String en) {
        return Text.literal(isEnglish ? en : zh);
    }

    @Override
    public void render(DrawContext dc, int mx, int my, float d) {
        super.render(dc, mx, my, d);
        dc.drawCenteredTextWithShadow(this.textRenderer, getLangText("欢迎使用 MDITTER v1.5", "Welcome to MDITTER v1.5"), this.width / 2, this.height / 2 - 85, 0xFFFFFF);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
