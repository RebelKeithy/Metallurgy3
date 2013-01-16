package rebelkeithy.mods.metallurgy.core.metalsets;

import static rebelkeithy.mods.metallurgy.core.metalsets.OreType.ALLOY;
import static rebelkeithy.mods.metallurgy.core.metalsets.OreType.CATALYST;
import static rebelkeithy.mods.metallurgy.core.metalsets.OreType.ORE;
import static rebelkeithy.mods.metallurgy.core.metalsets.OreType.RESPAWN;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import java.util.Map;
import java.util.Random;

import rebelkeithy.mods.metablock.MetaBlock;
import rebelkeithy.mods.metablock.SubBlock;

public class OreInfo implements IWorldGenerator
{
	protected String setName;
	protected String name;
	protected OreType type;
	protected int oreID;
	protected int oreMeta;
	protected int blockID;
	protected int blockMeta;
	protected int brickID;
	protected int brickMeta;
	protected int itemIDs;
	
	//TODO: remove in 1.5
	protected int iconColumn;
	
	protected Object[] alloyRecipe;
	protected int abstractorXP;
	protected int blockLvl;
	
	protected int pickLvl;
	protected int toolDura;
	protected int toolDamage;
	protected int toolSpeed;
	protected int toolEnchant;
	
	protected int helmetArmor;
	protected int chestArmor;
	protected int legsArmor;
	protected int bootsArmor;
	protected int armorDura;
	
	protected int dungeonLootChance;
	protected int dungeonLootAmount;
	protected int veinCount;
	protected int oreCount;
	protected int minHeight;
	protected int maxHeight;
	protected int veinChance;
	protected int veinDensity;
	protected String[] diminsions;
	
	
	public SubBlock ore;
	public SubBlock block;
	public SubBlock brick;
	
	public Item dust;
	public Item ingot;
	
	public Item pickaxe;
	public Item shovel;
	public Item axe;
	public Item hoe;
	public Item sword;
	
	public Item helmet;
	public Item chest;
	public Item legs;
	public Item boots;
	
	public OreInfo(Map<String, String> info)
	{
		setName = info.get("Metal Set");
		name = info.get("Name");
		System.out.println("reading " + name);
		if(info.get("Type").equals("Ore"))
			type = ORE;
		else if(info.get("Type").equals("Catalyst"))
			type = CATALYST;
		else if(info.get("Type").equals("Alloy"))
			type = ALLOY;
		else if(info.get("Type").equals("Respawn"))
			type = RESPAWN;
		
		alloyRecipe = info.get("Alloy Recipe").split(" ");
		for(int n = 0; n < alloyRecipe.length; n++)
			alloyRecipe[n] = "dust" + alloyRecipe[n];
		
		if(type.generates())
		{
			oreID = Integer.parseInt(info.get("Ore ID").split(":")[0]);
			oreMeta = Integer.parseInt(info.get("Ore ID").split(":")[1]);
		}
		blockID = Integer.parseInt(info.get("Block ID").split(":")[0]);
		blockMeta = Integer.parseInt(info.get("Block ID").split(":")[1]);
		brickID = Integer.parseInt(info.get("Brick ID").split(":")[0]);
		brickMeta = Integer.parseInt(info.get("Brick ID").split(":")[1]);
		itemIDs = Integer.parseInt(info.get("Item IDs"));
		
		iconColumn = Integer.parseInt(info.get("Icon Column"));
		
		abstractorXP = Integer.parseInt(info.get("Abstractor XP"));
		blockLvl = Integer.parseInt(info.get("Block lvl"));
		
		if(type != CATALYST)
		{
			pickLvl = Integer.parseInt(info.get("Pick lvl"));
			toolDura = Integer.parseInt(info.get("Durability"));
			toolDamage = Integer.parseInt(info.get("Damage"));
			toolSpeed = Integer.parseInt(info.get("Speed"));
			toolEnchant = Integer.parseInt(info.get("Enchantability"));
			
			helmetArmor = Integer.parseInt(info.get("Helmet Armor"));
			chestArmor = Integer.parseInt(info.get("Chestplate Armor"));
			legsArmor = Integer.parseInt(info.get("Leggings Armor"));
			bootsArmor = Integer.parseInt(info.get("Boots Armor"));
			armorDura = Integer.parseInt(info.get("Durability Multiplier"));
		}
		
		dungeonLootChance = Integer.parseInt(info.get("Dungeon Loot Chance"));
		dungeonLootAmount = Integer.parseInt(info.get("Dungeon Loot Amount"));
		if(type.generates())
		{
			veinCount = Integer.parseInt(info.get("Veins Per Chunk"));
			oreCount = Integer.parseInt(info.get("Ores Per Vein"));
			minHeight = Integer.parseInt(info.get("Min Level"));
			maxHeight = Integer.parseInt(info.get("Max Level"));
			veinChance = Integer.parseInt(info.get("Vein Chance Per Chunk"));
			veinDensity = Integer.parseInt(info.get("Vein Density"));
			diminsions = info.get("Diminsions").split(" ");
		}
		
	}

