package rebelkeithy.mods.metallurgy.machines.storage;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import rebelkeithy.mods.metallurgy.machines.MetallurgyMachines;

public class StorageBlock extends Block
{

    public StorageBlock(int par1)
    {
        super(par1, Material.iron);
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (world.isRemote)
        {
            return true;
        }
        else
        {
            // TileEntityDispenser var10 =
            // (TileEntityDispenser)world.getBlockTileEntity(x, y, z);

            // if (var10 != null)
            {
                par5EntityPlayer.openGui(MetallurgyMachines.instance, 0, world, x, y, z);
            }

            return true;
        }
    }
}
