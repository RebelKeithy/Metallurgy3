package rebelkeithy.mods.metallurgy.integration;

import net.minecraft.item.ItemStack;
import rebelkeithy.mods.metallurgy.api.IOreInfo;
import rebelkeithy.mods.metallurgy.api.MetallurgyAPI;
import cpw.mods.fml.common.Loader;

public class IndustrialCraftIntegration 
{

	public static void init()
	{
		if (Loader.isModLoaded("IC2"))
		{
			System.out.println("Metallurgy: Adding IC2 Integration");
			addCompatability();
		}
	}

	private static void addCompatability() 
	{	
		for (String setName : rebelkeithy.mods.metallurgy.api.MetallurgyAPI.getMetalSetNames() )
		{
			for (IOreInfo oreInfo : MetallurgyAPI.getMetalSet(setName).getOreList().values())
			{
				ItemStack ore = oreInfo.getOre();
				ItemStack dust = oreInfo.getDust();
				ItemStack ingot = oreInfo.getIngot();
				
				if(ore != null && dust != null)
				{
					ItemStack output = dust.copy();
					output.stackSize = 2;
					//Recipes.macerator.addRecipe(ore, output);
					//Recipes.macerator.addRecipe(ingot, dust);
				}
			}
		}
	}
}
