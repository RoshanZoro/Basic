package com.roshan.basic;

import com.roshan.basic.commands.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Location;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Basic extends JavaPlugin {
    private final Map<UUID, Location> homes = new HashMap<>();
    private static Basic instance;
    private TpaManager tpaManager;

    @Override
    public void onEnable() {
        instance = this;
        tpaManager = new TpaManager();


        getLogger().info("Basic plugin enabled.");

        getCommand("god").setExecutor(new GodCommand());
        getCommand("tpa").setExecutor(new TpaCommand());
        getCommand("tpaccept").setExecutor(new TpAcceptCommand());
        getCommand("tpdeny").setExecutor(new TpDenyCommand());

        getCommand("sethome").setExecutor(new SetHomeCommand(homes));
        getCommand("home").setExecutor(new HomeCommand(homes));
    }

    @Override
    public void onDisable() {
        getLogger().info("Basic plugin disabled!");
    }

    public static Basic getInstance() {
        return instance;
    }

    public TpaManager getTpaManager() {
        return tpaManager;
    }
}
