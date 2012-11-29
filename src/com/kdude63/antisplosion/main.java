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
	}
}