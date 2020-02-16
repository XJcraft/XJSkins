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
        try {
            Skins.getInstance().getStorage().checkSkin(event.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        ProfileProperty prop = Skins.getInstance().getStorage().getSkin(player.getName());
        if (prop != null) {
            Skins.changeSkin(player, prop);
        }
    }
}
