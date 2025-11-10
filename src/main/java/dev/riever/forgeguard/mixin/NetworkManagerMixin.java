package dev.riever.forgeguard.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import dev.riever.forgeguard.ForgeGuardNetworkManager;
import dev.riever.forgeguard.ForwardedProfile;

@Mixin(net.minecraft.network.NetworkManager.class)
public class NetworkManagerMixin implements ForgeGuardNetworkManager {

    @Unique
    private ForwardedProfile forgeguard$forwardedProfile;

    @Override
    public ForwardedProfile forgeguard$getForwardedProfile() {
        return forgeguard$forwardedProfile;
    }

    @Override
    public boolean forgeguard$hasForwardedProfile() {
        return forgeguard$forwardedProfile != null && forgeguard$forwardedProfile.isComplete();
    }

    @Override
    public void forgeguard$setForwardedProfile(ForwardedProfile forwardedProfile) {
        forgeguard$forwardedProfile = forwardedProfile;
    }
}
