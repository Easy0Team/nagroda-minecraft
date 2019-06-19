package net.wajwuz.discord.listeners;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.wajwuz.discord.main.DiscordBot;
import net.wajwuz.spigot.main.DiscordPlugin;
import org.bukkit.plugin.Plugin;

public class GuildMemberJoinListener extends ListenerAdapter {

    private final Plugin plugin = DiscordPlugin.getPlugin(DiscordPlugin.class);

    private final DiscordBot bot;

    public GuildMemberJoinListener(DiscordBot bot) {
        this.bot = bot;
        this.bot.getJda().addEventListener(this);
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        final Member member = event.getMember();
        if(!plugin.getConfig().contains("users." + member.getUser().getId())) {
            plugin.getConfig().set("users." + member.getUser().getId() + ".award", true);
            plugin.getConfig().set("users." + member.getUser().getId() + ".name", member.getUser().getName());
            plugin.saveConfig();
        }
    }
}
