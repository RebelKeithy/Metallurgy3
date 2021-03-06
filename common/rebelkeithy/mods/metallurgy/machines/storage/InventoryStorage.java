package rebelkeithy.mods.metallurgy.machines.storage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.HashMap;
import java.util.Map;

public class InventoryStorage implements IInventory
{
	public TileEntity tileEntity;

	public ItemStack[] items;
	
	public int scroll;
	
	public InventoryStorage(TileEntity te, int size)
	{
		System.out.println("CREATING STORAGE INVENTORY ONCE");
		items = new ItemStack[size];
		tileEntity = te;
	}
	
	public void addSlots(int i) 
	{
		/*
		if(tileEntity.worldObj.isRemote)
			System.out.println("On client");
		else
			System.out.println("On server");
		*/
		//System.out.println("Adding " + i + " to tab size " + items.length);
		ItemStack[] newArray = new ItemStack[items.length + i];
		for(int n = 0; n < items.length; n ++)
			newArray[n] = items[n];
		items = newArray;
	}
	
	@Override
	public int getSizeInventory() 
	{
		return items.length;
	}

	@Override
	public ItemStack getStackInSlot(int var1) 
	{
		if(var1 + scroll*9 < items.length)
			return items[(int) (var1 + scroll*9)];
		else
			return null;
	}


    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
	@Override
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (items[par1 + scroll*9] != null)
        {
            ItemStack var3;

            if (items[par1 + scroll*9].stackSize <= par2)
            {
                var3 = items[par1 + scroll*9];
                items[par1 + scroll*9] = null;
                onInventoryChanged();
                return var3;
            }
            else
            {
                var3 = items[par1 + scroll*9].splitStack(par2);

                if (items[par1 + scroll*9].stackSize == 0)
                {
                	items[par1 + scroll*9] = null;
                }

                onInventoryChanged();
                return var3;
            }
        }
        else
        {
            return null;
        }
    }

	@Override
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (items[par1 + scroll*9] != null)
        {
            ItemStack var2 = items[par1];
            items[par1 + scroll*9] = null;
            return var2;
        }
        else
        {
            return null;
        }
    }

	@Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
		items[par1 + scroll*9] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }

        this.onInventoryChanged();
    }

	@Override
	public String getInvName() {
		return "container.storage";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
		return tileEntity.worldObj.getBlockTileEntity(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord) != tileEntity ? false : par1EntityPlayer.getDistanceSq((double)tileEntity.xCoord + 0.5D, (double)tileEntity.yCoord + 0.5D, (double)tileEntity.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public void onInventoryChanged() 
	{
        if (tileEntity.worldObj != null)
        {
            int blockMetadata = tileEntity.worldObj.getBlockMetadata(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
            tileEntity.worldObj.updateTileEntityChunkAndDoNothing(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, tileEntity);
        }
	}

	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		// TODO Auto-generated method stub
		return true;
	}

}
