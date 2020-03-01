package com.xj.skins.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Channels {

    public static byte[] format(String channel, String... args) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);

        out.writeUTF(channel);

        for (String arg : args) {
            if (arg != null) {
                out.writeUTF(arg);
            }
        }
        return b.toByteArray();
    }

    public static String[] parse(DataInputStream in, int length) throws IOException {
        String[] result = new String[length];
        for (int i = 0; i < length; i++) {
            result[i] = in.readUTF();
        }
        return result;
    }

    public static ChannelBuilder build(String channel) {
        return new ChannelBuilder(channel);
    }

    public static class ChannelBuilder {
        private String name;
        private Map<String, SubChannelHandler> subChannels;

        ChannelBuilder(String name) {
            this.name = name;
            this.subChannels = new HashMap<String, SubChannelHandler>();
        }

        public ChannelBuilder addSubChannel(String name, SubChannelHandler handler) {
            subChannels.put(name, handler);
            return this;
        }

        public void register(final Plugin plugin) {
            final String name = this.name;
            final Map<String, SubChannelHandler> subChannels = this.subChannels;
            Messenger messenger = Bukkit.getMessenger();
            messenger.registerOutgoingPluginChannel(plugin, name);
            messenger.registerIncomingPluginChannel(plugin, name, new PluginMessageListener() {
                public void onPluginMessageReceived(String channel, final Player player, final byte[] message) {
                    if (!channel.equals(name)) {
                        return;
                    }
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                        public void run() {
                            DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
                            try {
                                String subchannel = in.readUTF();
                                SubChannelHandler handler = subChannels.get(subchannel);
                                if (handler != null) {
                                    handler.run(player, in);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        }
    }

    public interface SubChannelHandler {
        void run(Player player, DataInputStream in) throws Exception;
    }
}
