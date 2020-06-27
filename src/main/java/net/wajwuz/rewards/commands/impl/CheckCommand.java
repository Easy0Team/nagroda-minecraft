package net.wajwuz.rewards.commands.impl;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.wajwuz.rewards.commands.Command;
import net.wajwuz.rewards.data.Store;
import net.wajwuz.rewards.data.User;

import java.awt.*;
import java.util.Optional;

public class CheckCommand extends Command {
    private Store storeData;

    public CheckCommand(Store storeData) {
        super("check", "sprawdz");
        this.storeData = storeData;
    }

    @Override
    public void execute(MessageReceivedEvent event, String... strings) {
        if (strings.length != 2 || event.getMember() == null || !event.getMember().hasPermission(Permission.KICK_MEMBERS)) {
            event.getMessage().addReaction("\uD83E\uDD2F").queue();
            return;
        }

        String identifier = strings[1];
        if (event.getMessage().getMentionedMembers().size() != 0)
            identifier = event.getMessage().getMentionedMembers().get(0).getId();

        Optional<User> user = storeData.getUser(identifier);
        if (user.isPresent())
            event.getChannel().sendMessage(new EmbedBuilder().setTitle("Informacje o graczu " + user.get().getMinecraftPlayerName()).setColor(Color.GREEN)
                    .addField("ID Konta Discorda", user.get().getDiscordUserId() + " (<@" + user.get().getDiscordUserId() + ">)", false)
                    .addField("Nick w mc", user.get().getMinecraftPlayerName(), false)
                    .build()).queue();
        else
            event.getChannel().sendMessage(new EmbedBuilder().setTitle("Blad").setDescription("Gracz nie znajduje sie w bazie danych.").setColor(Color.RED).build()).queue();
    }
}
