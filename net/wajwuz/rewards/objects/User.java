package net.wajwuz.rewards.objects;

import net.dv8tion.jda.api.entities.Member;
import net.wajwuz.rewards.basic.impl.RewardPlugin;
import org.bukkit.plugin.Plugin;

public class User {
    private static final Plugin plugin = RewardPlugin.getPlugin(RewardPlugin.class);

    public static boolean getAwardStatus(final Member member) {
        return plugin.getConfig().getBoolean("users." + member.getUser().getId() + ".award");
    }

    public static void setAwardStatus(final Member member, final boolean b) {
        plugin.getConfig().set("users." + member.getUser().getId() + ".award", b);
    }

    public static void createUser(final Member member) {
        if (!plugin.getConfig().contains("users." + member.getUser().getId())) {
            plugin.getConfig().set("users." + member.getUser().getId() + ".award", true);
            plugin.getConfig().set("users." + member.getUser().getId() + ".name", member.getUser().getName());
        }
    }

    public static boolean getUser(final Member member) {
        return plugin.getConfig().contains("users." + member.getUser().getId());
    }
}
