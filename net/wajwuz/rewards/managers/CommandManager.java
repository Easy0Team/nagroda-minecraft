package net.wajwuz.rewards.managers;

import net.wajwuz.rewards.commands.Command;
import net.wajwuz.rewards.commands.impl.AwardCommand;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private final List<Command> commandList;

    public CommandManager() {
        this.commandList = new ArrayList<>();
    }

    public void loadCommands() {
        this.commandList.add(new AwardCommand());
    }

    public Command getCommand(final String arg) {
        return this.commandList.stream().filter(command -> command.getInfo().getName().equals(arg) || command.getInfo().getAliases().contains(arg)).findFirst().orElse(null);
    }
}
