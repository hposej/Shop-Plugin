package com.gmail.kylerslade.profiles;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.kylerslade.gui.ShopWindow;
import com.gmail.kylerslade.shop.Shops;

public class SaveProfile {
	public void createPlayerLocations(){

		int slot = 0; 

		File oldProfilePath = Shops.getInstance().getProfile().getFile();

		//Clear the yml file.
		oldProfilePath.delete();
		File profilePath = new File(Shops.getInstance().getDataFolder(), "shoplocations.yml");

		FileConfiguration fileData = YamlConfiguration.loadConfiguration(profilePath);

		//Create the profile if it does not exist.
		if (!profilePath.exists()){
			try {

				fileData.createSection("playeruuids");

				fileData.createSection("locations");
				fileData.createSection("names");
				fileData.createSection("descriptions");
				fileData.save(profilePath);

			}catch (IOException exception) {
				exception.printStackTrace();
			}
		}

		ShopWindow shopWindow = new ShopWindow();
		HashMap<UUID, Location> shopLocations = shopWindow.getShopLocations();
		HashMap<UUID, String> shopNames = shopWindow.getShopNames();
		HashMap<UUID, String> shopDescriptions = shopWindow.getShopDescriptions();

		//Save the players shop locations.
		for (Iterator<UUID> iterator = shopLocations.keySet().iterator(); iterator.hasNext();){
			UUID playerUUID = iterator.next();

			Location location = shopLocations.get(playerUUID);

			int x = location.getBlockX(); 
			int y = location.getBlockY(); 
			int z = location.getBlockZ(); 

			String strSlot = Integer.toString(slot); 

			fileData.set("playeruuids." + strSlot, playerUUID.toString());

			Shops.getInstance().getLogger().info(strSlot);

			fileData.set("locations." + playerUUID.toString() + ".x", x);
			fileData.set("locations." + playerUUID.toString() + ".y", y);
			fileData.set("locations." + playerUUID.toString() + ".z", z);

			slot++;
		}	

		//Save the shop locations.
		try {
			fileData.save(profilePath);
		} catch (IOException exe) {
			exe.printStackTrace();
		}	

		//Save the players shop names.
		for (Iterator<UUID> iterator = shopNames.keySet().iterator(); iterator.hasNext();){
			UUID playerUUID = iterator.next();
			fileData.set("names." + playerUUID.toString() + ".name", shopNames.get(playerUUID));
		}

		//Save the shop names.
		try {
			fileData.save(profilePath);
		} catch (IOException exe) {
			exe.printStackTrace();
		}	

		//Save the players shop Lores.
		for (Iterator<UUID> iterator = shopNames.keySet().iterator(); iterator.hasNext();){
			UUID playerUUID = iterator.next();
			fileData.set("descriptions." + playerUUID.toString() + ".description", shopDescriptions.get(playerUUID));
		}

		//Save the shop descriptions.
		try {
			fileData.save(profilePath);
		} catch (IOException exe) {
			exe.printStackTrace();
		}	
	}	
}
