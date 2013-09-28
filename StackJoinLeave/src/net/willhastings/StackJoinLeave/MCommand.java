package net.willhastings.StackJoinLeave;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class MCommand implements CommandExecutor 
{	
	public static ArrayList<String> ignored = new ArrayList<String>();
	
	private void invalidCommand(CommandSender sender)
	{
		sender.sendMessage(ChatColor.RED + "Invalid or Missing Sub-Command!");
		sender.sendMessage("/stackjoinleave toggle");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) 
	{
		if(sender instanceof Player)
		{
			if(args.length > 0 && args[0].equalsIgnoreCase("Toggle"))
			{
				if(ignored.contains(sender.getName()))
				{
					ignored.remove(sender.getName());
					sender.sendMessage("You will now see the stacked join leave messages!");
				}
				else
				{
					ignored.add(sender.getName());
					sender.sendMessage("You will no longer see the stacked join leave messages!");
				}
			}
			else this.invalidCommand(sender);
			return true;
		}
		return false;
	}
}