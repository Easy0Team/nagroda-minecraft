package net.wajwuz.spigot.utils;

import net.dv8tion.jda.core.entities.Member;
import net.wajwuz.spigot.main.DiscordPlugin;
import org.bukkit.plugin.Plugin;

public class User {
    private static Plugin plugin = DiscordPlugin.getPlugin(DiscordPlugin.class);

    public static boolean getAwardStatus(Member member) {
        return plugin.getConfig().getBoolean("users." + member.getUser().getId() + ".award");
    }

    public static void setAwardStatus(Member member, boolean b) {
        getAwardStatus(member);
        boolean status;
        status = b;
        plugin.getConfig().set("users." + member.getUser().getId() + ".award", status);
        plugin.saveConfig();
    }

    public static void createUser(Member member) {
        if(!plugin.getConfig().contains("users." + member.getUser().getId())) {
            plugin.getConfig().set("users." + member.getUser().getId() + ".award", true);
            plugin.getConfig().set("users." + member.getUser().getId() + ".name", member.getUser().getName());
            plugin.saveConfig();
        }
    }
}
