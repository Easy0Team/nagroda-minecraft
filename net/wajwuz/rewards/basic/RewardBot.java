package net.wajwuz.rewards.basic;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.wajwuz.rewards.configuration.Config;
import net.wajwuz.rewards.listeners.MessageReceivedListener;
import net.wajwuz.rewards.managers.CommandManager;

import javax.security.auth.login.LoginException;

public class RewardBot {
    private final JDA jda;
    private final CommandManager commandManager;

    public RewardBot(final String token) {
        this.commandManager = new CommandManager();
        this.jda = this.createUser(token);
        if (this.jda == null) {
            return;
        }
        this.commandManager.loadCommands();
        new MessageReceivedListener(this);
    }

    private JDA createUser(final String token) {
        final JDABuilder jdaBuilder = new JDABuilder(AccountType.BOT)
                .setToken(token)
                .setActivity(Activity.listening(Config.BOT$GAME$NAME))
                .setAutoReconnect(true);
        try {
            return jdaBuilder.build();
        }
        catch (LoginException e) {
            e.printStackTrace();
        }
        return jda;
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public JDA getJda() {
        return this.jda;
    }
}
