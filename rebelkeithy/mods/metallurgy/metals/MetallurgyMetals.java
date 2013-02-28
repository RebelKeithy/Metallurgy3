package rebelkeithy.mods.metallurgy.metals;

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

	MetalSet baseSet;
	MetalSet preciousSet;
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		MetalInfoDatabase.readDataFromJar("spreadsheet.csv", "mods\\MetallurgyBase.jar");
		baseSet = new MetalSet("Base", MetalInfoDatabase.getSpreadsheetDataForSet("Base"));
		
		preciousSet = new MetalSet("Precious", MetalInfoDatabase.getSpreadsheetDataForSet("Precious"));
	}
}
