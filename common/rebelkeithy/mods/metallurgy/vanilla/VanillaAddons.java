package rebelkeithy.mods.metallurgy.vanilla;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import java.io.File;
import java.io.IOException;

import rebelkeithy.mods.metablock.MetaBlock;
import rebelkeithy.mods.metablock.SubBlock;
import rebelkeithy.mods.metallurgy.core.MetallurgyCore;

public class VanillaAddons {
	public static SubBlock goldBrick;
	public static SubBlock ironBrick;
	
	public static Item dustIron;
	public static Item dustGold;
	
	public static int goldBrickID;
	public static int ironBrickID;
	public static int goldBrickMeta;
	public static int ironBrickMeta;
	
	public static int ironDustID;
	public static int goldDustID;
	
	public static void init()
	{
		initConfig();
		
		goldBrick = new SubBlock(goldBrickID, goldBrickMeta).setTextureFile("/Overrides.png").setBlockTextureIndex(16*5+1).setBlockName("GoldBricks").setCreativeTab(CreativeTabs.tabBlock);
		ironBrick = new SubBlock(ironBrickID, ironBrickMeta).setTextureFile("/Overrides.png").setBlockTextureIndex(16*5).setBlockName("IronBricks").setCreativeTab(CreativeTabs.tabBlock);
		MetaBlock.registerID(goldBrickID);
		MetaBlock.registerID(ironBrickID);
		
		dustIron = new Item(ironDustID).setTextureFile("/Overrides.png").setIconCoord(5, 4).setItemName("MetallurgyIronDust").setCreativeTab(CreativeTabs.tabMaterials);
		dustGold = new Item(goldDustID).setTextureFile("/Overrides.png").setIconCoord(6, 4).setItemName("MetallurgyGoldDust").setCreativeTab(CreativeTabs.tabMaterials);
	}
	
	public static void initConfig()
	{
		File fileDir = new File(MetallurgyCore.proxy.getMinecraftDir() + "/config/Metallurgy3");
    	fileDir.mkdir();
    	File cfgFile = new File(MetallurgyCore.proxy.getMinecraftDir() + "/config/Metallurgy3/MetalSetVanilla.cfg");
    	
        try
        {
            cfgFile.createNewFile();
            System.out.println("[Metallurgy3] Successfully created/read configuration file for Metallurgy 3's metal set Vanilla");
        }
        catch (IOException e)
        {
            System.out.println("[Metallurgy3] Could not create configuration file for Metallurgy 3 metal set Vanilla. Reason:");
            System.out.println(e);
        }
        
		Configuration config = new Configuration(cfgFile);
		config.load();
		
		ironBrickID = Integer.parseInt(config.get("Iron", "Brick ID", "903:0").value.split(":")[0]);
		ironBrickMeta = Integer.parseInt(config.get("Iron", "Brick ID", "903:0").value.split(":")[1]);
		
		goldBrickID = Integer.parseInt(config.get("Gold", "Brick ID", "903:1").value.split(":")[0]);
		goldBrickMeta = Integer.parseInt(config.get("Gold", "Brick ID", "903:1").value.split(":")[1]);
		
		ironDustID = config.get("Iron", "Iron Dust ID", 26200).getInt();
		goldDustID = config.get("Gold", "Gold Dust ID", 26201).getInt();
		
		config.save();
	}
	
	public static void load()
	{
		OreDictionary.registerOre("dustIron", dustIron);
		OreDictionary.registerOre("dustGold", dustGold);
		
		GameRegistry.addRecipe(new ItemStack(goldBrickID, 4, goldBrickMeta), "XX", "XX", 'X', Item.ingotGold);
		GameRegistry.addRecipe(new ItemStack(ironBrickID, 4, ironBrickMeta), "XX", "XX", 'X', Item.ingotIron);
		GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotGold), new ItemStack(goldBrickID, 1, goldBrickMeta));
		GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron), new ItemStack(ironBrickID, 1, ironBrickMeta));
		
	}

	public static void registerNames() 
	{
		System.out.println("Setting vanilla addon names " + (new ItemStack(goldBrickID, 1, goldBrickMeta)).getItem().getItemNameIS(new ItemStack(goldBrickID, 1, goldBrickMeta)) + " " + (new ItemStack(ironBrickID, 1, ironBrickMeta)).getItem().getItemNameIS(new ItemStack(ironBrickID, 1, ironBrickMeta)));
		LanguageRegistry.addName(new ItemStack(goldBrickID, 1, goldBrickMeta), "Gold Bricks");
		LanguageRegistry.addName(new ItemStack(ironBrickID, 1, ironBrickMeta), "Iron Bricks");
		
		LanguageRegistry.addName(dustIron, "Iron Dust");
		LanguageRegistry.addName(dustGold, "Gold Dust");
		
	}
}
