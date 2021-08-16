package com.blunix.blunixofflineauth.commands;

import com.blunix.blunixofflineauth.BlunixOfflineAuth;
import com.blunix.blunixofflineauth.files.DataManager;
import com.blunix.blunixofflineauth.util.Messager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandLogin extends BlunixCommand {
    private final BlunixOfflineAuth plugin;
    private final DataManager dataManager;

    public CommandLogin(BlunixOfflineAuth plugin) {
        this.plugin = plugin;
        this.dataManager = plugin.getDataManager();

        setName("login");
        setHelpMessage("Logins you to the server using your password.");
        setPermission("offlineauth");
        setUsageMessage("/auth login <Password>");
        setArgumentLength(2);
        setPlayerCommand(true);
    }

    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        String password = args[1];
        if (!plugin.getLoginPlayers().containsKey(player.getUniqueId())) {
            Messager.sendErrorMessage(player, "&cYou are already logged in to the server.");
            return;
        }
        if (!dataManager.isRegistered(player)) {
            Messager.sendErrorMessage(player, "&cYou haven't registered to the server yet.");
            return;
        }
        if (!dataManager.isCorrectPassword(player, password)) return;

        player.teleport(plugin.getLoginPlayers().get(player.getUniqueId()));
        plugin.getLoginPlayers().remove(player.getUniqueId());
        Messager.sendSuccessMessage(player, "&aYou successfully logged in to the server.");
    }
}
