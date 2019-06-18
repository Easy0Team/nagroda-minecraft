package net.wajwuz.discord.command;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public interface Command {

    CommandInfo getInfo();

    void execute(MessageReceivedEvent event, String... args);
}
