package net.willhastings.StackJoinLeave;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Main extends JavaPlugin implements Listener
{
	public static ArrayList<String> pJoin = new ArrayList<String>(), 
			pQuit = new ArrayList<String>();
	
	private BukkitTask task;
	
	public void onEnable()
	{
		this.getServer().getPluginManager().registerEvents(this, this);
		task = new JoinLeaveMessage().runTaskTimer(this, 20*60*3, 20*60*3);
	}
	
	public void onDisable()
	{
		task.cancel();
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		pJoin.add(event.getPlayer().getName());
		event.setJoinMessage(null);
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		pQuit.add(event.getPlayer().getName());
		event.setQuitMessage(null);
	}
}

class JoinLeaveMessage extends BukkitRunnable 
{
	String temp = "";
	
	public JoinLeaveMessage() {}
	
	public void run() 
	{	
		if(Main.pJoin.size() > 0)
		{
			temp = "";
			if(Main.pJoin.size() < 2)
			{
				Bukkit.broadcastMessage(ChatColor.GRAY + "In the past 3 min " + Main.pJoin.get(0) + " has joined the server!");
			}
			else if(Main.pJoin.size() < 3)
			{
				Bukkit.broadcastMessage(ChatColor.GRAY + "In the past 3 min " + Main.pJoin.get(0) + ", and " + Main.pJoin.get(1) + " have joined the server!");
			}
			else
			{
				Bukkit.broadcastMessage(ChatColor.GRAY + "In the past 3 min " + Main.pJoin.get(0) + ", " + Main.pJoin.get(1) + ", and " + (Main.pJoin.size() - 1) 
						+ " others have joined the sever!");
			}
			Main.pJoin.clear();
		}
		
		if(Main.pQuit.size() > 0)
		{
			temp = "";
			if(Main.pQuit.size() < 2)
			{
				Bukkit.broadcastMessage(ChatColor.GRAY + "In the past 3 min " + Main.pQuit.get(0) + " has left the server!");
			}
			else if(Main.pQuit.size() < 3)
			{
				Bukkit.broadcastMessage(ChatColor.GRAY + "In the past 3 min " + Main.pQuit.get(0) + ", and " + Main.pQuit.get(1) + " have left the server!");
			}
			else
			{
				Bukkit.broadcastMessage(ChatColor.GRAY + "In the past 3 min " + Main.pQuit.get(0) + ", " + Main.pQuit.get(1) + ", and " + (Main.pQuit.size() - 1) 
						+ " others have left the sever!");
			}
			Main.pQuit.clear();
		}
	}
	
}