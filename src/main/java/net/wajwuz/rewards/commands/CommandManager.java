package net.wajwuz.rewards.commands;

import net.wajwuz.rewards.commands.impl.AwardCommand;
import net.wajwuz.rewards.commands.impl.CheckCommand;
import net.wajwuz.rewards.configuration.PluginConfiguration;
import net.wajwuz.rewards.data.Store;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CommandManager {
    private final List<Command> commandList;

    public CommandManager(PluginConfiguration configuration, Store storeData) {
        this.commandList = Arrays.asList(
                new AwardCommand(configuration, storeData),
                new CheckCommand(storeData)
        );
    }

    public Optional<Command> getCommand(String commandName) {
        return this.commandList.stream()
                .filter(command -> command.isName(commandName))
                .findFirst();
    }
}
