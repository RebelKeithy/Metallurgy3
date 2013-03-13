package rebelkeithy.mods.metallurgy.machines.storage;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
    }
    
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving par5EntityLiving, ItemStack par6ItemStack)
    {
    	if(par6ItemStack.hasTagCompound())
    	{
    		NBTTagCompound tag = par6ItemStack.getTagCompound();
    		tag.setInteger("tabID", 257);
    		par6ItemStack.setTagCompound(tag);
    	} else {
    		NBTTagCompound tag = new NBTTagCompound();
    		tag.setInteger("tabID", 257);
    		par6ItemStack.setTagCompound(tag);
    	}
    	
    	TileEntityStorageBlock tesb = (TileEntityStorageBlock) world.getBlockTileEntity(x, y, z);
    	if(par6ItemStack.hasTagCompound())
    	{
    		NBTTagCompound tag = par6ItemStack.getTagCompound();
    		if(tag.hasKey("tabID"))
    		{
    			int tabID = tag.getInteger("tabID");
    			tesb.setTabID(tabID);
    			System.out.println("Block setting tab ID " + tabID);
    		}
    	}
    }
    
    @Override
    public void onPostBlockPlaced(World world, int x, int y, int z, int meta)
    {

    	TileEntityStorageBlock tesb = (TileEntityStorageBlock) world.getBlockTileEntity(x, y, z);
    	//tesb.setTabID(par6ItemStack).tabID);
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
