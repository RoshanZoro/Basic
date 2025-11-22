package com.roshan.basic;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class HomeManager {

    private final File homesFile;
    private final FileConfiguration homesConfig;

    public HomeManager() {
        // Create plugin folder + homes.yml
        homesFile = new File(Basic.getInstance().getDataFolder(), "homes.yml");
        if (!homesFile.exists()) {
            homesFile.getParentFile().mkdirs();
            try {
                homesFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        homesConfig = YamlConfiguration.loadConfiguration(homesFile);
    }

    // Set home for a player
    public void setHome(Player player, Location location) {
        String path = player.getUniqueId().toString();
        homesConfig.set(path + ".world", location.getWorld().getName());
        homesConfig.set(path + ".x", location.getX());
        homesConfig.set(path + ".y", location.getY());
        homesConfig.set(path + ".z", location.getZ());
        homesConfig.set(path + ".yaw", location.getYaw());
        homesConfig.set(path + ".pitch", location.getPitch());

        save();
    }

    // Get home location for a player
    public Location getHome(Player player) {
        String path = player.getUniqueId().toString();
        if (!homesConfig.contains(path)) return null;

        String world = homesConfig.getString(path + ".world");
        double x = homesConfig.getDouble(path + ".x");
        double y = homesConfig.getDouble(path + ".y");
        double z = homesConfig.getDouble(path + ".z");
        float yaw = (float) homesConfig.getDouble(path + ".yaw");
        float pitch = (float) homesConfig.getDouble(path + ".pitch");

        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

    // Check if a player has a home
    public boolean hasHome(Player player) {
        return homesConfig.contains(player.getUniqueId().toString());
    }

    // Save the file
    private void save() {
        try {
            homesConfig.save(homesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
