package com.koponat.mditter;

import com.koponat.mditter.gui.MditterScreen;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class MditterMod implements ModInitializer {
    private static KeyBinding openGuiKey;

    @Override
    public void onInitialize() {
        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.mditter.open_gui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                "category.mditter.general"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGuiKey.wasPressed()) {
                client.setScreen(new MditterScreen());
            }
        });

        ClientSendMessageEvents.ALLOW_CHAT.register(message -> {
            if (MditterScreen.packetBlockerActive) {
                if (net.minecraft.client.MinecraftClient.getInstance().player != null) {
                    net.minecraft.client.MinecraftClient.getInstance().player.sendMessage(Text.literal("§c[MDITTER] 数据包已拦截，发送失败"), false);
                }
                return false; 
            }
            return true;
        });

        ClientSendMessageEvents.ALLOW_COMMAND.register(command -> {
            if (MditterScreen.packetBlockerActive) {
                return false;
            }
            return true;
        });

        ClientReceiveMessageEvents.GAME.register((message, overlay) -> {
            if (MditterScreen.snifferActive) {
                System.out.println("[MDITTER SNIFFER] 捕获服务器下发数据: " + message.getString());
            }
        });
    }
}
