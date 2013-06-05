package rebelkeithy.mods.metallurgy.metals;

import java.io.File;
import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.potion.Potion;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import rebelkeithy.mods.metallurgy.api.IOreInfo;
import rebelkeithy.mods.metallurgy.core.MetalInfoDatabase;
import rebelkeithy.mods.metallurgy.core.MetallurgyCore;
import rebelkeithy.mods.metallurgy.core.MetallurgyTabs;
import rebelkeithy.mods.metallurgy.core.metalsets.ISwordHitListener;
import rebelkeithy.mods.metallurgy.core.metalsets.MetalSet;
import rebelkeithy.mods.metallurgy.core.metalsets.OreInfo;
import rebelkeithy.mods.metallurgy.integration.ThaumcraftIntegration;
import rebelkeithy.mods.metallurgy.metals.utilityItems.ItemFertilizer;
import rebelkeithy.mods.metallurgy.metals.utilityItems.ItemIgniter;
import rebelkeithy.mods.metallurgy.metals.utilityItems.tnt.EntityLargeTNTPrimed;
import rebelkeithy.mods.metallurgy.metals.utilityItems.tnt.EntityMinersTNTPrimed;
import rebelkeithy.mods.metallurgy.metals.utilityItems.tnt.BlockLargeTNT;
import rebelkeithy.mods.metallurgy.metals.utilityItems.tnt.BlockMinersTNT;
import rebelkeithy.mods.particleregistry.ParticleRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid="Metallurgy3Base", name="Metallurgy 3 Base", version="3.0.0.0.10")
@NetworkMod(channels = {"MetallurgyBase"}, clientSideRequired = true, serverSideRequired = false)
public class MetallurgyMetals {

	public boolean isRelease = true;
	
	public static MetalSet baseSet;
	public static MetalSet preciousSet;
	public static MetalSet netherSet;
	public static MetalSet fantasySet;
	public static MetalSet enderSet;
	public static MetalSet utilitySet;
	
	public static CreativeTabs baseTab;
	public static CreativeTabs preciousTab;
	public static CreativeTabs netherTab;
	public static CreativeTabs fantasyTab;
	public static CreativeTabs enderTab;
	public static CreativeTabs utilityTab;
	
	public static Configuration baseConfig;
	public static Configuration utilityConfig;
	public static Configuration fantasyConfig;

	//Vanilla Items
	public static Item dustIron;
	public static Item dustGold;
	
	//Utility Items
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


