package rebelkeithy.mods.metallurgy.metals.utilityItems;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemIgniter extends ItemFlintAndSteel
{
    public ItemIgniter(int par1)
    {
        super(par1);
    }

    /**
     * Callback for item usage. If the item does something special on right
     * clicking, he will have one of those. Return True if something happen and
     * false if it don't. This is for ITEMS, not BLOCKS
     */
    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par3World.getBlockId(par4, par5, par6) == Block.tnt.blockID)
        {
            Block.tnt.onBlockDestroyedByPlayer(par3World, par4, par5, par6, 1);
            par3World.setBlock(par4, par5, par6, 0, 0, 3);
            return true;
        }

        return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
    }

    @Override
    public ItemIgniter setTextureName(String par1Str)
    {
        super.setTextureName(par1Str);
        return this;
    }
}