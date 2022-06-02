package org.rvs.newturfwars.manager;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.rvs.newturfwars.Main;

public class YMLFile {
	   
    static Main plugin = Main.getPlugin(Main.class);
   
    public static String read(String file, String arg){
		File pldata = new File(plugin.getDataFolder(), File.separator + "DATA");
		File f = new File(pldata, File.separator + file + ".yml");
		FileConfiguration data = YamlConfiguration.loadConfiguration(f);
		
		return data.getString(arg);
    }
    
    public static int readInt(String file, String arg){
		File pldata = new File(plugin.getDataFolder(), File.separator + "DATA");
		File f = new File(pldata, File.separator + file + ".yml");
		FileConfiguration data = YamlConfiguration.loadConfiguration(f);
		
		return data.getInt(arg);
    }
    
    public static double readDouble(String file, String arg){
		File pldata = new File(plugin.getDataFolder(), File.separator + "DATA");
		File f = new File(pldata, File.separator + file + ".yml");
		FileConfiguration data = YamlConfiguration.loadConfiguration(f);
		
		return data.getDouble(arg);
    }
    
    public static void write(String file, String arg, String data){
		    	
		File pldata = new File(plugin.getDataFolder(), File.separator + "DATA");
		File f = new File(pldata, File.separator + file + ".yml");
		FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
	
		try {
			
			//playerData.createSection("Data");
			playerData.set(arg, data);
			   
			/*playerData.createSection("Stats");
			playerData.set("Stats.Level", 1);
			playerData.set("Stats.XP", 0);*/
			       
			playerData.save(f);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
    
    public static void write(String file, String arg, double data){
    	
		File pldata = new File(plugin.getDataFolder(), File.separator + "DATA");
		File f = new File(pldata, File.separator + file + ".yml");
		FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
		
		try {
			
			//playerData.createSection("Data");
			playerData.set(arg, data);
			   
			/*playerData.createSection("Stats");
			playerData.set("Stats.Level", 1);
			playerData.set("Stats.XP", 0);*/
			       
			playerData.save(f);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
       
    
}