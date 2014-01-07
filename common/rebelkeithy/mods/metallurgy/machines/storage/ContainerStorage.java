package rebelkeithy.mods.metallurgy.machines.storage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import invtweaks.api.container.ChestContainer;

@ChestContainer
public class ContainerStorage extends Container
{
    InventoryStorage inventory;
    private int numRows;

    public ContainerStorage(IInventory playerInveotry, InventoryStorage storageInventory)
    {
        super();
        inventory = storageInventory;
        if (storageInventory != null)
        {
            numRows = storageInventory.getSizeInventory() / 9;
        }
        else
        {
            numRows = 0;
        }
        if (numRows > 5)
        {
            numRows = 5;
        }
        final int var3 = 18;

        for (int var4 = 0; var4 < numRows; ++var4)
        {
            for (int var5 = 0; var5 < 9; ++var5)
            {
                addSlotToContainer(new Slot(storageInventory, var5 + var4 * 9, 9 + var5 * 18, 18 + var4 * 18));
            }
        }

        for (int var4 = 0; var4 < 3; ++var4)
        {
            for (int var5 = 0; var5 < 9; ++var5)
            {
                addSlotToContainer(new Slot(playerInveotry, var5 + var4 * 9 + 9, 8 + var5 * 18 + 1, 103 + var4 * 18 + var3 - 9));
            }
        }

        for (int n = 0; n < 9; ++n)
        {
            addSlotToContainer(new Slot(playerInveotry, n, 8 + n * 18 + 1, 161 + var3 - 9));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer var1)
    {
        return true;
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or
     * you will crash when someone does that.
     */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack var3 = null;
        final Slot var4 = (Slot) inventorySlots.get(par2);

        if (var4 != null && var4.getHasStack())
        {
            final ItemStack var5 = var4.getStack();
            var3 = var5.copy();

            if (par2 < numRows * 9)
            {
                if (!mergeItemStack(var5, numRows * 9, inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!mergeItemStack(var5, 0, numRows * 9, false))
            {
                return null;
            }

            if (var5.stackSize == 0)
            {
                var4.putStack((ItemStack) null);
            }
            else
            {
                var4.onSlotChanged();
            }
        }

        return var3;
    }

}
