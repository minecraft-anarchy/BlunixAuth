package com.blunix.blunixofflineauth.commands;

import com.blunix.blunixofflineauth.OfflineAuth;
import com.blunix.blunixofflineauth.files.DataManager;
import com.blunix.blunixofflineauth.util.Messager;
import com.blunix.blunixofflineauth.util.UUIDUtil;
import java.io.File;
import java.util.Iterator;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandChangeUsername extends BlunixCommand {
   private final DataManager dataManager;

   public CommandChangeUsername(OfflineAuth plugin) {
      this.dataManager = plugin.getDataManager();

      setName("changeusername");
      setHelpMessage("Transfers all your previous player data to your current username.");
      setPermission("offlineauth.changeusername");
      setUsageMessage("/auth changeusername <OldUsername> <Password>");
      setArgumentLength(3);
      setPlayerCommand(true);
   }

   public void execute(CommandSender sender, String[] args) {
      Player player = (Player)sender;
      String oldUsername = args[1];
      UUID oldUUID = UUIDUtil.getOfflineUUID(oldUsername);
      String password = args[2];
      if (oldUsername.equals(player.getName())) {
         Messager.sendErrorMessage(player, "&cYou can't transfer data from your current username.");
      } else if (!dataManager.isRegistered(UUIDUtil.getOfflineUUID(oldUsername))) {
         Messager.sendErrorMessage(player, "&c&l" + oldUsername + " &cisn't registered in the server.");
      } else if (dataManager.isCorrectPassword(player, oldUUID, password)) {
         transferPlayerData(oldUUID, player.getName());
         player.kickPlayer(ChatColor.GREEN + "Your data has been successfully transfered!");
      }
   }

   private void transferPlayerData(UUID oldUuid, String newUsername) {
      UUID newUuid = UUIDUtil.getOfflineUUID(newUsername);
      File oldData;
      File newData;
      for(World world : Bukkit.getWorlds()) {
         oldData = new File(world.getWorldFolder() + "/playerdata/" + oldUuid.toString() + ".dat");
         newData = new File(world.getWorldFolder() + "/playerdata/" + newUuid.toString() + ".dat");
         if (newData.exists()) {
            newData.delete();
         }
         oldData.renameTo(newData);
         oldData = new File(world.getWorldFolder() + "/playerdata/" + oldUuid.toString() + ".dat_old");
         if (oldData.exists()) {
            oldData.delete();
         }
      }
   }
}
