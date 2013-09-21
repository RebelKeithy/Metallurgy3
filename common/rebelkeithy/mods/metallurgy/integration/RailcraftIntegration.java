package rebelkeithy.mods.metallurgy.integration;

import net.minecraft.item.ItemStack;
import rebelkeithy.mods.metallurgy.api.IOreInfo;
import rebelkeithy.mods.metallurgy.api.MetallurgyAPI;

public class RailcraftIntegration
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
                    // IRockCrusherRecipe recipe =
                    // RailcraftCraftingManager.rockCrusher.createNewRecipe(ore,
                    // true, false);
                    // recipe.addOutput(output, 1.0f);

                    // recipe =
                    // RailcraftCraftingManager.rockCrusher.createNewRecipe(ingot,
                    // true, false);
                    // recipe.addOutput(dust, 1.0f);
                }
            }
        }
    }

    public static void init()
    {
        try
        {
            Class.forName("mods.railcraft.api.crafting.RailcraftCraftingManager");

            System.out.println("Metallurgy: Adding Railcraft Integration");
            addCompatability();
        } catch (final Exception e)
        {
            System.out.println("Metlalurgy: Skipping Railcraft Integration");
        }
    }
}
