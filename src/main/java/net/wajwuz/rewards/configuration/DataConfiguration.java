package net.wajwuz.rewards.configuration;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;

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

            try {
                Files.createParentDirs(file);
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadConfiguration() {
        try {
            this.configuration = new YamlConfiguration();
            this.configuration.load(new InputStreamReader(new FileInputStream(file), Charsets.UTF_8));
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void saveConfiguration() {
        try {
            Files.createParentDirs(file);
            try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), Charsets.UTF_8)) {
                writer.write(configuration.saveToString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public YamlConfiguration getConfiguration() {
        return configuration;
    }
}
