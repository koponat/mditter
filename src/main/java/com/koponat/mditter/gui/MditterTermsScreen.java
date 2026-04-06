package com.koponat.mditter.gui;

import com.koponat.mditter.network.PacketBlinker;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class MditterTermsScreen extends Screen {
    private final Screen parent;

    public MditterTermsScreen(Screen parent) {
        super(Text.literal("Mditter Terms"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal(PacketBlinker.getLang("返回", "Back")), 
            b -> this.client.setScreen(this.parent)
        ).dimensions(this.width / 2 - 100, this.height - 40, 200, 20).build());
    }

    @Override
    public void render(DrawContext dc, int mx, int my, float d) {
        this.renderBackground(dc); // 1.20.1 仅需 1 个参数
        
        String title = PacketBlinker.getLang("MDITTER 用户使用条款须知", "MDITTER Terms of Service");
        String line1 = PacketBlinker.getLang("1. 本插件仅供技术研究使用。", "1. This mod is for technical research only.");
        String line2 = PacketBlinker.getLang("2. 请勿在任何非法环境使用。", "2. Do not use in any illegal environment.");
        String line3 = PacketBlinker.getLang("3. 使用者需自行承担相关风险。", "3. Users assume all risks involved.");

        dc.drawCenteredTextWithShadow(this.textRenderer, title, this.width / 2, 40, 0xFFFF00);
        dc.drawCenteredTextWithShadow(this.textRenderer, line1, this.width / 2, 80, 0xFFFFFF);
        dc.drawCenteredTextWithShadow(this.textRenderer, line2, this.width / 2, 100, 0xFFFFFF);
        dc.drawCenteredTextWithShadow(this.textRenderer, line3, this.width / 2, 120, 0xFFFFFF);
        
        super.render(dc, mx, my, d);
    }
}
