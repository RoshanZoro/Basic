package com.roshan.basic.commands;

import com.roshan.basic.Basic;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.PluginManager;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GodCommand implements CommandExecutor, Listener, TabCompleter {

    private final Set<Player> godModePlayers = new HashSet<>();
    private final MiniMessage mm = MiniMessage.miniMessage();

    public GodCommand() {
        // Register listener to block damage
        PluginManager pm = Basic.getInstance().getServer().getPluginManager();
        pm.registerEvents(this, Basic.getInstance());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(mm.deserialize("<#ff1100>Only players can use this command!"));
            return true;
        }

        if (!player.hasPermission("basic.god")) {
            player.sendMessage(mm.deserialize("<#ff0000>You don't have permission to use this!"));
            return true;
        }

        if (godModePlayers.contains(player)) {
            godModePlayers.remove(player);
            player.sendMessage(mm.deserialize("<#d900ff>God mode <#ffffff>disabled."));
        } else {
            godModePlayers.add(player);
            player.sendMessage(mm.deserialize("<#d900ff>God mode <#00fff7>enabled."));
        }

        return true;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player && godModePlayers.contains(player)) {
            event.setCancelled(true);
        }
    }

    // Hide from tab completion
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}
