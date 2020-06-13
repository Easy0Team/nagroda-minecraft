package net.wajwuz.rewards.basic;

import net.wajwuz.rewards.basic.updater.PluginUpdate;
import net.wajwuz.rewards.basic.updater.PluginUpdater;
import net.wajwuz.rewards.configuration.PluginConfiguration;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class RewardPlugin extends JavaPlugin {
    private final PluginUpdater updater = new PluginUpdater(this);
    private RewardBot botInstance;
    private PluginConfiguration pluginConfiguration;

    @Override
    public void onEnable() {
        new Metrics(this, 7650);

        this.pluginConfiguration = new PluginConfiguration(this);
        this.botInstance = new RewardBot(this);
        this.botInstance.startBot();

        PluginUpdate update = updater.getUpdate();
        if (!update.getUpdateName().equals(this.getDescription().getVersion())) {
            getLogger().warning("Znaleziono nowa wersje pluginu do pobrania (" + update.getUpdateName() + ")!");
            try {
                updater.updatePlugin(update);
            } catch (Exception ex) {
                ex.printStackTrace();
                getLogger().warning("Wystapil blad podczas aktualizacji pluginu. Zglos ten blad na https://github.com/pizdo/nagroda-minecraft/issues");
            }
        }

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
