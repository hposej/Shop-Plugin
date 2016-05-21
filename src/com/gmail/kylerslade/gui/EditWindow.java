package com.gmail.kylerslade.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.kylerslade.shop.Shops;

import net.md_5.bungee.api.ChatColor;

public class EditWindow {
	
	private final int ROW = 9;
	
	public void editGUI(Player player){
		//Making an inventory edit for the player.
		Inventory shopWindow = Bukkit.createInventory(player, ROW * 1, "Edit Shop");
		player.closeInventory();
		Shops.getInstance().getInventoryClick().addCannotMove(player);
		
		//Allows access to editing.
		Shops.getInstance().getInventoryClick().addEditShop(player);
		
		//Create buttons for editing.
		ItemStack exitButton = new ItemStack(Material.REDSTONE);
		ItemStack nameButton = new ItemStack(Material.SIGN);
		ItemStack descriptionButton = new ItemStack(Material.NAME_TAG);
		
		//Grab the buttons's meta.
		ItemMeta exitButtonMeta = exitButton.getItemMeta();
		ItemMeta nameButtonMeta = nameButton.getItemMeta();
		ItemMeta descriptionButtonMeta = descriptionButton.getItemMeta();
		
		//Edit the buttons's display name.
		exitButtonMeta.setDisplayName(ChatColor.RED + "Exit editing.");
		nameButtonMeta.setDisplayName(ChatColor.YELLOW + "Edit shop name.");
		descriptionButtonMeta.setDisplayName(ChatColor.YELLOW + "Edit shop description");
		
		//Set the button's meta.
		exitButton.setItemMeta(exitButtonMeta);
		nameButton.setItemMeta(nameButtonMeta);
		descriptionButton.setItemMeta(descriptionButtonMeta);
		
		//Add the buttons to the edit GUI.
		shopWindow.setItem(8, exitButton);
		shopWindow.setItem(3, nameButton);
		shopWindow.setItem(4, descriptionButton);
		
		player.openInventory(shopWindow);
	}
}
