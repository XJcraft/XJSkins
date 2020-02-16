package com.xj.skins.bukkit;

import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import com.xj.skins.ProfileProperty;
import com.xj.skins.SkinProfile;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Skins extends JavaPlugin {

    private static Skins instance;

    private SkinStorage storage;

    public static Skins getInstance() {
        return instance;
    }

    public SkinStorage getStorage() {
        return storage;
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    @Override
    public void onEnable() {
        instance = this;
        storage = new SkinStorage();
        storage.loadData();
        getCommand("skin").setExecutor(new Commands());
        getCommand("skina").setExecutor(new AdminCommands());
        getServer().getPluginManager().registerEvents(new LoginListener(), this);
    }

    public static void changeSkin(Player player, ProfileProperty skin) {
        WrappedGameProfile profile = WrappedGameProfile.fromPlayer(player);
        WrappedSignedProperty property = WrappedSignedProperty.fromValues(skin.getName(), skin.getValue(), skin.getSignature());
        profile.getProperties().put(property.getName(), property);
    }
}