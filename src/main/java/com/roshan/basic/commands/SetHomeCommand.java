package com.roshan.basic.commands;

import com.roshan.basic.Basic;
import com.roshan.basic.HomeManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHomeCommand implements CommandExecutor {

    private final MiniMessage mm = MiniMessage.miniMessage();
    private final HomeManager homeManager;

    public SetHomeCommand(HomeManager homeManager) {
        this.homeManager = homeManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(mm.deserialize("<#ff1100>Only players can use this command!"));
            return true;
        }

        homeManager.setHome(player, player.getLocation());
        player.sendMessage(mm.deserialize("<#00fff7>Home set at your current location!"));
        return true;
    }
}
