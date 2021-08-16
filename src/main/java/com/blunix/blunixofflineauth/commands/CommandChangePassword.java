package com.blunix.blunixofflineauth.commands;

import com.blunix.blunixofflineauth.BlunixOfflineAuth;
import com.blunix.blunixofflineauth.files.DataManager;
import com.blunix.blunixofflineauth.util.Messager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandChangePassword extends BlunixCommand {
    private final BlunixOfflineAuth plugin;
    private final DataManager dataManager;

    public CommandChangePassword(BlunixOfflineAuth plugin) {
        this.plugin = plugin;
        this.dataManager = plugin.getDataManager();

        setName("changepassword");
        setHelpMessage("Changes your current password for the one you specify.");
        setPermission("offlineauth.changepassword");
        setUsageMessage("/auth changepassword <OldPassword> <NewPassword>");
        setArgumentLength(3);
        setPlayerCommand(true);
    }

    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        String oldPassword = args[1];
        String newPassword = args[2];
        if (plugin.getLoginPlayers().containsKey(player.getUniqueId())) {
            Messager.sendErrorMessage(player, "&cYou need to login before you can change your password.");
            return;
        }
        if (!dataManager.isRegistered(player)) {
            Messager.sendErrorMessage(player, "&cYou are not registered in the server yet.");
            return;
        }
        if (!dataManager.isCorrectPassword(player, oldPassword)) return;
        if (newPassword.length() < 6) {
            Messager.sendErrorMessage(player, "&cYou must enter a password with at least 6 characters.");
            return;
        }
        if (oldPassword.equals(newPassword)) {
            Messager.sendErrorMessage(player, "&cYou can't set the same password again.");
            return;
        }
        dataManager.registerPlayer(player, newPassword);
        Messager.sendSuccessMessage(player, "&aYou successfully changed your password.");
    }
}
