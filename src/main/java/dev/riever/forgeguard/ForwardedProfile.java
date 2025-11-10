package dev.riever.forgeguard;

import java.util.List;
import java.util.UUID;

import com.github.bsideup.jabel.Desugar;
import com.mojang.authlib.properties.Property;

@Desugar
public record ForwardedProfile(String address, UUID id, List<Property> properties) {

    public boolean isComplete() {
        return address != null && id != null && properties != null;
    }
}
