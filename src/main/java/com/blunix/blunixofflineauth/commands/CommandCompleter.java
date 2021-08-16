package com.blunix.blunixofflineauth.commands;

import com.blunix.blunixofflineauth.OfflineAuth;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class CommandCompleter implements TabCompleter {
   private final OfflineAuth plugin;

   public CommandCompleter(OfflineAuth plugin) {
      this.plugin = plugin;
   }

   public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
      ArrayList<String> arguments = new ArrayList<>();
      for (BlunixCommand subCommand : plugin.getSubcommands().values()) {
         if (!sender.hasPermission(subCommand.getPermission()))
            continue;

         arguments.add(subCommand.getName());
      }
      BlunixCommand subcommand = plugin.getSubcommands().get(args[0]);
      if (args.length > 1 && (subcommand == null || !sender.hasPermission(subcommand.getPermission()))) {
         arguments.clear();
         return arguments;
      }
      if (args.length < 2) {
         return getCompletion(arguments, args, 0);
      }
      return arguments;
   }

   private ArrayList<String> getCompletion(ArrayList<String> arguments, String[] args, int index) {
      ArrayList<String> results = new ArrayList<>();
      for (String argument : arguments) {
         if (argument.toLowerCase().startsWith(args[index].toLowerCase())) {
            results.add(argument);
         }
      }
      return results;
   }
}
