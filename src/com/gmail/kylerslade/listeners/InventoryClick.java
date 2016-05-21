package com.gmail.kylerslade.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.gmail.kylerslade.gui.ShopWindow;
import com.gmail.kylerslade.shop.Shops;

import net.md_5.bungee.api.ChatColor;

public class InventoryClick implements Listener{

	private ArrayList<Player> cannotMove = new ArrayList<Player>();
	private ArrayList<Player> editShop = new ArrayList<Player>();
	private ArrayList<Player> openedShopWindow = new ArrayList<Player>();

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){

		Player player = (Player) event.getWhoClicked();

		if(cannotMove.contains(player)){
			event.setCancelled(true);

			HashMap<UUID, Location> shopLocations = new ShopWindow().getShopLocations();

			int clickedSlot = event.getSlot();

			//The item clicked on.
			Inventory clickedInventory = event.getClickedInventory();

			//For teleporting to another shop.
			if(openedShopWindow.contains(player)){
				if(clickedInventory != null) {	
					if (event.getClickedInventory().getItem(clickedSlot) != null) {	
						ItemStack headClicked = event.getClickedInventory().getItem(clickedSlot);

						if (headClicked != null){
							if (headClicked.hasItemMeta()){
								if(headClicked.getType().equals(Material.SKULL_ITEM)){
									SkullMeta clickedSkullMeta = (SkullMeta) headClicked.getItemMeta();

									ItemStack typeSkull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3); 

									//Loop through all the keys in the map and check if
									//the owner of the item clicked on equals the one of the items in the map.
									for(Iterator<UUID> iterator = shopLocations.keySet().iterator(); iterator.hasNext();){
										UUID playerUUIDs = iterator.next();
										
										//Good to go.
										OfflinePlayer players = Bukkit.getOfflinePlayer(playerUUIDs);

										SkullMeta skullMeta = (SkullMeta) typeSkull.getItemMeta();
										skullMeta.setOwner(players.getName());
										typeSkull.setItemMeta(skullMeta);

										if (clickedSkullMeta.getOwner().equals(skullMeta.getOwner())){

											//Get the location for the shop that the player clicked on.
											Location location = shopLocations.get(playerUUIDs);
											player.teleport(location);
											player.closeInventory();
											//Allow the user to move items again.
											Shops.getInstance().getInventoryClick().removeCannotMove(player);
										}
									}
								}
							}
						}
					}
				}//For editing the shop.
			}else if (editShop.contains(player)){
				if(clickedInventory != null) {	
					ItemStack clickedButton = event.getClickedInventory().getItem(clickedSlot);
	
					if (clickedButton != null){
						if (clickedButton.hasItemMeta()){
							if (clickedButton.getItemMeta().getDisplayName() != null){
								if (clickedButton.getItemMeta().getDisplayName().equals(ChatColor.RED + "Exit editing.")){
									//Exit the edit window.
									player.closeInventory();
									editShop.remove(player.getUniqueId());
								}else if (clickedButton.getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Edit shop name.")){
									//Have the user change his shop name.									
									player.closeInventory();
									
									player.sendMessage(ChatColor.GREEN + "Please enter your shop's name.");
									
									Shops.getInstance().getAsyncPlayerChat().addEditName(player);
								}else if (clickedButton.getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Edit shop description")){
									//Have the user change the description of his shop.						
									player.closeInventory();
									
									player.sendMessage(ChatColor.GREEN + "Please enter your shop's description.");
									
									Shops.getInstance().getAsyncPlayerChat().addEditDescription(player);
								}
							}
						}
					}
				}
			}
		}else{
			event.setCancelled(false);
		}
	}

	public void addCannotMove(Player player){
		cannotMove.add(player);
	}

	public void removeCannotMove(Player player){
		cannotMove.remove(player);
	}

	public void addEditShop(Player player){
		editShop.add(player);
	}

	public void removeEditShop(Player player){
		editShop.remove(player);
	}

	public void addOpenedShopWindow(Player player){
		openedShopWindow.add(player);
	}

	public void removeOpenedShopWindow(Player player){
		openedShopWindow.remove(player);
	}
}
