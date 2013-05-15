package rebelkeithy.mods.metallurgy.integration;

import ic2.api.recipe.Recipes;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import rebelkeithy.mods.metallurgy.api.IMetalSet;
import rebelkeithy.mods.metallurgy.api.IOreInfo;
import rebelkeithy.mods.metallurgy.api.MetallurgyAPI;
import rebelkeithy.mods.metallurgy.api.OreType;

public class IC2Integration {
	public static final String[] METAL_SETS = { "base", "precious", "nether", "fantasy", "ender", "utility" };
	public static void init() {
		try{
			
		      for (String setname : METAL_SETS) {
		    	IMetalSet set = MetallurgyAPI.getMetalSet(setname);
		        ArrayList<IOreInfo> oreList = new ArrayList<IOreInfo>(set.getOreList().values());

		        for (IOreInfo ore : oreList){
		          if (ore.getType() == OreType.ORE || ore.getType() == OreType.CATALYST)
		          {
		        	  ItemStack dust = ore.getDust().copy();
		        	  dust.stackSize = 2;
		        	  Recipes.macerator.addRecipe(ore.getOre(), dust);
		        	  Recipes.macerator.addRecipe(ore.getIngot(), ore.getDust());
		          }
		          else if(ore.getType() == OreType.ALLOY){
		        	  Recipes.macerator.addRecipe(ore.getIngot(), ore.getDust());
		          }
		          else if(ore.getType() == OreType.DROP){
		        	  ItemStack drop = ore.getDrop().copy();
		        	  drop.stackSize = MathHelper.floor_float((ore.getDropAmountMax() * 1.5F));
		        	  Recipes.macerator.addRecipe(ore.getOre(), drop);
		          }
		        }
		      }
		    }
			catch(Exception e)
			{
				System.out.println("IC2");
			}
		
	}

}
