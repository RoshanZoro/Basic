package com.roshan.basic.commands;

import com.roshan.basic.Basic;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class HomeCommand implements CommandExecutor {

    private final MiniMessage mm = MiniMessage.miniMessage();
    private final Map<UUID, Location> homes;

    public HomeCommand(Map<UUID, Location> homes) {
        this.homes = homes;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(mm.deserialize("<#ff1100>Only players can use this command!"));
            return true;
        }

        UUID uuid = player.getUniqueId();
        if (!homes.containsKey(uuid)) {
            player.sendMessage(mm.deserialize("<#ff5555>You have not set a home yet! Use /sethome first."));
            return true;
        }

        Location homeLocation = homes.get(uuid);

        // Delay 3 seconds (60 ticks)
        player.sendMessage(mm.deserialize("<#00fff7>Teleporting to your home in 3 seconds..."));
        Bukkit.getScheduler().runTaskLater(Basic.getInstance(), () -> {
            if (!player.isOnline()) return;
            player.teleport(homeLocation);
            player.sendMessage(mm.deserialize("<#00fff7>Teleported to your home!"));
        }, 60L); // 60 ticks = 3 seconds

        return true;
    }
}
