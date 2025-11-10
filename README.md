# ForgeGuard

An implementation of [BungeeGuard](https://www.spigotmc.org/resources/bungeeguard.79601/) protocol for Forge 1.7.10, inspired by [BungeeForge](https://github.com/caunt/BungeeForge/tree/1.7.10) and [BungeeGuard](https://github.com/lucko/BungeeGuard)

Designed to work with [GTNH](https://www.gtnewhorizons.com/) behind [Velocity](https://papermc.io/software/velocity/)

## How to use

1. Install Velocity and GTNH
2. Set up [BungeeGuard forwarding](https://docs.papermc.io/velocity/player-information-forwarding/#configuring-legacy-bungeecord-compatible-forwarding) on Velocity
3. Install ForgeGuard into your `mods` folder
4. Start the server to generate the config file
5. Open `forgeguard.cfg` in the `config` folder and set the `bungeeguardToken` to the forwarding secret from Step 2

Note: if you disable `bungeeguardEnabled` then ForgeGuard is functionally identical to [BungeeForge](https://github.com/caunt/BungeeForge/tree/1.7.10).
