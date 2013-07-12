package rebelkeithy.mods.metallurgy.integration;

import net.minecraft.item.ItemStack;
import rebelkeithy.mods.metallurgy.api.IOreInfo;
import rebelkeithy.mods.metallurgy.api.MetallurgyAPI;

public class RailcraftIntegration 
{
	public static void init()
	{
		try
		{
			Class a = Class.forName("mods.railcraft.api.crafting.RailcraftCraftingManager");
			
			System.out.println("Metallurgy: Adding Railcraft Integration");
			addCompatability();
		} 
		catch(Exception e) 
		{
			System.out.println("Metlalurgy: Skipping Railcraft Integration");
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
					//IRockCrusherRecipe recipe = RailcraftCraftingManager.rockCrusher.createNewRecipe(ore, true, false);
					//recipe.addOutput(output, 1.0f);

					//recipe = RailcraftCraftingManager.rockCrusher.createNewRecipe(ingot, true, false);
					//recipe.addOutput(dust, 1.0f);
				}
			}
		}
	}
}
