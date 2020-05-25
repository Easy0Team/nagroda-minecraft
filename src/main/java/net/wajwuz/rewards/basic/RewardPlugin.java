package net.wajwuz.rewards.basic;

import net.wajwuz.rewards.configuration.PluginConfiguration;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.URL;
import java.net.URLClassLoader;

public class RewardPlugin extends JavaPlugin {
    private RewardBot botInstance;
    private PluginConfiguration pluginConfiguration;

    @Override
    public void onEnable() {
        new Metrics(this, 7650);

        this.pluginConfiguration = new PluginConfiguration(this);
        this.botInstance = new RewardBot(this);
        this.botInstance.startBot();

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> botInstance.getStore().saveData(), 0L, 20L * 60L * 3L);
    }

    @Override
    public void onDisable() {
        botInstance.getStore().saveData();
    }

    public PluginConfiguration getPluginConfiguration() {
        return pluginConfiguration;
    }
}
