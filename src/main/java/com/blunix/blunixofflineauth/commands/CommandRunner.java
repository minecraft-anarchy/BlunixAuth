package com.blunix.blunixofflineauth.commands;

import com.blunix.blunixofflineauth.OfflineAuth;
import com.blunix.blunixofflineauth.util.Messager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRunner implements CommandExecutor {
   private OfflineAuth plugin;

   public CommandRunner(OfflineAuth plugin) {
      this.plugin = plugin;
   }

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      if (!cmd.getName().equalsIgnoreCase("offlineauth")) {
         return true;
      } else if (args.length == 0) {
         Messager.sendHelpMessage(sender);
         return true;
      } else if (!this.plugin.getSubcommands().containsKey(args[0].toLowerCase())) {
         Messager.sendMessage(sender, "&cUnknown command. Type &l/auth help &cto see the full command list.");
         return true;
      } else {
         BlunixCommand subcommand = (BlunixCommand)this.plugin.getSubcommands().get(args[0].toLowerCase());
         if (!sender.hasPermission(subcommand.getPermission())) {
            Messager.sendNoPermissionMessage(sender);
            return true;
         } else if (subcommand.isPlayerCommand() && !(sender instanceof Player)) {
            Messager.sendMessage(sender, "&cNot available for consoles.");
            return true;
         } else if (subcommand.isConsoleCommand() && sender instanceof Player) {
            Messager.sendMessage(sender, "&cNot available for players.");
            return true;
         } else if (args.length < subcommand.getArgumentLength()) {
            Messager.sendMessage(sender, "&cUsage: &l" + subcommand.getUsageMessage());
            return true;
         } else {
            subcommand.execute(sender, args);
            return true;
         }
      }
   }
}