	public void initConfig(Configuration config) 
	{
		if(!type.equals(RESPAWN))
		{
			String id;
			if((type == ORE || type == CATALYST) && oreID != 0)
			{
				id = config.get(name + ".IDs", "Ore", oreID + ":" + oreMeta).value;
				oreID = Integer.parseInt(id.split(":")[0]);
				oreMeta = Integer.parseInt(id.split(":")[1]);
			}
			if(blockID != 0)
			{
				id = config.get(name + ".IDs", "Block", blockID + ":" + blockMeta).value;
				blockID = Integer.parseInt(id.split(":")[0]);
				blockMeta = Integer.parseInt(id.split(":")[1]);
			}
			if(brickID != 0)
			{
				id = config.get(name + ".IDs", "Brick", brickID + ":" + brickMeta).value;
				brickID = Integer.parseInt(id.split(":")[0]);
				brickMeta = Integer.parseInt(id.split(":")[1]);
			}
			itemIDs = config.get(name + ".IDs", "Item IDs (reserves 50)", itemIDs).getInt();
			
			abstractorXP = config.get(name + ".misc", "abstractor xp", abstractorXP).getInt();
			blockLvl = config.get(name + ".misc", "Block Hardness Level", blockLvl).getInt();
			
			if(type != CATALYST)
			{
				pickLvl = config.get(name + ".Tool Info", "Pick Level", pickLvl).getInt();
				toolDura = config.get(name + ".Tool Info", "Durability", toolDura).getInt();
				toolDamage = config.get(name + ".Tool Info", "Damage", toolDamage).getInt();
				toolSpeed = config.get(name + ".Tool Info", "Speed", toolSpeed).getInt();
				toolEnchant = config.get(name + ".Tool Info", "Enchantability", toolEnchant).getInt();
				
				helmetArmor = config.get(name + ".Armor Info", "Helmet Armor", helmetArmor).getInt();
				chestArmor = config.get(name + ".Armor Info", "Chestplate Armor", chestArmor).getInt();
				legsArmor = config.get(name + ".Armor Info", "Leggings Armor", legsArmor).getInt();
				bootsArmor = config.get(name + ".Armor Info", "Boots Armor", bootsArmor).getInt();
				armorDura = config.get(name + ".Armor Info", "Durability Multiplier", armorDura).getInt();
			}
			
			dungeonLootChance = config.get(name + ".World Gen", "Dungeon Loot Chance", dungeonLootChance).getInt();
			dungeonLootAmount = config.get(name + ".World Gen", "Dungeon Loot Amount", dungeonLootAmount).getInt();
		}
		if(type.generates())
		{
			veinCount = config.get(name + ".World Gen", "Veins Per Chunk", veinCount).getInt();
			oreCount = config.get(name + ".World Gen", "Vein Size", oreCount).getInt();
			minHeight = config.get(name + ".World Gen", "Min Height", minHeight).getInt();
			maxHeight = config.get(name + ".World Gen", "Max Height", maxHeight).getInt();
			veinChance = config.get(name + ".World Gen", "Vein Chance", veinChance).getInt();
			veinDensity = config.get(name + ".World Gen", "Vein Density", veinDensity).getInt();
			
			String dimCombined = "";
			if(diminsions.length > 0)
			{
				dimCombined = diminsions[0];
				for(int n = 1; n < diminsions.length; n++)
					dimCombined += " " + diminsions[n];
			}
			diminsions = config.get(name + ".World Gen", "Diminsions", dimCombined).value.split(" ");
		}
	}
	
