package rebelkeithy.mods.metallurgy.metals.utilityItems;

import rebelkeithy.mods.metallurgy.metals.MetallurgyMetals;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemIgniter extends ItemFlintAndSteel
{
    public ItemIgniter(int par1)
    {
        super(par1);
        this.maxStackSize = 1;
        this.setMaxDamage(128);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
    	if(par3World.getBlockId((int)par4, (int)par5, (int)par6) == Block.tnt.blockID)
    	{
    		Block.tnt.onBlockDestroyedByPlayer(par3World, par4, par5, par6, 1);
    		par3World.setBlock(par4, par5, par6, 0, 0, 3);
    		return true;
    	}
    	
    	return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
    }
}