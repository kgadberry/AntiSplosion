package com.kdude63.antisplosion;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.MetricsLite;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

//import java.util.List;

public class Main extends JavaPlugin implements Listener {
    Logger logger = Logger.getLogger("Minecraft");
    FileConfiguration config;

    Boolean enderdragon;
    Boolean wither;
    Boolean creeper;
    Boolean tnt;
    Boolean tntcart;
    Boolean ghast;
    Boolean logging;

    public void onEnable()	{

        getServer().getPluginManager().registerEvents(this, this);
        config = getConfig();
        if (!new File(this.getDataFolder().getPath() + File.separatorChar + "config.yml").exists())
            saveDefaultConfig();

        enderdragon = config.getBoolean("enderdragon");
        wither = config.getBoolean("wither");
        creeper = config.getBoolean("creeper");
        tnt = config.getBoolean("tnt");
        tntcart = config.getBoolean("tntcart");
        ghast = config.getBoolean("ghast");
        logging = config.getBoolean("logging");

        try {
            MetricsLite metrics = new MetricsLite(this);
            metrics.start();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        // Cancels the explosion based on configuration parameters. Still damages entities, but not blocks.

        switch(e.getEntityType()) {
            default:
                break;
            case PRIMED_TNT:
                if (tnt)
                    e.setCancelled(true);
                break;
            case MINECART_TNT:
                if (tntcart)
                    e.setCancelled(true);
                break;
            case FIREBALL:
                if (ghast)
                    e.setCancelled(true);
                break;
            case WITHER_SKULL:
                if (wither)
                    e.setCancelled(true);
                break;
            case CREEPER:
                if (creeper)
                    e.setCancelled(true);
                break;
            case ENDER_DRAGON:
                if (enderdragon)
                    e.setCancelled(true);
        }

        if (logging) {

            Integer x = (int)e.getLocation().getX();
            Integer y = (int)e.getLocation().getY();
            Integer z = (int)e.getLocation().getZ();

            // Log the explosion and it's world/position to the console.
            logger.log(Level.INFO, "AntiSplosion stopped an explosion in world '" + e.getLocation().getWorld().getName() + "'");
            logger.log(Level.INFO, "Coordinates of cancelled explosion are X:" + x + " Y:" + y + " Z:" + z);
            logger.log(Level.INFO, "Explosion was caused by " + e.getEntityType());
        }
    }
}
