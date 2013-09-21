package rebelkeithy.mods.metallurgy.machines.enchanter;

import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;

class SlotMetallurgyEnchantmentTable extends InventoryBasic
{
    /** The brewing stand this slot belongs to. */
    final ContainerMetallurgyEnchantment container;

    SlotMetallurgyEnchantmentTable(ContainerMetallurgyEnchantment par1ContainerEnchantment, String par2Str, boolean par3, int par4)
    {
        super(par2Str, par3, par4);
        container = par1ContainerEnchantment;
    }

    public boolean func_94041_b(int par1, ItemStack par2ItemStack)
    {
        return true;
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be
     * 64, possibly will be extended. *Isn't this more of a set than a get?*
     */
    @Override
    public int getInventoryStackLimit()
    {
        return 1;
    }

    /**
     * Called when an the contents of an Inventory change, usually
     */
    @Override
    public void onInventoryChanged()
    {
        super.onInventoryChanged();
        container.onCraftMatrixChanged(this);
    }
}
