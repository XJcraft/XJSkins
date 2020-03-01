package com.xj.skins.bukkit;

import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import com.xj.skins.ProfileProperty;
import com.xj.skins.util.Channels;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.DataInputStream;

public class Skins extends JavaPlugin {

    private static Skins instance;

    private SkinStorage storage;

    private boolean bungeeEnabled;

    public static Skins getInstance() {
        return instance;
    }

    public SkinStorage getStorage() {
        return storage;
    }

    @Override
    public void onDisable() {
        instance = null;
        storage = null;
    }

    @Override
    public void onEnable() {
        instance = this;
        storage = new SkinStorage();

        checkBungeeMode();
        if (bungeeEnabled) {
            Channels.build("skins:skinchange")
                    .addSubChannel("SkinClear", new Channels.SubChannelHandler() {
                        public void run(Player player, DataInputStream in) throws Exception {
                            // TODO
                        }
                    })
                    .addSubChannel("SkinUpdate", new Channels.SubChannelHandler() {
                        public void run(Player player, DataInputStream in) throws Exception {
                            Skins.changeSkin(player, new ProfileProperty(in.readUTF(), in.readUTF(), in.readUTF()));
                        }
                    })
                    .register(this);


        } else {
            storage.loadData();
            getCommand("skin").setExecutor(new Commands());
            getCommand("skina").setExecutor(new AdminCommands());
            getServer().getPluginManager().registerEvents(new LoginListener(), this);
        }
    }


    private void checkBungeeMode() {
        try {
            bungeeEnabled = getServer().spigot().getConfig().getBoolean("settings.bungeecord");
        } catch (Throwable e) {
            bungeeEnabled = false;
        }

        if (bungeeEnabled) {
            getLogger().info("This plugin is running in Bungee mode!");
        }

    }

    public static void changeSkin(Player player, ProfileProperty skin) {
        WrappedGameProfile profile = WrappedGameProfile.fromPlayer(player);
        WrappedSignedProperty property = WrappedSignedProperty.fromValues(skin.getName(), skin.getValue(), skin.getSignature());
        profile.getProperties().put(property.getName(), property);
    }
}