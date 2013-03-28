package rebelkeithy.mods.metallurgy.machines.forge;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;

import buildcraft.api.inventory.ISpecialInventory;
import cpw.mods.fml.common.network.PacketDispatcher;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TileEntityNetherForge extends TileEntity implements ISidedInventory, ITankContainer, ISpecialInventory, net.minecraftforge.common.ISidedInventory
{
    /**
     * The ItemStacks that hold the items currently being used in the furnace
     */
    private ItemStack[] furnaceItemStacks = new ItemStack[2];

    /** The number of ticks that the furnace will keep burning */
    public int furnaceBurnTime = 0;

    /**
     * The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for
     */
    public int currentItemBurnTime = 0;

    /** The number of ticks that the current item has been cooking for */
    public int furnaceCookTime = 0;
    public float fuelMultiplier = 0.5f;
    
    public int furnaceTimeBase = 200;
    
    public int fuel = 0;
    public int maxFuel = 10000;
    public int fuelPerItem = 100;
    public boolean isBurning;

    public int direction = 0;

	private int ticksSinceSync;
    
    public void setSpeed(int var1)
    {
    	furnaceTimeBase = var1;
    }
    
    public void setMaxBuckets(int buckets)
    {
    	maxFuel = buckets * 1000;
    }
    
    public void addFuelBucket()
    {
    	fuel += 1000;
    	fuel = (fuel < maxFuel) ? fuel : maxFuel;
    	//sync();
    	if(!worldObj.isRemote)
    		sendPacket();
    }

	public void addTakeBucket() 
	{
		if(fuel >= 1000)
			fuel -= 1000;
    	if(!worldObj.isRemote)
    		sendPacket();
	}
    
    public int getFuelScaled(int scale)
    {
    	int retValue = fuel * scale / maxFuel;
    	if(retValue > scale)
    		return scale;
    	else
    		return retValue;
        //return fuel * scale / maxFuel;
    }
    
    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return furnaceItemStacks.length;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int par1)
    {
        return furnaceItemStacks[par1];
    }
    
    public void setDirection(int par1)
    {
    	direction = par1;
    }
    
    public int getDirection()
    {
    	return direction;
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (furnaceItemStacks[par1] != null)
        {
            ItemStack var3;

            if (furnaceItemStacks[par1].stackSize <= par2)
            {
                var3 = furnaceItemStacks[par1];
                furnaceItemStacks[par1] = null;
                return var3;
            }
            else
            {
                var3 = furnaceItemStacks[par1].splitStack(par2);

                if (furnaceItemStacks[par1].stackSize == 0)
                {
                    furnaceItemStacks[par1] = null;
                }

                return var3;
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (furnaceItemStacks[par1] != null)
        {
            ItemStack var2 = furnaceItemStacks[par1];
            furnaceItemStacks[par1] = null;
            return var2;
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        furnaceItemStacks[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > getInventoryStackLimit())
        {
            par2ItemStack.stackSize = getInventoryStackLimit();
        }
    }

    /**
     * Returns the name of the inventory.
     */
    public String getInvName()
    {
        return "container.furnace";
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
        furnaceItemStacks = new ItemStack[getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            byte var5 = var4.getByte("Slot");

            if (var5 >= 0 && var5 < furnaceItemStacks.length)
            {
                furnaceItemStacks[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }

        fuel = par1NBTTagCompound.getInteger("Fuel");
        furnaceCookTime = par1NBTTagCompound.getShort("CookTime");
        direction = par1NBTTagCompound.getShort("Direction");
        furnaceTimeBase = par1NBTTagCompound.getShort("TimeBase");
        maxFuel = par1NBTTagCompound.getInteger("MaxFuel");
        sync();
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("Fuel", fuel);
        par1NBTTagCompound.setShort("CookTime", (short)furnaceCookTime);
        par1NBTTagCompound.setShort("Direction", (short)direction);
        par1NBTTagCompound.setShort("TimeBase", (short)furnaceTimeBase);
        par1NBTTagCompound.setInteger("MaxFuel", maxFuel);
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < furnaceItemStacks.length; ++var3)
        {
            if (furnaceItemStacks[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                furnaceItemStacks[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        par1NBTTagCompound.setTag("Items", var2);
        sync();
    }
    
    public void sync()
    {
    	if(worldObj != null)
	    {
			int id = worldObj.getBlockId(xCoord, yCoord, zCoord);
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 1, direction);
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 2, furnaceTimeBase);
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 3, fuel);
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 4, maxFuel);
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 5, furnaceCookTime);
    	}
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * Returns an integer between 0 and the passed value representing how close the current item is to being completely
     * cooked
     */
    public int getCookProgressScaled(int par1)
    {
    	//System.out.println(furnaceTimeBase);
        return furnaceCookTime * par1 / furnaceTimeBase;
    }

    /**
     * Returns true if the furnace is currently burning
     */
    public boolean isBurning()
    {
        return fuel > 0 && canSmelt();
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
    	furnaceTimeBase = 20;
		if ((++ticksSinceSync % 80) == 0) 
        {
			sendPacket();
		}
		
        boolean var1 = furnaceBurnTime > 0;
        boolean checkBurning = false;
        boolean prevIsBurning = isBurning;

        if(canSmelt() && fuel > 0)
        {
			++furnaceCookTime;
			isBurning = true;

			if (furnaceCookTime == furnaceTimeBase) 
			{
				furnaceCookTime = 0;
				fuel -= fuelPerItem;
				smeltItem();
				checkBurning = true;
			}
        }
        else
        {
            furnaceCookTime = 0;
            isBurning = false;
        }
        
        
        if(prevIsBurning != isBurning)
        {
        	checkBurning = true;
        }

        if (checkBurning)
        {
        	worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            onInventoryChanged();
			//int id = worldObj.getBlockId(xCoord, yCoord, zCoord);
			//sync();
			sendPacket();
        }
    }

    /**
     * Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
     */
    private boolean canSmelt()
    {
        ItemStack var1 = FurnaceRecipes.smelting().getSmeltingResult(furnaceItemStacks[0]);
        if (var1 == null) return false;
        if (furnaceItemStacks[1] == null) return true;
        if (!furnaceItemStacks[1].isItemEqual(var1)) return false;
        int result = furnaceItemStacks[1].stackSize + var1.stackSize;
        return (result <= getInventoryStackLimit() && result <= var1.getMaxStackSize());
    }

    /**
     * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
     */
    public void smeltItem()
    {
        if (canSmelt())
        {
            ItemStack var1 = FurnaceRecipes.smelting().getSmeltingResult(furnaceItemStacks[0]);

            if (furnaceItemStacks[1] == null)
            {
                furnaceItemStacks[1] = var1.copy();
            }
            else if (furnaceItemStacks[1].isItemEqual(var1))
            {
                ++furnaceItemStacks[1].stackSize;
            }

            --furnaceItemStacks[0].stackSize;

            if (furnaceItemStacks[0].stackSize <= 0)
            {
                furnaceItemStacks[0] = null;
            }
        }
    }


    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D) <= 64.0D;
    }

    public void openChest() {}

    public void closeChest() {}


    @Override
	public boolean receiveClientEvent(int i, int j) 
    {
    	/*
		if (i == 1) {
			direction = j;
		} else if (i == 2) {
			furnaceTimeBase = j;
		} else if (i == 3) {
			fuel = j;
		} else if (i == 4) {
			maxFuel = j;
		} else if (i == 5) {
			furnaceCookTime = j;
		}
		*/
    	return false;
	}

    @Override
    public int getStartInventorySide(ForgeDirection side)
    {
        if (side == ForgeDirection.DOWN) return 1;
        if (side == ForgeDirection.UP) return 0; 
        return 2;
    }

    @Override
    public int getSizeInventorySide(ForgeDirection side)
    {
        return 1;
    }

	public int getType() {
		int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		return (meta < 8) ? meta : meta - 8;
	}

	public int getScaledFuel(int i) {
		int scaledFuel = MathHelper.ceiling_float_int(i * (fuel/((float)(maxFuel))));
		return (scaledFuel >= i) ? i : scaledFuel;
	}
	
	public void sendPacket()
	{
		if(worldObj.isRemote)
			return;
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeShort(50);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
			dos.writeInt(direction);
			dos.writeInt(furnaceTimeBase);
			dos.writeInt(furnaceBurnTime);
			dos.writeInt(furnaceCookTime);
			dos.writeInt(fuel);
			dos.writeInt(maxFuel);
		} catch (IOException e) {
			// UNPOSSIBLE?
		}
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "M3Machines";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		packet.isChunkDataPacket = true;
		
		if (packet != null) {
			PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 16, worldObj.provider.dimensionId, packet);
		}
	}

	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill) {
		if(resource.itemID != Block.lavaStill.blockID)
			return 0;
		

		if(fuel < maxFuel)
		{
			int res = 0;
			if(fuel + resource.amount <= maxFuel)
			{
				res = resource.amount;
				fuel += resource.amount;
			} 
			else
			{
				res = maxFuel - fuel;
				fuel = maxFuel;
			}
			//sendPacket();
			sync();
			return res;
		} else {
			return 0;
		}
	}

	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public LiquidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LiquidStack drain(int tankIndex, int maxDrain, boolean doDrain) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int addItem(ItemStack stack, boolean doAdd, ForgeDirection from) {		
		int slot = 0;		

		if(furnaceItemStacks[slot] == null)
		{
			if(doAdd)
				furnaceItemStacks[slot] = stack;
			return stack.stackSize;
		} else {
			if(furnaceItemStacks[slot].itemID == stack.itemID && furnaceItemStacks[slot].getItemDamage() == stack.getItemDamage())
			{
				if(furnaceItemStacks[slot].stackSize + stack.stackSize > stack.getMaxStackSize())
				{
					int amount = stack.getMaxStackSize() - furnaceItemStacks[1].stackSize;
					if(doAdd)
						furnaceItemStacks[slot].stackSize = furnaceItemStacks[1].getMaxStackSize();
					return amount;
				} else {
					if(doAdd)
						furnaceItemStacks[slot].stackSize += stack.stackSize;
					return stack.stackSize;
				}
			} else {
				return 0;
			}
		}
	}

	@Override
	public ItemStack[] extractItem(boolean doRemove, ForgeDirection from, int maxItemCount) {
		if(furnaceItemStacks[1] != null)
		{
			int amount = (furnaceItemStacks[1].stackSize < maxItemCount) ? furnaceItemStacks[1].stackSize : maxItemCount;
			ItemStack[] returnStack = { new ItemStack(furnaceItemStacks[1].itemID, amount, furnaceItemStacks[1].getItemDamage()) };
			if(doRemove)
				decrStackSize(1, amount);
			return returnStack;
		}
		return null;
	}

	@Override
	public ILiquidTank[] getTanks(ForgeDirection direction) {
		// TODO Auto-generated method stub
		return new LiquidTank[] { new LiquidTank(Block.lavaStill.blockID, fuel, maxFuel) };
	}

	@Override
	public ILiquidTank getTank(ForgeDirection direction, LiquidStack type) {
		// TODO Auto-generated method stub
		if(type.itemID == Block.lavaStill.blockID)
			return new LiquidTank(Block.lavaStill.blockID, fuel, maxFuel);
		else
			return null;
	}

	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return false;
	}
	
    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    public boolean isStackValidForSlot(int par1, ItemStack par2ItemStack)
    {
        return par1 == 2 ? false : true;
    }

    /**
     * Get the size of the side inventory.
     */
    @Override
    public int[] getSizeInventorySide(int par1)
    {
        if(par1 == 1)
        	return new int[] {0};
        else if(par1 == 2)
        	return new int[] {1};
        else 
        	return new int[] {0};
    }


    @Override
    public boolean func_102007_a(int par1, ItemStack par2ItemStack, int par3)
    {
        return this.isStackValidForSlot(par1, par2ItemStack);
    }

    @Override
    public boolean func_102008_b(int par1, ItemStack par2ItemStack, int par3)
    {
        return par3 != 1 || par2ItemStack.itemID == Item.bucketEmpty.itemID;
    }
}
