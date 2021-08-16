package com.blunix.blunixofflineauth.commands;

import com.blunix.blunixofflineauth.OfflineAuth;
import com.blunix.blunixofflineauth.files.DataManager;
import com.blunix.blunixofflineauth.util.Messager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandUnregister extends BlunixCommand {
   private OfflineAuth plugin;
   private DataManager dataManager;

   public CommandUnregister(OfflineAuth plugin) {
      this.plugin = plugin;
      this.dataManager = plugin.getDataManager();
      this.setName("unregister");
      this.setHelpMessage("Unregisters your username from the server.");
      this.setPermission("offlineauth.unregister");
      this.setUsageMessage("/auth unregister <Password>");
      this.setArgumentLength(2);
      this.setPlayerCommand(true);
   }

   public void execute(CommandSender sender, String[] args) {
      Player player = (Player)sender;
      String password = args[1];
      if (this.plugin.getLoginPlayers().containsKey(player.getUniqueId())) {
         Messager.sendMessage(player, "&cYou need to login before you can unregister your username.");
      } else if (!this.dataManager.isRegistered(player)) {
         Messager.sendMessage(player, "&cYou are not registered in the server yet.");
      } else if (this.dataManager.isCorrectPassword(player, password)) {
         if (!this.plugin.getUnregisteringPlayers().contains(player)) {
            this.plugin.getUnregisteringPlayers().add(player);
            Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
               if (this.plugin.getUnregisteringPlayers().contains(player)) {
                  this.plugin.getUnregisteringPlayers().remove(player);
               }
            }, 1200L);
         }

         Messager.sendMessage(player, "&6Type &l/auth confirm &6to unregister your username from the server.");
      }
   }
}
