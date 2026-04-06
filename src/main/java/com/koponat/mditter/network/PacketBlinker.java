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
        if (client.player == null || client.world == null) return;
        active = !active;

        if (active) {
            // 1. 生成肉身留在原地
            spawnFakePlayer(client);
            
            // 2. 开启灵魂状态：允许穿墙和飞行
            client.player.noClip = true;
            client.player.getAbilities().flying = true;
            client.player.getAbilities().allowFlying = true;
        } else {
            // 3. 归位：坐标瞬间拉回到肉身位置
            if (fakePlayer != null) {
                client.player.refreshPositionAndAngles(fakePlayer.getX(), fakePlayer.getY(), fakePlayer.getZ(), fakePlayer.getYaw(), fakePlayer.getPitch());
            }
            
            // 4. 恢复物理状态
            client.player.noClip = false;
            client.player.getAbilities().flying = false;
            if (!client.player.isCreative()) {
                client.player.getAbilities().allowFlying = false;
            }
            
            removeFakePlayer();
        }
    }

    public static boolean shouldCancel(Packet<?> packet) {
        if (!active) return false;
        
        // 灵魂视角的关键：拦截所有向服务器发送的移动包
        // 这样在服务器看来，你一直停在原地（肉身处）
        if (packet instanceof PlayerMoveC2SPacket) return true;
        
        // 放行心跳包，否则会被踢
        return !(packet instanceof KeepAliveC2SPacket);
    }

    public static void toggleLanguage() { useChinese = !useChinese; }
    public static String getLang(String zh, String en) { return useChinese ? zh : en; }
    public static boolean isActive() { return active; }

    private static void spawnFakePlayer(MinecraftClient client) {
        fakePlayer = new OtherClientPlayerEntity(client.world, client.player.getGameProfile());
        fakePlayer.copyFrom(client.player);
        fakePlayer.copyPositionAndRotation(client.player);
        fakePlayer.headYaw = client.player.headYaw;
        fakePlayer.setUuid(UUID.randomUUID()); // 使用随机UUID防止冲突
        client.world.addEntity(fakePlayer.getId(), fakePlayer);
    }

    private static void removeFakePlayer() {
        if (fakePlayer != null) {
            fakePlayer.discard();
            fakePlayer = null;
        }
    }
}
