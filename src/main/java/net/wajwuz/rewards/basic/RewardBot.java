package net.wajwuz.rewards.basic;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.wajwuz.rewards.commands.Command;
import net.wajwuz.rewards.commands.CommandManager;
import net.wajwuz.rewards.configuration.FlatDataConfiguration;
import net.wajwuz.rewards.configuration.PluginConfiguration;
import net.wajwuz.rewards.data.Store;
import net.wajwuz.rewards.data.impl.FlatStore;
import net.wajwuz.rewards.data.impl.MySQLStore;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class RewardBot extends ListenerAdapter {
    private final PluginConfiguration pluginConfiguration;
    private CommandManager commandManager;
    private Store userData;
    private JDA jda;

    RewardBot(RewardPlugin rewardPlugin) {
        this.pluginConfiguration = rewardPlugin.getPluginConfiguration();

        if (pluginConfiguration.saveType.equalsIgnoreCase("flat")) {
            FlatDataConfiguration dataConfiguration = new FlatDataConfiguration(rewardPlugin);
            dataConfiguration.loadConfiguration();
            this.userData = new FlatStore(dataConfiguration);
        } else if (pluginConfiguration.saveType.equalsIgnoreCase("mysql")) {
            this.userData = new MySQLStore(rewardPlugin);
        } else {
            rewardPlugin.getLogger().info("Nie znaleziono typu zapisywania danych " + pluginConfiguration.saveType + ". Wylaczanie pluginu.");
            rewardPlugin.getServer().getPluginManager().disablePlugin(rewardPlugin);
            return;
        }

        this.userData.loadData();
        this.commandManager = new CommandManager(pluginConfiguration, userData);
    }

    void startBot() {
        this.jda = this.createUser(pluginConfiguration.botToken);
        if (this.jda == null) {
            return;
        }
        this.jda.addEventListener(this);
    }

    private JDA createUser(final String token) {
        final JDABuilder jdaBuilder = new JDABuilder(AccountType.BOT)
                .setToken(token)
                .setActivity(Activity.of(Activity.ActivityType.valueOf(pluginConfiguration.botGameType), pluginConfiguration.botGameName))
                .setAutoReconnect(true);

        try {
            return jdaBuilder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        return jda;
    }

    @Override
    public void onMessageReceived(final @NotNull MessageReceivedEvent event) {
        if (event.getChannel().getId().equals(pluginConfiguration.botAwardChannelId) && !event.getAuthor().isBot())
            event.getMessage().delete().queueAfter(1L, TimeUnit.SECONDS);

        String messageContent = event.getMessage().getContentRaw();
        if (!messageContent.startsWith(pluginConfiguration.botPrefix)) return;

        String[] args = messageContent.substring(pluginConfiguration.botPrefix.length()).split(" ");
        Optional<Command> command = commandManager.getCommand(args[0]); //args[0] to nazwa komendy btw

        command.ifPresent(value -> value.execute(event, args));
    }

    Store getStore() {
        return userData;
    }
}
