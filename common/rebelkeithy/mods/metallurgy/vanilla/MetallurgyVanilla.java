package rebelkeithy.mods.metallurgy.vanilla;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;

import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

import java.util.Map;

import rebelkeithy.mods.metallurgy.core.MetalInfoDatabase;
import rebelkeithy.mods.metallurgy.core.metalsets.MetalSet;

@Mod(modid="Metallurgy3Vanilla", name="Metallurgy 3 Vanilla", version="1.4.7-1.11.13-1a")
@NetworkMod(channels = {"MetallurgyVanilla"}, clientSideRequired = true, serverSideRequired = false)
public class MetallurgyVanilla 
{
	@SidedProxy(clientSide = "rebelkeithy.mods.metallurgy.vanilla.ClientProxy", serverSide = "rebelkeithy.mods.metallurgy.vanilla.CommonProxy")
	public static CommonProxy proxy;
	
	public static MetalSet vanillaSet;
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		
		Map<String, Map<String, String>> vanillaList = MetalInfoDatabase.getSpreadsheetDataForSet("Vanilla");
		vanillaList.remove("Wood/Leather");
		vanillaList.remove("Stone/Chainmail");
		vanillaSet = new MetalSet("Vanilla", vanillaList, CreativeTabs.tabBlock);
		
		VanillaAddons.init();
		
		//MinecraftForge.ORE_GEN_BUS.register(new VanillaOreInhibitor());
		
	}
	
	@Init
	public void Init(FMLInitializationEvent event)
	{		
		VanillaAddons.load();
		proxy.registerNames();
	}
}
