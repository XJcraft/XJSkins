package com.xj.skins.bukkit;

import com.xj.skins.ProfileProperty;
import com.xj.skins.SkinProfile;
import com.xj.skins.util.Mojang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class Commands implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                if ("change".equals(args[0].toLowerCase())) {
                    return onChangeCommand(player, args);
                } else if ("revert".equals(args[0].toLowerCase())) {
                    return onRevertCommand(player, args);
                }
            }
        }
        return false;
    }

    public boolean onChangeCommand(final Player player, final String[] args) {
        if (!player.hasPermission("skin.command")) {
            player.sendMessage("You don't have permission to do this");
            return true;
        }
        if (args.length == 2) {
            player.sendMessage(ChatColor.AQUA + "正在修改皮肤...");
            final Skins instance = Skins.getInstance();
            Bukkit.getScheduler().runTaskAsynchronously(instance, new Runnable() {
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

    public boolean onRevertCommand(final Player player, final String[] args) {
        if (args.length == 1) {
            player.sendMessage(ChatColor.AQUA + "正在删除皮肤...");
            final Skins instance = Skins.getInstance();
            Bukkit.getScheduler().runTaskAsynchronously(instance, new Runnable() {
                public void run() {
                    instance.getStorage().removeSkin(player.getName());
                    player.sendMessage(ChatColor.AQUA + "皮肤删除成功，重新登录后生效");
                }
            });
            return true;
        }
        return false;
    }
}
