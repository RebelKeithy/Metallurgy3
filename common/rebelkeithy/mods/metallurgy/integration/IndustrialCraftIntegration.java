package rebelkeithy.mods.metallurgy.integration;

import net.minecraft.item.ItemStack;
import rebelkeithy.mods.metallurgy.api.IOreInfo;
import rebelkeithy.mods.metallurgy.api.MetallurgyAPI;
import cpw.mods.fml.common.Loader;

public class IndustrialCraftIntegration
{

    private static void addCompatability()
    {
        for (final String setName : rebelkeithy.mods.metallurgy.api.MetallurgyAPI.getMetalSetNames())
        {
            for (final IOreInfo oreInfo : MetallurgyAPI.getMetalSet(setName).getOreList().values())
            {
                final ItemStack ore = oreInfo.getOre();
                final ItemStack dust = oreInfo.getDust();
                oreInfo.getIngot();

                if (ore != null && dust != null)
                {
                    final ItemStack output = dust.copy();
                    output.stackSize = 2;
                    // Recipes.macerator.addRecipe(ore, output);
                    // Recipes.macerator.addRecipe(ingot, dust);
                }
            }
        }
    }

    public static void init()
    {
        if (Loader.isModLoaded("IC2"))
        {
            System.out.println("Metallurgy: Adding IC2 Integration");
            addCompatability();
        }
    }
}
