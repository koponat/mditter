package com.koponat.mditter.gui;

import com.koponat.mditter.network.PacketBlinker;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

public class MditterTermsScreen extends Screen {
    private final Screen parent;

    public MditterTermsScreen(Screen parent) {
        super(Text.literal("Mditter Terms"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int x = this.width / 2 - 100;
        int y = this.height / 2 + 40;

        this.addDrawableChild(ButtonWidget.builder(
            Text.literal(PacketBlinker.getLang("访问仓库 (GitHub)", "Visit Repository (GitHub)")), 
            b -> Util.getOperatingSystem().open("https://github.com/koponat/mditter")
        ).dimensions(x, y, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
            Text.literal(PacketBlinker.getLang("返回", "Back")), 
            b -> this.client.setScreen(this.parent)
        ).dimensions(x, y + 25, 200, 20).build());
    }

    @Override
    public void render(DrawContext dc, int mx, int my, float d) {
        // 修正：同步更新为 4 参数版
        this.renderBackground(dc, mx, my, d);
        
        String title = PacketBlinker.getLang("MDITTER 使用须知", "MDITTER Usage Notice");
        String warn1 = PacketBlinker.getLang("任何使用 mditter 导致被服务器拉黑等产生的后果", "Any consequences such as being banned from servers");
        String warn2 = PacketBlinker.getLang("均与 mditter 仓库没有任何关系。", "have nothing to do with the mditter repository.");
        
        dc.drawCenteredTextWithShadow(this.textRenderer, title, this.width / 2, 30, 0xFFFF00);
        dc.drawCenteredTextWithShadow(this.textRenderer, warn1, this.width / 2, 60, 0xFF5555);
        dc.drawCenteredTextWithShadow(this.textRenderer, warn2, this.width / 2, 75, 0xFF5555);
        
        super.render(dc, mx, my, d);
    }
}
