package net.wajwuz.rewards.data.impl;

import net.wajwuz.rewards.data.Store;
import net.wajwuz.rewards.data.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FlatStore extends Store {
    private final File directory;

    public FlatStore(File directory) {
        this.directory = directory;
        directory.mkdirs();
    }

    @Override
    public void loadData() {
        for (File file : directory.listFiles()) {
            try (FileInputStream inputStream = new FileInputStream(file)) {
                String discordId = Long.toString(Long.parseLong(file.getName().substring(0, file.getName().lastIndexOf(".")), 16));

                byte[] usernameRaw = new byte[inputStream.read()];
                inputStream.read(usernameRaw);
                String username = new String(usernameRaw);

                userMap.put(username, new User(username, discordId, inputStream.read() == 1));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void saveData() {
        for (User user : userMap.values()) {
            if (!user.needSave()) continue;
            try (FileOutputStream outputStream = new FileOutputStream(getPlayerSaveFile(user.getDiscordUserId()))) {
                byte[] usernameBytes = user.getMinecraftPlayerName().getBytes();
                outputStream.write(usernameBytes.length);
                outputStream.write(usernameBytes);
                outputStream.write(user.hasUnlockedPrize() ? 1 : 0);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private File getPlayerSaveFile(String discordId) {
        return new File(directory, Long.toHexString(Long.parseLong(discordId)) + ".bin");
    }
}
