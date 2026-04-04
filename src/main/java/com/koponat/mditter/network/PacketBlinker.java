package com.koponat.mditter.network;

public class PacketBlinker {
    private static boolean active = false;
    public static void toggle() { active = !active; }
    public static boolean isActive() { return active; }
}
