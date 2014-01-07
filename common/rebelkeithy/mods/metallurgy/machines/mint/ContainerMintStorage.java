package rebelkeithy.mods.metallurgy.machines.mint;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import invtweaks.api.container.InventoryContainer;

@InventoryContainer
public class ContainerMintStorage extends Container
{
    private final IInventory lowerChestInventory;
    private final int numRows;
    private final int numCols;

    public ContainerMintStorage(InventoryPlayer playerInv, TileEntity chestInv)
    {
        lowerChestInventory = (IInventory) chestInv;
        numRows = 2;
        numCols = 3;
        ((IInventory) chestInv).openChest();
        final int var3 = (numRows - 4) * 18;
        int currRow;
        int currCol;

        for (currRow = 0; currRow < numRows; ++currRow)
        {
            for (currCol = 0; currCol < numCols; ++currCol)
            {
                addSlotToContainer(new SlotMint(lowerChestInventory, currCol + currRow * numCols, 62 + currCol * 18, 18 + currRow * 18));
            }
        }

        for (currRow = 0; currRow < 3; ++currRow)
        {
            for (currCol = 0; currCol < 9; ++currCol)
            {
                addSlotToContainer(new Slot(playerInv, currCol + currRow * 9 + 9, 8 + currCol * 18, 113 + currRow * 18 + var3));
            }
        }

        for (currRow = 0; currRow < 9; ++currRow)
        {
            addSlotToContainer(new Slot(playerInv, currRow, 8 + currRow * 18, 171 + var3));
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
}
