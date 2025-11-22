package com.roshan.basic.commands;

import com.roshan.basic.Basic;
import com.roshan.basic.TpaManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpAcceptCommand implements CommandExecutor {

    private final MiniMessage mm = MiniMessage.miniMessage();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player target)) {
            sender.sendMessage(mm.deserialize("<#ff1100>Only players can use this command!"));
            return true;
        }

        TpaManager manager = Basic.getInstance().getTpaManager();

        if (!manager.hasRequest(target)) {
            target.sendMessage(mm.deserialize("<#d900ff>You have <#00fff7>no<#d900ff> pending teleport requests."));
            return true;
        }

        Player requester = manager.getRequester(target);

        if (requester == null) {
            target.sendMessage(mm.deserialize("<#ff5555>The teleport request has expired."));
            manager.removeRequest(target);
            return true;
        }

        target.sendMessage(mm.deserialize("<#00fff7>" + requester.getName() + "<#d900ff> will teleport to you in 3 seconds..."));
        requester.sendMessage(mm.deserialize("<#d900ff>Teleporting to <#00fff7>" + target.getName() + "<#d900ff> in 3 seconds..."));

        // Delay 3 seconds (60 ticks)
        Bukkit.getScheduler().runTaskLater(Basic.getInstance(), () -> {
            // Check if requester is still online
            if (!requester.isOnline()) {
                target.sendMessage(mm.deserialize("<#ff5555>The player is no longer online."));
                manager.removeRequest(target);
                return;
            }

            requester.teleport(target.getLocation());
            requester.sendMessage(mm.deserialize("<#d900ff>Teleported to <#00fff7>" + target.getName()));
            target.sendMessage(mm.deserialize("<#00fff7>" + requester.getName() + "<#d900ff> has teleported to you!"));

            manager.removeRequest(target);
        }, 60L); // 60 ticks = 3 seconds

        return true;
    }
}
