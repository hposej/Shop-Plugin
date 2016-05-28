package com.gmail.kylerslade.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.gmail.kylerslade.shop.Shops;

public class ShopWindow {
	
	private final int ROW = 9;
	private static HashMap<UUID, Location> shopLocations = new HashMap<UUID, Location>();
	private static HashMap<UUID, String> shopNames = new HashMap<UUID, String>();
	private static HashMap<UUID, String> shopDescriptions = new HashMap<UUID, String>();
	
	public void shopGUI(Player player){
		//Making an inventory shop for the player.
		Inventory shopWindow = Bukkit.createInventory(player, ROW * 6, "shopList");
		player.closeInventory();
		Shops.getInstance().getInventoryClick().addCannotMove(player);
		
		//Allows access to the shop Window.
		Shops.getInstance().getInventoryClick().addOpenedShopWindow(player);
		
		//Creating skulls for the inventory.
		for(Iterator<UUID> iterator = shopLocations.keySet().iterator(); iterator.hasNext();){
			UUID playerUUIDs = iterator.next();
			
			//TODO: Add a next page button.
			
			ItemStack skulls = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
			SkullMeta skullsMeta = (SkullMeta) skulls.getItemMeta();
			
			OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerUUIDs);
			String offlinePlayerName = offlinePlayer.getName();
			
			//Give the skulls an owner to display the head.
			skullsMeta.setOwner(offlinePlayerName);
			//Set the shop display name.
			if (shopNames.containsKey(playerUUIDs)){
				skullsMeta.setDisplayName(shopNames.get(playerUUIDs));
			}
			
			//Add shop description.
			ArrayList<String> lore = new ArrayList<String>();
			if (shopDescriptions.containsKey(playerUUIDs)){
				lore.add(shopDescriptions.get(playerUUIDs));
				skullsMeta.setLore(lore);
			}
			
			skulls.setItemMeta(skullsMeta);
			//Add the skulls to the shop GUI.
			shopWindow.addItem(skulls);
 		}
		
		//Open the inventory.
		player.openInventory(shopWindow);	
	}
	
	/*
	 * Below: Setters and getters.
	 */
	
	public void setShopLocations(UUID playerUUID, Location location){	
		if (shopLocations.containsKey(playerUUID)){
			shopLocations.remove(playerUUID);
		}	
		shopLocations.put(playerUUID, location);
	}
	
	public void removeShopLocations(UUID playerUUID){
		if(shopLocations.containsKey(playerUUID)){
			shopLocations.remove(playerUUID);
		}
	}
	
	public void setShopNames(UUID playerUUID, String name){
		if (shopNames.containsKey(playerUUID)){
			shopNames.remove(playerUUID);
		}
		shopNames.put(playerUUID, name);
	}
	
	public void removeShopNames(UUID playerUUID){
		if(shopNames.containsKey(playerUUID)){
			shopNames.remove(playerUUID);
		}
	}
	
	public void setShopDescriptions(UUID playerUUID, String lores){		
		if (shopDescriptions.containsValue(playerUUID)){
			shopDescriptions.remove(playerUUID);
		}
		shopDescriptions.put(playerUUID, lores);
	}
	
	public void removeShopDescriptions(UUID playerUUID){
		if(shopDescriptions.containsKey(playerUUID)){
			shopDescriptions.remove(playerUUID);
		}
	}
	
	public HashMap<UUID, Location> getShopLocations(){
		return shopLocations;
	}
	
	public HashMap<UUID, String> getShopNames(){
		return shopNames;
	}
	
	public HashMap<UUID, String> getShopDescriptions(){
		return shopDescriptions;
	}
}
