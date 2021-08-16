package com.blunix.blunixofflineauth.files;

import com.blunix.blunixofflineauth.OfflineAuth;
import com.blunix.blunixofflineauth.util.Messager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataManager {
    private final OfflineAuth plugin;
    private final DataFile dataFile;

    public DataManager(OfflineAuth plugin) {
        this.plugin = plugin;
        this.dataFile = new DataFile(plugin);
    }

    public void registerPlayer(Player player, String password) {
        FileConfiguration data = getData();
        Map<String, String> hashedData = generateHashedData(password, null);
        String path = "registered-players." + player.getUniqueId() + ".";
        data.set(path + "salt", hashedData.keySet().iterator().next());
        data.set(path + "hash", hashedData.values().iterator().next());
        saveData();
    }

    public void unregisterPlayer(Player player) {
        getData().set("registered-players." + player.getUniqueId(), null);
        saveData();
        plugin.getUnregisteringPlayers().remove(player);
    }

    public void setRecoveryEmail(Player player, String email) {
        getData().set("registered-players." + player.getUniqueId() + ".recovery-email", email);
        saveData();
    }

    public boolean isCorrectPassword(Player player, String inputPassword) {
        return isCorrectPassword(player, player.getUniqueId(), inputPassword);
    }

    public boolean isCorrectPassword(Player player, UUID uuid, String inputPassword) {
        String hashedPassword = getHashedPassword(uuid);
        String generatedPassword = generateHashedData(inputPassword, getSalt(uuid)).values().iterator().next();
        if (!generatedPassword.equals(hashedPassword)) {
            Messager.sendErrorMessage(player, "&cIncorrect password.");
            String ipAddress = player.getAddress().getAddress().getHostAddress();
            Bukkit.getLogger().info("Incorrect password from: \"" + player.getName() + "\" \"" + ipAddress + "\"");
            return false;
        }
        return true;
    }

    public String getHashedPassword(UUID uuid) {
        ConfigurationSection section = getData().getConfigurationSection("registered-players");
        if (section == null) {
            Bukkit.getLogger().info("[OfflineAuth] There was an error reading registered-players in data.yml");
            return null;
        }
        return section.getString(uuid.toString() + ".hash");
    }

    public byte[] getSalt(UUID uuid) {
        ConfigurationSection section = getData().getConfigurationSection("registered-players");
        if (section == null) {
            Bukkit.getLogger().info("[OfflineAuth] There was an error reading registered-players in data.yml");
            return null;
        }
        return Base64.getDecoder().decode(section.getString(uuid.toString() + ".salt"));
    }

    public String getPlayerRecoveryEmail(String uuid) {
        ConfigurationSection section = getData().getConfigurationSection("registered-players");
        if (section == null) {
            Bukkit.getLogger().info("[OfflineAuth] There was an error reading registered-players in data.yml");
            return null;
        }
        return section.getString(uuid + ".recovery-email");
    }

    public boolean isRegistered(Player player) {
        return isRegistered(player.getUniqueId());
    }

    public boolean isRegistered(UUID uuid) {
        ConfigurationSection section = getData().getConfigurationSection("registered-players");
        if (section == null) {
            Bukkit.getLogger().info("[OfflineAuth] There was an error reading registered-players in data.yml");
            return false;
        }
        return section.contains(uuid.toString());
    }

    private Map<String, String> generateHashedData(String password, byte[] salt) {
        if (salt == null) {
            salt = new byte[16];
            new SecureRandom().nextBytes(salt);
        }
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        byte[] hashed;
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hashed = factory.generateSecret(spec).getEncoded();
        } catch (Exception var9) {
            var9.printStackTrace();
            return null;
        }
        Encoder encoder = Base64.getEncoder();
        Map<String, String> hashedData = new HashMap<>();
        hashedData.put(encoder.encodeToString(salt), encoder.encodeToString(hashed));
        return hashedData;
    }

    private void saveData() {
        dataFile.saveConfig();
    }

    private FileConfiguration getData() {
        return dataFile.getConfig();
    }
}
