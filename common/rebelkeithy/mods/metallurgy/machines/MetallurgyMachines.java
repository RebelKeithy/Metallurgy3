package rebelkeithy.mods.metallurgy.machines;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import rebelkeithy.mods.guiregistry.GuiRegistry;
import rebelkeithy.mods.metallurgy.machines.abstractor.BlockAbstractor;
import rebelkeithy.mods.metallurgy.machines.abstractor.BlockAbstractorItem;
import rebelkeithy.mods.metallurgy.machines.abstractor.ContainerAbstractor;
import rebelkeithy.mods.metallurgy.machines.abstractor.GuiAbstractor;
import rebelkeithy.mods.metallurgy.machines.abstractor.TileEntityAbstractor;
import rebelkeithy.mods.metallurgy.machines.crusher.BlockCrusher;
import rebelkeithy.mods.metallurgy.machines.crusher.BlockCrusherItem;
import rebelkeithy.mods.metallurgy.machines.crusher.ContainerCrusher;
import rebelkeithy.mods.metallurgy.machines.crusher.CrusherUpgradeRecipes;
import rebelkeithy.mods.metallurgy.machines.crusher.GuiCrusher;
import rebelkeithy.mods.metallurgy.machines.crusher.TileEntityCrusher;
import rebelkeithy.mods.metallurgy.machines.forge.BlockNetherForge;
import rebelkeithy.mods.metallurgy.machines.forge.BlockNetherForgeItem;
import rebelkeithy.mods.metallurgy.machines.forge.ContainerNetherForge;
import rebelkeithy.mods.metallurgy.machines.forge.GuiNetherForge;
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
import rebelkeithy.mods.metallurgy.metals.MetallurgyMetals;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid="Metallurgy3Machines", name="Metallurgy 3 Machines", dependencies = "required-after:Metallurgy3Base", version="1.4.7_1.16.13_1a")
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
	public Block abstractor;

	public static int[] extractorSpeeds;
	public static double[] xpBonus;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{		
		initStorage();
		initCrusher();
		initFurnace();
		initForge();
		initAbstractor();
		
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
		
		GuiRegistry.registerGui(GuiNetherForge.class, ContainerNetherForge.class, this, "NetherForge");
	}
	
	public void initAbstractor()
	{
		abstractor = new BlockAbstractor(ConfigMachines.abstractorID, false).setHardness(3.5F).setBlockName("M3Abstractor").setTextureFile("/Metallurgy/FantasyFurnace.png").setCreativeTab(CreativeTabs.tabDecorations);
		GameRegistry.registerBlock(abstractor, BlockAbstractorItem.class, "BlockM3Abstractor");
		GameRegistry.registerTileEntity(TileEntityAbstractor.class, "TileEntityAbstractor");
		
		extractorSpeeds = new int[11];
		extractorSpeeds[0] = MetallurgyMetals.fantasyConfig.get("Abstractor", "Speed Prometheum", 22).getInt();
		extractorSpeeds[1] = MetallurgyMetals.fantasyConfig.get("Abstractor", "Speed Deep Iron", 20).getInt();
		extractorSpeeds[2] = MetallurgyMetals.fantasyConfig.get("Abstractor", "Speed Block Steel", 18).getInt();
		extractorSpeeds[3] = MetallurgyMetals.fantasyConfig.get("Abstractor", "Speed Oureclase", 16).getInt();
		extractorSpeeds[4] = MetallurgyMetals.fantasyConfig.get("Abstractor", "Speed Aredrite", 14).getInt();
		extractorSpeeds[5] = MetallurgyMetals.fantasyConfig.get("Abstractor", "Speed Mithril", 12).getInt();
		extractorSpeeds[6] = MetallurgyMetals.fantasyConfig.get("Abstractor", "Speed Haderoth", 10).getInt();
		extractorSpeeds[7] = MetallurgyMetals.fantasyConfig.get("Abstractor", "Speed Orichalcum", 8).getInt();
		extractorSpeeds[8] = MetallurgyMetals.fantasyConfig.get("Abstractor", "Speed Adamantine", 6).getInt();
		extractorSpeeds[9] = MetallurgyMetals.fantasyConfig.get("Abstractor", "Speed Atlarus", 4).getInt();
		extractorSpeeds[10] = MetallurgyMetals.fantasyConfig.get("Abstractor", "Speed Tartarite", 2).getInt();
		
		xpBonus = new double[11];
		xpBonus[0] = MetallurgyMetals.fantasyConfig.get("Abstractor", "Bonus Prometheum", 1.0).getDouble(1.0);
		xpBonus[1] = MetallurgyMetals.fantasyConfig.get("Abstractor", "Bonus Deep Iron", 1.2).getDouble(1.2);
		xpBonus[2] = MetallurgyMetals.fantasyConfig.get("Abstractor", "Bonus Black Steel", 1.4).getDouble(1.4);
		xpBonus[3] = MetallurgyMetals.fantasyConfig.get("Abstractor", "Bonus Oureclase", 1.6).getDouble(1.6);
		xpBonus[4] = MetallurgyMetals.fantasyConfig.get("Abstractor", "Bonus Aredrite", 1.8).getDouble(1.8);
		xpBonus[5] = MetallurgyMetals.fantasyConfig.get("Abstractor", "Bonus Mithril", 2.0).getDouble(2.0);
		xpBonus[6] = MetallurgyMetals.fantasyConfig.get("Abstractor", "Bonus Haderoth", 2.4).getDouble(2.4);
		xpBonus[7] = MetallurgyMetals.fantasyConfig.get("Abstractor", "Bonus Oreichalcum", 2.8).getDouble(2.8);
		xpBonus[8] = MetallurgyMetals.fantasyConfig.get("Abstractor", "Bonus Admantine", 3.2).getDouble(3.2);
		xpBonus[9] = MetallurgyMetals.fantasyConfig.get("Abstractor", "Bonus Atlarus", 3.6).getDouble(3.6);
		xpBonus[10] = MetallurgyMetals.fantasyConfig.get("Abstractor", "Bonus Tartarite", 4.0).getDouble(4.0);
		
		MetallurgyMetals.fantasyConfig.save();
		
		GuiRegistry.registerGui(GuiAbstractor.class, ContainerAbstractor.class, this, "Abstractor");
	}
}
