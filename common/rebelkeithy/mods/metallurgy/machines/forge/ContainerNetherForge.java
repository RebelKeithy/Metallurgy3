package rebelkeithy.mods.metallurgy.machines.forge;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

import invtweaks.api.container.InventoryContainer;
import invtweaks.api.container.ContainerSectionCallback;
import invtweaks.api.container.ContainerSection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@InventoryContainer
public class ContainerNetherForge extends Container
{
    private final TileEntityNetherForge furnace;
    private int lastCookTime = 0;
    private int lastItemBurnTime = 0;

    public ContainerNetherForge(InventoryPlayer par1InventoryPlayer, TileEntity par2TileEntityNetherForge)
    {
        furnace = (TileEntityNetherForge) par2TileEntityNetherForge;
        addSlotToContainer(new Slot(furnace, 0, 36, 34));
        addSlotToContainer(new SlotNetherForge(par1InventoryPlayer.player, furnace, 1, 96, 34));
        int var3;

        for (var3 = 0; var3 < 3; ++var3)
        {
            for (int var4 = 0; var4 < 9; ++var4)
            {
                addSlotToContainer(new Slot(par1InventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3)
        {
            addSlotToContainer(new Slot(par1InventoryPlayer, var3, 8 + var3 * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return furnace.isUseableByPlayer(par1EntityPlayer);
    }

    /**
     * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
     */
    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int var1 = 0; var1 < crafters.size(); ++var1)
        {
            final ICrafting var2 = (ICrafting) crafters.get(var1);

            if (lastCookTime != furnace.furnaceCookTime)
            {
                var2.sendProgressBarUpdate(this, 0, furnace.furnaceCookTime);
            }

            if (lastItemBurnTime != furnace.currentItemBurnTime)
            {
                var2.sendProgressBarUpdate(this, 1, furnace.currentItemBurnTime);
            }
        }

        lastCookTime = furnace.furnaceCookTime;
        lastItemBurnTime = furnace.currentItemBurnTime;
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

            if (par1 == 1)
            {
                if (!mergeItemStack(var4, 2, 38, true))
                {
                    return null;
                }

                var3.onSlotChange(var4, var2);
            }
            else if (par1 != 1 && par1 != 0)
            {
                if (FurnaceRecipes.smelting().getSmeltingResult(var4) != null)
                {
                    if (!mergeItemStack(var4, 0, 1, false))
                    {
                        return null;
                    }
                }
                else if (TileEntityFurnace.isItemFuel(var4))
                {
                    if (!mergeItemStack(var4, 0, 1, false))
                    {
                        return null;
                    }
                }
                else if (par1 >= 2 && par1 < 29)
                {
                    if (!mergeItemStack(var4, 29, 38, false))
                    {
                        return null;
                    }
                }
                else if (par1 >= 29 && par1 < 38 && !mergeItemStack(var4, 2, 29, false))
                {
                    return null;
                }
            }
            else if (!mergeItemStack(var4, 2, 38, false))
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

            if (var4.stackSize == var2.stackSize)
            {
                return null;
            }

            var3.onPickupFromSlot(par1EntityPlayer, var4);
        }

        return var2;
    }

    @Override
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0)
        {
            furnace.furnaceCookTime = par2;
        }

        if (par1 == 1)
        {
            furnace.currentItemBurnTime = par2;
        }
    }

	@ContainerSectionCallback
	public Map<ContainerSection, List<Slot>> getSections() {
        Map<ContainerSection, List<Slot>> slotRefs = new HashMap<ContainerSection, List<Slot>>();

        slotRefs.put(ContainerSection.FURNACE_IN, inventorySlots.subList(0, 1));
        slotRefs.put(ContainerSection.FURNACE_OUT, inventorySlots.subList(1, 2));
        return slotRefs;
	}
}
