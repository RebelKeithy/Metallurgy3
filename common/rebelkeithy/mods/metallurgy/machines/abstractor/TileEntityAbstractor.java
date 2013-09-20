package rebelkeithy.mods.metallurgy.machines.abstractor;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet250CustomPayload;
import rebelkeithy.mods.keithyutils.Coord;
import rebelkeithy.mods.metallurgy.core.MetallurgyCore;
import rebelkeithy.mods.metallurgy.machines.ConfigMachines;
import rebelkeithy.mods.metallurgy.machines.MetallurgyMachines;
import rebelkeithy.mods.metallurgy.machines.TileEntityMachineBase;
import rebelkeithy.mods.metallurgy.machines.xptank.TileEntityXpTank;
import rebelkeithy.mods.metallurgy.metals.MetallurgyMetals;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TileEntityAbstractor extends TileEntityMachineBase implements ISidedInventory
{
    /**
     * The ItemStacks that hold the items currently being used in the furnace
     */
    private ItemStack[] furnaceItemStacks = new ItemStack[3];

    /** The number of ticks that the furnace will keep burning */
    public int furnaceBurnTime = 0;

    /**
     * The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for
     */
    public int currentItemBurnTime = 0;

    /** The number of ticks that the current item has been cooking for */
    public int furnaceCookTime = 0;
    
    public int furnaceTimeBase = 200;

	private int ticksSinceSync;
    
    public void setSpeed(int var1)
    {
    	furnaceTimeBase = var1;
    }
    
    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return this.furnaceItemStacks.length;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int par1)
    {
        return this.furnaceItemStacks[par1];
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.furnaceItemStacks[par1] != null)
        {
            ItemStack var3;

            if (this.furnaceItemStacks[par1].stackSize <= par2)
            {
                var3 = this.furnaceItemStacks[par1];
                this.furnaceItemStacks[par1] = null;
                return var3;
            }
            else
            {
                var3 = this.furnaceItemStacks[par1].splitStack(par2);

                if (this.furnaceItemStacks[par1].stackSize == 0)
                {
                    this.furnaceItemStacks[par1] = null;
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
        if (this.furnaceItemStacks[par1] != null)
        {
            ItemStack var2 = this.furnaceItemStacks[par1];
            this.furnaceItemStacks[par1] = null;
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
        this.furnaceItemStacks[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
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
        this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            byte var5 = var4.getByte("Slot");

            if (var5 >= 0 && var5 < this.furnaceItemStacks.length)
            {
                this.furnaceItemStacks[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }

        this.furnaceBurnTime = par1NBTTagCompound.getShort("BurnTime");
        this.furnaceCookTime = par1NBTTagCompound.getShort("CookTime");
        this.direction = par1NBTTagCompound.getShort("Direction");
        this.furnaceTimeBase = par1NBTTagCompound.getShort("TimeBase");
        this.currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);
        ticksSinceSync = 40;
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("BurnTime", (short)this.furnaceBurnTime);
        par1NBTTagCompound.setShort("CookTime", (short)this.furnaceCookTime);
        par1NBTTagCompound.setShort("Direction", (short)this.direction);
        par1NBTTagCompound.setShort("TimeBase", (short)this.furnaceTimeBase);
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.furnaceItemStacks.length; ++var3)
        {
            if (this.furnaceItemStacks[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.furnaceItemStacks[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        par1NBTTagCompound.setTag("Items", var2);
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
        return this.furnaceCookTime * par1 / furnaceTimeBase;
    }

    /**
     * Returns an integer between 0 and the passed value representing how much burn time is left on the current fuel
     * item, where 0 means that the item is exhausted and the passed value means that the item is fresh
     */
    public int getBurnTimeRemainingScaled(int par1)
    {
        if (this.currentItemBurnTime == 0)
        {
            this.currentItemBurnTime = furnaceTimeBase;
        }

        return this.furnaceBurnTime * par1 / this.currentItemBurnTime;
    }

    /**
     * Returns true if the furnace is currently burning
     */
    @Override
    public boolean isActive()
    {
        return this.furnaceBurnTime > 0;
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    @Override
    public void updateEntity()
    {    	
        boolean prevIsBurning = this.furnaceBurnTime > 0;
        boolean var2 = false;

		if ((++ticksSinceSync) > 40) 
        {
			sendPacket();
			/*
			int id = worldObj.getBlockId(xCoord, yCoord, zCoord);
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 1, direction);
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 2, furnaceTimeBase);
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 3, furnaceBurnTime);
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 4, furnaceCookTime);
			*/
			ticksSinceSync = 0;
	
		}

        if (this.furnaceBurnTime > 0)
        {
            --this.furnaceBurnTime;
        }

        if (!this.worldObj.isRemote)
        {
            if (this.furnaceBurnTime == 0 && this.canSmelt())
            {
                this.currentItemBurnTime = this.furnaceBurnTime = (int)(getItemBurnTime(this.furnaceItemStacks[1]));

                if (this.furnaceBurnTime > 0)
                {
                    var2 = true;

                    if (this.furnaceItemStacks[1] != null)
                    {
                        --this.furnaceItemStacks[1].stackSize;

                        if (this.furnaceItemStacks[1].stackSize == 0)
                        {
                            this.furnaceItemStacks[1] = null;
                        }
                    }
                }
            }

            if (this.isActive() && this.canSmelt())
            {
                ++this.furnaceCookTime;

                if (this.furnaceCookTime >= furnaceTimeBase)
                {
                    this.furnaceCookTime = 0;
                    this.smeltItem();
                    var2 = true;
                }
            }
            else
            {
                this.furnaceCookTime = 0;
            }

            if (prevIsBurning != this.furnaceBurnTime > 0)
            {
                var2 = true;
            }
        }

        if (var2)
        {
        	worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            this.onInventoryChanged();
            sendPacket();
            /*
			int id = worldObj.getBlockId(xCoord, yCoord, zCoord);
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 1, direction);
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 2, furnaceTimeBase);
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 3, furnaceBurnTime);
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            */
        }
    }

    /**
     * Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
     */
    private boolean canSmelt()
    {
        if (this.furnaceItemStacks[0] == null)
        {
            return false;
        }
        else
        {
            int amount = AbstractorRecipes.essence().getEssenceResult(this.furnaceItemStacks[0]);
            if (amount == 0) return false;
            return true;
        }
    }

    /**
     * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
     */
    public void smeltItem()
    {
        if (this.canSmelt())
        {
            int var1 = AbstractorRecipes.essence().getEssenceResult(this.furnaceItemStacks[0]);

            int type = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
            
            int totalXP = (int) (var1 * ConfigMachines.xpBonus[type]);
            
            --this.furnaceItemStacks[0].stackSize;

            if (this.furnaceItemStacks[0].stackSize <= 0)
            {
                this.furnaceItemStacks[0] = null;
            }

            List<Coord> coords = Coord.get4AdjacentSides(xCoord, yCoord, zCoord);
            
            for(Coord coord : coords)
            {
            	if(coord.getBlockID(worldObj) == MetallurgyMachines.xpTank.blockID)
            	{
            		TileEntityXpTank tileEntity = (TileEntityXpTank) coord.getTileEntity(worldObj);
            		totalXP -= tileEntity.addXP(totalXP);
            		if(totalXP <= 0)
            			break;
            	}
            }
            
            spawnXP(totalXP);
            
            sendPacket();
        }
    }
    
    public void spawnXP(int totalXP)
    {
    	int xpPerOrb = 1;
        
        int orbCount = totalXP;
        
        if(totalXP > 20) {
            xpPerOrb = 2;
            orbCount = (totalXP/2) + 1;
        } 
        if(totalXP > 40) {
            xpPerOrb = 4;
            orbCount = (totalXP/4) + 1;
        }
        if(totalXP > 80) {
            xpPerOrb = 8;
            orbCount = (totalXP/8) + 1;
        }
            
        EntityXPOrb orb;
        for(int n = 0; n < orbCount; n++)
        {
        	double xOffset = 0.5;
        	double zOffset = 0.5;
        	double xMotion = 0;
        	double zMotion = 0;
        	
        	if(direction == 2) {
        		zOffset = 0;
        		zOffset = -0.1;
        	} else if(direction == 3) {
        		zOffset = 1;
        		zMotion = 0.1;
        	} else if(direction == 4) {
        		xOffset = 0;
        		xMotion = -0.1;
        	} else if(direction == 5) {
        		xOffset = 1;
        		xMotion = 0.1;
        	}
        	
        	Random rand = new Random();
        	xOffset += (rand.nextInt(21) - 10) / 100.0;
          	zOffset += (rand.nextInt(21) - 10) / 100.0;
        	xMotion += (rand.nextInt(11) - 5) / 100.0;
          	zMotion += (rand.nextInt(11) - 5) / 100.0;
          	double yMotion  = (rand.nextInt(11) - 5) / 200.0;
        	
          	MetallurgyCore.proxy.spawnParticle("abstractorSmall", worldObj, this.xCoord + xOffset, this.yCoord + 0.75, this.zCoord + zOffset, xMotion*0.7f, yMotion, zMotion*0.7f);
            orb = new EntityXPOrb(this.worldObj, this.xCoord + xOffset, this.yCoord + 0.5f, this.zCoord + zOffset, xpPerOrb);
            orb.motionX = xMotion;
            orb.motionZ = zMotion;
            if(!worldObj.isRemote)
            {
            	this.worldObj.spawnEntityInWorld(orb);
            	this.worldObj.updateEntity(orb);
            	this.worldObj.updateEntities();
            }
        }
    }

    /**
     * Returns the number of ticks that the supplied fuel item will keep the furnace burning, or 0 if the item isn't
     * fuel
     */
    public static int getItemBurnTime(ItemStack par0ItemStack)
    {
        if (par0ItemStack == null)
        {
            return 0;
        }
        else
        {
            int var1 = par0ItemStack.getItem().itemID;
            Item var2 = par0ItemStack.getItem();

            if (var1 == MetallurgyMetals.fantasySet.getOreInfo("Prometheum").dust.itemID) return 1760;
            if (var1 == MetallurgyMetals.fantasySet.getOreInfo("Astral Silver").dust.itemID) return 7040;
            if (var1 == MetallurgyMetals.fantasySet.getOreInfo("Carmot").dust.itemID) return 14080;
            return AbstractorRecipes.getFuelAmount(par0ItemStack);
        }
    }

    @Override
	public boolean receiveClientEvent(int i, int j) 
    {
		if (i == 1) {
			direction = j;
		} else if (i == 2) {
			furnaceTimeBase = j;
		} else if (i == 3) {
			furnaceBurnTime = j;
		} else if (i == 4) {
			furnaceCookTime = j;
		} else {
			return false;
		}

        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		return true;
	}
    
    /**
     * Return true if item is a fuel source (getItemBurnTime() > 0).
     */
    public static boolean isItemFuel(ItemStack par0ItemStack)
    {
        return getItemBurnTime(par0ItemStack) > 0;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    @Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openChest() {}

    @Override
    public void closeChest() {}

    /*
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
    */


	public int getType() {
		return worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
	}

	public void sendPacket()
	{
		if(worldObj.isRemote)
			return;

		ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeShort(51);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
			dos.writeInt(direction);
			dos.writeInt(furnaceTimeBase);
			dos.writeInt(furnaceBurnTime);
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

	/*
	@Override
	public int addItem(ItemStack stack, boolean doAdd, ForgeDirection from) {		
		int slot = 0;
		if(this.getItemBurnTime(stack) > 0)
		{
			slot = 1;
		}


		if(this.furnaceItemStacks[slot] == null)
		{
			if(doAdd)
				this.furnaceItemStacks[slot] = stack;
			return stack.stackSize;
		} else {
			if(this.furnaceItemStacks[slot].itemID == stack.itemID && furnaceItemStacks[slot].getItemDamage() == stack.getItemDamage())
			{
				if(this.furnaceItemStacks[slot].stackSize + stack.stackSize > stack.getMaxStackSize())
				{
					int amount = stack.getMaxStackSize() - this.furnaceItemStacks[slot].stackSize;
					if(doAdd)
						this.furnaceItemStacks[slot].stackSize = this.furnaceItemStacks[slot].getMaxStackSize();
					return amount;
				} else {
					if(doAdd)
						this.furnaceItemStacks[slot].stackSize += stack.stackSize;
					return stack.stackSize;
				}
			} else {
				return 0;
			}
		}
	}

	@Override
	public ItemStack[] extractItem(boolean doRemove, ForgeDirection from, int maxItemCount) {
		return null;
	}
	*/

	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return false;
	}
	
    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
	@Override
    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
    {
        return par1 == 2 ? false : (par1 == 1 ? isItemFuel(par2ItemStack) : true);
    }

    /**
     * Get the size of the side inventory.
     */
    @Override
    public int[] getAccessibleSlotsFromSide(int par1)
    {
        return par1 == 0 ? new int[] {1, 2} : (par1 == 1 ? new int[] {1, 0, 2} : new int[] {1, 2});
    }


    @Override
    public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3)
    {
        return this.isItemValidForSlot(par1, par2ItemStack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack par2ItemStack, int side)
    {
        return slot == 2;
    }
}