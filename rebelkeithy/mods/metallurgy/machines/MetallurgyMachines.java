package rebelkeithy.mods.metallurgy.machines;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;

import rebelkeithy.mods.metallurgy.machines.storage.StorageBlock;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid="Metallurgy3Machines", name="Metallurgy 3 Machines", version="1.4.7_1.16.13_1a")
@NetworkMod(channels = {"MetallurgyMachines"}, clientSideRequired = true, serverSideRequired = false)
public class MetallurgyMachines 
{
	@SidedProxy(clientSide = "rebelkeithy.mods.metallurgy.machines.ClientProxy", serverSide = "rebelkeithy.mods.metallurgy.machines.CommonProxy")
	public static CommonProxy proxy;
	
	@Instance(value = "Metallurgy3Machines")
	public static MetallurgyMachines instance;
	
	Block storage;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{		
		storage = new StorageBlock(919).setBlockName("StorageBlock").setCreativeTab(CreativeTabs.tabDecorations);
		storage.blockIndexInTexture = 32;
		GameRegistry.registerBlock(storage);
	}
}
