package com.blunix.blunixofflineauth;

import com.blunix.blunixofflineauth.commands.*;
import com.blunix.blunixofflineauth.listeners.LogListener;
import com.blunix.blunixofflineauth.listeners.ChatHandler;
import com.blunix.blunixofflineauth.listeners.PlayerConnectionHandler;
import com.blunix.blunixofflineauth.listeners.PlayerRespawn;
import com.blunix.blunixofflineauth.files.DataManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class BlunixOfflineAuth extends JavaPlugin {
   private final Map<String, BlunixCommand> subcommands = new LinkedHashMap<>();
   private final Map<UUID, Location> loginPlayers = new HashMap<>();
   private final List<Player> unregisteringPlayers = new ArrayList<>();
   private DataManager dataManager;

   public static BlunixOfflineAuth getInstance() {
      return BlunixOfflineAuth.getPlugin(BlunixOfflineAuth.class);
   }

   public void onEnable() {
      configureFiles();
      registerCommands();
      registerListeners();
   }

   public void onDisable() {

   }

   private void configureFiles() {
      saveDefaultConfig();
      dataManager = new DataManager(this);
   }

   private void registerCommands() {
      getCommand("auth").setExecutor(new CommandRunner(this));
      getCommand("auth").setTabCompleter(new CommandCompleter(this));
      subcommands.put("help", new CommandHelp());
      subcommands.put("register", new CommandRegister(this));
      subcommands.put("login", new CommandLogin(this));
      subcommands.put("unregister", new CommandUnregister(this));
      subcommands.put("changepassword", new CommandChangePassword(this));
      subcommands.put("changeusername", new CommandChangeUsername(this));
      subcommands.put("confirm", new CommandConfirm(this));
      subcommands.put("recoveryemail", new CommandRecoveryEmail(this));
      subcommands.put("recover", new CommandRecover(this));
      subcommands.put("reload", new CommandReload(this));
   }

   private void registerListeners() {
      PluginManager pluginManager = this.getServer().getPluginManager();
      pluginManager.registerEvents(new PlayerConnectionHandler(this), this);
      pluginManager.registerEvents(new ChatHandler(this), this);
      pluginManager.registerEvents(new PlayerRespawn(this), this);
      new LogListener().registerFilter();
   }

   public Map<String, BlunixCommand> getSubcommands() {
      return this.subcommands;
   }

   public Map<UUID, Location> getLoginPlayers() {
      return this.loginPlayers;
   }

   public List<Player> getUnregisteringPlayers() {
      return this.unregisteringPlayers;
   }

   public DataManager getDataManager() {
      return this.dataManager;
   }
}
