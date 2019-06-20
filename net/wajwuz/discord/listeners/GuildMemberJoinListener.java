package net.wajwuz.discord.listeners;

import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.wajwuz.discord.basic.DiscordBot;
import net.wajwuz.spigot.utils.User;

public class GuildMemberJoinListener extends ListenerAdapter {


    private final DiscordBot bot;

    public GuildMemberJoinListener(DiscordBot bot) {
        this.bot = bot;
        this.bot.getJda().addEventListener(this);
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        if(!User.getUser(event.getMember())) {
            try {
                User.createUser(event.getMember());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
