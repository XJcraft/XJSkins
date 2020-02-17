package com.xj.skins.util;

import com.xj.skins.ProfileProperty;
import com.xj.skins.SkinProfile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public abstract class ASkinStorage {

    protected static String FILE = "playerdata.yml";
    protected Map<String, SkinProfile> cache = new LRUCache<String, SkinProfile>(15);
    protected Map<String, String> data = new HashMap<String, String>();


    public ProfileProperty checkSkin(String nick) throws IOException {
        Logger log = getLogger();
        String skin = data.get(nick.toLowerCase());
        if (skin != null) {
            SkinProfile skinProfile = cache.get(skin);
            if (skinProfile == null || skinProfile.isTooDamnOld()) {
                ProfileProperty prop = Mojang.getSkinProperty(skin);
                skinProfile = new SkinProfile(prop);
                cache.put(skin, skinProfile);
                log.info(String.format("Fetch %s 's Skin(%s) from network", nick, skin));
            } else {
                log.info(String.format("Fetch %s 's Skin(%s) from cache", nick, skin));
            }
            return skinProfile.getPlayerSkinProperty();
        }
        return null;
    }

    public ProfileProperty getSkin(String nick) {
        String skin = data.get(nick.toLowerCase());
        if (skin != null) {
            SkinProfile skinProfile = cache.get(skin);
            if (skinProfile != null) {
                return skinProfile.getPlayerSkinProperty();
            }
        }
        return null;
    }

    public void setSkin(String nick, String skin) throws IOException {
        ProfileProperty prop = Mojang.getSkinProperty(skin);
        SkinProfile skinProfile = new SkinProfile(prop);
        cache.put(skin, skinProfile);
        data.put(nick.toLowerCase(), skin);
        saveData();
    }

    public void removeSkin(String nick) {
        data.remove(nick.toLowerCase());
        saveData();
    }

    public abstract File getDataFile();

    public abstract void loadData();

    public abstract void saveData();

    public abstract Logger getLogger();
}