	//public static Potion potion;

	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) 
	{        
        baseConfig = initConfig("Base");
        baseConfig.load();
        
        utilityConfig = initConfig("Utility");
        utilityConfig.load();
        
        fantasyConfig = initConfig("Fantasy");
        
        //potion = new MetallurgyPotion(21, false, 8356754).setPotionName("Low Gravity");
        
        if(isSetEnabled("Base"))
        	baseTab = new MetallurgyTabs("Metallurgy: Base");
        else
        	baseTab = CreativeTabs.tabBlock;
        if(isSetEnabled("Precious"))
        	preciousTab = new MetallurgyTabs("Metallurgy: Precious");
        if(isSetEnabled("Nether"))
        	netherTab = new MetallurgyTabs("Metallurgy: Nether");
        if(isSetEnabled("Fantasy"))
        	fantasyTab = new MetallurgyTabs("Metallurgy: Fantasy");
        if(isSetEnabled("Ender"))
        	enderTab = new MetallurgyTabs("Metallurgy: Ender");
        if(isSetEnabled("Utility"))
        	utilityTab = new MetallurgyTabs("Metallurgy: Utility");
        
        LanguageRegistry.instance().addStringLocalization("itemGroup.Metallurgy: Base", "Metallurgy: Base");
        LanguageRegistry.instance().addStringLocalization("itemGroup.Metallurgy: Precious", "Metallurgy: Precious");
        LanguageRegistry.instance().addStringLocalization("itemGroup.Metallurgy: Nether", "Metallurgy: Nether");
        LanguageRegistry.instance().addStringLocalization("itemGroup.Metallurgy: Fantasy", "Metallurgy: Fantasy");
        LanguageRegistry.instance().addStringLocalization("itemGroup.Metallurgy: Utility", "Metallurgy: Utility");
        LanguageRegistry.instance().addStringLocalization("itemGroup.Metallurgy: Ender", "Metallurgy: Ender");
        
        //TODO
        String filepath = event.getSourceFile().getAbsolutePath();
        if(!isRelease)
        {
        	//TODO: Note: Other users will need to point this to the directory continaning Metallurgy.jar
        	filepath = "C:/Users/Keithy/Documents/Metallurgy 3 1.5/source/Metallurgy 3/resources/mods/Metallurgy.jar";
        	//filepath = "D:\\minecraft\\source\\Metallurgy3\\resources\\mods\\Metallurgy.jar";
        	MetalInfoDatabase.readMetalDataFromFile("C:/Users/Keithy/Documents/Metallurgy 3 1.5/source/Metallurgy 3/resources/spreadsheet.csv");
    		MetalInfoDatabase.readItemDataFromFile(utilityConfig, "C:/Users/Keithy/Documents/Metallurgy 3 1.5/source/Metallurgy 3/resources/Items.csv", utilityTab);
        	
        } else {
        
        	MetalInfoDatabase.readMetalDataFromJar("spreadsheet.csv", filepath);
    		MetalInfoDatabase.readItemDataFromJar(utilityConfig, "Items.csv", filepath, utilityTab);
        
        }
		
		
		baseSet = new MetalSet("Base", MetalInfoDatabase.getSpreadsheetDataForSet("Base"), baseTab);
		preciousSet = new MetalSet("Precious", MetalInfoDatabase.getSpreadsheetDataForSet("Precious"), preciousTab);
		netherSet = new MetalSet("Nether", MetalInfoDatabase.getSpreadsheetDataForSet("Nether"), netherTab);
		fantasySet = new MetalSet("Fantasy", MetalInfoDatabase.getSpreadsheetDataForSet("Fantasy"), fantasyTab);
		enderSet = new MetalSet("Ender", MetalInfoDatabase.getSpreadsheetDataForSet("Ender"), enderTab);
		utilitySet = new MetalSet("Utility", MetalInfoDatabase.getSpreadsheetDataForSet("Utility"), utilityTab);
	}
	
	private boolean isSetEnabled(String setName) 
	{

		File fileDir = new File(MetallurgyCore.proxy.getMinecraftDir() + "/config/Metallurgy3");
    	fileDir.mkdir();
    	File cfgFile = new File(MetallurgyCore.proxy.getMinecraftDir() + "/config/Metallurgy3/Metallurgy" + setName + ".cfg");
    	
        try
        {
            cfgFile.createNewFile();
            System.out.println("[Metallurgy3] Successfully created/read configuration file for Metallurgy 3's metal set " + setName);
        }
        catch (IOException e)
        {
            System.out.println("[Metallurgy3] Could not create configuration file for Metallurgy 3 metal set " + setName + ". Reason:");
            System.out.println(e);
        }
        
        Configuration config = new Configuration(cfgFile);
		config.load();
		
		boolean enabled = config.get("!Enable", "Enable " + setName + " Set", true).getBoolean(true);
		
		config.save();
		return enabled;
	}

	@Init
	public void Init(FMLInitializationEvent event)
	{
		//TODO add config for vanilla dusts
		dustIron = new Item(5100).setUnlocalizedName("Metallurgy:Vanilla/IronDust").setCreativeTab(CreativeTabs.tabMaterials);
		dustGold = new Item(5101).setUnlocalizedName("Metallurgy:Vanilla/GoldDust").setCreativeTab(CreativeTabs.tabMaterials);
		FurnaceRecipes.smelting().addSmelting(dustIron.itemID, 0, new ItemStack(Item.ingotIron), 0.7F);
		FurnaceRecipes.smelting().addSmelting(dustGold.itemID, 0, new ItemStack(Item.ingotGold), 0.7F);
		
		LanguageRegistry.addName(dustIron, "Iron Dust");
		LanguageRegistry.addName(dustGold, "Gold Dust");
		OreDictionary.registerOre("dustIron", dustIron);
		OreDictionary.registerOre("dustGold", dustGold);
		
		Item debug = new ItemOreFinder(5102).setUnlocalizedName("stick").setCreativeTab(CreativeTabs.tabTools);
		
		createUtilityItems();
		utilityConfig.save();

		if(fantasySet.getOreInfo("Atral Silver").ore != null)
			fantasySet.getOreInfo("Astral Silver").ore.addDisplayListener(new DisplayListenerOreParticles("FantasyOre", 0.6, 0.8, 0.95));
		if(fantasySet.getOreInfo("Carmot").ore != null)
			fantasySet.getOreInfo("Carmot").ore.addDisplayListener(new DisplayListenerOreParticles("FantasyOre", 0.8, 0.8, 0.4));
		if(fantasySet.getOreInfo("Mithril").ore != null)
			fantasySet.getOreInfo("Mithril").ore.addDisplayListener(new DisplayListenerOreParticles("FantasyOre", 0.6, 0.9, 0.95));
		if(fantasySet.getOreInfo("Orichalcum").ore != null)
			fantasySet.getOreInfo("Orichalcum").ore.addDisplayListener(new DisplayListenerOreParticles("FantasyOre", 0.3, 0.5, 0.15));
		if(fantasySet.getOreInfo("Adamantine").ore != null)
			fantasySet.getOreInfo("Adamantine").ore.addDisplayListener(new DisplayListenerOreParticles("FantasyOre", 0.5, 0.2, 0.2));
		if(fantasySet.getOreInfo("Atlarus").ore != null)
			fantasySet.getOreInfo("Atlarus").ore.addDisplayListener(new DisplayListenerOreParticles("FantasyOre", 0.8, 0.8, 0.2));

		if(netherSet.getOreInfo("Midasium").ore != null)
			netherSet.getOreInfo("Midasium").ore.addDisplayListener(new DisplayListenerOreParticles("NetherOre", 1.0, 0.8, 0.25));
		if(netherSet.getOreInfo("Vyroxeres").ore != null)
			netherSet.getOreInfo("Vyroxeres").ore.addDisplayListener(new DisplayListenerVyroxeresOreParticles());
		if(netherSet.getOreInfo("Ceruclase").ore != null)
			netherSet.getOreInfo("Ceruclase").ore.addDisplayListener(new DisplayListenerOreParticles("NetherOre", 0.35, 0.6, 0.9));
		if(netherSet.getOreInfo("Kalendrite").ore != null)
			netherSet.getOreInfo("Kalendrite").ore.addDisplayListener(new DisplayListenerOreParticles("NetherOre", 0.8, 0.4, 0.8));
		if(netherSet.getOreInfo("Vulcanite").ore != null)
			netherSet.getOreInfo("Vulcanite").ore.addDisplayListener(new DisplayListenerVulcaniteOreParticles());
		if(netherSet.getOreInfo("Sanguinite").ore != null)
			netherSet.getOreInfo("Sanguinite").ore.addDisplayListener(new DisplayListenerOreParticles("NetherOre", 0.85, 0.0, 0.0));

		if(netherSet.getOreInfo("Sanguinite").ore != null)
		netherSet.getOreInfo("Vyroxeres").ore.addCollisionListener(new VyroxeresCollisionListener());
		
		addRailRecipes();
		addSwordEffects();
		
		proxy.registerParticles();
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event)
	{
		if(baseSet.getOreInfo("Steel").helmet != null)
			((MetallurgyTabs)baseTab).setIconItem(baseSet.getOreInfo("Steel").helmet.itemID);
		if(preciousSet.getOreInfo("Platinum").helmet != null)
			((MetallurgyTabs)preciousTab).setIconItem(preciousSet.getOreInfo("Platinum").helmet.itemID);
		if(netherSet.getOreInfo("Sanguinite").helmet != null)
			((MetallurgyTabs)netherTab).setIconItem(netherSet.getOreInfo("Sanguinite").helmet.itemID);
		if(fantasySet.getOreInfo("Tartarite").helmet != null)
			((MetallurgyTabs)fantasyTab).setIconItem(fantasySet.getOreInfo("Tartarite").helmet.itemID);
		if(enderSet.getOreInfo("Desichalkos").helmet != null)
			((MetallurgyTabs)enderTab).setIconItem(enderSet.getOreInfo("Desichalkos").helmet.itemID);
		
		createMidasiumRecipes();
		ThaumcraftIntegration.init();
	}
	
	public void createUtilityItems()
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.blazeRod), "I", "I", 'I', "ingotVulcanite"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(this.dustIron, 2), "dustShadow Iron", "dustIgnatius"));

		int id = utilityConfig.get("Item IDs", "HE TNT", 920).getInt();
		if(id != 0)
		{
			largeTNT = new BlockLargeTNT(id).setUnlocalizedName("M3HETNT").setCreativeTab(utilityTab);
			GameRegistry.registerBlock(largeTNT, "M3HETNT");
			EntityRegistry.registerModEntity(EntityLargeTNTPrimed.class, "LargeTNTEntity", 113, this, 64, 10, true);
			LanguageRegistry.addName(largeTNT, "HE TNT");
			if(utilityConfig.get("Recipes", "Enable HE TNT", true).getBoolean(true))
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(largeTNT, 4), "MPM", "PTP", "MPM", 'M', "dustMagnesium", 'P', "dustPhosphorus", 'T', Block.tnt));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(largeTNT, 4), "PMP", "MTM", "PMP", 'M', "dustMagnesium", 'P', "dustPhosphorus", 'T', Block.tnt));
			}
		}
		
		id = utilityConfig.get("Item IDs", "LE TNT", 921).getInt();
		if(id != 0)
		{
			minersTNT = new BlockMinersTNT(id).setUnlocalizedName("M3LETNT").setCreativeTab(utilityTab);
			GameRegistry.registerBlock(minersTNT, "M3LETNT");
			EntityRegistry.registerModEntity(EntityMinersTNTPrimed.class, "MinersTNTEntity", 113, this, 64, 10, true);
			LanguageRegistry.addName(minersTNT, "LE TNT");
			if(utilityConfig.get("Recipes", "Enable LE TNT", true).getBoolean(true))
			{
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(minersTNT, 4), "MPM", "PTP", "MPM", 'M', "dustSaltpeter", 'P', "dustSulfur", 'T', Block.tnt));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(minersTNT, 4), "MPM", "PTP", "MPM", 'P', "dustSaltpeter", 'M', "dustSulfur", 'T', Block.tnt));
			}
		}
		
		id = utilityConfig.get("Item IDs", "Magnesium Igniter", 29007).getInt();
		magnesiumIgniter = new ItemIgniter(id).setUnlocalizedName("Metallurgy:Utility/Igniter").setCreativeTab(utilityTab);
		LanguageRegistry.addName(magnesiumIgniter, "Magnesium Igniter");
		if(utilityConfig.get("Recipes", "Enable Magnesium Igniter", true).getBoolean(true))
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(magnesiumIgniter), "X ", " F", 'X', "dustMagnesium", 'F', Item.flintAndSteel));
		}
		
		id = utilityConfig.get("Item IDs", "Match", 29008).getInt();
		match = new ItemIgniter(id).setUnlocalizedName("Metallurgy:Utility/Match").setCreativeTab(utilityTab);
		LanguageRegistry.addName(match, "Match");
		if(utilityConfig.get("Recipes", "Enable Match", true).getBoolean(true))
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(match), "X", "|", 'X', "dustPhosphorus", '|', Item.stick));
		}
		
		id = utilityConfig.get("Item IDs", "Fertilizer", 29009).getInt();
		fertilizer = new ItemFertilizer(id).setUnlocalizedName("Metallurgy:Utility/Fertilizer").setCreativeTab(utilityTab);
		LanguageRegistry.addName(fertilizer, "Fertilizer");
		if(utilityConfig.get("Recipes", "Enable Fertilizer", true).getBoolean(true))
		{
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fertilizer, 8), "dustPhosphorus", "dustMagnesium", "dustPotash"));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fertilizer, 8), "dustPhosphorus", "dustMagnesium", "dustSaltpeter"));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fertilizer, 8), "dustPhosphorus", "dustSaltpeter", "dustPotash"));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fertilizer, 8), "dustSaltpeter", "dustMagnesium", "dustPotash"));
		}
		OreDictionary.registerOre("itemFertilizer", fertilizer);
		
		id = utilityConfig.get("Item IDs", "Tar", 29010).getInt();
		tar = new Item(id).setUnlocalizedName("Metallurgy:Utility/Tar").setCreativeTab(utilityTab);
		LanguageRegistry.addName(tar, "Tar");
		OreDictionary.registerOre("itemTar", tar);
		GameRegistry.addSmelting(MetalInfoDatabase.getItem("Bitumen").itemID, new ItemStack(tar), 0.1F);
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(Item.gunpowder, new ItemStack(Item.coal, 1, 1), "dustSulfur", "dustSaltpeter"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(Item.magmaCream, "itemTar", Item.blazePowder));
		GameRegistry.addRecipe(new ShapedOreRecipe(Block.pistonStickyBase, "T", "P", 'T', "itemTar", 'P', Block.pistonBase));
		
		if(isSetEnabled("Utility"))
			((MetallurgyTabs)utilityTab).setIconItem(fertilizer.itemID);
	}
	
	  
	private void addRailRecipes() 
	{
	   GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.rail, 4), "X X", "XSX", "X X", 'X', "ingotCopper", 'S', Item.stick));
	   GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.rail, 10), "X X", "XSX", "X X", 'X', "ingotBronze", 'S', Item.stick));
	   GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.rail, 14), "X X", "XSX", "X X", 'X', "ingotHepatizon", 'S', Item.stick));
	   GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.rail, 26), "X X", "XSX", "X X", 'X', "ingotDamascus Steel", 'S', Item.stick));
	   GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.rail, 22), "X X", "XSX", "X X", 'X', "ingotAngmallen", 'S', Item.stick));
	   GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.rail, 32), "X X", "XSX", "X X", 'X', "ingotAngmallen", 'S', Item.stick));
	   
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
	
	public void createMidasiumRecipes()
	{
		String[] ores = OreDictionary.getOreNames();
		System.out.println("Searching for dust for midsasium recipes");
		int count = 0;
		for(String name : ores)
		{
			if(name.contains("dust") && !name.toLowerCase().contains("tiny") && !name.toLowerCase().contains("clay") && !name.toLowerCase().contains("quartz"))
			{
				System.out.println("Adding recipe for " + name + " midasium = gold");
				GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(dustGold), "dustMidasium", name));
				count++;
			}
		}
	}
	
	public void addSwordEffects()
	{
		ISwordHitListener swordEffects = new NetherSwordHitListener();
		MinecraftForge.EVENT_BUS.register(swordEffects); // Registers the on death event needed by Midasium's looting effect
		if(netherSet.getOreInfo("Ignatius").sword != null)
		{
			netherSet.getOreInfo("Ignatius").sword.addHitListener(swordEffects);
			netherSet.getOreInfo("Ignatius").sword.setSubText("cIgnite I");
		}
		if(netherSet.getOreInfo("Shadow Iron").sword != null)
		{
			netherSet.getOreInfo("Shadow Iron").sword.addHitListener(swordEffects);
			netherSet.getOreInfo("Shadow Iron").sword.setSubText("cWeakness I");
		}
		if(netherSet.getOreInfo("Shadow Steel").sword != null)
		{
			netherSet.getOreInfo("Shadow Steel").sword.addHitListener(swordEffects);
			netherSet.getOreInfo("Shadow Steel").sword.setSubText("7Weakness II");
		}
		if(netherSet.getOreInfo("Midasium").sword != null)
		{
			// Midsium'ss effect comes from the onDeath event, not the onHit method
			netherSet.getOreInfo("Midasium").sword.setSubText("7Looting I");
		}
		if(netherSet.getOreInfo("Vyroxeres").sword != null)
		{
			netherSet.getOreInfo("Vyroxeres").sword.addHitListener(swordEffects);
			netherSet.getOreInfo("Vyroxeres").sword.setSubText("cPoison I");
		}
		if(netherSet.getOreInfo("Ceruclase").sword != null)
		{
			netherSet.getOreInfo("Ceruclase").sword.addHitListener(swordEffects);
			netherSet.getOreInfo("Ceruclase").sword.setSubText("cSlowness");
		}
		if(netherSet.getOreInfo("Inolashite").sword != null)
		{
			netherSet.getOreInfo("Inolashite").sword.addHitListener(swordEffects);
			netherSet.getOreInfo("Inolashite").sword.setSubText("7Poison, Slowness");
		}
		if(netherSet.getOreInfo("Kalendrite").sword != null)
		{
			netherSet.getOreInfo("Kalendrite").sword.addHitListener(swordEffects);
			netherSet.getOreInfo("Kalendrite").sword.setSubText("7Regen");
		}
		if(netherSet.getOreInfo("Amordrine").sword != null)
		{
			netherSet.getOreInfo("Amordrine").sword.addHitListener(swordEffects);
			netherSet.getOreInfo("Amordrine").sword.setSubText("7Healing");
		}
		if(netherSet.getOreInfo("Vulcanite").sword != null)
		{
			netherSet.getOreInfo("Vulcanite").sword.addHitListener(swordEffects);
			netherSet.getOreInfo("Vulcanite").sword.setSubText("cIgnite II");
		}
		if(netherSet.getOreInfo("Sanguinite").sword != null)
		{
			netherSet.getOreInfo("Sanguinite").sword.addHitListener(swordEffects);
			netherSet.getOreInfo("Sanguinite").sword.setSubText("cWither I");
		}
		
		swordEffects = new FantasySwordHitListener();
		MinecraftForge.EVENT_BUS.register(swordEffects); // Registers the on death event needed by Astral Silver's and Carmot's looting effect
		
		if(netherSet.getOreInfo("Sanguinite").sword != null)
		{
			fantasySet.getOreInfo("Deep Iron").sword.addHitListener(swordEffects);
			fantasySet.getOreInfo("Deep Iron").sword.setSubText("cBlindness I");
		}
		if(netherSet.getOreInfo("Sanguinite").sword != null)
		{
			fantasySet.getOreInfo("Black Steel").sword.addHitListener(swordEffects);
			fantasySet.getOreInfo("Black Steel").sword.setSubText("cBlindness II");
		}
		if(netherSet.getOreInfo("Sanguinite").sword != null)
		{
			fantasySet.getOreInfo("Oureclase").sword.addHitListener(swordEffects);
			fantasySet.getOreInfo("Oureclase").sword.setSubText("7Resistance I");
		}
		if(netherSet.getOreInfo("Sanguinite").sword != null)
		{
			//fantasySet.getOreInfo("Astral Silver").sword.addHitListener(swordEffects);
			fantasySet.getOreInfo("Astral Silver").sword.setSubText("7Looting I");
		}
		if(netherSet.getOreInfo("Sanguinite").sword != null)
		{
			//fantasySet.getOreInfo("Carmot").sword.addHitListener(swordEffects);
			fantasySet.getOreInfo("Carmot").sword.setSubText("7Looting II");
		}
		if(netherSet.getOreInfo("Sanguinite").sword != null)
		{
			fantasySet.getOreInfo("Mithril").sword.addHitListener(swordEffects);
			fantasySet.getOreInfo("Mithril").sword.setSubText("7Haste I");
		}
		if(netherSet.getOreInfo("Sanguinite").sword != null)
		{
			fantasySet.getOreInfo("Quicksilver").sword.addHitListener(swordEffects);
			fantasySet.getOreInfo("Quicksilver").sword.setSubText("7Speed I");
		}
		if(netherSet.getOreInfo("Sanguinite").sword != null)
		{
			fantasySet.getOreInfo("Haderoth").sword.addHitListener(swordEffects);
			fantasySet.getOreInfo("Haderoth").sword.setSubText("cHaste I, Ignite II");
		}
		if(netherSet.getOreInfo("Sanguinite").sword != null)
		{
			fantasySet.getOreInfo("Orichalcum").sword.addHitListener(swordEffects);
			fantasySet.getOreInfo("Orichalcum").sword.setSubText("cResistance II");
		}
		if(netherSet.getOreInfo("Sanguinite").sword != null)
		{
			fantasySet.getOreInfo("Celenegil").sword.addHitListener(swordEffects);
			fantasySet.getOreInfo("Celenegil").sword.setSubText("7Resistance III");
		}
		if(netherSet.getOreInfo("Sanguinite").sword != null)
		{
			fantasySet.getOreInfo("Adamantine").sword.addHitListener(swordEffects);
			fantasySet.getOreInfo("Adamantine").sword.setSubText("7Fire Resist I, Ignite II");
		}
		if(netherSet.getOreInfo("Sanguinite").sword != null)
		{
			fantasySet.getOreInfo("Atlarus").sword.addHitListener(swordEffects);
			fantasySet.getOreInfo("Atlarus").sword.setSubText("7Strength II");
		}
		if(netherSet.getOreInfo("Sanguinite").sword != null)
		{
			fantasySet.getOreInfo("Tartarite").sword.addHitListener(swordEffects);
			fantasySet.getOreInfo("Tartarite").sword.setSubText("cWither, Igntite II");
		}
	}
}
