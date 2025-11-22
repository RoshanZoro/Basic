package com.roshan.basic;

import com.roshan.basic.commands.*;
import org.bukkit.plugin.java.JavaPlugin;

public class Basic extends JavaPlugin {

    private static Basic instance;
    private TpaManager tpaManager;
    private HomeManager homeManager;

    @Override
    public void onEnable() {
        instance = this;
        tpaManager = new TpaManager();
        homeManager = new HomeManager(); // Persistent homes

        getLogger().info("Basic plugin enabled.");

        getCommand("god").setExecutor(new GodCommand());
        getCommand("tpa").setExecutor(new TpaCommand());
        getCommand("tpaccept").setExecutor(new TpAcceptCommand());
        getCommand("tpdeny").setExecutor(new TpDenyCommand());

        getCommand("sethome").setExecutor(new SetHomeCommand(homeManager));
        getCommand("home").setExecutor(new HomeCommand(homeManager));
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

    public HomeManager getHomeManager() {
        return homeManager;
    }
}
