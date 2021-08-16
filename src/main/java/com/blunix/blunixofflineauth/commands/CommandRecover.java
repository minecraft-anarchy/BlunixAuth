package com.blunix.blunixofflineauth.commands;

import com.blunix.blunixofflineauth.OfflineAuth;
import com.blunix.blunixofflineauth.files.DataManager;
import com.blunix.blunixofflineauth.util.Messager;
import java.util.Random;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRecover extends BlunixCommand {
   private OfflineAuth plugin;
   private DataManager dataManager;

   public CommandRecover(OfflineAuth plugin) {
      this.plugin = plugin;
      this.dataManager = plugin.getDataManager();
      this.setName("recover");
      this.setHelpMessage("Sends an email to your recovery email with a temporary password you can use in case you forgot yours.");
      this.setPermission("offlineauth.recover");
      this.setUsageMessage("/auth recover <Username>");
      this.setArgumentLength(2);
      this.setPlayerCommand(true);
   }

   public void execute(CommandSender sender, String[] args) {
      Player player = (Player)sender;
      String username = args[1];
      if (!this.plugin.getLoginPlayers().containsKey(player.getUniqueId())) {
         Messager.sendMessage(player, "&cYou are already logged in to the server.");
      } else if (!this.dataManager.isRegistered(player.getUniqueId())) {
         Messager.sendMessage(player, "&c&l" + username + " &cisn't registered in the server yet.");
      } else {
         String emailTo = this.dataManager.getPlayerRecoveryEmail(username);
         if (emailTo == null) {
            Messager.sendMessage(player, "&cThis username does not have any recovery email registered.\nPlease register a recovery email typing &l/auth recoveryemail <Email> &cbefore running this command.");
         } else {
            Random random = new Random();
            String temporaryPassword = String.valueOf(random.nextInt());
            this.dataManager.registerPlayer(player, temporaryPassword);
            this.sendRecoveryEmail(emailTo, temporaryPassword);
            Messager.sendMessage(player, "&aCheck your recovery email and type &l/auth login <Password> &awith the password you received to login to the server.");
         }
      }
   }

   private void sendRecoveryEmail(String var1, String var2) {
      throw new Error("Unresolved compilation problems: \n\tSession cannot be resolved to a type\n\tSession cannot be resolved\n\tjavax.mail cannot be resolved to a type\n\tPasswordAuthentication cannot be resolved to a type\n\tPasswordAuthentication cannot be resolved to a type\n\tMimeMessage cannot be resolved to a type\n\tMimeMessage cannot be resolved to a type\n\tInternetAddress cannot be resolved to a type\n\tMessage cannot be resolved to a variable\n\tInternetAddress cannot be resolved to a type\n\tTransport cannot be resolved\n\tMessagingException cannot be resolved to a type\n");
   }
}
