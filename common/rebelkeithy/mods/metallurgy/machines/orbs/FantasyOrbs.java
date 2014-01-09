package rebelkeithy.mods.metallurgy.machines.orbs;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import rebelkeithy.mods.metallurgy.machines.ConfigMachines;
import rebelkeithy.mods.metallurgy.machines.MetallurgyMachines;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class FantasyOrbs
{
    public static Item orb;

    public static void init()
    {
        orb = new ItemOrb(ConfigMachines.orbID).setUnlocalizedName("MetallurgyOrb").setCreativeTab(MetallurgyMachines.machineTab);

        for (int i = 0; i < ItemOrb.names.length; i++)
        {
            LanguageRegistry.addName(new ItemStack(orb, 1, i), ItemOrb.names[i] + " Orb");
        }

        for (int i = 0; i < ItemOrb.names.length; i++)
        {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(orb, 1, i), "III", "IPI", "III", 'P', Item.enderPearl, 'I', "ingot" + ItemOrb.names[i]));
        }
    }
}
