package com.mcnsa.chat.server;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ServerConfig {
		private FileConfiguration customConfig = null;
		private File customConfigFile = null;

		public void reload() {
			if (customConfigFile == null)
				customConfigFile = new File("server_config.yml");
			customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
		}

		public FileConfiguration get() {
			if (customConfig == null)
				this.reload();
			return customConfig;
		}

		public void clear() {
			customConfig = new YamlConfiguration();
		}

		public void save() {
			if (customConfig == null || customConfigFile == null)
				return;
			try {
				get().save(customConfigFile);
			} catch (IOException ex) {
				System.out.println("Could not save config to " + customConfigFile);
			}
		}
	}
