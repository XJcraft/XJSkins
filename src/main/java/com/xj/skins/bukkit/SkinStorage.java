package com.xj.skins.bukkit;

import com.xj.skins.util.ASkinStorage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Logger;

public class SkinStorage extends ASkinStorage {

    public File getDataFile() {
        return new File(Skins.getInstance().getDataFolder(), FILE);
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
            getLogger().warning("保存数据文件出错");
            e.printStackTrace();
        }
    }

    public Logger getLogger() {
        return Skins.getInstance().getLogger();
    }
}
