package com.koponat.mditter;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import com.koponat.mditter.network.PacketBlinker;
import org.lwjgl.glfw.GLFW;

public class MditterMod implements ModInitializer {

    private static KeyBinding blinkKey;

    @Override
    public void onInitialize() {
        blinkKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.mditter.blink",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            "category.mditter.general"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (blinkKey.wasPressed()) {
                boolean currentState = PacketBlinker.isBlinking();
                PacketBlinker.setBlinking(!currentState);
            }
        });
    }
}
