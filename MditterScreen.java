package com.koponat.mditter.gui;

import com.koponat.mditter.network.PacketBlinker;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class MditterScreen extends Screen {

    public MditterScreen() {
        super(Text.literal("MDITTER Control Panel"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        this.addDrawableChild(ButtonWidget.builder(getToggleText(), button -> {
            PacketBlinker.toggle();
            button.setMessage(getToggleText());
        }).dimensions(centerX - 100, centerY, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Close Menu"), button -> {
            this.close();
        }).dimensions(centerX - 100, centerY + 30, 200, 20).build());
    }

    private Text getToggleText() {
        String status = PacketBlinker.isActive() ? "ON (BLOCKING PACKETS)" : "OFF (NORMAL)";
        return Text.literal("Network Status: " + status);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);

        int centerX = this.width / 2;
        
        context.drawCenteredTextWithShadow(this.textRenderer, "WELCOME TO MDITTER", centerX, this.height / 4, 0x00FF00);
        context.drawCenteredTextWithShadow(this.textRenderer, "Advanced Client-Side Packet Controller", centerX, this.height / 4 + 15, 0xAAAAAA);

        String currentStatus = PacketBlinker.isActive() ? "PROTECTED" : "UNPROTECTED";
        int statusColor = PacketBlinker.isActive() ? 0xFF5555 : 0x55FF55;
        context.drawCenteredTextWithShadow(this.textRenderer, "Current State: " + currentStatus, centerX, this.height / 4 + 40, statusColor);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
