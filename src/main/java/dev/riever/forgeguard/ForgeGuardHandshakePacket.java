package dev.riever.forgeguard;

import java.util.UUID;

import com.mojang.authlib.properties.Property;

public interface ForgeGuardHandshakePacket {

    String forgeguard$getAddress();

    UUID forgeguard$getId();

    Property[] forgeguard$getProperties();
}
