package com.aol.hposejhero.shop;

/*-----------------------------------------------------------------*\
| Name: Shops													    |
| Functionality: The plugin allows access to setting up shops and   |
| teleporting to them. Includes editing of shops.					|
| Version: Running on 1.9.2 +										|	
 \*----------------------------------------------------------------*/

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.aol.hposejhero.listeners.AsyncPlayerChat;
import com.aol.hposejhero.listeners.InventoryClick;
import com.aol.hposejhero.listeners.InventoryClose;
import com.aol.hposejhero.listeners.PlayerQuit;
import com.aol.hposejhero.profiles.LoadProfile;
import com.aol.hposejhero.profiles.Profile;
import com.aol.hposejhero.profiles.SaveProfile;

public class Shops extends JavaPlugin{
	
	private static Shops instance;
	private InventoryClick inventoryClick;
	private AsyncPlayerChat asyncPlayerChat;
	
	private Profile profile;
	private LoadProfile loadProfile;
	
	//Singleton
	public static Shops getInstance(){
		return instance;
	}	
	
	@Override
	public void onLoad(){
		Shops.instance = this;
	}
	
	@Override
	public void onEnable(){			
		//Generates the data folder for the plugin.
		this.getDataFolder().mkdir();
		
		//Allows for the execute of commands.
		CommandExecutor commands = new Commands();
		getCommand("Shop").setExecutor(commands);
		getCommand("SetShop").setExecutor(commands);
		getCommand("RemoveShop").setExecutor(commands);
		getCommand("EditShop").setExecutor(commands);
		
		registerListeners();	
		//Creates instances for saving player shop data.
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
