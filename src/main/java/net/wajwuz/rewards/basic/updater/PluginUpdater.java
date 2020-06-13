package net.wajwuz.rewards.basic.updater;

import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
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
            URL url = new URL(RELEASE_URL); // ja pierdole ale zjebany ten kod
            // wez sie typie naucz javy

            URLConnection connection = url.openConnection();
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            return new PluginUpdate(reader.parse(inputStream).getAsJsonObject());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void updatePlugin(PluginUpdate update) throws Exception{
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
