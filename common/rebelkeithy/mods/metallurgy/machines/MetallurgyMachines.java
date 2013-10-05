package rebelkeithy.mods.metallurgy.machines;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import rebelkeithy.mods.keithyutils.guiregistry.GuiRegistry;
import rebelkeithy.mods.metallurgy.core.MetallurgyTabs;
import rebelkeithy.mods.metallurgy.core.metalsets.ItemMetallurgy;
import rebelkeithy.mods.metallurgy.integration.AppliedEnergestics;
import rebelkeithy.mods.metallurgy.machines.abstractor.AbstractorRecipes;
import rebelkeithy.mods.metallurgy.machines.abstractor.BlockAbstractor;
import rebelkeithy.mods.metallurgy.machines.abstractor.BlockAbstractorItem;
import rebelkeithy.mods.metallurgy.machines.abstractor.TileEntityAbstractor;
import rebelkeithy.mods.metallurgy.machines.chests.BlockPreciousChest;
import rebelkeithy.mods.metallurgy.machines.chests.ItemBlockPreciousChest;
import rebelkeithy.mods.metallurgy.machines.chests.TileEntityPreciousChest;
import rebelkeithy.mods.metallurgy.machines.crusher.BlockCrusher;
import rebelkeithy.mods.metallurgy.machines.crusher.BlockCrusherItem;
import rebelkeithy.mods.metallurgy.machines.crusher.CrusherRecipes;
import rebelkeithy.mods.metallurgy.machines.crusher.TileEntityCrusher;
import rebelkeithy.mods.metallurgy.machines.enchanter.BlockMetallurgyEnchantmentTable;
import rebelkeithy.mods.metallurgy.machines.enchanter.TileEntityMetallurgyEnchantmentTable;
import rebelkeithy.mods.metallurgy.machines.forge.BlockNetherForge;
import rebelkeithy.mods.metallurgy.machines.forge.BlockNetherForgeItem;
import rebelkeithy.mods.metallurgy.machines.forge.TileEntityNetherForge;
import rebelkeithy.mods.metallurgy.machines.furnace.BlockMetalFurnace;
import rebelkeithy.mods.metallurgy.machines.furnace.BlockMetalFurnaceItem;
import rebelkeithy.mods.metallurgy.machines.furnace.TileEntityMetalFurnace;
import rebelkeithy.mods.metallurgy.machines.ladders.BlockMetalLadder;
import rebelkeithy.mods.metallurgy.machines.ladders.ItemBlockMetalLadder;
import rebelkeithy.mods.metallurgy.machines.lantern.BlockColoredGlass;
import rebelkeithy.mods.metallurgy.machines.lantern.BlockLantern;
import rebelkeithy.mods.metallurgy.machines.lantern.ItemBlockColoredGlass;
import rebelkeithy.mods.metallurgy.machines.lantern.ItemBlockLantern;
import rebelkeithy.mods.metallurgy.machines.lantern.ItemGlassDust;
import rebelkeithy.mods.metallurgy.machines.lantern.TileEntityLantern;
import rebelkeithy.mods.metallurgy.machines.laser.Laser;
import rebelkeithy.mods.metallurgy.machines.mint.BlockMint;
import rebelkeithy.mods.metallurgy.machines.mint.BlockMintStorage;
import rebelkeithy.mods.metallurgy.machines.mint.MetallurgyTradeHandler;
import rebelkeithy.mods.metallurgy.machines.mint.MintRecipes;
import rebelkeithy.mods.metallurgy.machines.mint.TileEntityMint;
import rebelkeithy.mods.metallurgy.machines.mint.TileEntityMintStorage;
import rebelkeithy.mods.metallurgy.machines.orbs.FantasyOrbs;
import rebelkeithy.mods.metallurgy.machines.pylon.Pylon;
import rebelkeithy.mods.metallurgy.machines.storage.BlockStorage;
import rebelkeithy.mods.metallurgy.machines.storage.BlockStorageAccessor;
import rebelkeithy.mods.metallurgy.machines.storage.TileEntityStorageAccessor;
import rebelkeithy.mods.metallurgy.machines.storage.TileEntityStorageBlock;
import rebelkeithy.mods.metallurgy.metals.MetallurgyMetals;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;

