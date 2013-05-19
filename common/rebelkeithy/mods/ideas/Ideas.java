package rebelkeithy.mods.ideas;

import java.io.File;

import rebelkeithy.mods.metallurgy.core.MetallurgyCore;
import net.minecraft.block.Block;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid="Ideas", name="Ideas", version="1")
public class Ideas 
{
	Block orenode;
	int orenodID = 999;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) 
	{
		File fileDir = new File(MetallurgyCore.proxy.getMinecraftDir() + "/config/Metallurgy3");
    	fileDir.mkdir();
    	File cfgFile = new File(MetallurgyCore.proxy.getMinecraftDir() + "/config/Metallurgy3/MetallurgyIdeas.cfg");

		Configuration config = new Configuration(cfgFile);
		orenodID = config.get("Block IDs", "Orenod", orenodID).getInt();
		
		config.save();
		
		orenode = new OreNode(orenodID).setUnlocalizedName("Metallurgy:OreNode").setHardness(3);
	}
	
	@Init
	public void init(FMLInitializationEvent event)
	{
		GameRegistry.registerBlock(orenode);
		MinecraftForge.setBlockHarvestLevel(orenode, 0, "pickaxe", 3);
	}
}
