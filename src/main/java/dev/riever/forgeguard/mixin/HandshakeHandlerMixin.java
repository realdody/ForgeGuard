package dev.riever.forgeguard.mixin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.util.ChatComponentText;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.authlib.properties.Property;

import dev.riever.forgeguard.Config;
import dev.riever.forgeguard.ForgeGuardHandshakePacket;
import dev.riever.forgeguard.ForgeGuardNetworkManager;
import dev.riever.forgeguard.ForwardedProfile;

@Mixin(net.minecraft.server.network.NetHandlerHandshakeTCP.class)
public class HandshakeHandlerMixin {

    @Final
    @Shadow
    private NetworkManager field_147386_b;

    @Unique
    private static final String BUNGEEGUARD_TOKEN_NAME = "bungeeguard-token";

    @Unique
    private void forgeguard$abort(String reason) {
        ChatComponentText chatcomponenttext = new ChatComponentText(reason);
        field_147386_b.scheduleOutboundPacket(new S00PacketDisconnect(chatcomponenttext));
        field_147386_b.closeChannel(chatcomponenttext);
    }

    @Inject(
        method = "processHandshake(Lnet/minecraft/network/handshake/client/C00Handshake;)V",
        at = @At("HEAD"),
        cancellable = true)
    public void onProcessHandshake(C00Handshake packetIn, CallbackInfo ci) {
        ForgeGuardNetworkManager networkManager = (ForgeGuardNetworkManager) field_147386_b;
        ForgeGuardHandshakePacket handshakePacket = (ForgeGuardHandshakePacket) packetIn;
        String address = handshakePacket.forgeguard$getAddress();
        UUID id = handshakePacket.forgeguard$getId();
        Property[] rawProperties = handshakePacket.forgeguard$getProperties();
        List<Property> properties = new ArrayList<>();
        // If bungeeguard is active
        String bungeeguardToken = null;
        if (rawProperties != null) {
            for (Property property : rawProperties) {
                if (property.getName()
                    .equals(BUNGEEGUARD_TOKEN_NAME)) {
                    bungeeguardToken = property.getValue();
                } else {
                    properties.add(property);
                }
            }
        }
        if (packetIn.func_149594_c() == EnumConnectionState.LOGIN && Config.bungeeguardEnabled) {
            if (bungeeguardToken == null) {
                forgeguard$abort("BungeeGuard token is missing");
                ci.cancel();
                return;
            }
            if (!bungeeguardToken.equals(Config.bungeeguardToken)) {
                forgeguard$abort("BungeeGuard token is invalid");
                ci.cancel();
                return;
            }
        }
        networkManager.forgeguard$setForwardedProfile(new ForwardedProfile(address, id, properties));
    }
}
