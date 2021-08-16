package com.blunix.blunixofflineauth.events;

import com.blunix.blunixofflineauth.OfflineAuth;
import com.blunix.blunixofflineauth.util.Messager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerChat implements Listener {
   private OfflineAuth plugin;

   public PlayerChat(OfflineAuth plugin) {
      this.plugin = plugin;
   }

   @EventHandler
   public void onPlayerChat(AsyncPlayerChatEvent event) {
      Player player = event.getPlayer();
      if (this.plugin.getLoginPlayers().containsKey(player.getUniqueId())) {
         event.setCancelled(true);
      }
   }

   @EventHandler
   public void onCommandPreProcess(PlayerCommandPreprocessEvent event) {
      Player player = event.getPlayer();
      if (this.plugin.getLoginPlayers().containsKey(player.getUniqueId())) {
         String message = event.getMessage();
         if (!message.startsWith("/auth")) {
            event.setCancelled(true);
            Messager.sendMessage(player, "&cYou must login before using any command other than /auth.");
         }
      }
   }
}
