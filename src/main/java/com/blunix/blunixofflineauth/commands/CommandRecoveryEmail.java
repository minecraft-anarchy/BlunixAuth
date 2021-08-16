package com.blunix.blunixofflineauth.commands;

import com.blunix.blunixofflineauth.BlunixOfflineAuth;
import com.blunix.blunixofflineauth.files.DataManager;
import com.blunix.blunixofflineauth.util.Messager;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

public class CommandRecoveryEmail extends BlunixCommand {
    private final BlunixOfflineAuth plugin;
    private final DataManager dataManager;

    public CommandRecoveryEmail(BlunixOfflineAuth plugin) {
        this.plugin = plugin;
        this.dataManager = plugin.getDataManager();

        setName("recoveryemail");
        setHelpMessage("Sets your recovery e-mail in case you forget your password.");
        setPermission("offlineauth.recoveryemail");
        setUsageMessage("/auth recoveryemail <E-mail>");
        setArgumentLength(2);
        setPlayerCommand(true);
    }

    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        String email = args[1];
        if (plugin.getLoginPlayers().containsKey(player.getUniqueId())) {
            Messager.sendErrorMessage(player, "&cYou need to login to the server before setting " +
                    "your recovery e-mail.");
            return;
        }
        if (!dataManager.isRegistered(player)) {
            Messager.sendErrorMessage(player, "&cYou are not registered in the server yet");
            return;
        }
        if (!isValidEmail(email)) {
            Messager.sendErrorMessage(player, "&cYou must enter a valid e-mail.");
            return;
        }
        dataManager.setRecoveryEmail(player, email);
        Messager.sendSuccessMessage(player, "&aYour recovery E-mail has been set to &l" + email);
    }

    private boolean isValidEmail(String email) {
        Pattern pat = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        return email != null && pat.matcher(email).matches();
    }
}
