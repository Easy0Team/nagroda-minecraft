package net.wajwuz.rewards.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class Command {
    private final String name;
    private final List<String> aliases;

    public Command(String name, String... aliases) {
        this.name = name;
        this.aliases = Arrays.asList(aliases);
    }

    boolean isName(String name) {
        return this.name.equalsIgnoreCase(name) || aliases.contains(name);
    }

    public abstract void execute(final MessageReceivedEvent event, final String... strings);

    protected void sendMessage(MessageChannel channel, User user, EmbedBuilder embededMessage) {
        channel.sendMessage(embededMessage.setFooter("Komenda uzyta przez " + user.getName(), user.getAvatarUrl()).build())
                .queue(message -> message.delete()
                        .queueAfter(5L, TimeUnit.SECONDS));
    }
}
