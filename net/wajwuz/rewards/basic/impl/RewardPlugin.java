package net.wajwuz.rewards.basic.impl;

import net.wajwuz.rewards.basic.RewardBot;
import net.wajwuz.rewards.configuration.Config;
import org.bukkit.plugin.java.JavaPlugin;

public class RewardPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        new RewardBot(Config.BOT$TOKEN);
    }

    @Override
    public void onDisable() {
        this.saveConfig();
    }
}
