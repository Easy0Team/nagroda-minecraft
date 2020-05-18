package net.wajwuz.rewards.basic;

import net.wajwuz.rewards.configuration.PluginConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class RewardPlugin extends JavaPlugin {
    private RewardBot botInstance;

    @Override
    public void onEnable() {
        PluginConfiguration configuration = new PluginConfiguration(this);
        this.botInstance = new RewardBot(this, configuration);
        this.botInstance.startBot();

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> botInstance.getStore().saveData(), 0L, 20L * 60L * 3L);
    }

    @Override
    public void onDisable() {
        botInstance.getStore().saveData();
    }
}
