package rebelkeithy.mods.metablock;

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

	@Override
    public String getItemDisplayName(ItemStack par1ItemStack)
    {
		//return getItemNameIS(par1ItemStack);
        return ("" + StringTranslate.getInstance().translateNamedKey(this.getLocalItemName(par1ItemStack))).trim();
    }
	
	@Override
	public int getMetadata(int metadata)
	{
		return metadata;
	}
	
	@Override
	public String getItemNameIS(ItemStack itemstack)
	{
		int meta = itemstack.getItemDamage();
		return getItemName() + "." + meta;
	}
}
