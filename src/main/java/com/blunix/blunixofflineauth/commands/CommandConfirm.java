package com.blunix.blunixofflineauth.commands;

import com.blunix.blunixofflineauth.BlunixOfflineAuth;
import com.blunix.blunixofflineauth.files.DataManager;
import com.blunix.blunixofflineauth.util.Messager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandConfirm extends BlunixCommand {
    private final BlunixOfflineAuth plugin;
    private final DataManager dataManager;

    public CommandConfirm(BlunixOfflineAuth plugin) {
        this.plugin = plugin;
        this.dataManager = plugin.getDataManager();

        setName("confirm");
        setHelpMessage("Confirms the unregister of your username from the server.");
        setPermission("offlineauth.confirm");
        setUsageMessage("/auth confirm");
        setArgumentLength(1);
        setPlayerCommand(true);
    }

    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (!plugin.getUnregisteringPlayers().contains(player)) {
            Messager.sendErrorMessage(player, "&cYou haven't open any unregister request yet. " +
                    "Type &l/auth unregister <Password> to open one.");
            return;
        }
        dataManager.unregisterPlayer(player);
        Messager.sendSuccessMessage(player, "&aUsername successfully unregistered.");
    }
}
