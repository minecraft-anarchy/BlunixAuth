package com.blunix.blunixofflineauth.events;

import com.blunix.blunixofflineauth.BlunixOfflineAuth;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawn implements Listener {
   private BlunixOfflineAuth plugin;

   public PlayerRespawn(BlunixOfflineAuth plugin) {
      this.plugin = plugin;
   }

   @EventHandler
   public void onPlayerRespawn(PlayerRespawnEvent event) {
      Player player = event.getPlayer();
      if (this.plugin.getLoginPlayers().containsKey(player.getUniqueId())) {
         event.setRespawnLocation(this.plugin.getLoginLocation());
      }
   }
}
