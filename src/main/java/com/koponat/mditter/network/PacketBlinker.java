package com.koponat.mditter.network;

import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.common.KeepAliveC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import java.util.UUID;

public class PacketBlinker {
    private static boolean active = false;
    private static OtherClientPlayerEntity fakePlayer = null;
    private static boolean useChinese = true;

    public static void toggle(MinecraftClient client) {
        if (client.player == null || client.world == null) return;
        active = !active;

        if (active) {
            spawnFakePlayer(client);
            client.player.noClip = true;
            client.player.getAbilities().flying = true;
        } else {
            if (fakePlayer != null) {
                client.player.refreshPositionAndAngles(fakePlayer.getX(), fakePlayer.getY(), fakePlayer.getZ(), fakePlayer.getYaw(), fakePlayer.getPitch());
            }
            client.player.noClip = false;
            client.player.getAbilities().flying = false;
            removeFakePlayer();
        }
    }

    public static boolean shouldCancel(Packet<?> packet) {
        if (!active) return false;
        // 1.21.2+ 移动包拦截逻辑
        if (packet instanceof PlayerMoveC2SPacket) return true;
        return !(packet instanceof KeepAliveC2SPacket);
    }

    public static void toggleLanguage() { useChinese = !useChinese; }
    public static String getLang(String zh, String en) { return useChinese ? zh : en; }
    public static boolean isActive() { return active; }

    private static void spawnFakePlayer(MinecraftClient client) {
        // 1.21.2 之后实体的创建需要显式处理 GameProfile
        fakePlayer = new OtherClientPlayerEntity(client.world, client.player.getGameProfile());
        fakePlayer.copyFrom(client.player);
        fakePlayer.copyPositionAndRotation(client.player);
        fakePlayer.setUuid(UUID.randomUUID());
        // 1.21.2 后的 addEntity 恢复了单参数（由系统内部处理 ID 分配）
        client.world.addEntity(fakePlayer);
    }

    private static void removeFakePlayer() {
        if (fakePlayer != null) {
            fakePlayer.discard();
            fakePlayer = null;
        }
    }
}
