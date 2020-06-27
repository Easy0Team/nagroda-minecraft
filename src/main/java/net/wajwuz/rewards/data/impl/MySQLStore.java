package net.wajwuz.rewards.data.impl;

import net.wajwuz.rewards.basic.RewardPlugin;
import net.wajwuz.rewards.configuration.PluginConfiguration;
import net.wajwuz.rewards.data.Store;
import net.wajwuz.rewards.data.User;

import java.sql.*;

public class MySQLStore extends Store {
    private static final String DATABASE_DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

    private Connection databaseConnection;


    public MySQLStore(RewardPlugin pluginInstance) {
        PluginConfiguration pluginConfiguration = pluginInstance.getPluginConfiguration();

        try {
            Class.forName(DATABASE_DRIVER_CLASS_NAME).newInstance();
            long connectionStart = System.currentTimeMillis();

            this.databaseConnection = DriverManager.getConnection(pluginConfiguration.databaseUrl, pluginConfiguration.databaseUser, pluginConfiguration.databasePass);
            pluginInstance.getLogger().info("Pomyslnie polaczono z baza danych w " + (System.currentTimeMillis() - connectionStart) + "ms");
            //dlaczego nie hikaricp? szkoda mi wielkosci pluginu, i tak juz jest duzy :(
        } catch (InstantiationException | IllegalAccessException
                | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            pluginInstance.getLogger().warning("Wystapil blad podczas tworzenia polaczenia z baza danych.");
            pluginInstance.getLogger().warning("Jezeli uwazasz ze to jest blad z pluginem, zglos to na https://github.com/pizdo/nagroda-minecraft/issues.");
        }
    }


    @Override
    public void loadData() { // 20, bo moze ktos w przyszlosci bedzie uzywac ten plugin, i moze bedzie wiecej uzypiernikow na discordzie?
        try {
            Statement stmt = databaseConnection.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `nagroda_minecraft` (MinecraftPlayerName varchar(16), DiscordUserId varchar(20) NOT NULL, HasUnlockedPrize int(1), PRIMARY KEY (MinecraftPlayerName))");

            ResultSet result = stmt.executeQuery("SELECT * FROM `nagroda_minecraft`");
            while (result.next()) {
                String playerName = result.getString("MinecraftPlayerName");
                super.userMap.put(playerName, new User(
                        playerName,
                        result.getString("DiscordUserId"),
                        result.getInt("HasUnlockedPrize") == 1));
            }

            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void saveData() {
        try {
            PreparedStatement updateStatement = databaseConnection.prepareStatement("INSERT INTO `nagroda_minecraft` VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE MinecraftPlayerName=?, HasUnlockedPrize=?");

            for(User user: userMap.values()) {
                if(!user.needSave()) continue;
                updateStatement.setString(1, user.getMinecraftPlayerName());
                updateStatement.setString(2, user.getDiscordUserId());
                updateStatement.setInt(3, user.hasUnlockedPrize() ? 1 : 0);
                updateStatement.setString(4, user.getMinecraftPlayerName());
                updateStatement.setInt(5, user.hasUnlockedPrize() ? 1 : 0);
                updateStatement.addBatch();
            }

            updateStatement.executeBatch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
