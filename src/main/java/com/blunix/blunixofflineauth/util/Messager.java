package com.blunix.blunixofflineauth.util;

import com.blunix.blunixofflineauth.BlunixOfflineAuth;
import com.blunix.blunixofflineauth.commands.BlunixCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Iterator;

public class Messager {

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void sendSuccessMessage(CommandSender sender, String message) {
        sendMessage(sender, message);
        SFXManager.playSuccessSound(sender);
    }

    public static void sendErrorMessage(CommandSender sender, String message) {
        sendMessage(sender, message);
        SFXManager.playErrorSound(sender);
    }

    public static void sendHelpMessage(CommandSender sender) {
        StringBuilder finalMessage = new StringBuilder("&e&lCommands\n");
        Iterator<BlunixCommand> iterator = BlunixOfflineAuth.getInstance().getSubcommands().values().iterator();
        while (iterator.hasNext()) {
            BlunixCommand subcommand = iterator.next();
            if (!sender.hasPermission(subcommand.getPermission())) continue;
            finalMessage.append("&a").append(subcommand.getUsageMessage()).append(" &e- &6").append(subcommand.getHelpMessage());
            if (iterator.hasNext()) {
                finalMessage.append("\n");
            }
        }
        Messager.sendSuccessMessage(sender, finalMessage.toString());
    }

    public static void sendNoPermissionMessage(CommandSender sender) {
        sendErrorMessage(sender, "&cYou do not have permissions to use this command!");
    }
}
