package rebelkeithy.mods.metallurgy.integration;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;


import cpw.mods.fml.common.Loader;

import rebelkeithy.mods.metallurgy.api.IMetalSet;
import rebelkeithy.mods.metallurgy.api.IOreInfo;
import rebelkeithy.mods.metallurgy.api.MetallurgyAPI;
import rebelkeithy.mods.metallurgy.api.OreType;
import thermalexpansion.api.crafting.CraftingHelpers;
import thermalexpansion.api.crafting.CraftingManagers;

public class ThermalExpansionIntegration {
	public static final String[] METAL_SETS = { "base", "precious", "nether", "fantasy", "ender", "utility" };
	public static void init(){
		
		try{
			
	      for (String setname : METAL_SETS) {
	    	IMetalSet set = MetallurgyAPI.getMetalSet(setname);
	        ArrayList<IOreInfo> oreList = new ArrayList<IOreInfo>(set.getOreList().values());

	        for (IOreInfo ore : oreList){
	          if (ore.getType() == OreType.ORE || ore.getType() == OreType.CATALYST)
	          {
	            CraftingHelpers.addPulverizerOreToDustRecipe(ore.getOre(), ore.getDust());
	            CraftingHelpers.addSmelterOreToIngotsRecipe(ore.getOre(), ore.getIngot());
	            CraftingHelpers.addPulverizerIngotToDustRecipe(ore.getIngot(), ore.getDust());
	            CraftingHelpers.addSmelterDustToIngotsRecipe(ore.getDust(), ore.getIngot());
	          }
	          else if(ore.getType() == OreType.ALLOY){
	        	   CraftingHelpers.addPulverizerIngotToDustRecipe(ore.getIngot(), ore.getDust());
		           CraftingHelpers.addSmelterDustToIngotsRecipe(ore.getDust(), ore.getIngot());
	          }
	          else if(ore.getType() == OreType.DROP){
	        	  ItemStack drop = ore.getDrop().copy();
	        	  drop.stackSize = MathHelper.floor_float((ore.getDropAmountMax() * 1.5F));
	        	  CraftingManagers.pulverizerManager.addRecipe(320, ore.getOre(), drop);
	          }
	        }
	      }
	    }
		catch(Exception e)
		{
			System.out.println("TE Integration failed");
		}
	 }
	
	  
}
