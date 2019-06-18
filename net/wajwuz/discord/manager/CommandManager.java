package net.wajwuz.discord.manager;

import net.wajwuz.discord.command.Command;
import net.wajwuz.discord.command.impl.AwardCommand;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    private List<Command> commandList = new ArrayList<>();

    public void loadCommands() {
        this.commandList.add(new AwardCommand());
    }

    public Command getCommand(String arg) {
        return this.commandList
                .stream()
                .filter(command -> command.getInfo().getName().equals(arg) || command.getInfo().getAliases().contains(arg))
                .findFirst()
                .orElse(null);
    }
}
