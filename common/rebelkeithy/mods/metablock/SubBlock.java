package rebelkeithy.mods.metablock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class SubBlock 
{
	private MetaBlock metaBlock;
	private CreativeTabs tab;
	int meta;
	
	private int textureIndex;
	
	private ItemStack drop;
	private int dropMin;
	private int dropMax;
	
	private float hardness;
	private float blockResistance;
	
	public List<IDisplayListener> dlList = new ArrayList<IDisplayListener>();
	public List<ICollisionListener> clList = new ArrayList<ICollisionListener>();
	private boolean collisionEffect;
	
	public Icon icon;
	public String iconName;
	
	public SubBlock(int id, int meta, String iconName)
	{
		if(Block.blocksList[id] == null)
		{
			metaBlock = new MetaBlock(id);
			metaBlock.addSubBlock(this, meta);
		} else {
			metaBlock = (MetaBlock) Block.blocksList[id];
			metaBlock.addSubBlock(this, meta);
		}
		
		this.iconName = iconName;
	}
	
	//Listeners
    public void addDisplayListener(IDisplayListener dl)
    {
        metaBlock.setTickRandomly(meta);
    	dlList.add(dl);
    }
    
    public void addCollisionListener(ICollisionListener cl)
    {
    	collisionEffect = true;
    	clList.add(cl);
    }
    
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) 
	{
		for(IDisplayListener dl : dlList)
		{
			dl.randomDisplayTick(par1World, par2, par3, par4, par5Random);
		}
	}
	
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
    	if(collisionEffect)
    	{
    		float var5 = 0.025F;
    		return AxisAlignedBB.getAABBPool().getAABB((double)par2, (double)par3, (double)par4, (double)(par2 + 1), (double)((float)(par3 + 1) - var5), (double)(par4 + 1));
    	}
    	
    	return null;
    }
	
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        for(ICollisionListener cl : clList)
        	cl.collide(par1World, par2, par3, par4, par5Entity, meta);
    }
	
	public Icon getBlockTexture(IBlockAccess par1iBlockAccess, int x, int y, int z, int side) 
	{
		return icon;
		
	}

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
    	this.icon = iconRegister.registerIcon(iconName);
    }


	public Icon getBlockTextureFromSide(int par1) {
		return icon;
	}

	public SubBlock setCreativeTab(CreativeTabs tab) 
	{
		metaBlock.setCreativeTab(tab);
		this.tab = tab;
		return this;
	}
	
	public CreativeTabs getCreativeTab()
	{
		return tab;
	}


	public SubBlock setUnlocalizedName(String string) {
		metaBlock.setUnlocalizedName(string);
		return this;
	}


	public Block getBlock() {
		return metaBlock;
	}
	
	public void setBlockDrops(ItemStack item, int min, int max)
	{
		drop = item.copy();
		dropMin = min;
		dropMax = max;
	}


	public int quantityDroppedWithBonus(int fortune, Random random) 
	{
		if(drop != null && dropMax > 1)	
			return dropMin + random.nextInt(dropMax + fortune) + fortune;
		else
			return 1;
	}


	public int idDropped(Random par2Random, int par3) 
	{
		if(drop != null)
			return drop.itemID;
		else
			return metaBlock.blockID;
	}


	public SubBlock setHardness(float par1) {
		hardness = par1;
		
        if (blockResistance < par1 * 5.0F)
        {
            blockResistance = par1 * 5.0F;
        }
        
		return this;
	}


	public float getBlockHardness() {
		return hardness;
	}


	public SubBlock setResistance(float par1) 
	{
		blockResistance = par1 * 3;
		return this;
	}


	public float getExplosionResistance(Entity entity) 
	{
		return blockResistance/5.0F;
	}
	
	public String toString()
	{
		return super.toString() + metaBlock.getUnlocalizedName();
	}

	public int damageDropped(int meta) 
	{
		if(idDropped(new Random(), meta) == metaBlock.blockID)
			return meta;
		else
			return 0;
	}

}
