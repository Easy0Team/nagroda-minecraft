package net.wajwuz.spigot.config;

import net.wajwuz.spigot.main.DiscordPlugin;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    public static String BOT$TOKEN;
    public static String BOT$PREFIX;
    public static String BOT$GAME$NAME;
    public static String BOT$AWARD$COMMAND;

    static {
        FileConfiguration fileConfiguration = DiscordPlugin.getPlugin(DiscordPlugin.class).getConfig();
        BOT$TOKEN = fileConfiguration.getString("bot.token");
        BOT$PREFIX = fileConfiguration.getString("bot.prefix");
        BOT$GAME$NAME = fileConfiguration.getString("bot.game");
        BOT$AWARD$COMMAND = fileConfiguration.getString("bot.award.command");
    }
}
