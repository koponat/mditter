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
    @Inject(at = @At("HEAD"), method = "send(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/PacketCallbacks;Z)V", cancellable = true)
    private void onSend(Packet<?> p, PacketCallbacks c, boolean flush, CallbackInfo ci) {
        if (PacketBlinker.shouldCancel(p)) {
            ci.cancel();
        }
    }
}
