package net.wajwuz.discord.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandInfo {

    private final String name;
    private final List<String> aliases;

    public CommandInfo(String name) {
        this.name = name;
        this.aliases = new ArrayList<>();
    }

    public CommandInfo(String name, String... aliases) {
        this.name = name;
        this.aliases = Arrays.asList(aliases);
    }

    public String getName() {
        return this.name;
    }

    public List<String> getAliases() {
        return this.aliases;
    }
}
