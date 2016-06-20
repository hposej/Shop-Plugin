package com.aol.hposejhero.listeners;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.aol.hposejhero.gui.ShopWindow;

import net.md_5.bungee.api.ChatColor;

public class AsyncPlayerChat implements Listener{
	
	private ArrayList<Player> editName = new ArrayList<Player>();
	private ArrayList<Player> editDescription = new ArrayList<Player>();
	
	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event){		
		
		Player player = event.getPlayer();
		UUID playerUUID = player.getUniqueId();
		
		//Editing the player shop name.
		if (editName.contains(player)){
			
			String command = event.getMessage();
						
			if (command.length() > 20){
				player.sendMessage(ChatColor.RED + "That name is too long");
				event.setCancelled(true);
				return;
			}
			
			player.sendMessage(ChatColor.AQUA + "You have your shop's name.");
			
			new ShopWindow().setShopNames(playerUUID, command);
			event.setCancelled(true);
			
			editName.remove(player);
		//Editing the player shop description.
		}else if (editDescription.contains(player)){
			
			String command = event.getMessage();
			
			if (command.length() > 25){
				player.sendMessage(ChatColor.RED + "That description is too long");
				event.setCancelled(true);
				return;
			}
			
			player.sendMessage(ChatColor.AQUA + "You have your shop's description.");
			
			new ShopWindow().setShopDescriptions(playerUUID, command);
			event.setCancelled(true);
			
			editDescription.remove(player);
		}
	}
	
	public void addEditName(Player player){
		editName.add(player);
	}

	public void removeEditName(Player player){
		editName.remove(player);
	}
	
	public void addEditDescription(Player player){
		editDescription.add(player);
	}

	public void removeEditDescription(Player player){
		editDescription.remove(player);
	}
}
