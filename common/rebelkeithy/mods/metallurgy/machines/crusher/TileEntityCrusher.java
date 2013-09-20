package rebelkeithy.mods.metallurgy.machines.crusher;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.GameRegistry;

public class TileEntityCrusher extends TileEntity implements IInventory, ISidedInventory
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

    public int direction = 2;

	private int ticksSinceSync;

	private boolean needsUpdate;

	private int type;
	
	private float crusherAngle = 0;
    
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
    @Override
    public ItemStack getStackInSlot(int par1)
    {
        return this.furnaceItemStacks[par1];
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
    @Override
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
    @Override
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
    @Override
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
    @Override
    public String getInvName()
    {
        return "container.furnace";
    }

    /**
     * Reads a tile entity from NBT.
     */
    @Override
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
        ticksSinceSync = 20;
    }

    /**
     * Writes a tile entity to NBT.
     */
    @Override
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
    @Override
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
    	if(furnaceTimeBase == 0)
    		return 0;
    	
        return furnaceCookTime * par1 / furnaceTimeBase;
    }

    /**
     * Returns an integer between 0 and the passed value representing how much burn time is left on the current fuel
     * item, where 0 means that the item is exhausted and the passed value means that the item is fresh
     */
    public int getBurnTimeRemainingScaled(int par1)
    {
        if (currentItemBurnTime == 0)
        {
            currentItemBurnTime = furnaceTimeBase;
        }

        return furnaceBurnTime * par1 / currentItemBurnTime;
    }

    /**
     * Returns true if the furnace is currently burning
     */
    public boolean isBurning()
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

		if ((++ticksSinceSync % 80) == 0 && !worldObj.isRemote) 
        {
			/*
			int id = worldObj.getBlockId(xCoord, yCoord, zCoord);
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 1, direction);
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 2, furnaceTimeBase);
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 3, furnaceBurnTime);
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 4, furnaceCookTime);
			*/
            sendPacket();
		}
		
        boolean var1 = this.furnaceBurnTime > 0;
        boolean var2 = false;

        if (this.furnaceBurnTime > 0)
        {
            --this.furnaceBurnTime;
            this.crusherAngle += 0.1;
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
                            this.furnaceItemStacks[1] = this.furnaceItemStacks[1].getItem().getContainerItemStack(furnaceItemStacks[1]);
                        }
                    }
                }
                
                this.worldObj.updateAllLightTypes(xCoord, yCoord, zCoord);
            }

            if (this.isBurning() && this.canSmelt())
            {
                ++this.furnaceCookTime;

                if (this.furnaceCookTime == furnaceTimeBase)
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

            if (var1 != this.furnaceBurnTime > 0)
            {
                var2 = true;
            }
        }

        if (var2)
        {
        	worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            this.onInventoryChanged();
            sendPacket();
        }
    }
    
    
    /*public void sync()
    {
    	
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, mod_MetallurgyCore.crusher.blockID, 1, direction);
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, mod_MetallurgyCore.crusher.blockID, 2, furnaceTimeBase);
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, mod_MetallurgyCore.crusher.blockID, 3, furnaceBurnTime);
    	
    }*/

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
            ItemStack var1 = CrusherRecipes.smelting().getCrushingResult(this.furnaceItemStacks[0]);
            if (var1 == null) return false;
            if (this.furnaceItemStacks[2] == null) return true;
            if (!this.furnaceItemStacks[2].isItemEqual(var1)) return false;
            int result = furnaceItemStacks[2].stackSize + var1.stackSize;
            return (result <= getInventoryStackLimit() && result <= var1.getMaxStackSize());
        }
    }

    /**
     * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
     */
    public void smeltItem()
    {
        if (this.canSmelt())
        {
            ItemStack var1 = CrusherRecipes.smelting().getCrushingResult(this.furnaceItemStacks[0]);

            if (this.furnaceItemStacks[2] == null)
            {
                this.furnaceItemStacks[2] = var1.copy();
            }
            else if (this.furnaceItemStacks[2].isItemEqual(var1))
            {
                this.furnaceItemStacks[2].stackSize += var1.stackSize;
            }

            --this.furnaceItemStacks[0].stackSize;

            if (this.furnaceItemStacks[0].stackSize <= 0)
            {
                this.furnaceItemStacks[0] = null;
            }
        }
    }

    /**
     * Returns the number of ticks that the supplied fuel item will keep the furnace burning, or 0 if the item isn't
     * fuel
     */
    public static int getItemBurnTime(ItemStack par1ItemStack)
    {
        if (par1ItemStack == null)
        {
            return 0;
        }
        else
        {
            int var1 = par1ItemStack.getItem().itemID;
            Item var2 = par1ItemStack.getItem();

            if (par1ItemStack.getItem() instanceof ItemBlock && Block.blocksList[var1] != null)
            {
                Block var3 = Block.blocksList[var1];

                if (var3 == Block.woodSingleSlab)
                {
                    return 113;
                }

                if (var3.blockMaterial == Material.wood)
                {
                    return 225;
                }

                if (var3 == Block.field_111034_cE)
                {
                    return 16000;
                }
            }
            if (var2 instanceof ItemTool && ((ItemTool) var2).getToolMaterialName().equals("WOOD")) return 150;
            if (var2 instanceof ItemSword && ((ItemSword) var2).getToolMaterialName().equals("WOOD")) return 150;
            if (var2 instanceof ItemHoe && ((ItemHoe) var2).getMaterialName().equals("WOOD")) return 150;
            if (var1 == Item.stick.itemID) return 75;
            if (var1 == Item.coal.itemID) return 1200;
            if (var1 == Item.bucketLava.itemID) return 15000;
            if (var1 == Block.sapling.blockID) return 75;
            if (var1 == Item.blazeRod.itemID) return 1800;
            return (int) Math.ceil(GameRegistry.getFuelValue(par1ItemStack) * 0.75);
        }
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
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }
    
    @Override
	public boolean receiveClientEvent(int i, int j) 
    {
		if (i == 1)
			direction = j;
		if (i == 2)
			furnaceTimeBase = j;
		if (i == 3)
			furnaceBurnTime = j;
		if (i == 4)
			furnaceCookTime = j;
		else
			return false;

		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		return true;
	}

    public void openChest() {}

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
		if(worldObj != null)
		{
			int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			return (meta < 8) ? meta : meta - 8;
		}
		
		return type;
	}
	
	public void sendPacket()
	{
		if(worldObj.isRemote)
			return;
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeShort(52);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
			dos.writeInt(direction);
			dos.writeInt(furnaceTimeBase);
			dos.writeInt(furnaceBurnTime);
			dos.writeInt(furnaceCookTime);
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

	public void setType(int metadata) {
		type = metadata;
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
	*/

	/*
	@Override
	public ItemStack[] extractItem(boolean doRemove, ForgeDirection from, int maxItemCount) {
		if(furnaceItemStacks[2] != null)
		{
			int amount = (furnaceItemStacks[2].stackSize < maxItemCount) ? furnaceItemStacks[2].stackSize : maxItemCount;
			ItemStack[] returnStack = { new ItemStack(furnaceItemStacks[2].itemID, amount, furnaceItemStacks[2].getItemDamage()) };
			if(doRemove)
				decrStackSize(2, amount);
			return returnStack;
		}
		return null;
	}
	*/

	@Override
	public boolean isInvNameLocalized() 
	{
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
    	if(par1 == 0)
    		return new int[] {1, 2};
    	else if(par1 == 1)
    		return new int[] {1, 0, 2};
    	else
    		return new int[] {1, 2};
    }


    @Override
    public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3)
    {
        return this.isItemValidForSlot(par1, par2ItemStack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack par2ItemStack, int side)
    {
        return slot == 2 || par2ItemStack.itemID == Item.bucketEmpty.itemID;
    }

	public float getCrusherAngles() 
	{
		return crusherAngle;
	}

}
