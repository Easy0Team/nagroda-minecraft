package net.wajwuz.spigot.basic;

import net.wajwuz.discord.basic.DiscordBot;
import net.wajwuz.spigot.config.Config;
import org.bukkit.plugin.java.JavaPlugin;

public class DiscordPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        new DiscordBot(Config.BOT$TOKEN);
    }
}
