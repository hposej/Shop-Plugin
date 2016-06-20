package com.aol.hposejhero.profiles;

import java.io.File;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.aol.hposejhero.gui.ShopWindow;
import com.aol.hposejhero.shop.Shops;

public class LoadProfile {
	public void loadProfile(){
				
		//Load the yml file.
		File profilePath = Shops.getInstance().getProfile().getFile();
		
		FileConfiguration fileData = YamlConfiguration.loadConfiguration(profilePath);
				
		ShopWindow shopWindow = new ShopWindow(); 
		
		//Grab all player uuids in the file.
		for (String slot : fileData.getConfigurationSection("playeruuids").getKeys(false)){
				
			String strPlayerUUID = fileData.getConfigurationSection("playeruuids").getString(slot);	
			UUID playerUUIDs = UUID.fromString(strPlayerUUID);
			
			int counter = 0;
			
			World world = Shops.getInstance().getServer().getWorld("world");
			Location loc = new Location(world, 0, 0, 0);
			
			//Set the locations in the shop locatons made.
			for (String pos : fileData.getConfigurationSection("locations." + strPlayerUUID).getKeys(false)){
						
				if (counter == 0){
					loc.setX(fileData.getConfigurationSection("locations." + strPlayerUUID).getInt(pos));
				}else if(counter == 1){
					loc.setY(fileData.getConfigurationSection("locations." + strPlayerUUID).getInt(pos));
				}else{
					loc.setY(fileData.getConfigurationSection("locations." + strPlayerUUID).getInt(pos));
				}
									
				counter++;
				if (counter > 2){
					counter = 0;
				}
			}
			
			shopWindow.setShopLocations(playerUUIDs, loc);
			
			//Sets the names for the shops.
			for (String names : fileData.getConfigurationSection("names." + strPlayerUUID).getKeys(false)){
				
				String passName = fileData.getConfigurationSection("names." + strPlayerUUID).getString(names);
				
				Shops.getInstance().getLogger().info(passName);	
				
				Shops.getInstance().getLogger().info(playerUUIDs.toString());
				Shops.getInstance().getLogger().info(passName);
				shopWindow.setShopNames(playerUUIDs, passName);
			}
			
			//Sets the shop descriptions. 
			for (String lores : fileData.getConfigurationSection("descriptions." + strPlayerUUID).getKeys(false)){
				
				String passLore = fileData.getConfigurationSection("descriptions." + strPlayerUUID).getString(lores);
				
				Shops.getInstance().getLogger().info(passLore);		
				
				Shops.getInstance().getLogger().info(playerUUIDs.toString());
				Shops.getInstance().getLogger().info(passLore);
				shopWindow.setShopDescriptions(playerUUIDs, passLore);
			}
		}	
	}
}	
