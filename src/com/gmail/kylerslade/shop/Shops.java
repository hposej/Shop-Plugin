package com.gmail.kylerslade.shop;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.kylerslade.listeners.AsyncPlayerChat;
import com.gmail.kylerslade.listeners.InventoryClick;
import com.gmail.kylerslade.listeners.InventoryClose;
import com.gmail.kylerslade.listeners.PlayerQuit;
import com.gmail.kylerslade.profiles.LoadProfile;
import com.gmail.kylerslade.profiles.Profile;
import com.gmail.kylerslade.profiles.SaveProfile;

public class Shops extends JavaPlugin{
	
	private static Shops instance;
	private InventoryClick inventoryClick;
	private AsyncPlayerChat asyncPlayerChat;
	
	private Profile profile;
	private LoadProfile loadProfile;
	
	public static Shops getInstance(){
		return instance;
	}	
	
	@Override
	public void onLoad(){
		Shops.instance = this;
	}
	
	@Override
	public void onEnable(){			
		this.getDataFolder().mkdir();
		
		CommandExecutor commands = new Commands();
		getCommand("Shop").setExecutor(commands);
		getCommand("SetShop").setExecutor(commands);
		getCommand("RemoveShop").setExecutor(commands);
		getCommand("EditShop").setExecutor(commands);
		
		registerListeners();
		
		makeProfileInstance();
	}

	private void registerListeners() {
		PluginManager pm = Bukkit.getServer().getPluginManager();
		
		asyncPlayerChat = new AsyncPlayerChat();
		inventoryClick = new InventoryClick();
		
		pm.registerEvents(asyncPlayerChat, this);
		pm.registerEvents(inventoryClick, this);
		pm.registerEvents(new InventoryClose(), this);
		pm.registerEvents(new PlayerQuit(), this);
	}
	
	private void makeProfileInstance() {
		profile = new Profile();
		profile.createNewProfile();
		loadProfile = new LoadProfile();
		loadProfile.loadProfile();
	}
	
	public Profile getProfile(){
		return profile;
	}
	
	public LoadProfile getLoadProfile(){
		return loadProfile;
	}
	
	public AsyncPlayerChat getAsyncPlayerChat(){
		return asyncPlayerChat;
	}
	
	public InventoryClick getInventoryClick(){
		return inventoryClick;
	}

	@Override
	public void onDisable(){
		new SaveProfile().createPlayerLocations();
	}
}
