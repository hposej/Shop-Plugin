package com.gmail.kylerslade.profiles;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.kylerslade.shop.Shops;

public class Profile {
	
	private HashMap<UUID, File> playerProfiles = new HashMap<UUID, File>();
	private File profilePath;

	public void createNewProfile(){
		profilePath = new File(Shops.getInstance().getDataFolder(), "shoplocations.yml");
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
	}

	public HashMap<UUID, File> getPlayerProfiles(){
		return playerProfiles;
	}
	
	public File getFile(){
		return profilePath;
	}
}
