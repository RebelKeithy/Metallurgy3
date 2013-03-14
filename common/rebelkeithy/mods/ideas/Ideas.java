package rebelkeithy.mods.ideas;

import net.minecraft.block.Block;
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
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) 
	{
		orenode = new OreNode(999).setUnlocalizedName("Metallurgy:OreNode").setHardness(3);
		
	}
	
	@Init
	public void init(FMLInitializationEvent event)
	{
		GameRegistry.registerBlock(orenode);
		MinecraftForge.setBlockHarvestLevel(orenode, 0, "pickaxe", 3);
	}
}
