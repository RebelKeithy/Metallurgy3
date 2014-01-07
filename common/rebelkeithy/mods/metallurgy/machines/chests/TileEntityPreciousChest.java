package rebelkeithy.mods.metallurgy.machines.chests;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import rebelkeithy.mods.metallurgy.machines.MetallurgyMachines;

public class TileEntityPreciousChest extends TileEntity implements IInventory
{
    private ItemStack[] chestContents = new ItemStack[120];

    /** The current angle of the lid (between 0 and 1) */
    public float lidAngle;

    /** The angle of the lid last tick */
    public float prevLidAngle;

    /** The number of players currently using this chest */
    public int numUsingPlayers;

    /** Server sync counter (once per 20 ticks) */
    private int ticksSinceSync;

    private int direction;
    private int type;

    @Override
    public void closeChest()
    {
        --numUsingPlayers;
        worldObj.addBlockEvent(xCoord, yCoord, zCoord, MetallurgyMachines.chest.blockID, 1, numUsingPlayers);
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of
     * the second int arg. Returns the new stack.
     */
    @Override
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (chestContents[par1] != null)
        {
            ItemStack var3;

            if (chestContents[par1].stackSize <= par2)
            {
                var3 = chestContents[par1];
                chestContents[par1] = null;
                onInventoryChanged();
                return var3;
            }
            else
            {
                var3 = chestContents[par1].splitStack(par2);

                if (chestContents[par1].stackSize == 0)
                {
                    chestContents[par1] = null;
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

    public int getDirection()
    {
        return direction;
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be
     * 64, possibly will be extended. *Isn't this more of a set than a get?*
     */
    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * Returns the name of the inventory.
     */
    @Override
    public String getInvName()
    {
        return "container.chest";
    }

    public int getNumCols()
    {
        switch (type)
        {
        case 0:
            return 9;
        case 1:
            return 9;
        case 2:
            return 9;
        case 3:
            return 10;
        case 4:
            return 12;
        default:
            return 9;
        }
    }

    public int getNumRows()
    {
        switch (type)
        {
        case 0:
            return 6;
        case 1:
            return 8;
        case 2:
            return 9;
        case 3:
            return 9;
        case 4:
            return 9;
        default:
            return 3;
        }
    }

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory()
    {
        return getNumRows() * getNumCols();
    }

    /**
     * Returns the stack in slot i
     */
    @Override
    public ItemStack getStackInSlot(int par1)
    {
        return chestContents[par1];
    }

    /**
     * When some containers are closed they call this on each slot, then drop
     * whatever it returns as an EntityItem - like when you close a workbench
     * GUI.
     */
    @Override
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (chestContents[par1] != null)
        {
            final ItemStack var2 = chestContents[par1];
            chestContents[par1] = null;
            return var2;
        }
        else
        {
            return null;
        }
    }

    public int getType()
    {
        return type;
    }

    /**
     * invalidates a tile entity
     */
    @Override
    public void invalidate()
    {
        updateContainingBlockInfo();
        super.invalidate();
    }

    @Override
    public boolean isInvNameLocalized()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {
        // TODO Auto-generated method stub
        return true;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes
     * with Container
     */
    @Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this ? false : par1EntityPlayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openChest()
    {
        ++numUsingPlayers;
        worldObj.addBlockEvent(xCoord, yCoord, zCoord, MetallurgyMachines.chest.blockID, 1, numUsingPlayers);
    }

    /**
     * Reads a tile entity from NBT.
     */
    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        direction = par1NBTTagCompound.getInteger("Direction");
        type = par1NBTTagCompound.getInteger("Type");

        final NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
        chestContents = new ItemStack[getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            final NBTTagCompound var4 = (NBTTagCompound) var2.tagAt(var3);
            final int var5 = var4.getByte("Slot") & 255;

            if (var5 >= 0 && var5 < chestContents.length)
            {
                chestContents[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }

    }

    /**
     * Called when a client event is received with the event number and
     * argument, see World.sendClientEvent
     * 
     * @return
     */
    @Override
    public boolean receiveClientEvent(int par1, int par2)
    {
        if (par1 == 1)
        {
            numUsingPlayers = par2;
        }
        else if (par1 == 2)
        {
            direction = par2;
        }
        else if (par1 == 3)
        {
            type = par2;
        }
        else
        {
            return false;
        }
        return true;
    }

    public void setDirection(int par1)
    {
        direction = par1;
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be
     * crafting or armor sections).
     */
    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        chestContents[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > getInventoryStackLimit())
        {
            par2ItemStack.stackSize = getInventoryStackLimit();
        }

        onInventoryChanged();
    }

    public void setType(int par1)
    {
        type = par1;
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses,
     * e.g. the mob spawner uses this to count ticks and creates a new spawn
     * inside its implementation.
     */
    @Override
    public void updateEntity()
    {
        super.updateEntity();

        if (++ticksSinceSync % 20 * 4 == 0)
        {
            worldObj.addBlockEvent(xCoord, yCoord, zCoord, MetallurgyMachines.chest.blockID, 2, direction);
            worldObj.addBlockEvent(xCoord, yCoord, zCoord, MetallurgyMachines.chest.blockID, 3, type);
        }

        prevLidAngle = lidAngle;
        final float var1 = 0.1F;
        double var4;

        if (numUsingPlayers > 0 && lidAngle == 0.0F)
        {
            final double var2 = xCoord + 0.5D;
            var4 = zCoord + 0.5D;

            worldObj.playSoundEffect(var2, yCoord + 0.5D, var4, "random.chestopen", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
        }

        if (numUsingPlayers == 0 && lidAngle > 0.0F || numUsingPlayers > 0 && lidAngle < 1.0F)
        {
            final float var8 = lidAngle;

            if (numUsingPlayers > 0)
            {
                lidAngle += var1;
            }
            else
            {
                lidAngle -= var1;
            }

            if (lidAngle > 1.0F)
            {
                lidAngle = 1.0F;
            }

            final float var3 = 0.5F;

            if (lidAngle < var3 && var8 >= var3)
            {
                var4 = xCoord + 0.5D;
                final double var6 = zCoord + 0.5D;

                worldObj.playSoundEffect(var4, yCoord + 0.5D, var6, "random.chestclosed", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }

            if (lidAngle < 0.0F)
            {
                lidAngle = 0.0F;
            }
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        final NBTTagList var2 = new NBTTagList();
        par1NBTTagCompound.setInteger("Direction", direction);
        par1NBTTagCompound.setInteger("Type", type);

        for (int var3 = 0; var3 < chestContents.length; ++var3)
        {
            if (chestContents[var3] != null)
            {
                final NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte) var3);
                chestContents[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        par1NBTTagCompound.setTag("Items", var2);
    }
}
