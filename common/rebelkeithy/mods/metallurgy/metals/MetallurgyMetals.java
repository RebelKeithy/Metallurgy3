package rebelkeithy.mods.metallurgy.metals;

import java.io.File;
import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import rebelkeithy.mods.metallurgy.core.MetalInfoDatabase;
import rebelkeithy.mods.metallurgy.core.MetallurgyCore;
import rebelkeithy.mods.metallurgy.core.MetallurgyTabs;
import rebelkeithy.mods.metallurgy.core.metalsets.ISwordHitListener;
import rebelkeithy.mods.metallurgy.core.metalsets.MetalSet;
import rebelkeithy.mods.metallurgy.metals.utilityItems.ItemFertilizer;
import rebelkeithy.mods.metallurgy.metals.utilityItems.ItemIgniter;
import rebelkeithy.mods.metallurgy.metals.utilityItems.tnt.EntityLargeTNTPrimed;
import rebelkeithy.mods.metallurgy.metals.utilityItems.tnt.EntityMinersTNTPrimed;
import rebelkeithy.mods.metallurgy.metals.utilityItems.tnt.LargeTNT;
import rebelkeithy.mods.metallurgy.metals.utilityItems.tnt.MinersTNT;
import rebelkeithy.mods.particleregistry.ParticleRegistry;
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

@Mod(modid="Metallurgy3Base", name="Metallurgy 3 Base", version="1.4.7-1.11.13-1a")
@NetworkMod(channels = {"MetallurgyBase"}, clientSideRequired = true, serverSideRequired = false)
public class MetallurgyMetals {

	public static MetallurgyTabs utilityTab;
	
	public static MetalSet baseSet;
	public static MetalSet preciousSet;
	public static MetalSet netherSet;
	public static MetalSet fantasySet;
	public static MetalSet utilitySet;
	
	public static CreativeTabs baseTab;
	
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
		
		baseTab = new CreativeTabs("Metallurgy: Base");
		
