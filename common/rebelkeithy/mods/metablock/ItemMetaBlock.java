package rebelkeithy.mods.metablock;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringTranslate;

public class ItemMetaBlock extends ItemBlock
{
	public ItemMetaBlock(int par1)
	{
		super(par1);
		setHasSubtypes(true);
	}
	
    public CreativeTabs[] getCreativeTabs()
    {
        return ((MetaBlock)Block.blocksList[this.getBlockID()]).getCreativeTabArray();
    }

	@Override
    public String getItemDisplayName(ItemStack par1ItemStack)
    {
		//return getItemNameIS(par1ItemStack);
        return ("" + StringTranslate.getInstance().translateNamedKey(this.getLocalizedName(par1ItemStack))).trim();
    }
	
	@Override
	public int getMetadata(int metadata)
	{
		return metadata;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		int meta = itemstack.getItemDamage();
		return getUnlocalizedName() + "." + meta;
	}
}
