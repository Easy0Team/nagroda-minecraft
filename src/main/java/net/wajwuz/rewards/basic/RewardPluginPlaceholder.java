package net.wajwuz.rewards.basic;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.wajwuz.rewards.data.User;
import org.bukkit.OfflinePlayer;

public class RewardPluginPlaceholder extends PlaceholderExpansion {
    private final RewardBot bot;

    public RewardPluginPlaceholder(RewardBot bot) {
        this.bot = bot;
    }

    @Override
    public String getIdentifier() {
        return "rewards";
    }

    @Override
    public String getAuthor() {
        return "Memehuj";
    }

    @Override
    public String getVersion() {
        return "Huj";
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier) {
        if(identifier.equalsIgnoreCase("discord_id")) {
            return bot.getStore().getUser(player.getName()).map(User::getDiscordUserId).orElse("Brak");
        } else return null;
    }
}