		baseSet = new MetalSet("Base", MetalInfoDatabase.getSpreadsheetDataForSet("Base"), baseTab);
		preciousSet = new MetalSet("Precious", MetalInfoDatabase.getSpreadsheetDataForSet("Precious"), baseTab);
		netherSet = new MetalSet("Nether", MetalInfoDatabase.getSpreadsheetDataForSet("Nether"), baseTab);
		fantasySet = new MetalSet("Fantasy", MetalInfoDatabase.getSpreadsheetDataForSet("Fantasy"), baseTab);
		utilitySet = new MetalSet("Utility", MetalInfoDatabase.getSpreadsheetDataForSet("Utility"), baseTab);
	}
	
	@Init
	public void Init(FMLInitializationEvent event)
	{
		//TODO add config for vanilla dusts
		dustIron = new Item(5100).setUnlocalizedName("Metallurgy:Vanilla/IronDust").setCreativeTab(CreativeTabs.tabMaterials);
		dustGold = new Item(5101).setUnlocalizedName("Metallurgy:Vanilla/GoldDust").setCreativeTab(CreativeTabs.tabMaterials);
		LanguageRegistry.addName(dustIron, "Iron Dust");
		LanguageRegistry.addName(dustGold, "Gold Dust");
		OreDictionary.registerOre("dustIron", dustIron);
		OreDictionary.registerOre("dustGold", dustGold);
		
		createUtilityItems();
		utilityConfig.save();
		
		ParticleRegistry.registerParticle("FantasyOre", EntityFantasyOreFX.class);

		fantasySet.getOreInfo("Astral Silver").ore.addDisplayListener(new DisplayListenerOreParticles("FantasyOre", 0.6, 0.8, 0.95));
		fantasySet.getOreInfo("Carmot").ore.addDisplayListener(new DisplayListenerOreParticles("FantasyOre", 0.8, 0.8, 0.4));
		fantasySet.getOreInfo("Mithril").ore.addDisplayListener(new DisplayListenerOreParticles("FantasyOre", 0.6, 0.9, 0.95));
		fantasySet.getOreInfo("Orichalcum").ore.addDisplayListener(new DisplayListenerOreParticles("FantasyOre", 0.3, 0.5, 0.15));
		fantasySet.getOreInfo("Adamantine").ore.addDisplayListener(new DisplayListenerOreParticles("FantasyOre", 0.5, 0.2, 0.2));
		fantasySet.getOreInfo("Atlarus").ore.addDisplayListener(new DisplayListenerOreParticles("FantasyOre", 0.8, 0.8, 0.2));
		
		ParticleRegistry.registerParticle("NetherOre", EntityNetherOreFX.class);
		
		netherSet.getOreInfo("Midasium").ore.addDisplayListener(new DisplayListenerOreParticles("NetherOre", 1.0, 0.8, 0.25));
		netherSet.getOreInfo("Vyroxeres").ore.addDisplayListener(new DisplayListenerVyroxeresOreParticles());
		netherSet.getOreInfo("Ceruclase").ore.addDisplayListener(new DisplayListenerOreParticles("NetherOre", 0.35, 0.6, 0.9));
		netherSet.getOreInfo("Kalendrite").ore.addDisplayListener(new DisplayListenerOreParticles("NetherOre", 0.8, 0.4, 0.8));
		netherSet.getOreInfo("Vulcanite").ore.addDisplayListener(new DisplayListenerVulcaniteOreParticles());
		netherSet.getOreInfo("Sanguinite").ore.addDisplayListener(new DisplayListenerOreParticles("NetherOre", 0.85, 0.0, 0.0));
		
		netherSet.getOreInfo("Vyroxeres").ore.addCollisionListener(new VyroxeresCollisionListener());
		
		addSwordEffects();
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event)
	{
		createMidasiumRecipes();
	}
	
	public void createUtilityItems()
	{
		int id = utilityConfig.get("Item IDs", "HE TNT", 920).getInt();
		largeTNT = new LargeTNT(id).setUnlocalizedName("M3HETNT").setCreativeTab(utilityTab);
		GameRegistry.registerBlock(largeTNT, "M3HETNT");
		EntityRegistry.registerModEntity(EntityLargeTNTPrimed.class, "LargeTNTEntity", 113, this, 64, 10, true);
		LanguageRegistry.addName(largeTNT, "HE TNT");
		
		id = utilityConfig.get("Item IDs", "LE TNT", 921).getInt();
		minersTNT = new MinersTNT(id).setUnlocalizedName("M3LETNT").setCreativeTab(utilityTab);
		GameRegistry.registerBlock(minersTNT, "M3LETNT");
		EntityRegistry.registerModEntity(EntityMinersTNTPrimed.class, "MinersTNTEntity", 113, this, 64, 10, true);
		LanguageRegistry.addName(minersTNT, "LE TNT");
		
		id = utilityConfig.get("Item IDs", "Magnesium Igniter", 29007).getInt();
		magnesiumIgniter = new ItemIgniter(id).setUnlocalizedName("Metallurgy:utility/Igniter").setCreativeTab(utilityTab);
		LanguageRegistry.addName(magnesiumIgniter, "Magnesium Igniter");
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(magnesiumIgniter), "X ", " F", 'X', "dustMagnesium", 'F', Item.flintAndSteel));

		id = utilityConfig.get("Item IDs", "Match", 29008).getInt();
		match = new ItemIgniter(id).setUnlocalizedName("Metallurgy:utility/Match").setCreativeTab(utilityTab);
		LanguageRegistry.addName(match, "Match");
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(match), "X", "|", 'X', "dustPhosphorus", '|', Item.stick));
		
		id = utilityConfig.get("Item IDs", "Fertilizer", 29009).getInt();
		fertilizer = new ItemFertilizer(id).setUnlocalizedName("Metallurgy:utility/Fertilizer").setCreativeTab(utilityTab);
		LanguageRegistry.addName(fertilizer, "Fertilizer");
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fertilizer), "dustPhosphorus", "dustMagnesium", "dustPotash"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fertilizer), "dustPhosphorus", "dustMagnesium", "dustSaltpeter"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fertilizer), "dustPhosphorus", "dustSaltpeter", "dustPotash"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fertilizer), "dustSaltpeter", "dustMagnesium", "dustPotash"));
		OreDictionary.registerOre("itemFertilizer", fertilizer);
		
		id = utilityConfig.get("Item IDs", "Tar", 29010).getInt();
		tar = new Item(id).setUnlocalizedName("Metallurgy:utility/Tar").setCreativeTab(utilityTab);
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
	
	public void createMidasiumRecipes()
	{
		String[] ores = OreDictionary.getOreNames();
		System.out.println("Searching for dust for midsasium recipes");
		int count = 0;
		for(String name : ores)
		{
			if(name.contains("dust"))
			{
				System.out.println("Adding recipe for " + name + " midasium = gold");
				GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(dustGold), "dustMidasium", name));
				count++;
			}
		}
		System.out.println("found " + count + " recipes");
	}
	
	public void addSwordEffects()
	{
		ISwordHitListener swordEffects = new NetherSwordHitListener();
		MinecraftForge.EVENT_BUS.register(swordEffects); // Registers the on death event needed by Midasium's looting effect
		netherSet.getOreInfo("Ignatius").sword.addHitListener(swordEffects);
		netherSet.getOreInfo("Ignatius").sword.setSubText("cIgnite I");
		netherSet.getOreInfo("Shadow Iron").sword.addHitListener(swordEffects);
		netherSet.getOreInfo("Shadow Iron").sword.setSubText("cWeakness I");
		netherSet.getOreInfo("Shadow Steel").sword.addHitListener(swordEffects);
		netherSet.getOreInfo("Shadow Steel").sword.setSubText("7Weakness II");
		// Midsium'ss effect comes from the onDeath event, not the onHit method
		netherSet.getOreInfo("Midasium").sword.setSubText("7Looting I");
		netherSet.getOreInfo("Vyroxeres").sword.addHitListener(swordEffects);
		netherSet.getOreInfo("Vyroxeres").sword.setSubText("cPoison I");
		netherSet.getOreInfo("Ceruclase").sword.addHitListener(swordEffects);
		netherSet.getOreInfo("Ceruclase").sword.setSubText("cSlowness");
		netherSet.getOreInfo("Inolashite").sword.addHitListener(swordEffects);
		netherSet.getOreInfo("Inolashite").sword.setSubText("7Poison, Sloness");
		netherSet.getOreInfo("Kalendrite").sword.addHitListener(swordEffects);
		netherSet.getOreInfo("Kalendrite").sword.setSubText("7Regen");
		netherSet.getOreInfo("Amordrine").sword.addHitListener(swordEffects);
		netherSet.getOreInfo("Amordrine").sword.setSubText("7Healing");
		netherSet.getOreInfo("Vulcanite").sword.addHitListener(swordEffects);
		netherSet.getOreInfo("Vulcanite").sword.setSubText("cIgnite II");
		netherSet.getOreInfo("Sanguinite").sword.addHitListener(swordEffects);
		netherSet.getOreInfo("Sanguinite").sword.setSubText("cWither I");
		
		swordEffects = new FantasySwordHitListener();
		MinecraftForge.EVENT_BUS.register(swordEffects); // Registers the on death event needed by Astral Silver's and Carmot's looting effect
		fantasySet.getOreInfo("Deep Iron").sword.addHitListener(swordEffects);
		fantasySet.getOreInfo("Deep Iron").sword.setSubText("cBlindness I");
		fantasySet.getOreInfo("Black Steel").sword.addHitListener(swordEffects);
		fantasySet.getOreInfo("Black Steel").sword.setSubText("cBlindness II");
		fantasySet.getOreInfo("Oureclase").sword.addHitListener(swordEffects);
		fantasySet.getOreInfo("Oureclase").sword.setSubText("7Resistance I");
		//fantasySet.getOreInfo("Astral Silver").sword.addHitListener(swordEffects);
		fantasySet.getOreInfo("Astral Silver").sword.setSubText("7Looting I");
		//fantasySet.getOreInfo("Carmot").sword.addHitListener(swordEffects);
		fantasySet.getOreInfo("Carmot").sword.setSubText("7Looting II");
		fantasySet.getOreInfo("Mithril").sword.addHitListener(swordEffects);
		fantasySet.getOreInfo("Mithril").sword.setSubText("7Haste I");
		fantasySet.getOreInfo("Quicksilver").sword.addHitListener(swordEffects);
		fantasySet.getOreInfo("Quicksilver").sword.setSubText("7Speed I");
		fantasySet.getOreInfo("Haderoth").sword.addHitListener(swordEffects);
		fantasySet.getOreInfo("Haderoth").sword.setSubText("cHaste I, Ignite II");
		fantasySet.getOreInfo("Orichalcum").sword.addHitListener(swordEffects);
		fantasySet.getOreInfo("Orichalcum").sword.setSubText("cResistance II");
		fantasySet.getOreInfo("Celenegil").sword.addHitListener(swordEffects);
		fantasySet.getOreInfo("Celenegil").sword.setSubText("7Resistance III");
		fantasySet.getOreInfo("Adamantine").sword.addHitListener(swordEffects);
		fantasySet.getOreInfo("Adamantine").sword.setSubText("7Fire Resist I, Ignite II");
		fantasySet.getOreInfo("Atlarus").sword.addHitListener(swordEffects);
		fantasySet.getOreInfo("Atlarus").sword.setSubText("7Strength II");
		fantasySet.getOreInfo("Tartarite").sword.addHitListener(swordEffects);
		fantasySet.getOreInfo("Tartarite").sword.setSubText("cWither, Igntite II");
	}
}
