package rebelkeithy.mods.metallurgy.machines.abstractor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ContainerAbstractor extends Container
{
    private TileEntityAbstractor abstractor;
    private int lastCookTime = 0;
    private int lastBurnTime = 0;
    private int lastItemBurnTime = 0;

    public ContainerAbstractor(InventoryPlayer par1InventoryPlayer, TileEntity par2TileEntityMetalFurnace)
    {
        this.abstractor = (TileEntityAbstractor) par2TileEntityMetalFurnace;
        this.addSlotToContainer(new Slot(abstractor, 0, 56, 17));
        this.addSlotToContainer(new Slot(abstractor, 1, 56, 53));
        this.addSlotToContainer(new SlotAbstractor(par1InventoryPlayer.player, abstractor, 2, 116, 35));
        int var3;

        for (var3 = 0; var3 < 3; ++var3)
        {
            for (int var4 = 0; var4 < 9; ++var4)
            {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3)
        {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var3, 8 + var3 * 18, 142));
        }
    }

    /**
     * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
     */
    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int var1 = 0; var1 < this.crafters.size(); ++var1)
        {
            ICrafting var2 = (ICrafting)this.crafters.get(var1);

            if (this.lastCookTime != this.abstractor.furnaceCookTime)
            {
                var2.sendProgressBarUpdate(this, 0, this.abstractor.furnaceCookTime);
            }

            if (this.lastBurnTime != this.abstractor.furnaceBurnTime)
            {
                var2.sendProgressBarUpdate(this, 1, this.abstractor.furnaceBurnTime);
            }

            if (this.lastItemBurnTime != this.abstractor.currentItemBurnTime)
            {
                var2.sendProgressBarUpdate(this, 2, this.abstractor.currentItemBurnTime);
            }
        }

        this.lastCookTime = this.abstractor.furnaceCookTime;
        this.lastBurnTime = this.abstractor.furnaceBurnTime;
        this.lastItemBurnTime = this.abstractor.currentItemBurnTime;
    }

    @Override
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0)
        {
            this.abstractor.furnaceCookTime = par2;
        }

        if (par1 == 1)
        {
            this.abstractor.furnaceBurnTime = par2;
        }

        if (par1 == 2)
        {
            this.abstractor.currentItemBurnTime = par2;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.abstractor.isUseableByPlayer(par1EntityPlayer);
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

            if (par1 == 2)
            {
                if (!this.mergeItemStack(var4, 3, 39, true))
                {
                    return null;
                }

                var3.onSlotChange(var4, var2);
            }
            else if (par1 != 1 && par1 != 0)
            {
                if (AbstractorRecipes.essence().getEssenceResult(var4) != 0)
                {
                    if (!this.mergeItemStack(var4, 0, 1, false))
                    {
                        return null;
                    }
                }
                else if (TileEntityAbstractor.isItemFuel(var4))
                {
                    if (!this.mergeItemStack(var4, 1, 2, false))
                    {
                        return null;
                    }
                }
                else if (par1 >= 3 && par1 < 30)
                {
                    if (!this.mergeItemStack(var4, 30, 39, false))
                    {
                        return null;
                    }
                }
                else if (par1 >= 30 && par1 < 39 && !this.mergeItemStack(var4, 3, 30, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var4, 3, 39, false))
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

            if (var4.stackSize == var2.stackSize)
            {
                return null;
            }

            var3.onPickupFromSlot(par1EntityPlayer, var4);
        }

        return var2;
    }
}