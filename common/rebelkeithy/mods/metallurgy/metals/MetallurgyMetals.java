package rebelkeithy.mods.metallurgy.metals;

import java.io.File;
import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import rebelkeithy.mods.metallurgy.core.MetalInfoDatabase;
import rebelkeithy.mods.metallurgy.core.MetallurgyCore;
import rebelkeithy.mods.metallurgy.core.MetallurgyTabs;
import rebelkeithy.mods.metallurgy.core.metalsets.MetalSet;
import rebelkeithy.mods.metallurgy.metals.utilityItems.ItemFertilizer;
import rebelkeithy.mods.metallurgy.metals.utilityItems.ItemIgniter;
import rebelkeithy.mods.metallurgy.metals.utilityItems.tnt.EntityLargeTNTPrimed;
import rebelkeithy.mods.metallurgy.metals.utilityItems.tnt.EntityMinersTNTPrimed;
import rebelkeithy.mods.metallurgy.metals.utilityItems.tnt.LargeTNT;
import rebelkeithy.mods.metallurgy.metals.utilityItems.tnt.MinersTNT;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid="Metallurgy3Base", name="Metallurgy 3 Base", version="1.4.7-1.11.13-1a")
@NetworkMod(channels = {"MetallurgyBase"}, clientSideRequired = true, serverSideRequired = false)
public class MetallurgyMetals {

	public static MetallurgyTabs utilityTab;
	
	public static MetalSet baseSet;
	public static MetalSet preciousSet;
	public static MetalSet netherSet;
	public static MetalSet fantasySet;
	public static MetalSet utilitySet;
	
	public static Configuration baseConfig;
	public static Configuration utilityConfig;
	public static Configuration fantasyConfig;

	
	public static Item magnesiumIgniter;
	public static Item match;
	public static Item fertilizer;
	public static Item tar;
	
	public static Block largeTNT;
	public static Block minersTNT;

	@SidedProxy(clientSide = "rebelkeithy.mods.metallurgy.metals.ClientProxy", serverSide = "rebelkeithy.mods.metallurgy.metals.CommonProxy")
	public static CommonProxy proxy;
	
