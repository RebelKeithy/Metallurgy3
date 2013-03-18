package rebelkeithy.mods.metablock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

//Things that can't be changed per subblock:
// - Creative Tab
// - Name
// - texturefile
public class MetaBlock extends Block {

	SubBlock[] subBlocks;
	List<Integer> tickList;
	ArrayList<CreativeTabs> tabs;
	
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
		tickList = new ArrayList();
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
	
    public int damageDropped(int par1)
    {
        return par1;
    }


	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) 
	{
		for (int n = 0; n < 16; n++) 
		{
			if(subBlocks[n] != null && par2CreativeTabs == subBlocks[n].getCreativeTab())
			{
				par3List.add(new ItemStack(this, 1, n));
			}
		}
	}
	

	//--Listeners--//
	public void setTickRandomly(int meta) 
	{
		if(!tickList.contains(meta))
		{
			this.setTickRandomly(true);
			tickList.add(meta);
		}
	}
	
	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
		int meta = par1World.getBlockMetadata(par2, par3, par4);
    	AxisAlignedBB ret = subBlocks[meta].getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
    	
    	if(ret != null)
    		return ret;
    	else
    		return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
    }
	
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
    	int meta = par1World.getBlockMetadata(par2, par3, par4);
    	subBlocks[meta].onEntityCollidedWithBlock(par1World, par2, par3, par4, par5Entity);
    }

    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        int meta = par1World.getBlockMetadata(par2, par3, par4);
        subBlocks[meta].randomDisplayTick(par1World, par2, par3, par4, par5Random);
    }
	
	//--Block Redirect Methods--//

    @SideOnly(Side.CLIENT)
	@Override
    public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int x, int y, int z, int side)
    {
		int meta = par1IBlockAccess.getBlockMetadata(x, y, z);
		return subBlocks[meta].getBlockTexture(par1IBlockAccess, x, y, z, side);
    }

    @SideOnly(Side.CLIENT)
    public void func_94332_a(IconRegister par1IconRegister)
    {
        for (int i = 0; i < 16; ++i)
        {
            if(subBlocks[i] != null)
            {
            	subBlocks[i].func_94332_a(par1IconRegister);
            }
        }
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    
    @Override
    public Icon getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
    	if(subBlocks[par2] != null)
    		return subBlocks[par2].getBlockTextureFromSide(par1);
    	
    	return null;
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

    /**
     * Sets the CreativeTab to display this block on.
     */
    public Block setCreativeTab(CreativeTabs par1CreativeTabs)
    {
    	if(tabs == null)
    		tabs = new ArrayList<CreativeTabs>();
    	
    	if(!tabs.contains(par1CreativeTabs))
    		tabs.add(par1CreativeTabs);
        
    	return this;
    }

	public CreativeTabs[] getCreativeTabArray() 
	{
		if(tabs == null)
			return new CreativeTabs[0];
		
		return tabs.toArray(new CreativeTabs[tabs.size()]);
	}
}
