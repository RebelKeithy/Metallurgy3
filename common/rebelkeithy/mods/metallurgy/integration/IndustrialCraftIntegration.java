package rebelkeithy.mods.metallurgy.integration;

import net.minecraft.item.ItemStack;
import rebelkeithy.mods.metallurgy.api.IOreInfo;
import rebelkeithy.mods.metallurgy.api.MetallurgyAPI;
import rebelkeithy.mods.metallurgy.core.MetallurgyCore;
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
                final ItemStack ingot = oreInfo.getIngot();

                if (ore != null && dust != null && ingot != null)
                {
                    final ItemStack output = dust.copy();
                    output.stackSize = 2;

//                    Recipes.macerator.addRecipe(new RecipeInputItemStack(ore), new NBTTagCompound(), output);
//                    Recipes.macerator.addRecipe(new RecipeInputItemStack(ingot), new NBTTagCompound(), dust);

                }
            }
        }
    }

    public static void init()
    {
        if (Loader.isModLoaded("IC2"))
        {
            MetallurgyCore.log.info("Metallurgy: Adding IC2 Integration");
            addCompatability();
        }
    }
}
