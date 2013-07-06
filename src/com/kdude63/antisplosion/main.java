package com.kdude63.antisplosion;

import java.io.File;
//import java.util.List;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener
{
	Logger log = Logger.getLogger("Minecraft");
	FileConfiguration config;
	
	Boolean enderdragon;
    Boolean wither;
    Boolean creeper;
    Boolean tnt;
    Boolean ghast;
    Boolean logging;
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		config = getConfig();
        if (!new File(this.getDataFolder().getPath() + File.separatorChar + "config.yml").exists())
            saveDefaultConfig();
        enderdragon = config.getBoolean("enderdragon");
        wither = config.getBoolean("wither");
        creeper = config.getBoolean("creeper");
        tnt = config.getBoolean("tnt");
        ghast = config.getBoolean("ghast");
        logging = config.getBoolean("logging");
	}
	
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent e)
	{
		// Cancels the explosion based on configuration parameters. Still damages entities, but not blocks.
		
		switch(e.getEntityType()) {
			default:
				break;
			case PRIMED_TNT:
				if (tnt)
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
			// Have to put these here since I can't perform toString().substring() on a primitive.
			Double x = e.getLocation().getX();
			Double y = e.getLocation().getY();
			Double z = e.getLocation().getZ();
			
			// Log the explosion and it's world/position to the console.
			System.out.println("AntiSplosion stopped an explosion in world '" + e.getLocation().getWorld().getName() + "'");
			System.out.println("Coordinates of cancelled explosion are X|" + x.toString().substring(0, 2) + " Y|" + y.toString().substring(0, 2) + " Z|" + z.toString().substring(0, 2));
			System.out.println("Explosion was caused by " + e.getEntityType());
		}
	}
}