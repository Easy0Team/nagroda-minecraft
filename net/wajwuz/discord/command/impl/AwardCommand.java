package net.wajwuz.discord.command.impl;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.wajwuz.discord.command.Command;
import net.wajwuz.discord.command.CommandBase;
import net.wajwuz.spigot.config.Config;
import net.wajwuz.spigot.utils.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.awt.Color;

public class AwardCommand implements Command {

    @Override
    public CommandBase getInfo() {
        return new CommandBase("nagroda");
    }

    @Override
    public void execute(MessageReceivedEvent event, String... args) {

        User.createUser(event.getMember());

        EmbedBuilder offlinePlayer = new EmbedBuilder();
        offlinePlayer.setDescription(":interrobang: Blad: Ten gracz jest offline!");
        offlinePlayer.setFooter("\uD83C\uDF89 " + event.getAuthor().getName() + " uzyl tej komendy!", null);
        offlinePlayer.setColor(Color.red);

        EmbedBuilder awardedPlayer = new EmbedBuilder();
        awardedPlayer.setDescription(":tada: Nagroda zostanie nadana do 30 sekund!");
        awardedPlayer.setFooter("\uD83C\uDF89 " + event.getAuthor().getName() + " uzyl tej komendy!", null);
        awardedPlayer.setColor(Color.green);

        EmbedBuilder neededArgument = new EmbedBuilder();
        neededArgument.setDescription(":interrobang: Musisz podac swoj nick aby odebrac nagrode!");
        neededArgument.setFooter("\uD83C\uDF89 " + event.getAuthor().getName() + " uzyl tej komendy!", null);
        neededArgument.setColor(Color.red);

        EmbedBuilder awardedBefore = new EmbedBuilder();
        awardedBefore.setDescription(":interrobang: Juz odebrales swoja nagrode!");
        awardedBefore.setFooter("\uD83C\uDF89 " + event.getAuthor().getName() + " uzyl tej komendy!", null);
        awardedBefore.setColor(Color.red);

        if (args.length == 1) {
            event.getChannel().sendMessage(neededArgument.build()).queue();
        } else {
            Player player = Bukkit.getPlayer(args[1]);
            if(User.getAwardStatus(event.getMember())) {
                if (player == null) {
                    event.getChannel().sendMessage(offlinePlayer.build()).queue();
                } else {
                    event.getChannel().sendMessage(awardedPlayer.build()).queue();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Config.BOT$AWARD$COMMAND.replace("{NICK}", player.getName()));
                    User.setAwardStatus(event.getMember(), !User.getAwardStatus(event.getMember()));
                }
            } else {
                event.getChannel().sendMessage(awardedBefore.build()).queue();
            }
        }
    }
}
