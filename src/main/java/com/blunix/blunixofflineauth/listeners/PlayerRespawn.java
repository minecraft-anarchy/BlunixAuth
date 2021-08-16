package com.blunix.blunixofflineauth.listeners;

import com.blunix.blunixofflineauth.BlunixOfflineAuth;
import com.blunix.blunixofflineauth.util.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawn implements Listener {
    private final BlunixOfflineAuth plugin;

    public PlayerRespawn(BlunixOfflineAuth plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (!plugin.getLoginPlayers().containsKey(player.getUniqueId())) return;

        event.setRespawnLocation(ConfigManager.getLoginLocation());
    }
}
