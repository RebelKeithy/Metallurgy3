package rebelkeithy.mods.metallurgy.machines.mint;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ContainerMintStorage extends Container
{
    private IInventory lowerChestInventory;
    private int numRows;
    private int numCols;

    public ContainerMintStorage(InventoryPlayer playerInv, TileEntity chestInv)
    {
        this.lowerChestInventory = (IInventory) chestInv;
        this.numRows = 2;
        this.numCols = 3;
        ((IInventory) chestInv).openChest();
        int var3 = (this.numRows - 4) * 18;
        int currRow;
        int currCol;

        
        int i = 0;
        for (currRow = 0; currRow < this.numRows; ++currRow)
        {
            for (currCol = 0; currCol < this.numCols; ++currCol)
            {
                this.addSlotToContainer(new SlotMint(lowerChestInventory, currCol + currRow * numCols, 62 + currCol * 18, 18 + currRow * 18));
            }
        }

        for (currRow = 0; currRow < 3; ++currRow)
        {
            for (currCol = 0; currCol < 9; ++currCol)
            {
                this.addSlotToContainer(new Slot(playerInv, currCol + currRow * 9 + 9, 8 + currCol * 18, 113 + currRow * 18 + var3));
            }
        }

        for (currRow = 0; currRow < 9; ++currRow)
        {
            this.addSlotToContainer(new Slot(playerInv, currRow, 8 + currRow * 18, 171 + var3));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.lowerChestInventory.isUseableByPlayer(par1EntityPlayer);
    }

    /**
     * Called to transfer a stack from one inventory to the other eg. when shift clicking.
     */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par1)
    {
        ItemStack var2 = null;
        Slot var3 = (Slot)this.inventorySlots.get(par1);

        if (var3 != null && var3.getHasStack())
        {
            ItemStack var4 = var3.getStack();
            var2 = var4.copy();

            if (par1 < this.numRows * this.numCols)
            {
                if (!this.mergeItemStack(var4, this.numRows * this.numCols, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var4, 0, this.numRows * this.numCols, false))
            {
                return null;
            }

            if (var4.stackSize == 0)
            {
                var3.putStack((ItemStack)null);
            }
            else
            {
                var3.onSlotChanged();
            }
        }

        return var2;
    }

    /**
     * Callback for when the crafting gui is closed.
     */
    @Override
    public void onContainerClosed(EntityPlayer par1EntityPlayer)
    {
        super.onContainerClosed(par1EntityPlayer);
        this.lowerChestInventory.closeChest();
    }
}
