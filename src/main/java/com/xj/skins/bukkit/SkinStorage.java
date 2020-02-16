package com.xj.skins.bukkit;

import com.xj.skins.SkinProfile;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class SkinStorage {

    private HashMap<String, SkinProfile> skins = new LinkedHashMap<String, SkinProfile>(150, 0.75F, true);


    public SkinProfile getSkin(String nick) {
        return skins.get(nick.toLowerCase());
    }

    public void setSkin(String nick, SkinProfile profile) {
        skins.put(nick.toLowerCase(), profile);
    }

    public void removeSkin(String nick) {
        skins.remove(nick.toLowerCase());
    }

    public File getDataFile() {
        return new File(Skins.getInstance().getDataFolder(), "playerdata.yml");
    }

    public void loadData() {

    }

    public void saveData() {

    }
}
