package rebelkeithy.mods.metallurgy.core.metalsets;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.Configuration;
import rebelkeithy.mods.metallurgy.api.IMetalSet;
import rebelkeithy.mods.metallurgy.api.IOreInfo;
import rebelkeithy.mods.metallurgy.core.MetallurgyCore;

public class MetalSet implements IMetalSet
{
	private String setName;
	private Map<String, IOreInfo> metals;
	private Configuration config;
	
	public MetalSet(String setName, Map<String, Map<String, String>> baseData, CreativeTabs tab)
	{
		this.setName = setName;
		
		metals = new HashMap<String, IOreInfo>();
		
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
		
		for(IOreInfo oreInfo : metals.values())
		{
			((OreInfo)oreInfo).initConfig(config);
		}
		
		config.save();
	}
	
	public void init()
	{
		for(IOreInfo oreInfo : metals.values())
		{
			((OreInfo)oreInfo).init();
		}
	}
	
	public void load()
	{
		for(IOreInfo oreInfo : metals.values())
		{
			((OreInfo)oreInfo).load();
		}
	}

	public void registerNames() {
		for(IOreInfo oreInfo : metals.values())
		{
			((OreInfo)oreInfo).registerNames();
		}
	}
	
	public OreInfo getOreInfo(String name)
	{
		if(metals.containsKey(name))
			return (OreInfo)metals.get(name);
		else
			return new OreInfo();
	}

	public OreInfo getOreInfo(int meta) 
	{
		for(IOreInfo oreInfo : metals.values())
		{
			if(((OreInfo)oreInfo).oreMeta == meta)
			{
				return (OreInfo)oreInfo;
			}
		}
		return null;
	}

	public Map<String, IOreInfo> getOreList() 
	{
		return metals;
	}
	
}
