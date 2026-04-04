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
                    Text.translatable("hud.mditter.active"),
                    10, 10, 0xFFFFFF
                );
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client == null || client.getWindow() == null) return;
            long handle = client.getWindow().getHandle();
            boolean isPressed = GLFW.glfwGetKey(handle, GLFW.GLFW_KEY_RIGHT_SHIFT) == GLFW.GLFW_PRESS;
            if (isPressed && !wasPressed && client.currentScreen == null) {
                client.setScreen(new MditterScreen());
            }
            wasPressed = isPressed;
        });
    }
}
