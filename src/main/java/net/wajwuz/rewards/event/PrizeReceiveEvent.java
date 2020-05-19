package net.wajwuz.rewards.event;

import net.wajwuz.rewards.data.User;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PrizeReceiveEvent extends PlayerEvent {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private User discordUser;

    public PrizeReceiveEvent(Player who, User discordUser) {
        super(who);
        this.discordUser = discordUser;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public User getDiscordUser() {
        return discordUser;
    }
}
