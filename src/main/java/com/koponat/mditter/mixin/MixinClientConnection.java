package com.koponat.mditter.mixin;

import com.koponat.mditter.network.PacketBlinker;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.PacketCallbacks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class MixinClientConnection {
    // 修正：1.20.1 的 send 方法只有两个参数
    @Inject(at = @At("HEAD"), method = "send(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/PacketCallbacks;)V", cancellable = true)
    private void onSend(Packet<?> p, PacketCallbacks c, CallbackInfo ci) {
        if (PacketBlinker.shouldCancel(p)) {
            ci.cancel();
        }
    }
}
