package rebelkeithy.mods.metallurgy.machines.pylon;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import rebelkeithy.mods.metallurgy.machines.ConfigMachines;
import rebelkeithy.mods.metallurgy.machines.MetallurgyMachines;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class Pylon
{
    public static Block pylon;

    public static void init()
    {
        pylon = new BlockPylon(ConfigMachines.pylonID).setHardness(4f).setCreativeTab(MetallurgyMachines.machineTab);

        GameRegistry.registerBlock(pylon, BlockPylonItem.class, "MetallurgyPylon");
        GameRegistry.registerTileEntity(TileEntityPylon.class, "MetallurgyPylonTileEntity");

        for (int i = 0; i < BlockPylon.names.length; i++)
        {
            LanguageRegistry.addName(new ItemStack(pylon, 1, i), BlockPylon.names[i] + " Pylon");
        }
    }
}
