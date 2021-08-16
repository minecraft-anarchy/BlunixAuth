package com.blunix.blunixofflineauth.util;

import com.blunix.blunixofflineauth.BlunixOfflineAuth;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    protected static final BlunixOfflineAuth PLUGIN = BlunixOfflineAuth.getInstance();

    public static Location getLoginLocation() {
        String path = "login-location";
        try {
            int x = getConfig().getInt(path + ".x");
            int y = getConfig().getInt(path + ".y");
            int z = getConfig().getInt(path + ".z");
            float pitch = (float) getConfig().getDouble(path + ".pitch");
            float yaw = (float) getConfig().getDouble(path + ".yaw");
            World world = Bukkit.getWorld(getConfig().getString(path + ".world"));
            Location loginLocation = new Location(world, x, y, z);
            loginLocation.setPitch(pitch);
            loginLocation.setYaw(yaw);
            return loginLocation;
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getLogger().info("[OfflineAuth] Error while getting login location from config.yml");
            return (Bukkit.getWorlds().iterator().next()).getSpawnLocation();
        }
    }

    public static long getKickTime() {
        return (long) getConfig().getDouble("kick-time") * 20L;
    }

    public static String getLoginInstructions() {
        return getConfig().getString("login-instruction-message");
    }

    public static String getEmailHost() {
       return getConfig().getString("email-host");
    }

    public static String getEmailPort() {
       return getConfig().getString("email-port");
    }

    public static String getEmailSender() {
       return getConfig().getString("email-sender");
    }

    public static String getEmailSenderPassword() {
       return getConfig().getString("email-sender-password");
    }

    public static String getEmailSubject() {
        return getConfig().getString("email-subject");
    }

    public static String getEmailContent() {
        return getConfig().getString("email-content");
    }

    private static FileConfiguration getConfig() {
        return PLUGIN.getConfig();
    }
}
