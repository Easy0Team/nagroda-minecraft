package net.wajwuz.rewards.commands.impl;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.wajwuz.rewards.basic.RewardPlugin;
import net.wajwuz.rewards.commands.Command;
import net.wajwuz.rewards.configuration.PluginConfiguration;
import net.wajwuz.rewards.data.Store;
import net.wajwuz.rewards.data.User;
import net.wajwuz.rewards.event.PrizeReceiveEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.TimeUnit;

public class AwardCommand extends Command {
    private final Store userData;
    private final PluginConfiguration configuration;

    public AwardCommand(PluginConfiguration configuration, Store userData) {
        super("nagroda", "reward");
        this.userData = userData;
        this.configuration = configuration;
    }

    @Override
    public void execute(MessageReceivedEvent event, String... strings) {
        if (strings.length <= 1 && !configuration.botPrefix.isEmpty()) {
            sendMessage(event.getChannel(), configuration.invalidArugmentMessage);
            return;
        }

        Player player = Bukkit.getPlayer(strings[1]);
        if (player == null) {
            sendMessage(event.getChannel(), configuration.playerOfflineMessage);
            return;
        }

        User user = userData.getUser(player.getName(), event.getAuthor().getId());
        if (user.hasUnlockedPrize()) {
            sendMessage(event.getChannel(), configuration.playerAlreadyReceivedMessage);
            return;
        }

        synchronized (this) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.getServer().dispatchCommand(RewardPlugin.getPlugin(RewardPlugin.class).getServer().getConsoleSender(), configuration.botAwardCommand.replace("{NICK}", player.getName()));
                }
            }.runTask(RewardPlugin.getPlugin(RewardPlugin.class));
        }

        user.unlockPrize();
        sendMessage(event.getChannel(), configuration.playerAwardedMessage);
        Bukkit.getPluginManager().callEvent(new PrizeReceiveEvent(player, user));
    }
}