package com.blunix.blunixofflineauth.util;

import com.blunix.blunixofflineauth.OfflineAuth;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class ConfigManager {
   private OfflineAuth plugin;

   public ConfigManager(OfflineAuth plugin) {
      this.plugin = plugin;
   }

   public Location getLoginLocation() {
      String path = "login-location";

      try {
         int x = this.plugin.getConfig().getInt(path + ".x");
         int y = this.plugin.getConfig().getInt(path + ".y");
         int z = this.plugin.getConfig().getInt(path + ".z");
         float pitch = (float)this.plugin.getConfig().getDouble(path + ".pitch");
         float yaw = (float)this.plugin.getConfig().getDouble(path + ".yaw");
         World world = Bukkit.getWorld(this.plugin.getConfig().getString(path + ".world"));
         Location loginLocation = new Location(world, (double)x, (double)y, (double)z);
         loginLocation.setPitch(pitch);
         loginLocation.setYaw(yaw);
         return loginLocation;
      } catch (Exception var9) {
         var9.printStackTrace();
         Bukkit.getLogger().info("[OfflineAuth] Error while getting login location from config.yml");
         return ((World)Bukkit.getWorlds().iterator().next()).getSpawnLocation();
      }
   }

   public long getKickTime() {
      return (long)this.plugin.getConfig().getDouble("kick-time") * 20L;
   }

   public String getString(String path) {
      return this.plugin.getConfig().getString(path);
   }
}
