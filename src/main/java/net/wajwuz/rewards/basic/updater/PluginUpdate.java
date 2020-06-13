package net.wajwuz.rewards.basic.updater;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.net.MalformedURLException;
import java.net.URL;

public class PluginUpdate {
    private String updateName;
    private URL updateLink;

    PluginUpdate(JsonObject object) {
        this.updateName = object.get("tag_name").getAsString();
        for (JsonElement element : object.get("assets").getAsJsonArray())
            if (element.getAsJsonObject().has("browser_download_url")) {
                String url = element.getAsJsonObject().get("browser_download_url").getAsString();
                if (url.contains(".jar"))
                    try {
                        this.updateLink = new URL(url);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                        return;
                    }
                return;
            }
    }

    URL getDownloadUrl() {
        return updateLink;
    }

    public String getUpdateName() {
        return updateName;
    }
}
