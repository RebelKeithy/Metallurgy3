package rebelkeithy.mods.metablock;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.IBlockAccess;

public class SubBlock 
{
	private MetaBlock metaBlock;
	int meta;
	
	private int[] textureIndex;
	
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
}