	public void init()
	{
		if(!type.equals(RESPAWN))
		{
			if(type.generates() && oreID != 0)
			{
				ore = new SubBlock(oreID, oreMeta).setBlockName(setName + oreID).setTextureFile("/Metallurgy" + setName + ".png").setCreativeTab(CreativeTabs.tabBlock);
				ore.setBlockTextureIndex(iconColumn);
			}
			if(blockID != 0)
			{
				block = new SubBlock(blockID, blockMeta).setBlockName(setName + blockID).setTextureFile("/Metallurgy" + setName + ".png").setCreativeTab(CreativeTabs.tabBlock);
				block.setBlockTextureIndex(iconColumn + 16);
			}
			if(brickID != 0)
			{
				brick = new SubBlock(brickID, brickMeta).setBlockName(setName + brickID).setTextureFile("/Metallurgy" + setName + ".png").setCreativeTab(CreativeTabs.tabBlock);
				brick.setBlockTextureIndex(iconColumn + 16 * 2);
			}
			dust = new Item(itemIDs).setTextureFile("/Metallurgy" + setName + ".png").setIconCoord(iconColumn, 3).setItemName(setName + "." + name + "Dust").setCreativeTab(CreativeTabs.tabMaterials);
			ingot = new Item(itemIDs+1).setTextureFile("/Metallurgy" + setName + ".png").setIconCoord(iconColumn, 4).setItemName(setName + "." + name + "Bar").setCreativeTab(CreativeTabs.tabMaterials);
			
			if(type != CATALYST)
			{
				EnumToolMaterial toolEnum = EnumHelper.addToolMaterial(name, pickLvl, toolDura, toolSpeed, toolDamage, toolEnchant);
				System.out.println(name.toUpperCase() + "TOOL SPEED = " + toolSpeed);
				pickaxe = new ItemPickaxe(itemIDs + 2, toolEnum).setTextureFile("/Metallurgy" + setName + ".png").setIconCoord(iconColumn, 7).setItemName(setName + "." + name + "Pick").setCreativeTab(CreativeTabs.tabTools);
				shovel = new ItemSpade(itemIDs + 3, toolEnum).setTextureFile("/Metallurgy" + setName + ".png").setIconCoord(iconColumn, 8).setItemName(setName + "." + name + "Shovel").setCreativeTab(CreativeTabs.tabTools);
				axe = new ItemAxe(itemIDs + 4, toolEnum).setTextureFile("/Metallurgy" + setName + ".png").setIconCoord(iconColumn, 5).setItemName(setName + "." + name + "Axe").setCreativeTab(CreativeTabs.tabTools);
				hoe = new ItemHoe(itemIDs + 5, toolEnum).setTextureFile("/Metallurgy" + setName + ".png").setIconCoord(iconColumn, 6).setItemName(setName + "." + name + "Hoe").setCreativeTab(CreativeTabs.tabTools);
				sword = new ItemSword(itemIDs + 6, toolEnum).setTextureFile("/Metallurgy" + setName + ".png").setIconCoord(iconColumn, 9).setItemName(setName + "." + name + "Sword").setCreativeTab(CreativeTabs.tabCombat);
				
				EnumArmorMaterial armorEnum = EnumHelper.addArmorMaterial(name, armorDura, new int[] {helmetArmor, chestArmor, legsArmor, bootsArmor}, toolEnchant);
				helmet = new ItemArmor(itemIDs + 7, armorEnum, 0, 0).setTextureFile("/Metallurgy" + setName + ".png").setIconCoord(iconColumn, 12).setItemName(setName + "." + name + "Helmet").setCreativeTab(CreativeTabs.tabCombat);
				chest = new ItemArmor(itemIDs + 8, armorEnum, 1, 1).setTextureFile("/Metallurgy" + setName + ".png").setIconCoord(iconColumn, 13).setItemName(setName + "." + name + "Chest").setCreativeTab(CreativeTabs.tabCombat);
				legs = new ItemArmor(itemIDs + 9, armorEnum, 2, 2).setTextureFile("/Metallurgy" + setName + ".png").setIconCoord(iconColumn, 14).setItemName(setName + "." + name + "Legs").setCreativeTab(CreativeTabs.tabCombat);
				boots = new ItemArmor(itemIDs + 10, armorEnum, 3, 3).setTextureFile("/Metallurgy" + setName + ".png").setIconCoord(iconColumn, 15).setItemName(setName + "." + name + "Boots").setCreativeTab(CreativeTabs.tabCombat);
			}
		}
		
		if(type.generates())
		{
			
			GameRegistry.registerWorldGenerator(this);
		}
	}

