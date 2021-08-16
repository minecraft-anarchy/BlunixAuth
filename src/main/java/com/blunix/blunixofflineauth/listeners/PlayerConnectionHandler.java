package com.blunix.blunixofflineauth.listeners;

import com.blunix.blunixofflineauth.BlunixOfflineAuth;
import com.blunix.blunixofflineauth.util.ConfigManager;
import com.blunix.blunixofflineauth.util.Messager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerConnectionHandler implements Listener {
    private final BlunixOfflineAuth plugin;

    public PlayerConnectionHandler(BlunixOfflineAuth plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!plugin.getDataManager().isRegistered(player)) return;
        if (!plugin.getLoginPlayers().containsKey(player.getUniqueId())) {
            plugin.getLoginPlayers().put(player.getUniqueId(), player.getLocation());
        }
        player.teleport(ConfigManager.getLoginLocation());
        Messager.sendMessage(player, ConfigManager.getLoginInstructions());
        startKickTimer(player);
    }

    private void startKickTimer(Player player) {
        long kickTime = ConfigManager.getKickTime();
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (plugin.getLoginPlayers().containsKey(player.getUniqueId())) return;

            player.kickPlayer(ChatColor.RED + "You did not use /auth within " + kickTime / 20L + " seconds. " +
                    "Please re-login and authenticate in time.");
        }, kickTime);
    }
}
