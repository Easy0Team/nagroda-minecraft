package net.wajwuz.rewards.configuration;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.EmbedType;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PluginConfiguration extends DataConfiguration {
    public String botToken;
    public String botPrefix;
    public String botGameType;
    public String botGameName;
    public String botAwardCommand;
    public String botAwardMessage;
    public List<String> botAwardChannels;
    public EmbedBuilder playerOfflineMessage;
    public EmbedBuilder playerAwardedMessage;
    public EmbedBuilder invalidArugmentMessage;
    public EmbedBuilder playerAlreadyReceivedMessage;
    public String saveType;
    public String databaseUrl;
    public String databaseUser;
    public String databasePass;

    public PluginConfiguration(Plugin plugin) {
        super(plugin, "config.yml");
        super.loadConfiguration();
        botToken = configuration.getString("bot.token");
        botPrefix = configuration.getString("bot.prefix");
        botGameType = configuration.getString("bot.game.type");
        botGameName = configuration.getString("bot.game.message");
        botAwardCommand = configuration.getString("bot.award.command");
        botAwardMessage = configuration.getStringList("bot.award.message").stream().map(str -> ChatColor.translateAlternateColorCodes('&', str)).collect(Collectors.joining("\n"));
        botAwardChannels = configuration.getStringList("bot.award.channels");
        playerOfflineMessage = getEmbed("bot.messages.offline");
        playerAwardedMessage = getEmbed("bot.messages.awarded");
        invalidArugmentMessage = getEmbed("bot.messages.empty");
        playerAlreadyReceivedMessage = getEmbed("bot.messages.received");
        saveType = configuration.getString("save.type");
        databaseUrl = configuration.getString("save.credentials.url");
        databaseUser = configuration.getString("save.credentials.user");
        databasePass = configuration.getString("save.credentials.pass");
    }

    private EmbedBuilder getEmbed(String path) {
        ConfigurationSection embedSection = configuration.getConfigurationSection(path);
        if(embedSection == null) {
            super.plugin.getLogger().warning("Wiadomosc " + path + " nie zostala odnaleziona w configu. ");
            return null;
        }

        return new EmbedBuilder(new MessageEmbed(
                embedSection.getString("url"),
                embedSection.getString("title"),
                embedSection.getString("description"),
                EmbedType.RICH,
                OffsetDateTime.now(),
                embedSection.getInt("color"),
                embedSection.getString("thumbnailUrl") == null ? null : new MessageEmbed.Thumbnail(embedSection.getString("thumbnailUrl"), null, 0, 0),
                null,
                embedSection.getString("author") == null ? null : new MessageEmbed.AuthorInfo(embedSection.getString("author"), embedSection.getString("author.url"), embedSection.getString("author.icon"), null),
                null,
                new MessageEmbed.Footer("invoked by null", null, null),
                embedSection.getString("image") == null ? null : new MessageEmbed.ImageInfo(embedSection.getString("image"), null, 0, 0),
                Collections.emptyList()
        ));
    }




}
