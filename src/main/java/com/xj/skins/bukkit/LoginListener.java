package com.xj.skins.bukkit;

import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import com.xj.skins.Profile;
import com.xj.skins.ProfileProperty;
import com.xj.skins.SkinProfile;
import com.xj.skins.util.Mojang;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.io.IOException;

public class LoginListener implements Listener {
    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        Skins.getInstance().getLogger().info("AsyncPlayerPreLoginEvent");
        try {
            ProfileProperty prop = Mojang.getSkinProperty(event.getName());
            Skins.getInstance().getStorage().setSkin(event.getName(), new SkinProfile(prop));
            Skins.getInstance().getLogger().info("set skin " + prop);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        SkinProfile prop = Skins.getInstance().getStorage().getSkin(event.getPlayer().getName());
        Skins.changeSkin(player, prop.getPlayerSkinProperty());
    }
}
