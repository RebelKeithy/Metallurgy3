package rebelkeithy.mods.metallurgy.machines.storage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerStorage extends Container
{
	InventoryStorage inventory;
	private int numRows;
	private int currentTab;
	
	public ContainerStorage(IInventory playerInveotry, InventoryStorage storageInventory)
	{
		super();
		inventory = storageInventory;
		if(storageInventory != null)
			numRows = storageInventory.getSizeInventory() / 9;
		else
			numRows = 0;
		if(numRows > 5)
        	numRows = 5;
        int var3 = 18;
        
        for (int var4 = 0; var4 < numRows; ++var4)
        {
            for (int var5 = 0; var5 < 9; ++var5)
            {
                this.addSlotToContainer(new Slot(storageInventory, var5 + var4 * 9, 9 + var5 * 18, 18 + var4 * 18));
            }
        }
        
        for (int var4 = 0; var4 < 3; ++var4)
        {
            for (int var5 = 0; var5 < 9; ++var5)
            {
                this.addSlotToContainer(new Slot(playerInveotry, var5 + var4 * 9 + 9, 8 + var5 * 18 + 1, 103 + var4 * 18 + var3 - 9));
            }
        }

        for (int n = 0; n < 9; ++n)
        {
            this.addSlotToContainer(new Slot(playerInveotry, n, 8 + n * 18 + 1, 161 + var3 - 9));
        }
	}
	

	@Override
	public boolean canInteractWith(EntityPlayer var1) 
	{
		return true;
	}

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
	@Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(par2);

        if (var4 != null && var4.getHasStack())
        {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();

            if (par2 < this.numRows * 9)
            {
                if (!this.mergeItemStack(var5, this.numRows * 9, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var5, 0, this.numRows * 9, false))
            {
                return null;
            }

            if (var5.stackSize == 0)
            {
                var4.putStack((ItemStack)null);
            }
            else
            {
                var4.onSlotChanged();
            }
        }

        return var3;
    }

}
