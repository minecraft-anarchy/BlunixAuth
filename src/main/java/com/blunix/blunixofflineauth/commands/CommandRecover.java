package com.blunix.blunixofflineauth.commands;

import com.blunix.blunixofflineauth.BlunixOfflineAuth;
import com.blunix.blunixofflineauth.files.DataManager;
import com.blunix.blunixofflineauth.util.ConfigManager;
import com.blunix.blunixofflineauth.util.LogUtil;
import com.blunix.blunixofflineauth.util.Messager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class CommandRecover extends BlunixCommand {
    private final BlunixOfflineAuth plugin;
    private final DataManager dataManager;

    public CommandRecover(BlunixOfflineAuth plugin) {
        this.plugin = plugin;
        this.dataManager = plugin.getDataManager();

        setName("recover");
        setHelpMessage("Sends an email to your recovery email with a temporary password you can use in case you forgot yours.");
        setPermission("offlineauth.recover");
        setUsageMessage("/auth recover <Username>");
        setArgumentLength(2);
        setPlayerCommand(true);
    }

    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        String username = args[1];
        if (!plugin.getLoginPlayers().containsKey(player.getUniqueId())) {
            Messager.sendErrorMessage(player, "&cYou are already logged in to the server.");
            return;
        }
        if (!dataManager.isRegistered(player.getUniqueId())) {
            Messager.sendErrorMessage(player, "&c&l" + username + " &cisn't registered in the server yet.");
            return;
        }
        String emailTo = dataManager.getPlayerRecoveryEmail(username);
        if (emailTo == null) {
            Messager.sendErrorMessage(player, "&cThis username does not have any recovery email registered.\n" +
                    "Please register a recovery email typing &l/auth recoveryemail <Email> &cbefore running this command.");
            return;
        }
        String temporaryPassword = String.valueOf(new Random().nextInt());
        dataManager.registerPlayer(player, temporaryPassword);
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> sendRecoveryEmail(emailTo, temporaryPassword));
        Messager.sendMessage(player, "&aCheck your recovery email and type &l/auth login <Password> " +
                "&awith the password you received to login to the server.");
    }

    private void sendRecoveryEmail(String emailTo, String temporaryPassword) {
        String emailFrom = ConfigManager.getEmailSender();
        String password = ConfigManager.getEmailSenderPassword();
        String host = ConfigManager.getEmailHost();
        String port = ConfigManager.getEmailPort();
        String subject = ConfigManager.getEmailSubject();
        String content = ConfigManager.getEmailContent().replace("{PASSWORD}", temporaryPassword);
        Properties properties = System.getProperties();
        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        // Get the Session object and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailFrom, password);
            }
        });
        // Enable deubgging to find potential issues
        session.setDebug(true);
        try {
            // Create a default MimeMessage object
            MimeMessage message = new MimeMessage(session);
            // Set From: header field of the header
            message.setFrom(new InternetAddress(emailFrom));
            // Set To: header field of the header
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
            // Set Subject: header field
            message.setSubject(subject);
            // Now set the actual message
            message.setText(content);
            LogUtil.sendInfoLog("Sending email to " + emailTo);
            // Send email
            Transport.send(message);
            LogUtil.sendInfoLog("Email successfully sent to " + emailTo);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
