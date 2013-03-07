package rebelkeithy.mods.metallurgy.metals;

import java.io.File;
import java.io.IOException;

import net.minecraftforge.common.Configuration;
import rebelkeithy.mods.metallurgy.core.MetalInfoDatabase;
import rebelkeithy.mods.metallurgy.core.MetallurgyCore;
import rebelkeithy.mods.metallurgy.core.metalsets.MetalSet;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid="Metallurgy3Base", name="Metallurgy 3 Base", version="1.4.7-1.11.13-1a")
@NetworkMod(channels = {"MetallurgyBase"}, clientSideRequired = true, serverSideRequired = false)
public class MetallurgyMetals {

	public MetalSet baseSet;
	public MetalSet preciousSet;
	public MetalSet utilitySet;
	
	public Configuration baseConfig;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) 
	{
		File fileDir = new File(MetallurgyCore.proxy.getMinecraftDir() + "/config/Metallurgy3");
    	fileDir.mkdir();
    	File cfgFile = new File(MetallurgyCore.proxy.getMinecraftDir() + "/config/Metallurgy3/MetallurgyBaseItems.cfg");
    	
        try
        {
            cfgFile.createNewFile();
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
        
        baseConfig = new Configuration(cfgFile);
        baseConfig.load();
		
		
		MetalInfoDatabase.readMetalDataFromJar("spreadsheet.csv", "mods\\MetallurgyBase.jar");
		MetalInfoDatabase.readItemDataFromJar(baseConfig, "Items.csv", "mods\\MetallurgyBase.jar");
		
		baseSet = new MetalSet("Base", MetalInfoDatabase.getSpreadsheetDataForSet("Base"));
		preciousSet = new MetalSet("Precious", MetalInfoDatabase.getSpreadsheetDataForSet("Precious"));
		utilitySet = new MetalSet("Utility", MetalInfoDatabase.getSpreadsheetDataForSet("Utility"));
	}
}
