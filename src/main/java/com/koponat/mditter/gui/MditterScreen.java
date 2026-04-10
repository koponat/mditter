package com.koponat.mditter.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

public class MditterScreen extends Screen {
    public static boolean packetBlockerActive = false;
    public static boolean ghostModeActive = false;
    public static boolean snifferActive = false;
    private static boolean isEnglish = false;

    public MditterScreen() {
        super(Text.literal("MDITTER PRO v1.5"));
    }

    @Override
    protected void init() {
        int x = this.width / 2 - 100;
        int y = this.height / 2 - 80;

        this.addDrawableChild(ButtonWidget.builder(
            getLangText("数据包拦截: " + (packetBlockerActive ? "ON" : "OFF"), 
                        "Packet Blocker: " + (packetBlockerActive ? "ON" : "OFF")), 
            button -> {
                packetBlockerActive = !packetBlockerActive;
                button.setMessage(getLangText("数据包拦截: " + (packetBlockerActive ? "ON" : "OFF"), 
                                             "Packet Blocker: " + (packetBlockerActive ? "ON" : "OFF")));
            }
        ).dimensions(x, y, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
            getLangText("虚拟人镜像: " + (ghostModeActive ? "ON" : "OFF"), 
                        "Ghost Mode: " + (ghostModeActive ? "ON" : "OFF")), 
            button -> {
                ghostModeActive = !ghostModeActive;
                button.setMessage(getLangText("虚拟人镜像: " + (ghostModeActive ? "ON" : "OFF"), 
                                             "Ghost Mode: " + (ghostModeActive ? "ON" : "OFF")));
            }
        ).dimensions(x, y + 25, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
            getLangText("抓包器状态: " + (snifferActive ? "运行中" : "已停止"), 
                        "Sniffer: " + (snifferActive ? "RUNNING" : "STOPPED")), 
            button -> {
                snifferActive = !snifferActive;
                button.setMessage(getLangText("抓包器状态: " + (snifferActive ? "运行中" : "已停止"), 
                                             "Sniffer: " + (snifferActive ? "RUNNING" : "STOPPED")));
            }
        ).dimensions(x, y + 50, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
            getLangText("访问仓库", "GitHub"), 
            button -> {
                Util.getOperatingSystem().open("https://github.com/koponat/mditter");
            }
        ).dimensions(x, y + 75, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
            getLangText("语言切换", "Language"), 
            button -> {
                isEnglish = !isEnglish;
                this.clearAndInit();
            }
        ).dimensions(x, y + 100, 200, 20).build());
    }

    private Text getLangText(String zh, String en) {
        return Text.literal(isEnglish ? en : zh);
    }

    @Override
    public void render(DrawContext dc, int mx, int my, float d) {
        super.render(dc, mx, my, d);
        dc.drawCenteredTextWithShadow(this.textRenderer, "MDITTER v1.5 - PRIVACY MODE", this.width / 2, this.height / 2 - 105, 0xFF55FF);
    }

    @Override
    public boolean shouldPause() { return false; }
}
