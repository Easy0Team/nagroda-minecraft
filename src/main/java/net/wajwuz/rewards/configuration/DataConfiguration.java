package net.wajwuz.rewards.configuration;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class DataConfiguration {
    private File file;
    YamlConfiguration configuration;
    protected Plugin plugin;

    DataConfiguration(Plugin plugin, String fileName) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) {
            if (plugin.getResource(fileName) != null) {
                plugin.saveResource(fileName, false);
                return;
            }
            
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadConfiguration() {
        this.configuration = YamlConfiguration.loadConfiguration(file);
    }

    public void saveConfiguration() {
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public YamlConfiguration getConfiguration() {
        return configuration;
    }
}
