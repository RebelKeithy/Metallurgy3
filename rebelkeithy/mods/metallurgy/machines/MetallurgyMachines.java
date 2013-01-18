package rebelkeithy.mods.metallurgy.machines;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;

import rebelkeithy.mods.metallurgy.machines.storage.BlockStorage;
import rebelkeithy.mods.metallurgy.machines.storage.BlockStorageAccessor;
import rebelkeithy.mods.metallurgy.machines.storage.TileEntityStorageAccessor;
import rebelkeithy.mods.metallurgy.machines.storage.TileEntityStorageBlock;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid="Metallurgy3Machines", name="Metallurgy 3 Machines", version="1.4.7_1.16.13_1a")
@NetworkMod(channels = {"M3Machines"}, clientSideRequired = true, serverSideRequired = false, packetHandler=PacketHandler.class)
public class MetallurgyMachines 
{
	@SidedProxy(clientSide = "rebelkeithy.mods.metallurgy.machines.ClientProxy", serverSide = "rebelkeithy.mods.metallurgy.machines.CommonProxy")
	public static CommonProxy proxy;
	
	@Instance(value = "Metallurgy3Machines")
	public static MetallurgyMachines instance;
	
	public Block storageAccessor;
	public Block storageBlock;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{		
		storageAccessor = new BlockStorageAccessor(919).setBlockName("StorageAccessorBlock").setCreativeTab(CreativeTabs.tabDecorations);
		storageBlock = new BlockStorage(920).setBlockName("StorageBlock").setCreativeTab(CreativeTabs.tabDecorations);
		storageAccessor.blockIndexInTexture = 32;
		storageBlock.blockIndexInTexture = 33;
		GameRegistry.registerTileEntity(TileEntityStorageAccessor.class, "TileEntityStorage");
		GameRegistry.registerTileEntity(TileEntityStorageBlock.class, "TileEntityStorageBlock");
		GameRegistry.registerBlock(storageAccessor);
		GameRegistry.registerBlock(storageBlock);
		
		NetworkRegistry.instance().registerGuiHandler(this, new StorageGuiHandler());
	}
}
