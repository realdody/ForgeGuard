package dev.riever.forgeguard;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {

    public static boolean bungeeguardEnabled = true;
    public static String bungeeguardToken = "";

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);

        bungeeguardEnabled = configuration.getBoolean(
            "bungeeguardEnabled",
            Configuration.CATEGORY_GENERAL,
            bungeeguardEnabled,
            "Enable BungeeGuard?");
        bungeeguardToken = configuration
            .getString("bungeeguardToken", Configuration.CATEGORY_GENERAL, bungeeguardToken, "BungeeGuard token");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
