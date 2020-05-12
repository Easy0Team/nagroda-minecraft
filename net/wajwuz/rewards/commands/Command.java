package net.wajwuz.rewards.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface Command {
    CommandBase getInfo();

    void execute(final MessageReceivedEvent event, final String... strings);
}
