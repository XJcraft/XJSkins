package com.xj.skins.bungee;

import com.xj.skins.ProfileProperty;
import com.xj.skins.util.Channels;
import com.xj.skins.util.Reflections;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.connection.LoginResult;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import net.md_5.bungee.protocol.Property;


public class LoginListener implements Listener {


    @EventHandler(priority = EventPriority.HIGH)
    public void onPreLogin(final LoginEvent event) throws Exception {
        final PendingConnection connection = event.getConnection();
        final String nickName = connection.getName();
        final Skins instance = Skins.getInstance();
        event.registerIntent(instance);
        instance.getProxy().getScheduler().runAsync(instance, new Runnable() {
            public void run() {
                try {
                    LoginResult loginProfile = (LoginResult) Reflections.getValue(connection, "loginProfile");
                    if (loginProfile == null) {
                        loginProfile = new LoginResult(null, null, null);
                    }
                    ProfileProperty property = instance.getStorage().checkSkin(nickName);
                    if (property == null) {
                        loginProfile.setProperties(null);
                    } else {
                        Property textures = new Property("textures", property.getValue(), property.getSignature());
                        loginProfile.setProperties(new Property[]{textures});
                        instance.getLogger().info(String.format("Inject textures for %s", nickName));
                    }
                    Reflections.setValue(connection, "loginProfile", loginProfile);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    event.completeIntent(instance);
                }
            }
        });
    }

    private void sendUpdateRequest(ProxiedPlayer p, Object textures) {
        if (p == null)
            return;

        if (p.getServer() == null)
            return;

        if (textures == null)
            return;

        try {

            p.getServer().sendData("skins:skinchange", Channels.format(
                    "SkinUpdate",
                    (String) Reflections.getValue(textures, "name"),
                    (String) Reflections.getValue(textures, "value"),
                    (String) Reflections.getValue(textures, "signature")
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
