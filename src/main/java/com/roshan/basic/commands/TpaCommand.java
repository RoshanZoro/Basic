package com.roshan.basic.commands;

import com.roshan.basic.Basic;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class TpaCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        MiniMessage mm = MiniMessage.miniMessage();

        if (!(sender instanceof Player player)) {
            sender.sendMessage(mm.deserialize("<#ff0000>Only players can use this command!"));
            return true;
        }

        if (!player.hasPermission("basic.tpa")) {
            player.sendMessage(mm.deserialize("<#ff0000>You don't have permission to use this command!"));
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(mm.deserialize("<#d900ff>Usage: <#ffffff>/tpa <player>"));
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null || !target.isOnline()) {
            player.sendMessage(mm.deserialize("<#ff5555>Player not found."));
            return true;
        }

        Basic.getInstance().getTpaManager().addRequest(player, target);

        player.sendMessage(mm.deserialize("<#d900ff>Teleport request sent to <#00fff7>" + target.getName()));
        target.sendMessage(mm.deserialize("<#00fff7>" + player.getName() + "<#d900ff> wants to teleport to you! Accept with /tpaccept or deny with /tpdeny."));

        return true;
    }
}
