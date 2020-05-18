package net.wajwuz.rewards.data.impl;

import net.wajwuz.rewards.configuration.FlatDataConfiguration;
import net.wajwuz.rewards.data.Store;
import net.wajwuz.rewards.data.User;
import org.bukkit.configuration.ConfigurationSection;

public class FlatStore extends Store {
    private final FlatDataConfiguration flatDataConfiguration;

    public FlatStore(FlatDataConfiguration flatDataConfiguration) {
        this.flatDataConfiguration = flatDataConfiguration;
    }

    @Override
    public void loadData() {
        ConfigurationSection saveSection = flatDataConfiguration.getConfiguration().getConfigurationSection("users");
        if (saveSection == null) return;

        for (String key : saveSection.getKeys(false)) {
            ConfigurationSection userSection = saveSection.getConfigurationSection(key);

            String userName = userSection.getString("minecraft_player_name");
            super.userMap.put(userName, new User(
                    userName,
                    userSection.getName(),
                    userSection.getBoolean("has_used_prize")));
        }
    }

    @Override
    public void saveData() {
        for (User user : userMap.values()) {
            if (!user.needSave()) continue;
            ConfigurationSection userSection = flatDataConfiguration.getConfiguration().getConfigurationSection("users." + user.getDiscordUserId());
            if (userSection == null)
                userSection = flatDataConfiguration.getConfiguration().createSection("users." + user.getDiscordUserId());

            userSection.set("minecraft_player_name", user.getMinecraftPlayerName());
            userSection.set("has_used_prize", user.hasUnlockedPrize());

            user.save();
        }
        flatDataConfiguration.saveConfiguration();
    }
}
