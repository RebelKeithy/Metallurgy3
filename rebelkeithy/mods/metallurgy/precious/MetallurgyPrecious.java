package rebelkeithy.mods.metallurgy.precious;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import rebelkeithy.mods.metallurgy.core.MetalInfoDatabase;
import rebelkeithy.mods.metallurgy.core.metalsets.MetalSet;

@Mod(modid="Metallurgy3Precious", name="Metallurgy 3 Precious", version="1.4.7-1.11.13-1a")
@NetworkMod(channels = {"MetallurgyPrecious"}, clientSideRequired = true, serverSideRequired = false)
public class MetallurgyPrecious {

	MetalSet preciousSet;
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		preciousSet = new MetalSet("Precious", MetalInfoDatabase.getSpreadsheetDataForSet("Precious"));
	}
}
