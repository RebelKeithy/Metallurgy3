package rebelkeithy.mods.metallurgy.integration;

import cpw.mods.fml.common.Loader;

public class IntegrationHooks {
	
	public static void init(){
		if (Loader.isModLoaded("Thaumcraft")){
		ThaumcraftIntegration.init();
		}
		if (Loader.isModLoaded("Railcraft")){
		RailcraftIntegration.init();
		}
		if (Loader.isModLoaded("ThermalExpansion")){
		ThermalExpansionIntegration.init();
		}
		if (Loader.isModLoaded("IC2")){
		IC2Integration.init();
		}
	}

}
