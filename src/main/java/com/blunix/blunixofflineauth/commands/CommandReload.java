package com.blunix.blunixofflineauth.commands;

import com.blunix.blunixofflineauth.OfflineAuth;
import com.blunix.blunixofflineauth.util.Messager;
import org.bukkit.command.CommandSender;

public class CommandReload extends BlunixCommand {
   private OfflineAuth plugin;

   public CommandReload(OfflineAuth plugin) {
      this.plugin = plugin;
      this.setName("reload");
      this.setHelpMessage("Reloads the plugin's config.");
      this.setPermission("offlineauth.reload");
      this.setUsageMessage("/auth reload");
      this.setArgumentLength(1);
      this.setUniversalCommand(true);
   }

   public void execute(CommandSender sender, String[] args) {
      this.plugin.reloadConfig();
      this.plugin.setLoginLocation();
      Messager.sendMessage(sender, "&aConfig reloaded.");
   }
}