	@Instance(value = "Metallurgy3Base")
	public static MetallurgyMetals instance;


	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) 
	{        
        baseConfig = initConfig("MetallurgyBase");
        baseConfig.load();
        
        utilityConfig = initConfig("MetallurgyUtility");
        utilityConfig.load();
        
        fantasyConfig = initConfig("MetallurgyFantasy");
        
        utilityTab = new MetallurgyTabs("Metallurgy Utility");
        LanguageRegistry.instance().addStringLocalization("itemGroup.Metallurgy Utility", "Metallurgy Utility");
		
		MetalInfoDatabase.readMetalDataFromJar("spreadsheet.csv", "mods\\MetallurgyBase.jar");
		MetalInfoDatabase.readItemDataFromJar(baseConfig, "Items.csv", "mods\\MetallurgyBase.jar");
		
		baseSet = new MetalSet("Base", MetalInfoDatabase.getSpreadsheetDataForSet("Base"));
		preciousSet = new MetalSet("Precious", MetalInfoDatabase.getSpreadsheetDataForSet("Precious"));
		netherSet = new MetalSet("Nether", MetalInfoDatabase.getSpreadsheetDataForSet("Nether"));
		fantasySet = new MetalSet("Fantasy", MetalInfoDatabase.getSpreadsheetDataForSet("Fantasy"));
		utilitySet = new MetalSet("Utility", MetalInfoDatabase.getSpreadsheetDataForSet("Utility"));
	}
	
	@Init
	public void Init(FMLInitializationEvent event)
	{
		createUtilityItems();
		utilityConfig.save();
	}
	
	public void createUtilityItems()
	{
		int id = utilityConfig.get("Item IDs", "HE TNT", 911).getInt();
		largeTNT = new LargeTNT(id, 16 * 3).setBlockName("M3HETNT").setCreativeTab(utilityTab);
		GameRegistry.registerBlock(largeTNT, "M3HETNT");
		EntityRegistry.registerModEntity(EntityLargeTNTPrimed.class, "LargeTNTEntity", 113, this, 64, 10, true);
		LanguageRegistry.addName(largeTNT, "HE TNT");
		
		id = utilityConfig.get("Item IDs", "LE TNT", 912).getInt();
		minersTNT = new MinersTNT(id, 16 * 4).setBlockName("M3LETNT").setCreativeTab(utilityTab);
		GameRegistry.registerBlock(minersTNT, "M3LETNT");
		EntityRegistry.registerModEntity(EntityMinersTNTPrimed.class, "MinersTNTEntity", 113, this, 64, 10, true);
		LanguageRegistry.addName(minersTNT, "LE TNT");
		
		id = utilityConfig.get("Item IDs", "Magnesium Igniter", 25022).getInt();
		magnesiumIgniter = new ItemIgniter(id).setItemName("M3MagnesiumIgniter").setTextureFile("/Metallurgy/MagnesiumIgniter.png").setCreativeTab(utilityTab);
		LanguageRegistry.addName(magnesiumIgniter, "Magnesium Igniter");
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(magnesiumIgniter), "X ", " F", 'X', "dustMagnesium", 'F', Item.flintAndSteel));

		id = utilityConfig.get("Item IDs", "Match", 25023).getInt();
		match = new ItemIgniter(id).setItemName("M3Match").setTextureFile("/Metallurgy/Match.png").setCreativeTab(utilityTab);
		LanguageRegistry.addName(match, "Match");
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(match), "X", "|", 'X', "dustPhosphorus", '|', Item.stick));
		
		id = utilityConfig.get("Item IDs", "Fertilizer", 25024).getInt();
		fertilizer = new ItemFertilizer(id).setItemName("M3Fertilizer").setTextureFile("/Metallurgy/Fertilizer.png").setCreativeTab(utilityTab);
		LanguageRegistry.addName(fertilizer, "Fertilizer");
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fertilizer), "dustPhosphorus", "dustMagnesium", "dustPotash"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fertilizer), "dustPhosphorus", "dustMagnesium", "dustSaltpeter"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fertilizer), "dustPhosphorus", "dustSaltpeter", "dustPotash"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fertilizer), "dustSaltpeter", "dustMagnesium", "dustPotash"));
		OreDictionary.registerOre("itemFertilizer", fertilizer);
		
		id = utilityConfig.get("Item IDs", "Tar", 25025).getInt();
		tar = new Item(id).setItemName("M3Tar").setTextureFile("/Metallurgy/Tar.png").setCreativeTab(utilityTab);
		LanguageRegistry.addName(tar, "Tar");
		OreDictionary.registerOre("itemTar", tar);
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(Item.gunpowder, "dustSulfur", "dustSaltpeter"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(Item.magmaCream, "itemTar", Item.blazePowder));
		GameRegistry.addRecipe(new ShapedOreRecipe(Block.pistonStickyBase, "T", "P", 'T', "itemTar", 'P', Block.pistonBase));
		
		GameRegistry.addSmelting(MetalInfoDatabase.getItem("Bitumen").itemID, new ItemStack(tar), 0.1F);
		
		utilityTab.setIconItem(fertilizer.itemID);
	}
	
	public Configuration initConfig(String name)
	{
		File fileDir = new File(MetallurgyCore.proxy.getMinecraftDir() + "/config/Metallurgy3");
    	fileDir.mkdir();
    	File cfgFile = new File(MetallurgyCore.proxy.getMinecraftDir() + "/config/Metallurgy3/Metallurgy" + name + ".cfg");
    	
        try
        {
            cfgFile.createNewFile();
        } catch (IOException e) {
            System.out.println(e);
        }
        
        return new Configuration(cfgFile);
	}
}
