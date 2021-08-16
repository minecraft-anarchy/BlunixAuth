package com.blunix.blunixofflineauth.commands;

import com.blunix.blunixofflineauth.BlunixOfflineAuth;
import com.blunix.blunixofflineauth.files.DataManager;
import com.blunix.blunixofflineauth.util.Messager;
import com.blunix.blunixofflineauth.util.SFXManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandUnregister extends BlunixCommand {
    private final BlunixOfflineAuth plugin;
    private final DataManager dataManager;

    public CommandUnregister(BlunixOfflineAuth plugin) {
        this.plugin = plugin;
        this.dataManager = plugin.getDataManager();

        setName("unregister");
        setHelpMessage("Unregisters your username from the server.");
        setPermission("offlineauth.unregister");
        setUsageMessage("/auth unregister <Password>");
        setArgumentLength(2);
        setPlayerCommand(true);
    }

    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        String password = args[1];
        if (plugin.getLoginPlayers().containsKey(player.getUniqueId())) {
            Messager.sendErrorMessage(player, "&cYou need to login before you can unregister your username.");
            return;
        }
        if (!dataManager.isRegistered(player)) {
            Messager.sendErrorMessage(player, "&cYou are not registered in the server yet.");
            return;
        }
        if (!dataManager.isCorrectPassword(player, password)) return;
        if (plugin.getUnregisteringPlayers().contains(player)) return;

        plugin.getUnregisteringPlayers().add(player);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            plugin.getUnregisteringPlayers().remove(player);
        }, 1200L);
        Messager.sendMessage(player, "&6Type &l/auth confirm &6to unregister your username from the server.");
        SFXManager.playPlayerSound(player, Sound.UI_BUTTON_CLICK, 0.7F, 1.4F);
    }
}
