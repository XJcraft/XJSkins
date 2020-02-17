package com.xj.skins.bungee;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;

import java.io.IOException;

public class Commands extends Command {

    public Commands() {
        super("skin");
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("change")) {
                onChangeCommand(sender, args);
            }
        }
    }

    public boolean onChangeCommand(final CommandSender player, final String[] args) {
        if (args.length == 2) {
            player.sendMessage(ChatColor.AQUA + "正在修改皮肤...");
            final Skins instance = com.xj.skins.bungee.Skins.getInstance();
            ProxyServer.getInstance().getScheduler().runAsync(instance, new Runnable() {
                public void run() {
                    try {
                        instance.getStorage().setSkin(player.getName(), args[1]);
                        player.sendMessage(ChatColor.AQUA + "皮肤修改成功，重新登录后生效");
                    } catch (IOException e) {
                        player.sendMessage(ChatColor.RED + "皮肤修改失败，原因： " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            });
            return true;

        }
        return false;
    }

}
