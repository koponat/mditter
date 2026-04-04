package com.koponat.mditter;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import com.koponat.mditter.gui.MditterScreen;
import com.koponat.mditter.network.PacketBlinker;
import org.lwjgl.glfw.GLFW;
import net.minecraft.text.Text;

public class MditterMod implements ModInitializer {

    @Override
    public void onInitialize() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            while (GLFW.glfwGetKey(client.getWindow().getHandle(), GLFW.GLFW_KEY_RIGHT_SHIFT) == GLFW.GLFW_PRESS) {
                if (client.currentScreen == null) {
                    client.setScreen(new MditterScreen());
                }
                break;
            }

            if (PacketBlinker.isBlinking()) {
                client.player.sendMessage(Text.literal("§c[MDITTER] Packet Sending: BLOCKED"), true);
            }
        });
    }
}
