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

public class MetalInfoDatabase 
{
	private static List<Map<String, String>> spreadsheet;
	
	private static void readData(BufferedReader in)
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
	
	public static void readDataFromFile(String filepath)
	{
		try {
			File file = new File(MetallurgyCore.proxy.getMinecraftDir() + filepath);
			BufferedReader in = new BufferedReader(new FileReader(file));
			readData(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void readDataFromJar(String filename, String jarpath)
	{
		ZipFile zip;
		try {
			zip = new ZipFile(MetallurgyCore.proxy.getMinecraftDir() + "\\" + jarpath);
			ZipEntry entry = zip.getEntry(filename);
			BufferedReader in = new BufferedReader(new InputStreamReader(zip.getInputStream(entry)));
			readData(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Map<String, Map<String, String>> getSpreadsheetDataForSet(String name)
	{
		Map<String, Map<String, String>> returnData = new HashMap<String, Map<String, String>>();
		
		if(spreadsheet == null || !spreadsheet.contains(name))
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
		return returnData;
	}
}
