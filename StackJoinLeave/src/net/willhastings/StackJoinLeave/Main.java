package net.willhastings.StackJoinLeave;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
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
		this.getCommand("messages").setExecutor(new MCommand());
	}
	
	public void onDisable()
	{
		task.cancel();
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		Boolean bool = false;
		String join = event.getPlayer().getDisplayName();
		for(String i: pJoin)
		{
			if(i.equalsIgnoreCase(join)) bool = true;
		}
		if(!bool) pJoin.add(join);
		event.setJoinMessage(null);
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		Boolean bool = false;
		String quit = event.getPlayer().getDisplayName();
		for(String i: pJoin)
		{
			if(i.equalsIgnoreCase(quit)) bool = true;
		}
		if(!bool) pQuit.add(quit);
		event.setQuitMessage(null);
	}
}

class JoinLeaveMessage extends BukkitRunnable 
{
	String temp = "";
	
	public JoinLeaveMessage() {}
	
	public void run() 
	{	
		if(Main.pJoin.size() > 0 || Main.pQuit.size() > 0)
		{
			temp = "";
			if(Main.pJoin.size() > 0)
			{
				
				if(Main.pJoin.size() < 2) temp = ChatColor.GRAY + "In the past 3 min " + Main.pJoin.get(0) + " has joined";
				else if(Main.pJoin.size() < 3) temp = ChatColor.GRAY + "In the past 3 min " + Main.pJoin.get(0) + ", and " + Main.pJoin.get(1) + " have joined";
				else temp = ChatColor.GRAY + "In the past 3 min " + Main.pJoin.get(0) + ", " + Main.pJoin.get(1) + ", and " + (Main.pJoin.size() - 1) + " others have joined";
				Main.pJoin.clear();
			}
			else temp = ChatColor.GRAY + "In the past 3 min no one has joined";
			
			if(Main.pQuit.size() > 0)
			{
				if(Main.pQuit.size() < 2) temp += ", and " + Main.pQuit.get(0) + " has left the server!";
				else if(Main.pQuit.size() < 3) temp += ", and " + Main.pQuit.get(0) + ", and " + Main.pQuit.get(1) + " have left the server!";
				else temp += ", and " + Main.pQuit.get(0) + ", " + Main.pQuit.get(1) + ", and " + (Main.pQuit.size() - 1) + " others have left the server!";
				Main.pQuit.clear();
			}
			else temp += ", and no one has left the server!";
			
			for(Player p: Bukkit.getOnlinePlayers())
			{
				if(!MCommand.ignored.contains(p.getName())) p.sendMessage(temp);
				
			}
			
		}
	}	
}