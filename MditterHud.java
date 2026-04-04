package com.koponat.mditter.gui;

import com.koponat.mditter.network.PacketBlinker;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;

public class MditterHud implements HudRenderCallback {
    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        if (PacketBlinker.isBlinking()) {
            drawContext.drawTextWithShadow(
                net.minecraft.client.MinecraftClient.getInstance().textRenderer,
                "MDITTER: PACKET FREEZE ACTIVE",
                10, 10, 0xFF5555
            );
        }
    }
}
