package com.gmail.kylerslade.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import com.gmail.kylerslade.shop.Shops;

public class InventoryClose implements Listener{
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event){
		Player player = (Player) event.getPlayer();
		Shops.getInstance().getInventoryClick().removeCannotMove(player);
		Shops.getInstance().getInventoryClick().removeEditShop(player);
		Shops.getInstance().getInventoryClick().removeOpenedShopWindow(player);
	}
}
