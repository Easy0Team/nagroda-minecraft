package net.wajwuz.discord.main;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.wajwuz.discord.listeners.GuildMemberJoinListener;
import net.wajwuz.discord.listeners.MessageReceivedListener;
import net.wajwuz.discord.manager.CommandManager;
import net.wajwuz.spigot.config.Config;

import javax.security.auth.login.LoginException;

public class DiscordBot {

    private JDA jda;
    private CommandManager commandManager;

    public DiscordBot(String token) {
        commandManager = new CommandManager();
        jda = this.createUser(token);
        if (jda == null) return;
        this.commandManager.loadCommands();
        new MessageReceivedListener(this);
        new GuildMemberJoinListener(this);
    }

    private JDA createUser(String token) {
        JDABuilder jdaBuilder = new JDABuilder(AccountType.BOT)
                .setToken(token)
                .setGame(Game.listening(Config.BOT$GAME$NAME))
                .setAutoReconnect(true);
        try {
            return jdaBuilder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        return null;
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public JDA getJda() {
        return this.jda;
    }
}
