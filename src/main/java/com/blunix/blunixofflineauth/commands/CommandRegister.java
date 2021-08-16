package com.blunix.blunixofflineauth.commands;

import com.blunix.blunixofflineauth.BlunixOfflineAuth;
import com.blunix.blunixofflineauth.files.DataManager;
import com.blunix.blunixofflineauth.util.Messager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRegister extends BlunixCommand {
    private final DataManager dataManager;

    public CommandRegister(BlunixOfflineAuth plugin) {
        this.dataManager = plugin.getDataManager();

        setName("register");
        setHelpMessage("Registers your current username with the specified password.");
        setPermission("offlineauth.register");
        setUsageMessage("/auth register <Password> <ConfirmPassword>");
        setArgumentLength(3);
        setPlayerCommand(true);
    }

    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        String password = args[1];
        String confirmPassword = args[2];
        if (dataManager.isRegistered(player)) {
            Messager.sendErrorMessage(player, "&cYou are already registered in the server.");
            return;
        }
        if (password.length() < 6) {
            Messager.sendErrorMessage(player, "&cYou must enter a password with at least 6 characters.");
            return;
        }
        if (!password.equals(confirmPassword)) {
            Messager.sendErrorMessage(player, "&cBoth passwords must match.");
            return;
        }
        dataManager.registerPlayer(player, confirmPassword);
        Messager.sendSuccessMessage(player, "&aYou successfully registered to the server.");
    }
}
