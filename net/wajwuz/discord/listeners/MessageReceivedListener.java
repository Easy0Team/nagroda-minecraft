package net.wajwuz.discord.listeners;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.wajwuz.discord.command.Command;
import net.wajwuz.discord.basic.DiscordBot;
import net.wajwuz.spigot.config.Config;

import java.util.concurrent.TimeUnit;

public class MessageReceivedListener extends ListenerAdapter {

    private final DiscordBot bot;

    public MessageReceivedListener(DiscordBot bot) {
        this.bot = bot;
        this.bot.getJda().addEventListener(this);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        try {
            final String[] args = event.getMessage().getContentDisplay().split(" ");
            if (args[0].startsWith(Config.BOT$PREFIX)) {
                final Command command = this.bot.getCommandManager().getCommand(args[0].split(Config.BOT$PREFIX)[1]);
                if (command != null) command.execute(event, args);
                event.getMessage().delete().queueAfter(250, TimeUnit.MILLISECONDS);
            } else {
                return;
            }
        } catch (Exception ignored) {}
    }
}
