package com.blunix.blunixofflineauth.listeners;

import com.blunix.blunixofflineauth.BlunixOfflineAuth;
import com.blunix.blunixofflineauth.util.Messager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ChatHandler implements Listener {
    private final BlunixOfflineAuth plugin;

    public ChatHandler(BlunixOfflineAuth plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (!plugin.getLoginPlayers().containsKey(player.getUniqueId())) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onCommandPreProcess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (!plugin.getLoginPlayers().containsKey(player.getUniqueId())) return;
        String message = event.getMessage();
        if (message.startsWith("/auth")) return;
        
        event.setCancelled(true);
        Messager.sendErrorMessage(player, "&cYou must login before using any command other than /auth.");
    }
}
