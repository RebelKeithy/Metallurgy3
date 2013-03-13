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
		
		goldBrick = new SubBlock(goldBrickID, goldBrickMeta, "Metallurgy:GoldBrick").setUnlocalizedName("GoldBricks").setCreativeTab(CreativeTabs.tabBlock);
		ironBrick = new SubBlock(ironBrickID, ironBrickMeta, "Metallurgy:IronBrick").setUnlocalizedName("IronBricks").setCreativeTab(CreativeTabs.tabBlock);
		MetaBlock.registerID(goldBrickID);
		MetaBlock.registerID(ironBrickID);
		
		dustIron = new Item(ironDustID).setUnlocalizedName("MetallurgyIronDust").setCreativeTab(CreativeTabs.tabMaterials);
		dustGold = new Item(goldDustID).setUnlocalizedName("MetallurgyGoldDust").setCreativeTab(CreativeTabs.tabMaterials);
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
		
		ironBrickID = Integer.parseInt(config.get("Iron", "Brick ID", "900:3").getString().split(":")[0]);
		ironBrickMeta = Integer.parseInt(config.get("Iron", "Brick ID", "900:3").getString().split(":")[1]);
		
		goldBrickID = Integer.parseInt(config.get("Gold", "Brick ID", "900:4").getString().split(":")[0]);
		goldBrickMeta = Integer.parseInt(config.get("Gold", "Brick ID", "900:4").getString().split(":")[1]);
		
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
		LanguageRegistry.addName(new ItemStack(goldBrickID, 1, goldBrickMeta), "Gold Bricks");
		LanguageRegistry.addName(new ItemStack(ironBrickID, 1, ironBrickMeta), "Iron Bricks");
		
		LanguageRegistry.addName(dustIron, "Iron Dust");
		LanguageRegistry.addName(dustGold, "Gold Dust");
		
	}
}
