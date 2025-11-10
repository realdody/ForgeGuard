package dev.riever.forgeguard;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        Config.synchronizeConfiguration(event.getSuggestedConfigurationFile());

        ForgeGuard.LOG.info("I am ForgeGuard at version " + Tags.VERSION);
    }
}
