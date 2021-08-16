package com.blunix.blunixofflineauth.commands;

import com.blunix.blunixofflineauth.util.Messager;
import org.bukkit.command.CommandSender;

public class CommandHelp extends BlunixCommand {
   public CommandHelp() {
      this.setName("help");
      this.setHelpMessage("Displays this list.");
      this.setPermission("offlineauth.help");
      this.setUsageMessage("/auth help");
      this.setArgumentLength(1);
      this.setUniversalCommand(true);
   }

   public void execute(CommandSender sender, String[] args) {
      Messager.sendHelpMessage(sender);
   }
}
