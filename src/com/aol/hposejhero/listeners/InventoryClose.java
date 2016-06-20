package com.aol.hposejhero.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import com.aol.hposejhero.shop.Shops;

public class InventoryClose implements Listener{
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event){
		Player player = (Player) event.getPlayer();
		Shops.getInstance().getInventoryClick().removeCannotMove(player);
		Shops.getInstance().getInventoryClick().removeEditShop(player);
		Shops.getInstance().getInventoryClick().removeOpenedShopWindow(player);
	}
}
