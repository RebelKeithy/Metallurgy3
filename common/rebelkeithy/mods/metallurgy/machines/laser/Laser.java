package rebelkeithy.mods.metallurgy.machines.laser;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import rebelkeithy.mods.metallurgy.machines.ConfigMachines;
import rebelkeithy.mods.metallurgy.machines.MetallurgyMachines;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class Laser
{
    public static Block laser;

    public static void init()
    {
        laser = new BlockLaser(ConfigMachines.laserID).setHardness(0.2F).setUnlocalizedName("MinersLaser").setCreativeTab(MetallurgyMachines.machineTab);
        GameRegistry.registerBlock(laser, "MinersLaser");
        GameRegistry.registerTileEntity(TileEntityLaser.class, "MinersLaserTE");

        GameRegistry.addRecipe(new ItemStack(laser, 2), "SGS", "GRG", "SLS", 'S', Block.stone, 'G', Block.thinGlass, 'R', Block.blockRedstone, 'L', Block.torchWood);
        LanguageRegistry.addName(laser, "Measuring Laser");
    }
}
