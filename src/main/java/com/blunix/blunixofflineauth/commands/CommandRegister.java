package com.blunix.blunixofflineauth.commands;

import com.blunix.blunixofflineauth.OfflineAuth;
import com.blunix.blunixofflineauth.files.DataManager;
import com.blunix.blunixofflineauth.util.Messager;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRegister extends BlunixCommand {
   private DataManager dataManager;

   public CommandRegister(OfflineAuth plugin) {
      this.dataManager = plugin.getDataManager();
      this.setName("register");
      this.setHelpMessage("Registers your current username with the specified password.");
      this.setPermission("offlineauth.register");
      this.setUsageMessage("/auth register <Password> <ConfirmPassword>");
      this.setArgumentLength(3);
      this.setPlayerCommand(true);
   }

   public void execute(CommandSender sender, String[] args) {
      Player player = (Player)sender;
      String password = args[1];
      String confirmPassword = args[2];
      if (this.dataManager.isRegistered(player)) {
         Messager.sendMessage(player, "&cYou are already registered in the server.");
      } else if (password.length() < 6) {
         Messager.sendMessage(player, "&cYou must enter a password with at least 6 characters.");
      } else if (!password.equals(confirmPassword)) {
         Messager.sendMessage(player, "&cBoth passwords must match.");
      } else {
         this.dataManager.registerPlayer(player, confirmPassword);
         Messager.sendMessage(player, "&aYou successfully registered to the server.");
         player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 1.0F);
      }
   }
}
