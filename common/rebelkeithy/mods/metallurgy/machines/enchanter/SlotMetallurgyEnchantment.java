package rebelkeithy.mods.metallurgy.machines.enchanter;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

class SlotMetallurgyEnchantment extends Slot
{
    /** The brewing stand this slot belongs to. */
    final ContainerMetallurgyEnchantment container;

    SlotMetallurgyEnchantment(ContainerMetallurgyEnchantment par1ContainerEnchantment, IInventory par2IInventory, int par3, int par4, int par5)
    {
        super(par2IInventory, par3, par4, par5);
        container = par1ContainerEnchantment;
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for
     * the armor slots.
     */
    @Override
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        return true;
    }
}
