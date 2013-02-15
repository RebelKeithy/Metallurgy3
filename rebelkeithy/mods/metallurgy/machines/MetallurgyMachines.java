package rebelkeithy.mods.metallurgy.machines;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import rebelkeithy.mods.guiregistry.GuiRegistry;
import rebelkeithy.mods.metallurgy.machines.crusher.BlockCrusher;
import rebelkeithy.mods.metallurgy.machines.crusher.BlockCrusherItem;
import rebelkeithy.mods.metallurgy.machines.crusher.ContainerCrusher;
import rebelkeithy.mods.metallurgy.machines.crusher.CrusherUpgradeRecipes;
import rebelkeithy.mods.metallurgy.machines.crusher.GuiCrusher;
import rebelkeithy.mods.metallurgy.machines.crusher.TileEntityCrusher;
import rebelkeithy.mods.metallurgy.machines.forge.BlockNetherForge;
import rebelkeithy.mods.metallurgy.machines.forge.BlockNetherForgeItem;
import rebelkeithy.mods.metallurgy.machines.forge.TileEntityNetherForge;
import rebelkeithy.mods.metallurgy.machines.furnace.BlockMetalFurnace;
import rebelkeithy.mods.metallurgy.machines.furnace.BlockMetalFurnaceItem;
import rebelkeithy.mods.metallurgy.machines.furnace.ContainerMetalFurnace;
import rebelkeithy.mods.metallurgy.machines.furnace.GuiMetalFurnace;
import rebelkeithy.mods.metallurgy.machines.furnace.TileEntityMetalFurnace;
import rebelkeithy.mods.metallurgy.machines.storage.BlockStorage;
import rebelkeithy.mods.metallurgy.machines.storage.BlockStorageAccessor;
import rebelkeithy.mods.metallurgy.machines.storage.TileEntityStorageAccessor;
import rebelkeithy.mods.metallurgy.machines.storage.TileEntityStorageBlock;

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
	
	public Block crusher;
	public Block furnace;
	public Block forge;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{		
		initStorage();
		initCrusher();
		initFurnace();
		initForge();
		
		proxy.registerTileEntitySpecialRenderer();
		NetworkRegistry.instance().registerGuiHandler(this, new StorageGuiHandler());
		NetworkRegistry.instance().registerGuiHandler(this, GuiRegistry.instance());
		
	}
	
	@cpw.mods.fml.common.Mod.Init
	public void Init(FMLInitializationEvent event)
	{
		loadCrusher();
	}
	
	public void initStorage()
	{
		storageAccessor = new BlockStorageAccessor(919).setBlockName("StorageAccessorBlock").setCreativeTab(CreativeTabs.tabDecorations);
		storageBlock = new BlockStorage(920).setBlockName("StorageBlock").setCreativeTab(CreativeTabs.tabDecorations);
		storageAccessor.blockIndexInTexture = 32;
		storageBlock.blockIndexInTexture = 33;
		GameRegistry.registerTileEntity(TileEntityStorageAccessor.class, "TileEntityStorage");
		GameRegistry.registerTileEntity(TileEntityStorageBlock.class, "TileEntityStorageBlock");
		GameRegistry.registerBlock(storageAccessor, "BlockStorageAccessor");
		GameRegistry.registerBlock(storageBlock, "BlockStorage");
	}
	
	public void initCrusher()
	{
		crusher = new BlockCrusher(ConfigMachines.crusherID, false).setHardness(3.5F).setBlockName("Crusher").setCreativeTab(CreativeTabs.tabDecorations);
		GameRegistry.registerBlock(crusher, BlockCrusherItem.class, "BlockCrusher");
		GameRegistry.registerTileEntity(TileEntityCrusher.class, "TileEntityCrusher");
		
		GuiRegistry.registerGui(GuiCrusher.class, ContainerCrusher.class, this, "Crusher");
	}
	
	public void loadCrusher()
	{
		CrusherUpgradeRecipes.addRecipes();
	}
	
	public void initFurnace()
	{
		furnace = new BlockMetalFurnace(ConfigMachines.furnaceID, false).setHardness(3.5F).setBlockName("MetallurgyFurnace").setCreativeTab(CreativeTabs.tabDecorations);
		GameRegistry.registerBlock(furnace, BlockMetalFurnaceItem.class, "BlockMetalFurnace");
		GameRegistry.registerTileEntity(TileEntityMetalFurnace.class, "TileEntityMetalFurnace");
		
		GuiRegistry.registerGui(GuiMetalFurnace.class, ContainerMetalFurnace.class, this, "MetalFurnace");
	}
	
	public void initForge()
	{
		forge = new BlockNetherForge(ConfigMachines.forgeID, false).setHardness(3.5F).setBlockName("NetherForge").setCreativeTab(CreativeTabs.tabDecorations);
		GameRegistry.registerBlock(forge, BlockNetherForgeItem.class, "BlockNetherForge");
		GameRegistry.registerTileEntity(TileEntityNetherForge.class, "TileEntityNetherForge");
	}
}
