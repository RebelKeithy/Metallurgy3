package rebelkeithy.mods.metallurgy.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.oredict.OreDictionary;

public class MetalInfoDatabase 
{
	private static List<Map<String, String>> spreadsheet;
	private static Map<String, Item> items;
	private static Map<String, String> oreDictNames;
	
	private static void readOreData(BufferedReader in)
	{
		if(spreadsheet == null)
			spreadsheet = new ArrayList<Map<String, String>>();

		try {
			String input = in.readLine();
			String[] header = input.split(",");
			input = in.readLine();
			while(input != null)
			{
				String[] inputArray = input.split(",");
				Map<String, String> oreMap = new HashMap<String, String>();
				for(int n = 0; n < inputArray.length; n++)
				{
					if(inputArray[n].equals(""))
						inputArray[n] = "0";
					oreMap.put(header[n], inputArray[n]);
				}
				spreadsheet.add(oreMap);
				input = in.readLine();
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void readItemData(Configuration config, BufferedReader in)
	{
		if(items == null)
			items = new HashMap<String, Item>();
		if(oreDictNames == null)
			oreDictNames = new HashMap<String, String>();

		try {
			String input = in.readLine();
			String[] header = input.split(",");
			input = in.readLine();
			while(input != null)
			{
				String[] inputArray = input.split(",");
				Map<String, String> itemMap = new HashMap<String, String>();
				for(int n = 0; n < inputArray.length; n++)
				{
					if(inputArray[n].equals(""))
						inputArray[n] = "0";
					System.out.println("reading property " + header[n] + " as " + inputArray[n]);
					itemMap.put(header[n], inputArray[n]);
				}
				
				int id = Integer.parseInt(itemMap.get("Item ID"));
				
				id = config.get("Items", itemMap.get("Item Name"), id).getInt();
				
				Item item = new Item(id).setUnlocalizedName("Metallurgy:" + itemMap.get("Set Name") + "/" + itemMap.get("Item Name")).setCreativeTab(CreativeTabs.tabMisc);
				LanguageRegistry.addName(item, itemMap.get("Item Name"));
				
				items.put(itemMap.get("Item Name"), item);
				if(!itemMap.get("Ore Dictionary Name").equals("0"))
					oreDictNames.put(itemMap.get("Item Name"), itemMap.get("Ore Dictionary Name"));
				input = in.readLine();
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void registerItemsWithOreDict()
	{
		for(String name : oreDictNames.keySet())
		{
			OreDictionary.registerOre(oreDictNames.get(name), items.get(name));
		}
	}
	
	public static void readItemDataFromFile(Configuration config, String filepath)
	{
		try {
			File file = new File(MetallurgyCore.proxy.getMinecraftDir() + filepath);
			BufferedReader in = new BufferedReader(new FileReader(file));
			readItemData(config, in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void readItemDataFromJar(Configuration config, String filename, String jarpath) 
	{
		ZipFile zip;
		try {
			zip = new ZipFile(MetallurgyCore.proxy.getMinecraftDir() + "\\" + jarpath);
			ZipEntry entry = zip.getEntry(filename);
			BufferedReader in = new BufferedReader(new InputStreamReader(zip.getInputStream(entry)));
			readItemData(config, in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void readMetalDataFromFile(String filepath)
	{
		try {
			File file = new File(MetallurgyCore.proxy.getMinecraftDir() + filepath);
			BufferedReader in = new BufferedReader(new FileReader(file));
			readOreData(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void readMetalDataFromJar(String filename, String jarpath)
	{
		ZipFile zip;
		try {
			zip = new ZipFile(MetallurgyCore.proxy.getMinecraftDir() + "\\" + jarpath);
			ZipEntry entry = zip.getEntry(filename);
			BufferedReader in = new BufferedReader(new InputStreamReader(zip.getInputStream(entry)));
			readOreData(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Map<String, Map<String, String>> getSpreadsheetDataForSet(String name)
	{
		Map<String, Map<String, String>> returnData = new HashMap<String, Map<String, String>>();
		
		if(spreadsheet == null)
		{
			return returnData;
		}
		
		for(Map<String, String> data : spreadsheet)
		{
			
			if(data.get("Metal Set").equals(name))
			{
				returnData.put(data.get("Name"), data);
			}
		}
		System.out.println("found " + returnData.size() + " items");
		return returnData;
	}

	public static ItemStack getItem(String itemName) 
	{
		if(items == null || !items.containsKey(itemName))
		{
			System.out.println("Item " + itemName + " not found");
			return null;
		}
		
		return new ItemStack(items.get(itemName));
	}
}
