package rebelkeithy.mods.metallurgy.machines;

import java.io.File;
import java.io.IOException;

import net.minecraftforge.common.Configuration;
import rebelkeithy.mods.metallurgy.core.MetallurgyCore;

public class ConfigMachines 
{
	public static boolean crusherEnabled = true;
	public static int crusherID = 910;
	public static float stoneCrusherSpeed = 60;
	public static float copperCrusherSpeed = 30;
	public static float bronzeCrusherSpeed = 20;
	public static float ironCrusherSpeed = 15;
	public static float steelCrusherSpeed = 10;
	
	public static boolean furnaceEnabled = true;
	public static int furnaceID = 911;
	public static float copperFurnaceSpeed = 9.5F;
	public static float bronzeFurnaceSpeed = 9;
	public static float ironFurnaceSpeed = 8;
	public static float steelFurnaceSpeed = 7;
	
	public static boolean forgeEnabled = true;
	public static int forgeID = 912;
	public static float[] forgeSpeeds = new float[] {6, 5.5F, 5, 4.5F, 4, 3.5F, 3, 2};
	public static int[] forgeBuckets = new int[] {10, 20, 30, 40, 50, 60, 80, 100};
	public static boolean smelterDropsLava = false;
	
	public static boolean abstractorEnabled = true;
	public static int abstractorID = 913;
	
	public static boolean chestEnabled = true;
	public static int chestID = 914;
	
	public static boolean mintEnabled = true;
	public static int mintID = 915;
	public static boolean mintStorageEnabled = true;
	public static int mintStorageID = 916;
	
	
	public static int coinID = 29002;
	public static int stackID = 29003;
	public static int coinBagID = 29004;
	public static int bullionID = 29005;
	
	public static boolean tradesEnabled = true;
	
	public static int lanternID = 918;
	public static int glassDustID = 29006;
	public static int coloredGlassID = 917;
	
	public static boolean ladderEnabled = true;
	public static int ladderID = 919;
	
	private static int ironDustID = 29000;
	private static int goldDustID = 29001;
	
	public static void initConfig()
	{
		File fileDir = new File(MetallurgyCore.proxy.getMinecraftDir() + "/config/Metallurgy3");
    	fileDir.mkdir();
    	File cfgFile = new File(MetallurgyCore.proxy.getMinecraftDir() + "/config/Metallurgy3/MetallurgyMachines.cfg");
    	
        try
        {
            cfgFile.createNewFile();
        } catch (IOException e) {
            System.out.println(e);
        }
        
		Configuration config = new Configuration(cfgFile);
		
		crusherID = config.get("Block IDs", "Crusher", crusherID).getInt();
		furnaceID = config.get("Block IDs", "Metal Furnace", furnaceID).getInt();
		forgeID = config.get("Block IDs", "Smelter", forgeID).getInt();
		abstractorID = config.get("Block IDs", "Abstractor", abstractorID).getInt();
		chestID = config.get("Block IDs", "Precious Chest", chestID).getInt();
		mintID = config.get("Block IDs", "Mint", mintID).getInt();
		mintStorageID = config.get("Block IDs", "Mint Storage", mintStorageID).getInt();
		coloredGlassID = config.get("Block IDs", "Colored Glass", coloredGlassID).getInt();
		lanternID = config.get("Block IDs", "Lanterns", lanternID).getInt();
		ladderID = config.get("Block IDs", "Ladders", ladderID).getInt();
		
		ironDustID = config.get("Item IDs", "Iron Dust", ironDustID).getInt();
		goldDustID = config.get("Item IDs", "Iron Dust", goldDustID).getInt();
		coinID = config.get("Item IDs", "Coin", coinID).getInt();
		stackID = config.get("Item IDs", "Stack", stackID).getInt();
		coinBagID = config.get("Item IDs", "Coin Bag", coinBagID).getInt();
		bullionID = config.get("Item IDs", "Bullion", bullionID).getInt();
		glassDustID = config.get("Item IDs", "Glass Dusts", glassDustID).getInt();
		
		stoneCrusherSpeed = config.get("Crusher Speeds", "Stone", stoneCrusherSpeed*1000).getInt()/1000F;
		copperCrusherSpeed = config.get("Crusher Speeds", "Copper", copperCrusherSpeed*1000).getInt()/1000F;
		bronzeCrusherSpeed = config.get("Crusher Speeds", "Bronze", bronzeCrusherSpeed*1000).getInt()/1000F;
		ironCrusherSpeed = config.get("Crusher Speeds", "Iron", ironCrusherSpeed*1000).getInt()/1000F;
		steelCrusherSpeed = config.get("Crusher Speeds", "Steel", steelCrusherSpeed*1000).getInt()/1000F;
		
		copperFurnaceSpeed = config.get("Furnace Speeds", "Copper", copperFurnaceSpeed*1000).getInt()/1000F;
		bronzeFurnaceSpeed = config.get("Furnace Speeds", "Bronze", bronzeFurnaceSpeed*1000).getInt()/1000F;
		ironFurnaceSpeed = config.get("Furnace Speeds", "Iron", ironFurnaceSpeed*1000).getInt()/1000F;
		steelFurnaceSpeed = config.get("Furnace Speeds", "Steel", steelFurnaceSpeed*1000).getInt()/1000F;
		
		String[] forgeNames = {"Ignatius", "Shadow Iron", "Shadow Steel", "Vyroxeres", "Inolashite", "Kalendrite", "Vulcanite", "Sanguinite"};
		for(int i = 0; i < 8; i++)
		{
			forgeSpeeds[i] = config.get("Forge.Speeds", forgeNames[i], forgeSpeeds[i]).getInt();
			forgeBuckets[i] = config.get("Forge.Buckets", forgeNames[i], forgeBuckets[i]).getInt();
		}
		smelterDropsLava = config.get("Forge", "Drops Lava", smelterDropsLava).getBoolean(smelterDropsLava);
	}
}
