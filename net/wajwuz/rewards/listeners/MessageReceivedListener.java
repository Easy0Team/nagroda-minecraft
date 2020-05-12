package net.wajwuz.rewards.listeners;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.wajwuz.rewards.basic.RewardBot;
import net.wajwuz.rewards.commands.Command;
import net.wajwuz.rewards.configuration.Config;
import net.wajwuz.rewards.objects.User;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class MessageReceivedListener extends ListenerAdapter {
    private final RewardBot bot;

    public MessageReceivedListener(final RewardBot bot) {
        this.bot = bot;
        this.bot.getJda().addEventListener(this);
    }

    @Override
    public void onMessageReceived(final @NotNull MessageReceivedEvent event) {
        if (!User.getUser(event.getMember())) User.createUser(event.getMember());
        try {
            final String[] args = event.getMessage().getContentDisplay().split(" ");
            if (!args[0].startsWith(Config.BOT$PREFIX)) return;
            final Command command = this.bot.getCommandManager().getCommand(args[0].split(Config.BOT$PREFIX)[1]);
            if (command != null) command.execute(event, args);
            event.getMessage().delete().queueAfter(1L, TimeUnit.SECONDS);
        } catch (Exception ignored) {}
    }
}
