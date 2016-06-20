package com.aol.hposejhero.shop;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.aol.hposejhero.gui.EditWindow;
import com.aol.hposejhero.gui.ShopWindow;

import net.md_5.bungee.api.ChatColor;

public class Commands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		//Ensure that the sender is indeed a player.
		if (!(sender instanceof Player)){
			return false;
		}	

		Player player = (Player) sender;
		UUID playerUUID = player.getUniqueId();

		//Shows a GUI of player shops.
		if (cmd.getName().equalsIgnoreCase("Shop")) {	
			new ShopWindow().shopGUI(player);
		}

		//Sets your shop.
		if (cmd.getName().equalsIgnoreCase("SetShop")) {
			if (args.length == 0){
				Location location = player.getLocation();

				player.sendMessage(ChatColor.AQUA + "You have set your shop.");

				ShopWindow shopWindow = new ShopWindow();

				shopWindow.setShopLocations(playerUUID, location);	
				shopWindow.setShopNames(playerUUID, player.getName());
				shopWindow.setShopDescriptions(playerUUID, " ");	
			}
		}

		//Allows the user to edit their shop.
		if (cmd.getName().equalsIgnoreCase("EditShop")){
			new EditWindow().editGUI(player);
		}

		//Removes your shop or another player's shop.
		if (cmd.getName().equalsIgnoreCase("RemoveShop")) {
			if (args.length == 0){
				player.sendMessage(ChatColor.AQUA + "You have removed yourself from shops.");

				ShopWindow shopWindow = new ShopWindow(); 
				shopWindow.removeShopLocations(playerUUID);
				shopWindow.removeShopNames(playerUUID);
				shopWindow.removeShopDescriptions(playerUUID);
			} else if (args.length == 1){
				//Permission node is required for removing someone else's shop.
				if (player.hasPermission("shops.remove.players")) {

					ShopWindow shopWindow = new ShopWindow(); 
					HashMap<UUID, Location> shopLocations = shopWindow.getShopLocations();									
					
					for (Iterator<UUID> iterator = shopLocations.keySet().iterator(); iterator.hasNext();){
						UUID playerUUIDs = iterator.next();	

						OfflinePlayer offlinePlayers = Bukkit.getOfflinePlayer(playerUUIDs);	
						String offlinePlayerName = offlinePlayers.getName();

						if(offlinePlayerName.equals(args[0])){
							player.sendMessage(ChatColor.AQUA + "You have removed " + args[0] + " from the shops.");

							shopWindow.removeShopLocations(playerUUIDs);
							shopWindow.removeShopNames(playerUUIDs);
							shopWindow.removeShopDescriptions(playerUUIDs);
						}
					}
				}
			}
		}
		return false;
	}
}
