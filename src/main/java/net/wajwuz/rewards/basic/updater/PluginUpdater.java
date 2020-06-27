package net.wajwuz.rewards.basic.updater;

import com.google.gson.JsonParser;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class PluginUpdater {
    private static final String RELEASE_URL = "https://api.github.com/repos/pizdo/nagroda-minecraft/releases/latest";

    private final JsonParser reader = new JsonParser();
    private File jarFile;

    public PluginUpdater(Plugin plugin) {
        try {
            this.jarFile = new File(URLDecoder.decode(plugin.getClass().getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath(), StandardCharsets.UTF_8.name()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public PluginUpdate getUpdate() {
        try {
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(new URL(RELEASE_URL).openStream()));

            return new PluginUpdate(reader.parse(inputStream).getAsJsonObject());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void updatePlugin(PluginUpdate update) throws Exception {
        FileOutputStream fos = new FileOutputStream(jarFile);
        BufferedInputStream inputStream = new BufferedInputStream(update.getDownloadUrl().openStream());

        byte[] dataBuffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(dataBuffer, 0, 1024)) != -1)
            fos.write(dataBuffer, 0, bytesRead);

        fos.close();
        inputStream.close();
    }
}
