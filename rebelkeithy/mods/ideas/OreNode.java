package rebelkeithy.mods.ideas;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class OreNode extends Block
{

	public OreNode(int par1) 
	{
		super(par1, Material.rock);
	}
	
	@Override
    public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) 
	{
		System.out.println("HIT");
	}

	@Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
		if(Math.random() > 0.5)
		{
			dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(Item.ingotIron));
		}
		if(Math.random() > 0.1)
			par1World.setBlock(par2, par3, par4, blockID);
    }

}
