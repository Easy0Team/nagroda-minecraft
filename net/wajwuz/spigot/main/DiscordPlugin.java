package net.wajwuz.spigot.main;

import net.wajwuz.discord.main.DiscordBot;
import net.wajwuz.spigot.config.Config;
import org.bukkit.plugin.java.JavaPlugin;

public class DiscordPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        new DiscordBot(Config.BOT$TOKEN);
    }
}
