package com.blunix.blunixofflineauth.util;

import com.blunix.blunixofflineauth.BlunixOfflineAuth;
import org.bukkit.Bukkit;

public class LogUtil {
	protected static final String PLUGIN_NAME = BlunixOfflineAuth.getInstance().getDescription().getName();
	
	public static void sendInfoLog(String text) {
		Bukkit.getLogger().info("[" + PLUGIN_NAME + "] " + text);
	}

	public static void sendWarnLog(String text) {
		Bukkit.getLogger().warning("[" + PLUGIN_NAME + "] " + text);
	}

}
