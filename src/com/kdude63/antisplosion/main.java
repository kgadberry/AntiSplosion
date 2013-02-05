package com.kdude63.antisplosion;

import java.util.logging.Logger;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener
{
	Logger log = Logger.getLogger("Minecraft");
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent e)
	{
		e.setCancelled(true);
		
		Double x = e.getLocation().getX();
		Double y = e.getLocation().getY();
		Double z = e.getLocation().getZ();
		
		System.out.println("AntiSplosion stopped an explosion in world '" + e.getLocation().getWorld().getName() + "'");
		System.out.println("Coordinates of cancelled explosion are X|" + x.toString().substring(0, 2) + " Y|" + y.toString().substring(0, 2) + " Z|" + z.toString().substring(0, 2));
	}
}