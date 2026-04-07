package com.koponat.mditter;

import com.koponat.mditter.gui.MditterScreen;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class MditterMod implements ModInitializer {
    private static KeyBinding openGuiKey;

    @Override
    public void onInitialize() {
        // 注册按键：默认按 J 键打开界面
        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.mditter.open_gui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                "category.mditter.general"
        ));

        // 注册每一帧的监听
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGuiKey.wasPressed()) {
                client.setScreen(new MditterScreen());
            }
        });
    }
}
