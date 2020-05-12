package net.wajwuz.rewards.configuration;

import net.wajwuz.rewards.basic.impl.RewardPlugin;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    private final static FileConfiguration fileConfiguration = RewardPlugin.getPlugin(RewardPlugin.class).getConfig();
    public static String BOT$TOKEN = fileConfiguration.getString("bot.token");
    public static String BOT$PREFIX = fileConfiguration.getString("bot.prefix");
    public static String BOT$GAME$NAME = fileConfiguration.getString("bot.game");
    public static String BOT$AWARD$COMMAND = fileConfiguration.getString("bot.award.command");
    public static String BOT$AWARD$CHANNEL = fileConfiguration.getString("bot.award.channel");
    public static String OFFLINE$PLAYER$MESSAGE = fileConfiguration.getString("bot.messages.offline");
    public static String AWARDED$PLAYER$MESSAGE = fileConfiguration.getString("bot.messages.awarded");
    public static String EMPTY$PLAYER$MESSAGE = fileConfiguration.getString("bot.messages.empty");
    public static String AWARDED$BEFORE$PLAYER$MESSAGE = fileConfiguration.getString("bot.messages.received");
    public static String WRONG$CHANNEL$MESSAGE = fileConfiguration.getString("bot.messages.channel");
}
