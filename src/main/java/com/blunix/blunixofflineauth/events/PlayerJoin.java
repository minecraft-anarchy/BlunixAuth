package com.blunix.blunixofflineauth.events;

import com.blunix.blunixofflineauth.OfflineAuth;
import com.blunix.blunixofflineauth.util.ConfigManager;
import com.blunix.blunixofflineauth.util.Messager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
   private OfflineAuth plugin;
   private ConfigManager config;

   public PlayerJoin(OfflineAuth plugin) {
      this.plugin = plugin;
      this.config = plugin.getConfigManager();
   }

   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent event) {
      Player player = event.getPlayer();
      if (this.plugin.getDataManager().isRegistered(player)) {
         if (!this.plugin.getLoginPlayers().containsKey(player.getUniqueId())) {
            this.plugin.getLoginPlayers().put(player.getUniqueId(), player.getLocation());
         }

         player.teleport(this.plugin.getLoginLocation());
         Messager.sendMessage(player, this.config.getString("login-instruction-message"));
         this.startKickTimer(player);
      }
   }

   private void startKickTimer(Player player) {
      long kickTime = this.config.getKickTime();
      Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
         if (this.plugin.getLoginPlayers().containsKey(player.getUniqueId())) {
            player.kickPlayer(ChatColor.RED + "You did not use /auth within " + kickTime / 20L + " seconds. Please re-login and authenticate in time.");
         }
      }, kickTime);
   }
}
