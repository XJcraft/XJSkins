package com.xj.skins.bungee;

import net.md_5.bungee.api.plugin.Plugin;

public class Skins extends Plugin {
    private static Skins instance;

    private SkinStorage storage;

    public static Skins getInstance() {
        return instance;
    }

    public SkinStorage getStorage() {
        return storage;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;
        storage = new SkinStorage();
        getProxy().getScheduler().runAsync(this, new Runnable() {
            public void run() {
                storage.loadData();
            }
        });

        getProxy().getPluginManager().registerListener(this, new LoginListener());
        getProxy().getPluginManager().registerCommand(this, new Commands());
    }

    @Override
    public void onDisable() {
        super.onDisable();
        instance = null;
        storage = null;
    }
}