	public void load()
	{
		if(!type.equals(RESPAWN))
		{
			if(oreID != 0)
				MetaBlock.registerID(oreID);
			if(brickID != 0)
				MetaBlock.registerID(brickID);
			if(blockID != 0)
				MetaBlock.registerID(blockID);
			
			setLevels();
			addRecipes();
			registerMetal();
		}
	}
	
	public void setLevels()
	{
		MinecraftForge.setToolClass(pickaxe, "pickaxe", pickLvl);
		if(ore != null)
			MinecraftForge.setBlockHarvestLevel(ore.getBlock(), oreMeta, "pickaxe", blockLvl);
		if(block != null)
			MinecraftForge.setBlockHarvestLevel(block.getBlock(), blockMeta, "pickaxe", blockLvl);
		if(brick != null)
			MinecraftForge.setBlockHarvestLevel(brick.getBlock(), brickMeta, "pickaxe", blockLvl);
	}
	
	public void addRecipes()
	{
		if(type.generates() && ore != null)
		{
			FurnaceRecipes.smelting().addSmelting(oreID, oreMeta, new ItemStack(ingot), abstractorXP/10f);	
		}
		
		FurnaceRecipes.smelting().addSmelting(dust.itemID, 0, new ItemStack(ingot), abstractorXP/10f);

		ShapedOreRecipe recipe;
		if(block != null)
		{
			recipe = new ShapedOreRecipe(new ItemStack(blockID, 1, blockMeta), "XXX", "XXX", "XXX", 'X', "ingot" + name);
			GameRegistry.addRecipe(recipe);
			GameRegistry.addShapelessRecipe(new ItemStack(ingot, 9), new ItemStack(blockID, 1, blockMeta));
		}
		if(brick != null)
		{
			recipe = new ShapedOreRecipe(new ItemStack(brickID, 4, brickMeta), "XX", "XX", 'X', "ingot" + name);
			GameRegistry.addRecipe(recipe);
			GameRegistry.addShapelessRecipe(new ItemStack(ingot, 1), new ItemStack(brickID, 1, brickMeta));
		}
		
		if(type != CATALYST)
		{
			recipe = new ShapedOreRecipe(new ItemStack(pickaxe), "XXX", " S ", " S ", 'X', "ingot" + name, 'S', Item.stick);
			GameRegistry.addRecipe(recipe);
			recipe = new ShapedOreRecipe(new ItemStack(shovel), "X", "S", "S", 'X', "ingot" + name, 'S', Item.stick);
			GameRegistry.addRecipe(recipe);
			recipe = new ShapedOreRecipe(new ItemStack(axe), "XX", "SX", "S ", 'X', "ingot" + name, 'S', Item.stick);
			GameRegistry.addRecipe(recipe);
			recipe = new ShapedOreRecipe(new ItemStack(hoe), "XX", "S ", "S ", 'X', "ingot" + name, 'S', Item.stick);
			GameRegistry.addRecipe(recipe);
			recipe = new ShapedOreRecipe(new ItemStack(sword), "X", "X", "S", 'X', "ingot" + name, 'S', Item.stick);
			GameRegistry.addRecipe(recipe);
			
			recipe = new ShapedOreRecipe(new ItemStack(helmet), "XXX", "X X", 'X', "ingot" + name);
			GameRegistry.addRecipe(recipe);
			recipe = new ShapedOreRecipe(new ItemStack(chest), "X X", "XXX", "XXX", 'X', "ingot" + name);
			GameRegistry.addRecipe(recipe);
			recipe = new ShapedOreRecipe(new ItemStack(legs), "XXX", "X X", "X X", 'X', "ingot" + name);
			GameRegistry.addRecipe(recipe);
			recipe = new ShapedOreRecipe(new ItemStack(boots), "X X", "X X", 'X', "ingot" + name);
			GameRegistry.addRecipe(recipe);
		}
		
		if(type == ALLOY)
		{
			System.out.println("Adding alloy recipe for " + name);
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(dust, 2), alloyRecipe));
		}
	}
	
	public void registerMetal()
	{
		if(ore != null)
			OreDictionary.registerOre("ore" + name, new ItemStack(oreID, 1, oreMeta));
		if(block != null)
			OreDictionary.registerOre("block" + name, new ItemStack(blockID, 1, blockMeta));
		OreDictionary.registerOre("ingot" + name, ingot);
		OreDictionary.registerOre("dust" + name, dust);
	}

	public void registerNames() {
		if(type == RESPAWN)
			return;
		
		if(type.generates() && ore != null)
		{
			LanguageRegistry.addName(new ItemStack(oreID, 1, oreMeta), name + " Ore");
		}
		if(block != null)
			LanguageRegistry.addName(new ItemStack(blockID, 1, blockMeta), name + " Block");
		if(brick != null)
			LanguageRegistry.addName(new ItemStack(brickID, 1, brickMeta), name + " Brick");
		
		LanguageRegistry.addName(dust, name + " Dust");
		LanguageRegistry.addName(ingot, name + " Ingot");

		if(type != CATALYST)
		{
			LanguageRegistry.addName(pickaxe, name + " Pickaxe");
			LanguageRegistry.addName(shovel, name + " Shovel");
			LanguageRegistry.addName(axe, name + " Axe");
			LanguageRegistry.addName(hoe, name + " Hoe");
			LanguageRegistry.addName(sword, name + " Sword");
			
			LanguageRegistry.addName(helmet, name + " Helmet");
			LanguageRegistry.addName(chest, name + " Chestplate");
			LanguageRegistry.addName(legs, name + " Legs");
			LanguageRegistry.addName(boots, name + " Boots");
		}
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		if(!type.generates() || ore == null)
			return;
		
		for(int n = 0; n < veinCount; n++)
		{
			int randPosX = chunkX*16 + random.nextInt(16);
			int randPosY = random.nextInt(maxHeight - minHeight) + minHeight;
			int randPosZ = chunkZ*16 + random.nextInt(16);
			
			new MetallurgyWorldGenMinable(oreID, oreMeta, oreCount, veinDensity, Block.stone.blockID, 0).generate(world, random, randPosX, randPosY, randPosZ);
			//new WorldGenMinable(oreID, oreMeta, 40).generate(world, random, randPosX, randPosY, randPosZ);
		}
	}
}
