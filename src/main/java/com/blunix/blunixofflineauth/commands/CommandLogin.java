package com.blunix.blunixofflineauth.commands;

import com.blunix.blunixofflineauth.OfflineAuth;
import com.blunix.blunixofflineauth.files.DataManager;
import com.blunix.blunixofflineauth.util.Messager;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandLogin extends BlunixCommand {
   private OfflineAuth plugin;
   private DataManager dataManager;

   public CommandLogin(OfflineAuth plugin) {
      this.plugin = plugin;
      this.dataManager = plugin.getDataManager();
      this.setName("login");
      this.setHelpMessage("Logins you to the server using your password.");
      this.setPermission("offlineauth");
      this.setUsageMessage("/auth login <Password>");
      this.setArgumentLength(2);
      this.setPlayerCommand(true);
   }

   public void execute(CommandSender sender, String[] args) {
      Player player = (Player)sender;
      String password = args[1];
      if (!this.plugin.getLoginPlayers().containsKey(player.getUniqueId())) {
         Messager.sendMessage(player, "&cYou are already logged in to the server.");
      } else if (!this.dataManager.isRegistered(player)) {
         Messager.sendMessage(player, "&cYou haven't registered to the server yet.");
      } else if (this.dataManager.isCorrectPassword(player, password)) {
         player.teleport((Location)this.plugin.getLoginPlayers().get(player.getUniqueId()));
         this.plugin.getLoginPlayers().remove(player.getUniqueId());
         Messager.sendMessage(player, "&aYou successfully logged in to the server.");
         player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 1.0F);
      }
   }
}
