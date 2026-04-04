package com.koponat.mditter.network;

import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.MinecraftClient;

public class PacketBlinker {
    private static boolean active = false;
    private static OtherClientPlayerEntity fakePlayer;

    public static void toggle(MinecraftClient client) {
        active = !active;
        if (active) {
            spawnFakePlayer(client);
        } else {
            removeFakePlayer();
        }
    }

    public static boolean isActive() {
        return active;
    }

    private static void spawnFakePlayer(MinecraftClient client) {
        if (client.player == null || client.world == null) return;
        fakePlayer = new OtherClientPlayerEntity(client.world, client.player.getGameProfile());
        fakePlayer.copyFrom(client.player);
        fakePlayer.copyPositionAndRotation(client.player);
        fakePlayer.headYaw = client.player.headYaw;
        fakePlayer.uuid = java.util.UUID.randomUUID();
        client.world.addEntity(fakePlayer.getId(), fakePlayer);
    }

    private static void removeFakePlayer() {
        if (fakePlayer != null && fakePlayer.getWorld() != null) {
            fakePlayer.discard();
            fakePlayer = null;
        }
    }
}
