package rebelkeithy.mods.metallurgy.machines.storage;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

import rebelkeithy.mods.metallurgy.core.Coord;
import rebelkeithy.mods.metallurgy.machines.MetallurgyMachines;

public class BlockStorage extends BlockContainer
{

	public BlockStorage(int par1) 
	{
		super(par1, Material.iron);
	}

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World world, int x, int y, int z) 
    {
    	TileEntityStorageBlock tesb = (TileEntityStorageBlock) world.getBlockTileEntity(x, y, z);
    	boolean foundIt = false;
    	List<Coord>adjacent = Coord.getAdjacentCoords(new Coord(x, y, z));
    	for(Coord c : adjacent)
    	{
    		if(c.equals(x, y, z))
    			continue;

    		if(world.getBlockId(c.x, c.y, c.z) == MetallurgyMachines.instance.storageBlock.blockID)
    			foundIt = tesb.connectToStorageBlock(world, c.x, c.y, c.z);
    		if(!foundIt && world.getBlockId(c.x, c.y, c.z) == MetallurgyMachines.instance.storageAccessor.blockID)
        		foundIt = tesb.connectToStorageAccessor(world, c.x, c.y, c.z);
        	
    		if(foundIt)
    		{
    			System.out.println("connecting");
        		break;
    		}
    	}
    }

	@Override
	public TileEntity createNewTileEntity(World var1) 
	{
		return new TileEntityStorageBlock();
	}
}
