package rebelkeithy.mods.metallurgy.integration;

import java.io.File;
import java.util.logging.Level;

import rebelkeithy.mods.metallurgy.core.MetallurgyCore;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;

public class IntegrationHooks {
	
	static Configuration config;
	
	static boolean loadTC;
	static boolean loadIC2;
	static boolean loadTE;
	static boolean loadRC;
	
	static int[] RCBoreIds;
	
	static int[] BORE_ID_DEFAULTS = new int[] { 922, 923, 924, 925, 926};
	
	public static void init(){
		
		initConfig();
		
		if (Loader.isModLoaded("Thaumcraft") && loadTC){
		ThaumcraftIntegration.init();
		}
		if (Loader.isModLoaded("Railcraft" ) && loadRC){
		RailcraftIntegration.init(RCBoreIds);
		}
		if (Loader.isModLoaded("ThermalExpansion") && loadIC2){
		ThermalExpansionIntegration.init();
		}
		if (Loader.isModLoaded("IC2") && loadTE){
		IC2Integration.init();
		}
	}



	private static void initConfig() {
		try {
		File cfgFile = new File(MetallurgyCore.proxy.getMinecraftDir() + "/config/Metallurgy3/MetallurgyIntegration.cfg");
		config = new Configuration(cfgFile);
		
		loadTC = config.get("Thaumcraft", "integrate", true).getBoolean(true);
		loadRC = config.get("Railcraft", "integrate", true).getBoolean(true);
		loadTE = config.get("Thermal Expansion", "integrate", true).getBoolean(true);
		loadIC2 = config.get("IndustrialCraft2", "integrate", true).getBoolean(true);
		
		RCBoreIds = config.get("Railcraft", "BoreHeadIds", BORE_ID_DEFAULTS).getIntList();
		}
		catch (Exception e) {
            FMLLog.log(Level.SEVERE, e, "Metallurgy has had a problem loading its configuration");
        }
        finally {
            config.save();
        }
	}

}
