package com.xj.skins.bukkit;

import com.xj.skins.ProfileProperty;
import com.xj.skins.SkinProfile;
import com.xj.skins.util.Mojang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class Commands implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        try {
            ProfileProperty prop = Mojang.getSkinProperty(args[0]);
            Skins.getInstance().getStorage().setSkin(commandSender.getName(), new SkinProfile(prop));
            Skins.changeSkin((Player) commandSender, prop);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
