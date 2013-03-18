package rebelkeithy.mods.metallurgy.core.metalsets;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rebelkeithy.mods.metallurgy.core.MetallurgyCore;

public class MetalSet 
{
	private String setName;
	private Map<String, OreInfo> metals;
	private Configuration config;
	
	public MetalSet(String setName, Map<String, Map<String, String>> baseData, CreativeTabs tab)
	{
		this.setName = setName;
		
		metals = new HashMap<String, OreInfo>();
		
		for(Map<String, String> metalInfo : baseData.values())
		{
			metals.put(metalInfo.get("Name"), new OreInfo(metalInfo, tab));
		}
		
		MetallurgyCore.getMetalSetList().add(this);
		
		initConfig();
		init();
	}
	
	public void initConfig()
	{
		File fileDir = new File(MetallurgyCore.proxy.getMinecraftDir() + "/config/Metallurgy3");
    	fileDir.mkdir();
    	File cfgFile = new File(MetallurgyCore.proxy.getMinecraftDir() + "/config/Metallurgy3/Metallurgy" + setName + ".cfg");
    	
        try
        {
            cfgFile.createNewFile();
            System.out.println("[Metallurgy3] Successfully created/read configuration file for Metallurgy 3's metal set " + setName);
        }
        catch (IOException e)
        {
            System.out.println("[Metallurgy3] Could not create configuration file for Metallurgy 3 metal set " + setName + ". Reason:");
            System.out.println(e);
        }
        
		config = new Configuration(cfgFile);
		config.load();
		
		for(OreInfo oreInfo : metals.values())
		{
			oreInfo.initConfig(config);
		}
		
		config.save();
	}
	
	public void init()
	{
		for(OreInfo oreInfo : metals.values())
		{
			oreInfo.init();
		}
	}
	
	public void load()
	{
		for(OreInfo oreInfo : metals.values())
		{
			oreInfo.load();
		}
	}

	public void registerNames() {
		for(OreInfo oreInfo : metals.values())
		{
			oreInfo.registerNames();
		}
	}
	
	public OreInfo getOreInfo(String name)
	{
		return metals.get(name);
	}

	public OreInfo getOreInfo(int meta) 
	{
		for(OreInfo info : metals.values())
		{
			if(info.blockMeta == meta)
			{
				return info;
			}
		}
		return null;
	}
	
}
