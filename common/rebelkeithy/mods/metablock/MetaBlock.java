package rebelkeithy.mods.metablock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Things that can't be changed per subblock:
// - Creative Tab
// - Name
// - texturefile
public class MetaBlock extends Block {

	SubBlock[] subBlocks;
	
	public static List registeredIDs;
	
	public static void registerID(int id)
	{
		if(registeredIDs == null)
			registeredIDs = new ArrayList();
		
		if(registeredIDs.contains(id))
			return;
		
		Block block = Block.blocksList[id];
		if(block instanceof MetaBlock)
		{
			GameRegistry.registerBlock(block, ItemMetaBlock.class);
			registeredIDs.add(id);
		}
	}
	
	public MetaBlock(int id) 
	{
		super(id, Material.rock);
		subBlocks = new SubBlock[16];
	}

	public void addSubBlock(SubBlock block, int meta)
	{
		if(subBlocks[meta] == null)
		{
			subBlocks[meta] = block;
		} else {
			throw new IllegalArgumentException("[MetaBlock] In block " + this.blockID + " " + this + " metadata " + meta + " is already occupied by " + subBlocks[meta] + " when adding " + block);
		}
	}


	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (int n = 0; n < 16; n++) {
			if(subBlocks[n] != null)
			{
				par3List.add(new ItemStack(this, 1, n));
			}
		}
	}
	
	//--Block Redirect Methods--//

    @SideOnly(Side.CLIENT)
	@Override
    public int getBlockTexture(IBlockAccess par1IBlockAccess, int x, int y, int z, int side)
    {
		int meta = par1IBlockAccess.getBlockMetadata(x, y, z);
		return subBlocks[meta].getBlockTexture(par1IBlockAccess, x, y, z, side);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    
    @Override
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        return subBlocks[par2].getBlockTextureFromSide(par1);
    }

    public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        int count = quantityDropped(metadata, fortune, world.rand);
        for(int i = 0; i < count; i++)
        {
            int id = idDropped(metadata, world.rand, fortune);
            if (id > 0)
            {
                ret.add(new ItemStack(id, 1, damageDropped(metadata)));
            }
        }
        return ret;
    }

    @Override
    public int quantityDropped(int meta, int fortune, Random random)
    {
        return subBlocks[meta].quantityDroppedWithBonus(fortune, random);
    }
    
    @Override
    public int idDropped(int meta, Random par2Random, int par3)
    {
        return subBlocks[meta].idDropped(par2Random, par3);
    }

    /**
     * Returns the block hardness at a location. Args: world, x, y, z
     */
    @Override
    public float getBlockHardness(World world, int x, int y, int z)
    {
    	int meta = world.getBlockMetadata(x, y, z);
        return subBlocks[meta].getBlockHardness();
    }

    @Override
    public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ)
    {
    	int meta = world.getBlockMetadata(x, y, z);
        return subBlocks[meta].getExplosionResistance(entity);
    }
}
