package com.roshan.basic.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class SetHomeCommand implements CommandExecutor {

    private final MiniMessage mm = MiniMessage.miniMessage();
    private final Map<UUID, Location> homes;

    public SetHomeCommand(Map<UUID, Location> homes) {
        this.homes = homes;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(mm.deserialize("<#ff1100>Only players can use this command!"));
            return true;
        }

        // Store player's current location as home
        Location loc = player.getLocation();
        homes.put(player.getUniqueId(), loc);

        // Feedback message with coordinates
        player.sendMessage(mm.deserialize(
                "<#00fff7>Home set at <#d900ff>World: " + loc.getWorld().getName() +
                        " X: " + (int) loc.getX() +
                        " Y: " + (int) loc.getY() +
                        " Z: " + (int) loc.getZ() +
                        "<#00fff7>! Use /home to teleport back."
        ));

        return true;
    }
}
