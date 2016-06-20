package com.aol.hposejhero.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.aol.hposejhero.shop.Shops;

public class PlayerQuit implements Listener{
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event){
		Player player = (Player) event.getPlayer();
		Shops.getInstance().getInventoryClick().removeCannotMove(player);
		Shops.getInstance().getInventoryClick().removeEditShop(player);
		Shops.getInstance().getInventoryClick().removeOpenedShopWindow(player);
		Shops.getInstance().getAsyncPlayerChat().removeEditName(player);
		Shops.getInstance().getAsyncPlayerChat().removeEditDescription(player);
	}
}