@Mod(modid = "Metallurgy3Machines", name = "Metallurgy 3 Machines", dependencies = "required-after:Metallurgy3Base", version = "3.2.3")
@NetworkMod(channels =
{ "M3Machines" }, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class MetallurgyMachines
{
    @SidedProxy(clientSide = "rebelkeithy.mods.metallurgy.machines.ClientProxy", serverSide = "rebelkeithy.mods.metallurgy.machines.CommonProxy")
    public static CommonProxy proxy;

    @Instance(value = "Metallurgy3Machines")
    public static MetallurgyMachines instance;

    public static MetallurgyTabs machineTab;

    public static Item coin;
    public static Item stack;
    public static Item bag;
    public static Item bullion;
    public static Item glassDust;
    public static Item goldCog;
    public static Item debug;
    public static Item sawDust;

    public static Block storageAccessor;
    public static Block storageBlock;

    public static Block chest;
    public static Block crusher;
    public static Block furnace;
    public static Block forge;
    public static Block abstractor;
    public static Block xpTank;
    public static Block mint;
    public static Block mintStorage;
    public static Block coloredGlass;
    public static Block lantern;
    public static Block ladder;
    public static Block enchanter;
    public static Block laser;

    public void createMachineRecipes()
    {
        // Crusher Recipes
        if (ConfigMachines.crusherEnabled)
        {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(crusher, 1, 0), "CSC", "SFS", "CSC", 'C', Block.cobblestone, 'S', Item.stick, 'F', Block.furnaceIdle));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(crusher, 1, 1), "XXX", "XOX", "XXX", 'X', "ingotCopper", 'O', new ItemStack(crusher, 1, 0)));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(crusher, 1, 2), "XXX", "XOX", "XXX", 'X', "ingotBronze", 'O', new ItemStack(crusher, 1, 1)));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(crusher, 1, 3), "XXX", "XOX", "XXX", 'X', Item.ingotIron, 'O', new ItemStack(crusher, 1, 2)));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(crusher, 1, 4), "XXX", "XOX", "XXX", 'X', "ingotSteel", 'O', new ItemStack(crusher, 1, 3)));
        }

        // Furnace Recipes
        if (ConfigMachines.furnaceEnabled)
        {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(furnace, 1, 0), "XXX", "XOX", "XXX", 'X', "ingotCopper", 'O', new ItemStack(Block.furnaceIdle)));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(furnace, 1, 1), "XXX", "XOX", "XXX", 'X', "ingotBronze", 'O', new ItemStack(furnace, 1, 0)));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(furnace, 1, 2), "XXX", "XOX", "XXX", 'X', Item.ingotIron, 'O', new ItemStack(furnace, 1, 1)));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(furnace, 1, 3), "XXX", "XOX", "XXX", 'X', "ingotSteel", 'O', new ItemStack(furnace, 1, 2)));
        }

        // Precious Chests Recipes
        if (ConfigMachines.chestEnabled)
        {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chest, 1, 0), "XXX", "XOX", "XXX", 'X', "ingotBrass", 'O', new ItemStack(Block.chest)));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chest, 1, 1), "XXX", "XOX", "XXX", 'X', "ingotSilver", 'O', new ItemStack(chest, 1, 0)));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chest, 1, 2), "XXX", "XOX", "XXX", 'X', Item.ingotGold, 'O', new ItemStack(chest, 1, 1)));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chest, 1, 3), "XXX", "XOX", "XXX", 'X', "ingotElectrum", 'O', new ItemStack(chest, 1, 2)));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chest, 1, 4), "XXX", "XOX", "XXX", 'X', "ingotPlatinum", 'O', new ItemStack(chest, 1, 3)));
        }

        // Nether Forge Recipes
        if (ConfigMachines.forgeEnabled)
        {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(forge, 1, 0), "XXX", "X X", "XXX", 'X', "ingotIgnatius"));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(forge, 1, 1), "XXX", "XOX", "XXX", 'X', "ingotShadow Iron", 'O', new ItemStack(forge, 1, 0)));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(forge, 1, 2), "XXX", "XOX", "XXX", 'X', "ingotShadow Steel", 'O', new ItemStack(forge, 1, 1)));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(forge, 1, 3), "XXX", "XOX", "XXX", 'X', "ingotVyroxeres", 'O', new ItemStack(forge, 1, 2)));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(forge, 1, 4), "XXX", "XOX", "XXX", 'X', "ingotInolashite", 'O', new ItemStack(forge, 1, 3)));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(forge, 1, 5), "XXX", "XOX", "XXX", 'X', "ingotKalendrite", 'O', new ItemStack(forge, 1, 4)));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(forge, 1, 6), "XXX", "XOX", "XXX", 'X', "ingotVulcanite", 'O', new ItemStack(forge, 1, 5)));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(forge, 1, 7), "XXX", "XOX", "XXX", 'X', "ingotSanguinite", 'O', new ItemStack(forge, 1, 6)));
        }

        // Abstractor Recipes
        if (ConfigMachines.abstractorEnabled)
        {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(abstractor, 1, 0), "XXX", "X X", "XXX", 'X', "ingotPrometheum"));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(abstractor, 1, 1), "XXX", "XOX", "XXX", 'X', "ingotDeep Iron", 'O', new ItemStack(abstractor, 1, 0)));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(abstractor, 1, 2), "XXX", "XOX", "XXX", 'X', "ingotBlack Steel", 'O', new ItemStack(abstractor, 1, 1)));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(abstractor, 1, 3), "XXX", "XOX", "XXX", 'X', "ingotOureclase", 'O', new ItemStack(abstractor, 1, 2)));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(abstractor, 1, 4), "XXX", "XOX", "XXX", 'X', "ingotMithril", 'O', new ItemStack(abstractor, 1, 3)));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(abstractor, 1, 5), "XXX", "XOX", "XXX", 'X', "ingotHaderoth", 'O', new ItemStack(abstractor, 1, 4)));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(abstractor, 1, 6), "XXX", "XOX", "XXX", 'X', "ingotOrichalcum", 'O', new ItemStack(abstractor, 1, 5)));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(abstractor, 1, 7), "XXX", "XOX", "XXX", 'X', "ingotAdamantine", 'O', new ItemStack(abstractor, 1, 6)));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(abstractor, 1, 8), "XXX", "XOX", "XXX", 'X', "ingotAtlarus", 'O', new ItemStack(abstractor, 1, 7)));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(abstractor, 1, 9), "XXX", "XOX", "XXX", 'X', "ingotTartarite", 'O', new ItemStack(abstractor, 1, 8)));
        }

        // Mint Recipes
        if (ConfigMachines.mintEnabled)
        {
            GameRegistry.addRecipe(new ItemStack(goldCog), " G ", "GIG", " G ", 'G', Item.ingotGold, 'I', Item.ingotIron);
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mint), "III", "SRS", "IPI", 'I', Item.ingotIron, 'S', Item.stick, 'R', Item.redstone, 'P', Block.pistonBase));
            GameRegistry.addRecipe(new ItemStack(mintStorage), "GIG", "PCP", "GIG", 'G', goldCog, 'P', Block.pistonBase, 'C', Block.chest, 'I', Item.ingotIron);
        }

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(stack), "CCC", "CCC", "CCC", 'C', coin));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bag), "CCC", "CCC", "CCC", 'C', stack));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bullion), "CCC", "CCC", "CCC", 'C', bag));

        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(coin, 9), stack));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(stack, 9), bag));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(bag, 9), bullion));

        for (int n = 0; n < 8; n++)
        {
            FurnaceRecipes.smelting().addSmelting(glassDust.itemID, n + 1, new ItemStack(coloredGlass, 1, n), 0);
        }

        for (int n = 0; n < 8; n++)
        {
            GameRegistry.addRecipe(new ItemStack(lantern, 1, n), "SSS", "GTG", "SSS", 'S', Block.cobblestone, 'T', Block.torchWood, 'G', new ItemStack(coloredGlass, 1, n));
        }

        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(glassDust, 2, 1), new ItemStack(glassDust, 1, 0), "dustIron"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(glassDust, 2, 2), new ItemStack(glassDust, 1, 0), "dustBronze"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(glassDust, 2, 3), new ItemStack(glassDust, 1, 0), "dustCopper"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(glassDust, 2, 4), new ItemStack(glassDust, 1, 0), "dustAngmallen"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(glassDust, 2, 5), new ItemStack(glassDust, 1, 0), "dustGold"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(glassDust, 2, 6), new ItemStack(glassDust, 1, 0), "dustManganese"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(glassDust, 2, 7), new ItemStack(glassDust, 1, 0), "dustHepatizon"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(glassDust, 2, 8), new ItemStack(glassDust, 1, 0), "dustSteel"));

        CrusherRecipes.addCrushing(Block.glass.blockID, 0, new ItemStack(glassDust, 1, 0));

        if (ConfigMachines.ladderEnabled)
        {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ladder, 8, 0), "I I", "III", "I I", 'I', "ingotCopper"));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ladder, 8, 1), "I I", "III", "I I", 'I', "ingotBronze"));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ladder, 8, 2), "I I", "III", "I I", 'I', Item.ingotIron));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ladder, 8, 3), "I I", "III", "I I", 'I', "ingotSteel"));
        }
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        AppliedEnergestics.init();
    }
    
    @EventHandler
    public void Init(FMLInitializationEvent event)
    {
        loadCrusher();
        machineTab.setIconItem(crusher.blockID);
    }

    public void initAbstractor()
    {
        abstractor = new BlockAbstractor(ConfigMachines.abstractorID, false).setHardness(3.5F).setUnlocalizedName("M3Abstractor").setHardness(2.0F).setCreativeTab(machineTab);
        GameRegistry.registerBlock(abstractor, BlockAbstractorItem.class, "BlockM3Abstractor");
        GameRegistry.registerTileEntity(TileEntityAbstractor.class, "TileEntityAbstractor");

        LanguageRegistry.addName(new ItemStack(abstractor, 1, 0), "Prometheum Abstractor");
        LanguageRegistry.addName(new ItemStack(abstractor, 1, 1), "Deep Iron Abstractor");
        LanguageRegistry.addName(new ItemStack(abstractor, 1, 2), "Black Steel Abstractor");
        LanguageRegistry.addName(new ItemStack(abstractor, 1, 3), "Oureclase Abstractor");
        LanguageRegistry.addName(new ItemStack(abstractor, 1, 4), "Mithril Abstractor");
        LanguageRegistry.addName(new ItemStack(abstractor, 1, 5), "Haderoth Abstractor");
        LanguageRegistry.addName(new ItemStack(abstractor, 1, 6), "Orichalcum Abstractor");
        LanguageRegistry.addName(new ItemStack(abstractor, 1, 7), "Adamantine Abstractor");
        LanguageRegistry.addName(new ItemStack(abstractor, 1, 8), "Atlarus Abstractor");
        LanguageRegistry.addName(new ItemStack(abstractor, 1, 9), "Tartarite Abstractor");

        AbstractorRecipes.addEssence(Item.ingotIron.itemID, 0, 3);
        AbstractorRecipes.addEssence(Item.ingotGold.itemID, 0, 9);
    }

    public void initChests()
    {
        chest = new BlockPreciousChest(ConfigMachines.chestID).setUnlocalizedName("M3PreciousChest").setHardness(1.0F).setCreativeTab(machineTab);
        GameRegistry.registerBlock(chest, ItemBlockPreciousChest.class, "PreciousChest");
        GameRegistry.registerTileEntity(TileEntityPreciousChest.class, "TileEntityPreciousChest");
        LanguageRegistry.addName(new ItemStack(chest, 1, 0), "Brass Chest");
        LanguageRegistry.addName(new ItemStack(chest, 1, 1), "Silver Chest");
        LanguageRegistry.addName(new ItemStack(chest, 1, 2), "Gold Chest");
        LanguageRegistry.addName(new ItemStack(chest, 1, 3), "Electrum Chest");
        LanguageRegistry.addName(new ItemStack(chest, 1, 4), "Platinum Chest");
    }

    public void initCrusher()
    {
        crusher = new BlockCrusher(ConfigMachines.crusherID, false).setHardness(3.5F).setUnlocalizedName("Crusher").setHardness(2.0F).setCreativeTab(machineTab);
        GameRegistry.registerBlock(crusher, BlockCrusherItem.class, "BlockCrusher");
        GameRegistry.registerTileEntity(TileEntityCrusher.class, "TileEntityCrusher");
        LanguageRegistry.addName(new ItemStack(crusher, 1, 0), "Stone Crusher");
        LanguageRegistry.addName(new ItemStack(crusher, 1, 1), "Copper Crusher");
        LanguageRegistry.addName(new ItemStack(crusher, 1, 2), "Bronze Crusher");
        LanguageRegistry.addName(new ItemStack(crusher, 1, 3), "Iron Crusher");
        LanguageRegistry.addName(new ItemStack(crusher, 1, 4), "Steel Crusher");

        CrusherRecipes.addCrushing(Block.cobblestone.blockID, 0, new ItemStack(Block.gravel));
        CrusherRecipes.addCrushing(Block.stone.blockID, 0, new ItemStack(Block.gravel));
        CrusherRecipes.addCrushing(Block.netherrack.blockID, 0, new ItemStack(Block.slowSand));
        CrusherRecipes.addCrushing(Block.glowStone.blockID, 0, new ItemStack(Item.glowstone, 4));
        CrusherRecipes.addCrushing(Block.gravel.blockID, 0, new ItemStack(Block.sand));
        CrusherRecipes.addCrushing(Block.sandStone.blockID, 0, new ItemStack(Block.sand, 4));
    }

    public void initEnchanter()
    {
        enchanter = new BlockMetallurgyEnchantmentTable(ConfigMachines.enchanterID).setUnlocalizedName("Metallurgy:machines/enchanter/Enchanter").setHardness(2.0F)
                .setCreativeTab(machineTab);
        GameRegistry.registerBlock(enchanter, "M3Enchanter");
        GameRegistry.registerTileEntity(TileEntityMetallurgyEnchantmentTable.class, "TileEntityM3Enchanter");
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(enchanter), " B ", "IEI", "III", 'B', Item.enchantedBook, 'E', Block.enchantmentTable, 'I', "ingotTartarite"));
        LanguageRegistry.addName(enchanter, "Tartarite Enchanter");
    }

    public void initForge()
    {
        forge = new BlockNetherForge(ConfigMachines.forgeID, false).setHardness(3.5F).setUnlocalizedName("NetherForge").setHardness(2.0F).setCreativeTab(machineTab);
        GameRegistry.registerBlock(forge, BlockNetherForgeItem.class, "BlockNetherForge");
        GameRegistry.registerTileEntity(TileEntityNetherForge.class, "TileEntityNetherForge");

        LanguageRegistry.addName(new ItemStack(forge, 1, 0), "Ignatius Smelter");
        LanguageRegistry.addName(new ItemStack(forge, 1, 1), "Shadow Iron Smelter");
        LanguageRegistry.addName(new ItemStack(forge, 1, 2), "Shadow Steel Smelter");
        LanguageRegistry.addName(new ItemStack(forge, 1, 3), "Vyroxeres Smelter");
        LanguageRegistry.addName(new ItemStack(forge, 1, 4), "Inolashite Smelter");
        LanguageRegistry.addName(new ItemStack(forge, 1, 5), "Kalendrite Smelter");
        LanguageRegistry.addName(new ItemStack(forge, 1, 6), "Vulcanite Smelter");
        LanguageRegistry.addName(new ItemStack(forge, 1, 7), "Sanguinite Smelter");

    }

    public void initFurnace()
    {
        furnace = new BlockMetalFurnace(ConfigMachines.furnaceID, false).setHardness(3.5F).setUnlocalizedName("MetallurgyFurnace").setHardness(2.0F).setCreativeTab(machineTab);
        GameRegistry.registerBlock(furnace, BlockMetalFurnaceItem.class, "BlockMetalFurnace");
        GameRegistry.registerTileEntity(TileEntityMetalFurnace.class, "TileEntityMetalFurnace");
        LanguageRegistry.addName(new ItemStack(furnace, 1, 0), "Copper Furnace");
        LanguageRegistry.addName(new ItemStack(furnace, 1, 1), "Bronze Furnace");
        LanguageRegistry.addName(new ItemStack(furnace, 1, 2), "Iron Furnace");
        LanguageRegistry.addName(new ItemStack(furnace, 1, 3), "Steel Furnace");

    }

    public void initLadders()
    {
        ladder = new BlockMetalLadder(ConfigMachines.ladderID).setUnlocalizedName("M3Ladder").setHardness(1.0F).setCreativeTab(machineTab);
        GameRegistry.registerBlock(ladder, ItemBlockMetalLadder.class, "M3MetalLadder");

        LanguageRegistry.addName(new ItemStack(ladder, 1, 0), "Copper Ladder");
        LanguageRegistry.addName(new ItemStack(ladder, 1, 1), "Bronze Ladder");
        LanguageRegistry.addName(new ItemStack(ladder, 1, 2), "Iron Ladder");
        LanguageRegistry.addName(new ItemStack(ladder, 1, 3), "Steel Ladder");

    }

    public void initLantern()
    {
        lantern = new BlockLantern(ConfigMachines.lanternID).setHardness(0.1F).setUnlocalizedName("M3Lantern").setLightValue(1F).setCreativeTab(machineTab);
        GameRegistry.registerBlock(lantern, ItemBlockLantern.class, "M3Lantern");
        GameRegistry.registerTileEntity(TileEntityLantern.class, "TileEntityLantern");
        LanguageRegistry.addName(new ItemStack(lantern, 1, 0), "Red Lantern");
        LanguageRegistry.addName(new ItemStack(lantern, 1, 1), "Green Lantern");
        LanguageRegistry.addName(new ItemStack(lantern, 1, 2), "Blue Lantern");
        LanguageRegistry.addName(new ItemStack(lantern, 1, 3), "Orange Lantern");
        LanguageRegistry.addName(new ItemStack(lantern, 1, 4), "Yellow Lantern");
        LanguageRegistry.addName(new ItemStack(lantern, 1, 5), "Purple Lantern");
        LanguageRegistry.addName(new ItemStack(lantern, 1, 6), "Grey Lantern");
        LanguageRegistry.addName(new ItemStack(lantern, 1, 7), "White Lantern");

        coloredGlass = new BlockColoredGlass(ConfigMachines.coloredGlassID).setUnlocalizedName("M3ColoredGlass").setHardness(0.3F).setCreativeTab(machineTab);
        GameRegistry.registerBlock(coloredGlass, ItemBlockColoredGlass.class, "M3ColoredGlass");
        LanguageRegistry.addName(new ItemStack(coloredGlass, 1, 0), "Red Glass");
        LanguageRegistry.addName(new ItemStack(coloredGlass, 1, 1), "Green Glass");
        LanguageRegistry.addName(new ItemStack(coloredGlass, 1, 2), "Blue Glass");
        LanguageRegistry.addName(new ItemStack(coloredGlass, 1, 3), "Orange Glass");
        LanguageRegistry.addName(new ItemStack(coloredGlass, 1, 4), "Yellow Glass");
        LanguageRegistry.addName(new ItemStack(coloredGlass, 1, 5), "Purple Glass");
        LanguageRegistry.addName(new ItemStack(coloredGlass, 1, 6), "Grey Glass");
        LanguageRegistry.addName(new ItemStack(coloredGlass, 1, 7), "White Glass");

        glassDust = new ItemGlassDust(ConfigMachines.glassDustID).setUnlocalizedName("M3GlassDust").setCreativeTab(machineTab);
        LanguageRegistry.addName(new ItemStack(glassDust, 1, 0), "Glass Dust");
        LanguageRegistry.addName(new ItemStack(glassDust, 1, 1), "Red Glass Dust");
        LanguageRegistry.addName(new ItemStack(glassDust, 1, 2), "Green Glass Dust");
        LanguageRegistry.addName(new ItemStack(glassDust, 1, 3), "Blue Glass Dust");
        LanguageRegistry.addName(new ItemStack(glassDust, 1, 4), "Orange Glass Dust");
        LanguageRegistry.addName(new ItemStack(glassDust, 1, 5), "Yellow Glass Dust");
        LanguageRegistry.addName(new ItemStack(glassDust, 1, 6), "Purple Glass Dust");
        LanguageRegistry.addName(new ItemStack(glassDust, 1, 7), "Grey Glass Dust");
        LanguageRegistry.addName(new ItemStack(glassDust, 1, 8), "White Glass Dust");
        
        sawDust = new ItemMetallurgy(ConfigMachines.sawDustID).setTextureName("Metallurgy:machines/SawDust").setUnlocalizedName("Metallurgy:machines/SawDust").setCreativeTab(machineTab);
        LanguageRegistry.addName(new ItemStack(sawDust), "Saw Dust");
        GameRegistry.addSmelting(sawDust.itemID, new ItemStack(Item.coal, 1,1), 0.15F);
    }
    
    public void initMint()
    {
        mint = new BlockMint(ConfigMachines.mintID).setHardness(2.0F).setUnlocalizedName("M3Mint").setHardness(2.0F).setCreativeTab(machineTab);
        mintStorage = new BlockMintStorage(ConfigMachines.mintStorageID).setHardness(2.0F).setUnlocalizedName("M3MintStorage").setHardness(2.0F).setCreativeTab(machineTab);

        GameRegistry.registerBlock(mint, "M3Mint");
        GameRegistry.registerBlock(mintStorage, "M3MintStorage");
        GameRegistry.registerTileEntity(TileEntityMint.class, "TileEntityMint");
        GameRegistry.registerTileEntity(TileEntityMintStorage.class, "TileEntityMintStorage");

        LanguageRegistry.addName(mint, "Mint");
        LanguageRegistry.addName(mintStorage, "Mint Storage");

        coin = new ItemMetallurgy(ConfigMachines.coinID).setTextureName("Metallurgy:Precious/coin").setUnlocalizedName("Metallurgy:Precious/coin").setCreativeTab(machineTab);
        stack = new ItemMetallurgy(ConfigMachines.stackID).setTextureName("Metallurgy:Precious/ctack").setUnlocalizedName("Metallurgy:Precious/ctack").setCreativeTab(machineTab);
        bag = new ItemMetallurgy(ConfigMachines.coinBagID).setTextureName("Metallurgy:Precious/bag").setUnlocalizedName("Metallurgy:Precious/bag").setCreativeTab(machineTab);
        bullion = new ItemMetallurgy(ConfigMachines.bullionID).setTextureName("Metallurgy:Precious/bullion").setUnlocalizedName("Metallurgy:Precious/bullion")
                .setCreativeTab(machineTab);
        goldCog = new ItemMetallurgy(ConfigMachines.goldCogID).setTextureName("Metallurgy:Precious/goldCog").setUnlocalizedName("Metallurgy:Precious/goldCog")
                .setCreativeTab(machineTab);
        LanguageRegistry.addName(coin, "Coin");
        LanguageRegistry.addName(stack, "Stack");
        LanguageRegistry.addName(bag, "Bag");
        LanguageRegistry.addName(bullion, "Bullion");
        LanguageRegistry.addName(goldCog, "Gold Cog");

        if (MetallurgyMetals.preciousSet != null)
        {
            Item ingot;

            ingot = MetallurgyMetals.preciousSet.getOreInfo("Silver").ingot;
            if (ingot != null)
            {
                MintRecipes.minting().addMinting(ingot.itemID, 0, 3);
            }

            ingot = MetallurgyMetals.preciousSet.getOreInfo("Platinum").ingot;
            if (ingot != null)
            {
                MintRecipes.minting().addMinting(ingot.itemID, 0, 27);
            }

            ingot = MetallurgyMetals.preciousSet.getOreInfo("Brass").ingot;
            if (ingot != null)
            {
                MintRecipes.minting().addMinting(ingot.itemID, 0, 1);
            }

            ingot = MetallurgyMetals.preciousSet.getOreInfo("Electrum").ingot;
            if (ingot != null)
            {
                MintRecipes.minting().addMinting(ingot.itemID, 0, 13);
            }

            MintRecipes.minting().addMinting(Item.ingotGold.itemID, 0, 9);
        }

        if (ConfigMachines.tradesEnabled)
        {
            for (int i = 0; i < 5; i++)
            {
                VillagerRegistry.instance().registerVillageTradeHandler(i, new MetallurgyTradeHandler());
            }
        }
    }

    public void initStorage()
    {
        storageAccessor = new BlockStorageAccessor(919).setUnlocalizedName("StorageAccessorBlock").setHardness(2.0F).setCreativeTab(machineTab);
        storageBlock = new BlockStorage(920).setUnlocalizedName("StorageBlock").setHardness(2.0F).setCreativeTab(machineTab);
        // storageAccessor.blockIndexInTexture = 32;
        // storageBlock.blockIndexInTexture = 33;
        GameRegistry.registerTileEntity(TileEntityStorageAccessor.class, "TileEntityStorage");
        GameRegistry.registerTileEntity(TileEntityStorageBlock.class, "TileEntityStorageBlock");
        GameRegistry.registerBlock(storageAccessor, "BlockStorageAccessor");
        GameRegistry.registerBlock(storageBlock, "BlockStorage");
    }

    public void initXpTank()
    {
//        xpTank = new BlockXpTank(ConfigMachines.xpTankID).setHardness(3.5F).setUnlocalizedName("M3XpTank").setCreativeTab(machineTab);
//        GameRegistry.registerBlock(xpTank, "M3XpTank");
//        GameRegistry.registerTileEntity(TileEntityXpTank.class, "M3TileEntityXpTank");
//
//        LanguageRegistry.addName(new ItemStack(xpTank, 1, 0), "Xp Tank");
//
//        EntityRegistry.registerModEntity(EntityXpOrbContainer.class, "XpOrbContainer", 0, this, 60, 1, true);
    }

    public void loadCrusher()
    {
        // CrusherUpgradeRecipes.addRecipes();
        createMachineRecipes();

        CrusherRecipes.addCrushing(Block.oreIron.blockID, 0, new ItemStack(MetallurgyMetals.dustIron, 2));
        CrusherRecipes.addCrushing(Block.oreGold.blockID, 0, new ItemStack(MetallurgyMetals.dustGold, 2));
        CrusherRecipes.addCrushing(Item.ingotIron.itemID, 0, new ItemStack(MetallurgyMetals.dustIron));
        CrusherRecipes.addCrushing(Item.ingotGold.itemID, 0, new ItemStack(MetallurgyMetals.dustGold));
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        machineTab = new MetallurgyTabs("Metallurgy: Machines");
        // initStorage();

        ConfigMachines.initConfig();

        initCrusher();
        initFurnace(); 
        initForge();
        initChests();
        initMint();
        initAbstractor();
        initXpTank();
        initLantern();
        initLadders();

        initEnchanter();
        Laser.init();

        FantasyOrbs.init();
        Pylon.init();

        proxy.registerGUIs();
        proxy.registerTileEntitySpecialRenderer();
        NetworkRegistry.instance().registerGuiHandler(this, new StorageGuiHandler());
        NetworkRegistry.instance().registerGuiHandler(this, GuiRegistry.instance());

        LanguageRegistry.instance().addStringLocalization("itemGroup.Metallurgy: Machines", "Metallurgy: Machines");
    }
}
