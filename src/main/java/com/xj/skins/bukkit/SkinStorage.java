package com.xj.skins.bukkit;

import com.xj.skins.ProfileProperty;
import com.xj.skins.SkinProfile;
import com.xj.skins.util.Mojang;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class SkinStorage {

    private Map<String, SkinProfile> cache = new HashMap<String, SkinProfile>();
    private Map<String, String> data = new HashMap<String, String>();


    public void checkSkin(String nick) throws IOException {
        Logger log = Skins.getInstance().getLogger();
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
        }
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

    public File getDataFile() {
        return new File(Skins.getInstance().getDataFolder(), "playerdata.yml");
    }

    public void loadData() {
        FileConfiguration yaml = YamlConfiguration.loadConfiguration(getDataFile());
        long load = 0;
        for (String name : yaml.getKeys(false)) {
            String skin = yaml.getString(name);
            data.put(name, skin);
        }
    }

    public void saveData() {
        FileConfiguration yaml = new YamlConfiguration();
        Map<String, String> map = Collections.unmodifiableMap(data);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            yaml.set(entry.getKey(), entry.getValue());
        }
        try {
            yaml.save(getDataFile());
        } catch (IOException e) {
            Skins.getInstance().getLogger().warning("保存数据文件出错");
            e.printStackTrace();
        }
    }
}
