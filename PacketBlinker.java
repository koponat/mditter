package com.koponat.mditter.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientSendMessageEvents;
import net.fabricmc.fabric.api.client.networking.v1.C2SPlayChannelEvents;
import net.minecraft.network.packet.Packet;

public class PacketBlinker {

    private static boolean isBlinking = false;

    public static void setBlinking(boolean value) {
        isBlinking = value;
    }

    public static boolean isBlinking() {
        return isBlinking;
    }

    public static boolean shouldCancelPacket(Packet<?> packet) {
        if (isBlinking) {
            return true;
        }
        return false;
    }
}
