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
    public static float[] forgeSpeeds = new float[]
    { 6, 5.5F, 5, 4.5F, 4, 3.5F, 3, 2 };
    public static int[] forgeBuckets = new int[]
    { 10, 20, 30, 40, 50, 60, 80, 100 };
    public static boolean smelterDropsLava = false;

    public static boolean abstractorEnabled = true;
    public static int abstractorID = 913;

    public static boolean chestEnabled = true;
    public static int chestID = 914;

    public static boolean mintEnabled = true;
    public static int mintID = 915;
    public static boolean mintStorageEnabled = true;
    public static int mintStorageID = 916;

    public static boolean enchanterEnabled = true;
    public static int enchanterID = 930;

    public static int coinID = 29002;
    public static int stackID = 29003;
    public static int coinBagID = 29004;
    public static int bullionID = 29005;
    public static int goldCogID = 29017;

    public static boolean tradesEnabled = true;

    public static int lanternID = 918;
    public static int glassDustID = 29006;
    public static int coloredGlassID = 917;

    public static boolean ladderEnabled = true;
    public static int ladderID = 919;

    public static int laserID = 922;
    
    public static int[] extractorSpeeds = new int[11];
    public static double[] xpBonus = new double[11];
    public static int xpTankID = 923;
    public static int pylonID = 924;

    public static int storageAccessorID = 919;
	public static int storageBlockID = 920;
	
    public static int orbID = 29018;
	public static int sawDustID = 29019;

    public static void initConfig()
    {
        final File fileDir = new File(MetallurgyCore.proxy.getMinecraftDir() + "/config/Metallurgy3");
        fileDir.mkdir();
        final File cfgFile = new File(MetallurgyCore.proxy.getMinecraftDir() + "/config/Metallurgy3/MetallurgyMachines.cfg");

        try
        {
            cfgFile.createNewFile();
        } catch (final IOException e)
        {
        	 MetallurgyCore.log.info(e.getLocalizedMessage());
        }

        final Configuration config = new Configuration(cfgFile);
        
        crusherEnabled = config.get("Machines", "CrusherEnabled", crusherEnabled).getBoolean(crusherEnabled);
        furnaceEnabled = config.get("Machines", "FurnanceEnabled", furnaceEnabled).getBoolean(furnaceEnabled);
        forgeEnabled = config.get("Machines", "SmelterEnabled", forgeEnabled).getBoolean(forgeEnabled);
        abstractorEnabled = config.get("Machines", "AbstractorEnabled", abstractorEnabled).getBoolean(abstractorEnabled);
        chestEnabled = config.get("Machines", "ChestEnabled", chestEnabled).getBoolean(chestEnabled);
        mintEnabled = config.get("Machines", "mintEnabled", mintEnabled).getBoolean(mintEnabled);
        enchanterEnabled = config.get("Machines", "enchanterEnabled", enchanterEnabled).getBoolean(enchanterEnabled);
        ladderEnabled = config.get("Machines", "ladderEnabled", ladderEnabled).getBoolean(ladderEnabled);
        
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
        enchanterID = config.get("Block IDs", "Enchanter", enchanterID).getInt();
        laserID = config.get("Block IDs", "Miners Laser", laserID).getInt();
        xpTankID = config.get("Block IDs", "Xp Tank", xpTankID).getInt();
        pylonID = config.get("Block IDs", "Pylon", pylonID).getInt();

        storageBlockID = config.get("Block IDs", "Storage", storageBlockID).getInt();
        storageAccessorID = config.get("Block IDs", "Storage Accessor", storageAccessorID).getInt();
        
        coinID = config.get("Item IDs", "Coin", coinID).getInt();
        stackID = config.get("Item IDs", "Stack", stackID).getInt();
        coinBagID = config.get("Item IDs", "Coin Bag", coinBagID).getInt();
        bullionID = config.get("Item IDs", "Bullion", bullionID).getInt();
        glassDustID = config.get("Item IDs", "Glass Dusts", glassDustID).getInt();
        orbID = config.get("Item IDs", "Fantasy Orbs", orbID).getInt();
        sawDustID = config.get("Item IDs", "Saw Dust", sawDustID).getInt();
        goldCogID = config.get("Item IDs", "Gold Cog", goldCogID).getInt();
        
        tradesEnabled = config.get("Mint", "Enable Trades", true).getBoolean(true);

        stoneCrusherSpeed = config.get("Crusher Speeds", "Stone", (int) (stoneCrusherSpeed * 1000)).getInt() / 1000F;
        copperCrusherSpeed = config.get("Crusher Speeds", "Copper", (int) (copperCrusherSpeed * 1000)).getInt() / 1000F;
        bronzeCrusherSpeed = config.get("Crusher Speeds", "Bronze", (int) (bronzeCrusherSpeed * 1000)).getInt() / 1000F;
        ironCrusherSpeed = config.get("Crusher Speeds", "Iron", (int) (ironCrusherSpeed * 1000)).getInt() / 1000F;
        steelCrusherSpeed = config.get("Crusher Speeds", "Steel", (int) (steelCrusherSpeed * 1000)).getInt() / 1000F;

        copperFurnaceSpeed = config.get("Furnace Speeds", "Copper", (int) (copperFurnaceSpeed * 1000)).getInt() / 1000F;
        bronzeFurnaceSpeed = config.get("Furnace Speeds", "Bronze", (int) (bronzeFurnaceSpeed * 1000)).getInt() / 1000F;
        ironFurnaceSpeed = config.get("Furnace Speeds", "Iron", (int) (ironFurnaceSpeed * 1000)).getInt() / 1000F;
        steelFurnaceSpeed = config.get("Furnace Speeds", "Steel", (int) (steelFurnaceSpeed * 1000)).getInt() / 1000F;

        extractorSpeeds[0] = config.get("Abstractor", "Speed Prometheum", 8).getInt();
        extractorSpeeds[1] = config.get("Abstractor", "Speed Deep Iron", 8).getInt();
        extractorSpeeds[2] = config.get("Abstractor", "Speed Block Steel", 7).getInt();
        extractorSpeeds[3] = config.get("Abstractor", "Speed Oureclase", 7).getInt();
        extractorSpeeds[4] = config.get("Abstractor", "Speed Aredrite", 6).getInt();
        extractorSpeeds[5] = config.get("Abstractor", "Speed Mithril", 6).getInt();
        extractorSpeeds[6] = config.get("Abstractor", "Speed Haderoth", 6).getInt();
        extractorSpeeds[7] = config.get("Abstractor", "Speed Orichalcum", 5).getInt();
        extractorSpeeds[8] = config.get("Abstractor", "Speed Adamantine", 5).getInt();
        extractorSpeeds[9] = config.get("Abstractor", "Speed Atlarus", 4).getInt();
        extractorSpeeds[10] = config.get("Abstractor", "Speed Tartarite", 4).getInt();

        xpBonus[0] = config.get("Abstractor", "Bonus Prometheum", 1.0).getDouble(1.0);
        xpBonus[1] = config.get("Abstractor", "Bonus Deep Iron", 1.1).getDouble(1.1);
        xpBonus[2] = config.get("Abstractor", "Bonus Black Steel", 1.2).getDouble(1.2);
        xpBonus[3] = config.get("Abstractor", "Bonus Oureclase", 1.3).getDouble(1.3);
        xpBonus[4] = config.get("Abstractor", "Bonus Aredrite", 1.4).getDouble(1.4);
        xpBonus[5] = config.get("Abstractor", "Bonus Mithril", 1.5).getDouble(1.5);
        xpBonus[6] = config.get("Abstractor", "Bonus Haderoth", 1.6).getDouble(1.6);
        xpBonus[7] = config.get("Abstractor", "Bonus Oreichalcum", 1.7).getDouble(1.7);
        xpBonus[8] = config.get("Abstractor", "Bonus Admantine", 1.8).getDouble(1.8);
        xpBonus[9] = config.get("Abstractor", "Bonus Atlarus", 1.9).getDouble(1.9);
        xpBonus[10] = config.get("Abstractor", "Bonus Tartarite", 2.0).getDouble(2.0);

        final String[] forgeNames =
        { "Ignatius", "Shadow Iron", "Shadow Steel", "Vyroxeres", "Inolashite", "Kalendrite", "Vulcanite", "Sanguinite" };
        for (int i = 0; i < 8; i++)
        {
            forgeSpeeds[i] = config.get("Forge.Speeds", forgeNames[i], forgeSpeeds[i]).getInt();
            forgeBuckets[i] = config.get("Forge.Buckets", forgeNames[i], forgeBuckets[i]).getInt();
        }
        smelterDropsLava = config.get("Forge", "Drops Lava", smelterDropsLava).getBoolean(smelterDropsLava);

        config.save();
    }
}
