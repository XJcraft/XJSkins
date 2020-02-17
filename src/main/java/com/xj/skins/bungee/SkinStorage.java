package com.xj.skins.bungee;

import com.xj.skins.util.ASkinStorage;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

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
        File file = getDataFile();
        if (!file.exists()) {
            return;
        }
        try {
            Configuration yaml = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            for (String name : yaml.getKeys()) {
                String skin = yaml.getString(name);
                data.put(name, skin);
            }
        } catch (IOException e) {
            getLogger().warning("加载皮肤数据出错");
            e.printStackTrace();
        }
    }

    public void saveData() {
        Configuration yaml = new Configuration();
        Map<String, String> map = Collections.unmodifiableMap(data);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            yaml.set(entry.getKey(), entry.getValue());
        }
        try {
            File file = getDataFile();
            file.getParentFile().mkdirs();
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(yaml, file);
        } catch (IOException e) {
            getLogger().warning("保存数据文件出错");
            e.printStackTrace();
        }
    }

    public Logger getLogger() {
        return Skins.getInstance().getLogger();
    }
}
