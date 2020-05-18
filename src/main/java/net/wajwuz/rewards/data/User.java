package net.wajwuz.rewards.data;

public class User {
    private String minecraftPlayerName;
    private String discordUserId;
    private boolean hasUnlockedPrize;

    private boolean needSave;

    public User(String minecraftPlayerName, String discordUserId, boolean hasUnlockedPrize) {
        this.minecraftPlayerName = minecraftPlayerName;
        this.discordUserId = discordUserId;
        this.hasUnlockedPrize = hasUnlockedPrize;

        this.needSave = true;
    }

    public String getMinecraftPlayerName() {
        return minecraftPlayerName;
    }

    public String getDiscordUserId() {
        return discordUserId;
    }

    public boolean hasUnlockedPrize() {
        return hasUnlockedPrize;
    }

    public void unlockPrize() {
        this.hasUnlockedPrize = true;
        this.needSave = true;
    }

    public void save() {
        this.needSave = false;
    }

    public boolean needSave() {
        return needSave;
    }
}
