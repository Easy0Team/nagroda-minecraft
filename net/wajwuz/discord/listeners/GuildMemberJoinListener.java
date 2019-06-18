package net.wajwuz.discord.listeners;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.wajwuz.discord.main.DiscordBot;
import net.wajwuz.spigot.main.DiscordPlugin;

public class GuildMemberJoinListener extends ListenerAdapter {

    private final DiscordBot bot;

    public GuildMemberJoinListener(DiscordBot bot) {
        this.bot = bot;
        this.bot.getJda().addEventListener(this);
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Member member = event.getMember();
        if(!DiscordPlugin.getPlugin(DiscordPlugin.class).getConfig().contains("users." + member.getUser().getId())) {
            DiscordPlugin.getPlugin(DiscordPlugin.class).getConfig().set("users." + member.getUser().getId() + ".award", true);
            DiscordPlugin.getPlugin(DiscordPlugin.class).getConfig().set("users." + member.getUser().getId() + ".name", member.getUser().getName());
            DiscordPlugin.getPlugin(DiscordPlugin.class).saveConfig();
        }
    }
}
