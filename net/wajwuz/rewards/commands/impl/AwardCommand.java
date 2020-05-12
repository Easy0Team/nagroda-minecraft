package net.wajwuz.rewards.commands.impl;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.wajwuz.rewards.basic.impl.RewardPlugin;
import net.wajwuz.rewards.commands.Command;
import net.wajwuz.rewards.commands.CommandBase;
import net.wajwuz.rewards.configuration.Config;
import net.wajwuz.rewards.objects.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class AwardCommand implements Command {
    @Override
    public CommandBase getInfo() {
        return new CommandBase("nagroda", "reward");
    }

    @Override
    public void execute(MessageReceivedEvent event, String... strings) {
        final EmbedBuilder offlinePlayer = new EmbedBuilder().setDescription(Config.OFFLINE$PLAYER$MESSAGE).setColor(Color.RED);
        final EmbedBuilder awardedPlayer = new EmbedBuilder().setDescription(Config.AWARDED$PLAYER$MESSAGE).setColor(Color.GREEN);
        final EmbedBuilder neededArgument = new EmbedBuilder().setDescription(Config.EMPTY$PLAYER$MESSAGE).setColor(Color.YELLOW);
        final EmbedBuilder awardedBefore = new EmbedBuilder().setDescription(Config.AWARDED$BEFORE$PLAYER$MESSAGE).setColor(Color.RED);
        final EmbedBuilder wrongChannel = new EmbedBuilder().setDescription(Config.WRONG$CHANNEL$MESSAGE.replace("{CHANNEL}", "<#" + Config.BOT$AWARD$CHANNEL + ">")).setColor(Color.YELLOW);

        if (!event.getChannel().getId().equals(Config.BOT$AWARD$CHANNEL)) {
            event.getChannel().sendMessage(wrongChannel.build()).queue(message -> message.delete().queueAfter(5L, TimeUnit.SECONDS));
            return;
        }
        if (strings.length <= 1) event.getChannel().sendMessage(neededArgument.build()).queue(message -> message.delete().queueAfter(5L, TimeUnit.SECONDS));
        else {
            final Player player = Bukkit.getPlayer(strings[1]);
            if (User.getAwardStatus(event.getMember())) if (player == null) event.getChannel().sendMessage(offlinePlayer.build()).queue(message -> message.delete().queueAfter(5L, TimeUnit.SECONDS));
            else {
                User.setAwardStatus(event.getMember(), !User.getAwardStatus(event.getMember()));
                event.getChannel().sendMessage(awardedPlayer.build()).queue(message -> message.delete().queueAfter(5L, TimeUnit.SECONDS));
                synchronized (this) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Bukkit.getServer().dispatchCommand(RewardPlugin.getPlugin(RewardPlugin.class).getServer().getConsoleSender(), Config.BOT$AWARD$COMMAND.replace("{NICK}", player.getName()));
                        }
                    }.runTaskLaterAsynchronously(RewardPlugin.getPlugin(RewardPlugin.class), 10L);
                }
            } else event.getChannel().sendMessage(awardedBefore.build()).queue(message -> message.delete().queueAfter(5L, TimeUnit.SECONDS));
        }
    }
}