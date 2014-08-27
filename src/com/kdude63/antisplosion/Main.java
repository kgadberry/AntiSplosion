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
	Boolean bed;
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
		bed = config.getBoolean("netherbed");
		logging = config.getBoolean("logging");

		// Start metrics
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

		Boolean cancelled = false;
		String cause = "";

		if (e.getEntityType() != null){
			switch(e.getEntityType()) {
				default:
					break;
				case PRIMED_TNT:
					if (tnt) {
						e.setCancelled(true);
						cancelled = true;
						cause = "PRIMED_TNT";
					}
					break;
				case MINECART_TNT:
					if (tntcart) {
						e.setCancelled(true);
						cancelled = true;
						cause = "MINECART_TNT";
					}
					break;
				case FIREBALL:
					if (ghast) {
						e.setCancelled(true);
						cancelled = true;
						cause = "GHAST_FIREBALL";
					}
					break;
				case WITHER_SKULL:
					if (wither) {
						e.setCancelled(true);
						cancelled = true;
						cause = "WITHER_SKULL";
					}
					break;
				case CREEPER:
					if (creeper) {
						e.setCancelled(true);
						cancelled = true;
						cause = "CREEPER";
					}
					break;
				case ENDER_DRAGON:
					if (enderdragon) {
						e.setCancelled(true);
						cancelled = true;
						cause = "ENDER_DRAGON";
					}
			}
		}else{
			if (bed) {
				e.setCancelled(true);
				cancelled = true;
				cause = "BED";
			}
		}

		if (logging && cancelled) {

			Integer x = (int)e.getLocation().getX();
			Integer y = (int)e.getLocation().getY();
			Integer z = (int)e.getLocation().getZ();

			// Log the explosion and it's world/position to the console.
			logger.log(Level.INFO, "[AntiSplosion] Stopped an explosion in world '" + e.getLocation().getWorld().getName() + "'" + " at X:" + x + " Y:" + y + " Z:" + z + " caused by " + cause);
		}
	}
}
