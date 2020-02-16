package com.xj.skins.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xj.skins.Profile;
import com.xj.skins.ProfileProperty;
import com.xj.skins.bukkit.Skins;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class Mojang {

    public static Profile getProfile(String nick) throws IOException {
        String body = new Gson().toJson(Arrays.asList(nick));
        byte[] response = Https.request("POST", "https://api.mojang.com/profiles/minecraft", body.getBytes());
//        Skins.getInstance().getLogger().info("response: " + new String(response));
        Type type = new TypeToken<List<Profile>>() {
        }.getType();
        List<Profile> profiles = new Gson().fromJson(new String(response), type);
        return profiles.get(0);
    }

    public static ProfileProperty getSkin(String uuid) throws IOException {
        byte[] response = Https.request("GET", "https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false", null);
        Type type = new TypeToken<Profile>() {
        }.getType();
        Profile profile = new Gson().fromJson(new String(response), type);
        for (ProfileProperty prop : profile.getProperties()) {
            if (prop.getName().equals("textures")) {
                return prop;
            }
        }
        return null;
    }

    public static ProfileProperty getSkinProperty(String nick) throws IOException {
        Profile profile = Mojang.getProfile(nick);
        Skins.getInstance().getLogger().info("profile " + profile);
        return Mojang.getSkin(profile.getId());
    }

}
