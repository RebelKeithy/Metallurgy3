package rebelkeithy.mods.metablock;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;

public class SubBlock 
{
	private MetaBlock metaBlock;
	int meta;
	
	private int[] textureIndex;
	
	private ItemStack drop;
	private int dropMin;
	private int dropMax;
	
	private float hardness;
	private float blockResistance;
	
	public SubBlock(int id, int meta)
	{
		if(Block.blocksList[id] == null)
		{
			metaBlock = new MetaBlock(id);
			metaBlock.addSubBlock(this, meta);
		} else {
			metaBlock = (MetaBlock) Block.blocksList[id];
			metaBlock.addSubBlock(this, meta);
		}
		
		textureIndex = new int[16];
	}

	
	public SubBlock setBlockTextureIndex(int textureIndex)
	{
		this.textureIndex[meta] = textureIndex;
		return this;
	}
	
	public int getBlockTexture(IBlockAccess par1iBlockAccess, int x, int y, int z, int side) {
		return textureIndex[meta];
	}


	public int getBlockTextureFromSide(int par1) {
		return textureIndex[meta];
	}

	
	public SubBlock setTextureFile(String texturePath)
    {		
		metaBlock.setTextureFile(texturePath);
		return this;
    }


	public SubBlock setCreativeTab(CreativeTabs tab) 
	{
		metaBlock.setCreativeTab(tab);
		return this;
	}


	public SubBlock setBlockName(String string) {
		metaBlock.setBlockName(string);
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
		return super.toString() + metaBlock.getBlockName();
	}
}
