package dev.riever.forgeguard.mixin;

import java.io.IOException;
import java.util.UUID;

import net.minecraft.network.PacketBuffer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.google.gson.Gson;
import com.mojang.authlib.properties.Property;
import com.mojang.util.UUIDTypeAdapter;

import dev.riever.forgeguard.ForgeGuardHandshakePacket;

@Mixin(net.minecraft.network.handshake.client.C00Handshake.class)
public class HandshakePacketMixin implements ForgeGuardHandshakePacket {

    @Unique
    private String forgeguard$address;
    @Unique
    private UUID forgeguard$id;
    @Unique
    private Property[] forgeguard$properties;

    @Unique
    private static final Gson forgeguard$GSON = new Gson();

    @Redirect(
        method = "readPacketData",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/network/PacketBuffer;readStringFromBuffer(I)Ljava/lang/String;"))
    private String onReadPacketData(PacketBuffer buf, int length) throws IOException {
        String data = buf.readStringFromBuffer(Short.MAX_VALUE);
        String[] split = data.split("\0");
        if (split.length <= 2) {
            return data;
        }
        forgeguard$address = split[1];
        forgeguard$id = UUIDTypeAdapter.fromString(split[2]);
        if (split.length > 3) {
            forgeguard$properties = forgeguard$GSON.fromJson(split[3], Property[].class);
        }
        return split[1];
    }

    @Override
    public String forgeguard$getAddress() {
        return forgeguard$address;
    }

    @Override
    public UUID forgeguard$getId() {
        return forgeguard$id;
    }

    @Override
    public Property[] forgeguard$getProperties() {
        return forgeguard$properties;
    }
}
