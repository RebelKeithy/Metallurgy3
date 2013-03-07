package rebelkeithy.mods.metallurgy.machines.storage;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

import rebelkeithy.mods.metallurgy.core.Coord;
import rebelkeithy.mods.metallurgy.core.MetallurgyCore;
import rebelkeithy.mods.metallurgy.machines.MetallurgyMachines;
import rebelkeithy.mods.metallurgy.machines.PacketHandler;

public class BlockStorageAccessor extends BlockContainer
{

	public BlockStorageAccessor(int par1) 
	{
		super(par1, Material.iron);
	}

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (world.isRemote)
        {
            return true;
        }
        else
        {
            TileEntityStorageAccessor var10 = (TileEntityStorageAccessor)world.getBlockTileEntity(x, y, z);

            if(!var10.worldObj.isRemote)
            {
            	if(var10.inventories.size() > 0)
            	{
            		int id = var10.inventories.keySet().iterator().next();
            		int size = var10.getInventory(id).items.length;
            		PacketDispatcher.sendPacketToPlayer(PacketHandler.getSetInvSizePacket(id, size, x, y, z), (Player)par5EntityPlayer);
            		PacketDispatcher.sendPacketToPlayer(PacketHandler.getTabListPacket(world, x, y, z), (Player)par5EntityPlayer);
            	}
            }
            
            if (var10 != null)
            {
            	//System.out.println("Size = " + var10.corner2.subtract(var10.corner1));
            	//System.out.println("openning gui");
            	int firstID = 0;
            	if(var10.inventories.size() > 0)
            		firstID = var10.inventories.keySet().iterator().next();
                par5EntityPlayer.openGui(MetallurgyMachines.instance, 0, world, x, y, z);
            }

            return true;
        }
    }

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityStorageAccessor();
	}
}
