package com.blunix.blunixofflineauth.commands;

import com.blunix.blunixofflineauth.BlunixOfflineAuth;
import com.blunix.blunixofflineauth.util.Messager;
import org.bukkit.command.CommandSender;

public class CommandReload extends BlunixCommand {
   private final BlunixOfflineAuth plugin;

   public CommandReload(BlunixOfflineAuth plugin) {
      this.plugin = plugin;

      setName("reload");
      setHelpMessage("Reloads the plugin's config.");
      setPermission("offlineauth.reload");
      setUsageMessage("/auth reload");
      setArgumentLength(1);
      setUniversalCommand(true);
   }

   public void execute(CommandSender sender, String[] args) {
      this.plugin.reloadConfig();
      Messager.sendSuccessMessage(sender, "&aConfig reloaded.");
   }
}
