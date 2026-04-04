package com.koponat.mditter;

import com.koponat.mditter.gui.MditterScreen;
import com.koponat.mditter.network.PacketBlinker;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class MditterMod implements ModInitializer {

    private boolean wasPressed = false;

    @Override
    public void onInitialize() {
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            if (PacketBlinker.isActive()) {
                drawContext.drawTextWithShadow(
                    MinecraftClient.getInstance().textRenderer,
                    "§c[MDITTER] PACKET FREEZE ACTIVE",
                    10, 10, 0xFFFFFF
                );
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.window == null) return;

            boolean isPressed = GLFW.glfwGetKey(client.getWindow().getHandle(), GLFW.GLFW_KEY_RIGHT_SHIFT) == GLFW.GLFW_PRESS;

            if (isPressed && !wasPressed) {
                if (client.currentScreen == null) {
                    client.setScreen(new MditterScreen());
                }
            }
            wasPressed = isPressed;

            if (PacketBlinker.isActive() && client.player != null) {
                client.player.sendMessage(Text.literal("§eMDITTER is suppressing outgoing data..."), true);
            }
        });
    }
}
