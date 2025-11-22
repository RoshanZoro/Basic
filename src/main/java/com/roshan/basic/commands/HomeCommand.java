package com.roshan.basic.commands;

import com.roshan.basic.Basic;
import com.roshan.basic.HomeManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommand implements CommandExecutor {

    private final MiniMessage mm = MiniMessage.miniMessage();
    private final HomeManager homeManager;

    public HomeCommand(HomeManager homeManager) {
        this.homeManager = homeManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(mm.deserialize("<#ff1100>Only players can use this command!"));
            return true;
        }

        if (!homeManager.hasHome(player)) {
            player.sendMessage(mm.deserialize("<#ff5555>You have not set a home yet! Use /sethome first."));
            return true;
        }

        Location home = homeManager.getHome(player);

        // 3-second teleport delay
        player.sendMessage(mm.deserialize("<#00fff7>Teleporting to your home in 3 seconds..."));
        Bukkit.getScheduler().runTaskLater(Basic.getInstance(), () -> {
            if (!player.isOnline()) return;
            player.teleport(home);
            player.sendMessage(mm.deserialize("<#00fff7>Teleported to your home!"));
        }, 60L);

        return true;
    }
}
