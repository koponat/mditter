package com.koponat.mditter.gui;

import com.koponat.mditter.network.PacketBlinker;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class MditterScreen extends Screen {

    public MditterScreen() {
        super(Text.literal("MDITTER MENU"));
    }

    @Override
    protected void init() {
        int buttonWidth = 200;
        int buttonHeight = 20;
        int x = (this.width - buttonWidth) / 2;
        int y = (this.height / 2);

        this.addDrawableChild(ButtonWidget.builder(getButtonText(), button -> {
            PacketBlinker.setBlinking(!PacketBlinker.isBlinking());
            button.setMessage(getButtonText());
        }).dimensions(x, y, buttonWidth, buttonHeight).build());
    }

    private Text getButtonText() {
        String status = PacketBlinker.isBlinking() ? "ON (STOP SENDING)" : "OFF (NORMAL)";
        return Text.literal("Packet Block: " + status);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        
        float scale = 2.0f;
        context.getMatrices().push();
        context.getMatrices().scale(scale, scale, scale);
        
        int titleX = (int)((this.width / scale - this.textRenderer.getWidth("Welcome to MDITTER")) / 2);
        context.drawTextWithShadow(this.textRenderer, "Welcome to MDITTER", titleX, (int)(this.height / scale / 4), 0xFFFFFF);
        
        context.getMatrices().pop();
        
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
