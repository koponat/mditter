package com.koponat.mditter.network;

import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.KeepAliveC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import java.util.UUID;

public class PacketBlinker {
    private static boolean active = false;
    private static OtherClientPlayerEntity fakePlayer = null;
    private static boolean useChinese = true;

    public static void toggle(MinecraftClient client) {
        active = !active;
        if (client.player == null) return;

        if (active) {
            spawnFakePlayer(client);
            // 开启灵魂视角：允许飞行，关闭碰撞
            client.player.getAbilities().flying = true;
            client.player.noClip = true;
        } else {
            removeFakePlayer();
            // 关闭灵魂视角：恢复正常
            client.player.getAbilities().flying = false;
            client.player.noClip = false;
        }
    }

    public static boolean shouldCancel(Packet<?> packet) {
        if (!active) return false;
        // 灵魂视角核心：拦截所有移动包，防止服务器发现你在乱跑
        if (packet instanceof PlayerMoveC2SPacket) return true;
        // 放行心跳包防止掉线
        return !(packet instanceof KeepAliveC2SPacket);
    }

    public static void toggleLanguage() { useChinese = !useChinese; }
    public static String getLang(String zh, String en) { return useChinese ? zh : en; }
    public static boolean isActive() { return active; }

    private static void spawnFakePlayer(MinecraftClient client) {
        if (client.player == null || client.world == null) return;
        fakePlayer = new OtherClientPlayerEntity(client.world, client.player.getGameProfile());
        fakePlayer.copyFrom(client.player);
        fakePlayer.copyPositionAndRotation(client.player);
        fakePlayer.headYaw = client.player.headYaw;
        fakePlayer.setUuid(UUID.randomUUID());
        client.world.addEntity(fakePlayer.getId(), fakePlayer);
    }

    private static void removeFakePlayer() {
        if (fakePlayer != null) {
            if (fakePlayer.getWorld() != null) {
                fakePlayer.discard();
            }
            fakePlayer = null;
        }
    }
}
