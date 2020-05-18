package net.wajwuz.rewards.configuration;

import net.dv8tion.jda.api.entities.EmbedType;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import java.time.OffsetDateTime;
import java.util.Collections;

public class PluginConfiguration extends DataConfiguration {
    public String botToken;
    public String botPrefix;
    public String botGameType;
    public String botGameName;
    public String botAwardCommand;
    public String botAwardChannelId;
    public MessageEmbed playerOfflineMessage;
    public MessageEmbed playerAwardedMessage;
    public MessageEmbed invalidArugmentMessage;
    public MessageEmbed playerAlreadyReceivedMessage;
    public String saveType;

    public PluginConfiguration(Plugin plugin) {
        super(plugin, "config.yml");
        super.loadConfiguration();
        botToken = configuration.getString("bot.token");
        botPrefix = configuration.getString("bot.prefix");
        botGameType = configuration.getString("bot.game.type");
        botGameName = configuration.getString("bot.game.message");
        botAwardCommand = configuration.getString("bot.award.command");
        botAwardChannelId = configuration.getString("bot.award.channel");
        playerOfflineMessage = getEmbed("bot.messages.offline");
        playerAwardedMessage = getEmbed("bot.messages.awarded");
        invalidArugmentMessage = getEmbed("bot.messages.empty");
        playerAlreadyReceivedMessage = getEmbed("bot.messages.received");
        saveType = configuration.getString("save.type");
    }

    private MessageEmbed getEmbed(String path) {
        ConfigurationSection embedSection = configuration.getConfigurationSection(path);
        if(embedSection == null) {
            super.plugin.getLogger().warning("Wiadomosc " + path + " nie zostala odnaleziona w configu. ");
            return null;
        }

        return new MessageEmbed(
                embedSection.getString("url"),
                embedSection.getString("title"),
                embedSection.getString("description"),
                EmbedType.fromKey(embedSection.getString("embedType")),
                OffsetDateTime.now(),
                embedSection.getInt("color"),
                embedSection.getString("thumbnailUrl") == null ? null : new MessageEmbed.Thumbnail(embedSection.getString("thumbnailUrl"), null, 0, 0),
                null,
                embedSection.getString("author") == null ? null : new MessageEmbed.AuthorInfo(embedSection.getString("author"), embedSection.getString("author.url"), embedSection.getString("author.icon"), null),
                null,
                embedSection.getString("footer") == null ? null : new MessageEmbed.Footer(embedSection.getString("footer.text"), embedSection.getString("footer.image"), null),
                embedSection.getString("image") == null ? null : new MessageEmbed.ImageInfo(embedSection.getString("image"), null, 0, 0),
                Collections.emptyList()
        );
    }


}
