package com.gmail.kylerslade.profiles;

import java.io.File;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.kylerslade.gui.ShopWindow;
import com.gmail.kylerslade.shop.Shops;

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
			
			int x = 0;
			int y = 0;
			int z = 0;
			
			World world = Shops.getInstance().getServer().getWorld("world");
			Location loc = new Location(world, x, y, z);
			
			//Set the locations in the shop locatons made.
			for (String pos : fileData.getConfigurationSection("locations." + strPlayerUUID).getKeys(false)){
						
				if (counter == 0){
					x = fileData.getConfigurationSection("locations." + strPlayerUUID).getInt(pos);
				}else if(counter == 1){
					y = fileData.getConfigurationSection("locations." + strPlayerUUID).getInt(pos);
				}else{
					z = fileData.getConfigurationSection("locations." + strPlayerUUID).getInt(pos);
				}
						
				loc.setX(x);
				loc.setY(y);
				loc.setZ(z);
				
				counter++;
				if (counter > 2){
					counter = 0;
				}
			}
			
			Shops.getInstance().getLogger().info(playerUUIDs.toString());
			Shops.getInstance().getLogger().info(loc.toString());
			
			shopWindow.setShopLocations(playerUUIDs, loc);
			
			//Sets the names for the shops.
			for (String names : fileData.getConfigurationSection("names." + strPlayerUUID).getKeys(false)){
				
				String passName = fileData.getConfigurationSection("names." + strPlayerUUID).getString(names);
				
				Shops.getInstance().getLogger().info(passName);	
				
				Shops.getInstance().getLogger().info(playerUUIDs.toString());
				Shops.getInstance().getLogger().info(passName);
				shopWindow.setShopNames(playerUUIDs, passName);
			}
			
			//Sets the shop lores. 
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
