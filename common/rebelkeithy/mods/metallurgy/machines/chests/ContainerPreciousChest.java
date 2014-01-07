package rebelkeithy.mods.metallurgy.machines.chests;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import invtweaks.api.container.ChestContainer;

@ChestContainer(isLargeChest = true)
public class ContainerPreciousChest extends Container
{
    private final IInventory lowerChestInventory;
    private final int numRows;
    private final int numCols;

    public ContainerPreciousChest(InventoryPlayer playerInv, TileEntity chestInv)
    {
        lowerChestInventory = (IInventory) chestInv;
        numRows = ((TileEntityPreciousChest) chestInv).getNumRows();
        numCols = ((TileEntityPreciousChest) chestInv).getNumCols();
        lowerChestInventory.openChest();
        final int var3 = (numRows - 4) * 18;
        int currRow;
        int currCol;

        int modifier = 0;

        if (numCols == 12)
        {
            modifier = 27;
        }
        if (numCols == 10)
        {
            modifier = 9;
        }

        for (currRow = 0; currRow < numRows; ++currRow)
        {
            for (currCol = 0; currCol < numCols; ++currCol)
            {
                addSlotToContainer(new Slot(lowerChestInventory, currCol + currRow * numCols, 11 + currCol * 18, 18 + currRow * 18));
            }
        }

        for (currRow = 0; currRow < 3; ++currRow)
        {
            for (currCol = 0; currCol < 9; ++currCol)
            {
                addSlotToContainer(new Slot(playerInv, currCol + currRow * 9 + 9, 11 + currCol * 18 + modifier, 94 + currRow * 18 + var3));
            }
        }

        for (currRow = 0; currRow < 9; ++currRow)
        {
            addSlotToContainer(new Slot(playerInv, currRow, 11 + currRow * 18 + modifier, 152 + var3));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return lowerChestInventory.isUseableByPlayer(par1EntityPlayer);
    }

    /**
     * Callback for when the crafting gui is closed.
     */
    @Override
    public void onContainerClosed(EntityPlayer par1EntityPlayer)
    {
        super.onContainerClosed(par1EntityPlayer);
        lowerChestInventory.closeChest();
    }

    /**
     * Called to transfer a stack from one inventory to the other eg. when shift
     * clicking.
     */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par1)
    {
        ItemStack var2 = null;
        final Slot var3 = (Slot) inventorySlots.get(par1);

        if (var3 != null && var3.getHasStack())
        {
            final ItemStack var4 = var3.getStack();
            var2 = var4.copy();

            if (par1 < numRows * numCols)
            {
                if (!mergeItemStack(var4, numRows * numCols, inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!mergeItemStack(var4, 0, numRows * numCols, false))
            {
                return null;
            }

            if (var4.stackSize == 0)
            {
                var3.putStack((ItemStack) null);
            }
            else
            {
                var3.onSlotChanged();
            }
        }

        return var2;
    }

	@ChestContainer.RowSizeCallback
	public int getNumColumns() {
		return numCols;
	}
}
