package net.wajwuz.spigot.objects;

import net.dv8tion.jda.core.entities.Member;
import net.wajwuz.spigot.main.DiscordPlugin;

public class User {
    public static boolean getAwardStatus(Member member) {
        return DiscordPlugin.getPlugin(DiscordPlugin.class).getConfig().getBoolean("users." + member.getUser().getId() + ".award");
    }

    public static void setAwardStatus(Member member, boolean b) {
        boolean status = getAwardStatus(member);
        status = b;
        DiscordPlugin.getPlugin(DiscordPlugin.class).getConfig().set("users." + member.getUser().getId() + ".award", status);
        DiscordPlugin.getPlugin(DiscordPlugin.class).saveConfig();
    }

}
