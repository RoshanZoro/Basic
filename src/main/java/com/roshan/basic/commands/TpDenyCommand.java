package com.roshan.basic.commands;

import com.roshan.basic.Basic;
import com.roshan.basic.TpaManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpDenyCommand implements CommandExecutor {

    MiniMessage mm = MiniMessage.miniMessage();

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

        requester.sendMessage(mm.deserialize("<#00fff7>" + target.getName() + "<#d900ff> has denied your teleport request."));
        target.sendMessage(mm.deserialize("<#d900ff>You denied the teleport request from <#ffffff>" + requester.getName() + "."));

        manager.removeRequest(target);
        return true;
    }
}
