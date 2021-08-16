package com.blunix.blunixofflineauth.commands;

import com.blunix.blunixofflineauth.OfflineAuth;
import com.blunix.blunixofflineauth.files.DataManager;
import com.blunix.blunixofflineauth.util.Messager;
import java.util.regex.Pattern;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRecoveryEmail extends BlunixCommand {
   private OfflineAuth plugin;
   private DataManager dataManager;

   public CommandRecoveryEmail(OfflineAuth plugin) {
      this.plugin = plugin;
      this.dataManager = plugin.getDataManager();
      this.setName("recoveryemail");
      this.setHelpMessage("Sets you recovery e-mail in case you forget your password.");
      this.setPermission("offlineauth.recoveryemail");
      this.setUsageMessage("/auth recoveryemail <E-mail>");
      this.setArgumentLength(2);
      this.setPlayerCommand(true);
   }

   public void execute(CommandSender sender, String[] args) {
      Player player = (Player)sender;
      String email = args[1];
      if (this.plugin.getLoginPlayers().containsKey(player.getUniqueId())) {
         Messager.sendMessage(player, "&cYou need to login to the server before setting your recovery e-mail.");
      } else if (!this.dataManager.isRegistered(player)) {
         Messager.sendMessage(player, "&cYou are not registered in the server yet");
      } else if (!this.isValidEmail(email)) {
         Messager.sendMessage(player, "&cYou must enter a valid e-mail.");
      } else {
         this.dataManager.setRecoveryEmail(player, email);
         Messager.sendMessage(player, "&aYour recovery E-mail has been set to &l" + email);
         player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 1.0F);
      }
   }

   private boolean isValidEmail(String email) {
      String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
      Pattern pat = Pattern.compile(emailRegex);
      return email == null ? false : pat.matcher(email).matches();
   }
}
