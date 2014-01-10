package rebelkeithy.mods.metallurgy.machines.abstractor;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet250CustomPayload;
import rebelkeithy.mods.metallurgy.core.MetallurgyCore;
import rebelkeithy.mods.metallurgy.machines.ConfigMachines;
import rebelkeithy.mods.metallurgy.machines.TileEntityMachineBase;
import rebelkeithy.mods.metallurgy.metals.MetallurgyMetals;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TileEntityAbstractor extends TileEntityMachineBase implements ISidedInventory
{
    /**
     * Returns the number of ticks that the supplied fuel item will keep the
     * furnace burning, or 0 if the item isn't fuel
     */
    public static int getItemBurnTime(ItemStack par0ItemStack)
    {
        if (par0ItemStack == null)
        {
            return 0;
        }
        else
        {
            final int var1 = par0ItemStack.getItem().itemID;
            par0ItemStack.getItem();

            if (var1 == MetallurgyMetals.fantasySet.getOreInfo("Prometheum").dust.itemID)
            {
                return 1200;
            }
            if (var1 == MetallurgyMetals.fantasySet.getOreInfo("Astral Silver").dust.itemID)
            {
                return 3600;
            }
            if (var1 == MetallurgyMetals.fantasySet.getOreInfo("Carmot").dust.itemID)
            {
                return 7200;
            }
            return AbstractorRecipes.getFuelAmount(par0ItemStack);
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
     * The ItemStacks that hold the items currently being used in the furnace
     */
    private ItemStack[] furnaceItemStacks = new ItemStack[3];

    /** The number of ticks that the furnace will keep burning */
    public int furnaceBurnTime = 0;

    /**
     * The number of ticks that a fresh copy of the currently-burning item would
     * keep the furnace burning for
     */
    public int currentItemBurnTime = 0;

    /** The number of ticks that the current item has been cooking for */
    public int furnaceCookTime = 0;

    public int furnaceTimeBase = 200;

    private int ticksSinceSync;

    @Override
    public boolean canExtractItem(int slot, ItemStack par2ItemStack, int side)
    {
        return slot == 2;
    }

    @Override
    public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3)
    {
        return isItemValidForSlot(par1, par2ItemStack);
    }

    /**
     * Returns true if the furnace can smelt an item, i.e. has a source item,
     * destination stack isn't full, etc.
     */
    private boolean canSmelt()
    {
        if (furnaceItemStacks[0] == null)
        {
            return false;
        }
        else
        {
            final int amount = AbstractorRecipes.essence().getEssenceResult(furnaceItemStacks[0]);
            if (amount == 0)
            {
                return false;
            }
            return true;
        }
    }

    @Override
    public void closeChest()
    {
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of
     * the second int arg. Returns the new stack.
     */
    @Override
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
     * Get the size of the side inventory.
     */
    @Override
    public int[] getAccessibleSlotsFromSide(int par1)
    {
        return par1 == 0 ? new int[]
        { 1, 2 } : par1 == 1 ? new int[]
        { 1, 0, 2 } : new int[]
        { 1, 2 };
    }

    /**
     * Returns an integer between 0 and the passed value representing how much
     * burn time is left on the current fuel item, where 0 means that the item
     * is exhausted and the passed value means that the item is fresh
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
     * Returns an integer between 0 and the passed value representing how close
     * the current item is to being completely cooked
     */
    public int getCookProgressScaled(int par1)
    {
        return furnaceCookTime * par1 / furnaceTimeBase;
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
        return "container.furnace";
    }

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory()
    {
        return furnaceItemStacks.length;
    }

    /**
     * Returns the stack in slot i
     */
    @Override
    public ItemStack getStackInSlot(int par1)
    {
        return furnaceItemStacks[par1];
    }

    /**
     * When some containers are closed they call this on each slot, then drop
     * whatever it returns as an EntityItem - like when you close a workbench
     * GUI.
     */
    @Override
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (furnaceItemStacks[par1] != null)
        {
            final ItemStack var2 = furnaceItemStacks[par1];
            furnaceItemStacks[par1] = null;
            return var2;
        }
        else
        {
            return null;
        }
    }

    public int getType()
    {
        return worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
    }

    /**
     * Returns true if the furnace is currently burning
     */
    @Override
    public boolean isActive()
    {
        return furnaceBurnTime > 0;
    }

    @Override
    public boolean isInvNameLocalized()
    {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring
     * stack size) into the given slot.
     */
    @Override
    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
    {
        return par1 == 2 ? false : par1 == 1 ? isItemFuel(par2ItemStack) : true;
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
    }

    /**
     * Reads a tile entity from NBT.
     */
    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        final NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
        furnaceItemStacks = new ItemStack[getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            final NBTTagCompound var4 = (NBTTagCompound) var2.tagAt(var3);
            final byte var5 = var4.getByte("Slot");

            if (var5 >= 0 && var5 < furnaceItemStacks.length)
            {
                furnaceItemStacks[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }

        furnaceBurnTime = par1NBTTagCompound.getShort("BurnTime");
        furnaceCookTime = par1NBTTagCompound.getShort("CookTime");
        direction = par1NBTTagCompound.getShort("Direction");
        furnaceTimeBase = par1NBTTagCompound.getShort("TimeBase");
        currentItemBurnTime = getItemBurnTime(furnaceItemStacks[1]);
        ticksSinceSync = 40;
    }

    @Override
    public boolean receiveClientEvent(int i, int j)
    {
        if (i == 1)
        {
            direction = j;
        }
        else if (i == 2)
        {
            furnaceTimeBase = j;
        }
        else if (i == 3)
        {
            furnaceBurnTime = j;
        }
        else if (i == 4)
        {
            furnaceCookTime = j;
        }
        else
        {
            return false;
        }

        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        return true;
    }

    /*
     * @Override public int getStartInventorySide(ForgeDirection side) { if
     * (side == ForgeDirection.DOWN) return 1; if (side == ForgeDirection.UP)
     * return 0; return 2; }
     * 
     * @Override public int getSizeInventorySide(ForgeDirection side) { return
     * 1; }
     */

    public void sendPacket()
    {
        if (worldObj.isRemote)
        {
            return;
        }

        final ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
        final DataOutputStream dos = new DataOutputStream(bos);
        try
        {
            dos.writeShort(51);
            dos.writeInt(xCoord);
            dos.writeInt(yCoord);
            dos.writeInt(zCoord);
            dos.writeInt(direction);
            dos.writeInt(furnaceTimeBase);
            dos.writeInt(furnaceBurnTime);
        } catch (final IOException e)
        {
            // UNPOSSIBLE?
        }
        final Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = "M3Machines";
        packet.data = bos.toByteArray();
        packet.length = bos.size();
        packet.isChunkDataPacket = true;

        if (packet != null)
        {
            PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 16, worldObj.provider.dimensionId, packet);
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be
     * crafting or armor sections).
     */
    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        furnaceItemStacks[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > getInventoryStackLimit())
        {
            par2ItemStack.stackSize = getInventoryStackLimit();
        }
    }

    /*
     * @Override public int addItem(ItemStack stack, boolean doAdd,
     * ForgeDirection from) { int slot = 0; if(this.getItemBurnTime(stack) > 0)
     * { slot = 1; }
     * 
     * 
     * if(this.furnaceItemStacks[slot] == null) { if(doAdd)
     * this.furnaceItemStacks[slot] = stack; return stack.stackSize; } else {
     * if(this.furnaceItemStacks[slot].itemID == stack.itemID &&
     * furnaceItemStacks[slot].getItemDamage() == stack.getItemDamage()) {
     * if(this.furnaceItemStacks[slot].stackSize + stack.stackSize >
     * stack.getMaxStackSize()) { int amount = stack.getMaxStackSize() -
     * this.furnaceItemStacks[slot].stackSize; if(doAdd)
     * this.furnaceItemStacks[slot].stackSize =
     * this.furnaceItemStacks[slot].getMaxStackSize(); return amount; } else {
     * if(doAdd) this.furnaceItemStacks[slot].stackSize += stack.stackSize;
     * return stack.stackSize; } } else { return 0; } } }
     * 
     * @Override public ItemStack[] extractItem(boolean doRemove, ForgeDirection
     * from, int maxItemCount) { return null; }
     */

    public void setSpeed(int var1)
    {
        furnaceTimeBase = var1;
    }

    /**
     * Turn one item from the furnace source stack into the appropriate smelted
     * item in the furnace result stack
     */
    public void smeltItem()
    {
        if (canSmelt())
        {
            final int var1 = AbstractorRecipes.essence().getEssenceResult(furnaceItemStacks[0]);

            final int type = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);

            int totalXP = (int) (var1 * ConfigMachines.xpBonus[type]);

            --furnaceItemStacks[0].stackSize;

            if (furnaceItemStacks[0].stackSize <= 0)
            {
                furnaceItemStacks[0] = null;
            }

//            final List<Coord> coords = Coord.get4AdjacentSides(xCoord, yCoord, zCoord);

//            for (final Coord coord : coords)
//            {
//                if (coord.getBlockID(worldObj) == MetallurgyMachines.xpTank.blockID)
//                {
//                    final TileEntityXpTank tileEntity = (TileEntityXpTank) coord.getTileEntity(worldObj);
//                    totalXP -= tileEntity.addXP(totalXP);
//                    if (totalXP <= 0)
//                    {
//                        break;
//                    }
//                }
//            }

            spawnXP(totalXP);

            sendPacket();
        }
    }

    public void spawnXP(int totalXP)
    {
        int xpPerOrb = 1;

        int orbCount = totalXP;

        if (totalXP > 20)
        {
            xpPerOrb = 2;
            orbCount = totalXP / 2 + 1;
        }
        if (totalXP > 40)
        {
            xpPerOrb = 4;
            orbCount = totalXP / 4 + 1;
        }
        if (totalXP > 80)
        {
            xpPerOrb = 8;
            orbCount = totalXP / 8 + 1;
        }

        EntityXPOrb orb;
        for (int n = 0; n < orbCount; n++)
        {
            double xOffset = 0.5;
            double zOffset = 0.5;
            double xMotion = 0;
            double zMotion = 0;

            if (direction == 2)
            {
                zOffset = 0;
                zOffset = -0.1;
            }
            else if (direction == 3)
            {
                zOffset = 1;
                zMotion = 0.1;
            }
            else if (direction == 4)
            {
                xOffset = 0;
                xMotion = -0.1;
            }
            else if (direction == 5)
            {
                xOffset = 1;
                xMotion = 0.1;
            }

            final Random rand = new Random();
            xOffset += (rand.nextInt(21) - 10) / 100.0;
            zOffset += (rand.nextInt(21) - 10) / 100.0;
            xMotion += (rand.nextInt(11) - 5) / 100.0;
            zMotion += (rand.nextInt(11) - 5) / 100.0;
            final double yMotion = (rand.nextInt(11) - 5) / 200.0;

            MetallurgyCore.proxy.spawnParticle("abstractorSmall", worldObj, xCoord + xOffset, yCoord + 0.75, zCoord + zOffset, xMotion * 0.7f, yMotion, zMotion * 0.7f);
            orb = new EntityXPOrb(worldObj, xCoord + xOffset, yCoord + 0.5f, zCoord + zOffset, xpPerOrb);
            orb.motionX = xMotion;
            orb.motionZ = zMotion;
            if (!worldObj.isRemote)
            {
                worldObj.spawnEntityInWorld(orb);
                worldObj.updateEntity(orb);
                worldObj.updateEntities();
            }
        }
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses,
     * e.g. the mob spawner uses this to count ticks and creates a new spawn
     * inside its implementation.
     */
    @Override
    public void updateEntity()
    {
        final boolean prevIsBurning = furnaceBurnTime > 0;
        boolean var2 = false;

        if (++ticksSinceSync > 40)
        {
            sendPacket();
            /*
             * int id = worldObj.getBlockId(xCoord, yCoord, zCoord);
             * worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 1, direction);
             * worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 2,
             * furnaceTimeBase); worldObj.addBlockEvent(xCoord, yCoord, zCoord,
             * id, 3, furnaceBurnTime); worldObj.addBlockEvent(xCoord, yCoord,
             * zCoord, id, 4, furnaceCookTime);
             */
            ticksSinceSync = 0;

        }

        if (furnaceBurnTime > 0)
        {
            --furnaceBurnTime;
        }

        if (!worldObj.isRemote)
        {
            if (furnaceBurnTime == 0 && canSmelt())
            {
                currentItemBurnTime = furnaceBurnTime = getItemBurnTime(furnaceItemStacks[1]);

                if (furnaceBurnTime > 0)
                {
                    var2 = true;

                    if (furnaceItemStacks[1] != null)
                    {
                        --furnaceItemStacks[1].stackSize;

                        if (furnaceItemStacks[1].stackSize == 0)
                        {
                            furnaceItemStacks[1] = null;
                        }
                    }
                }
            }

            if (isActive() && canSmelt())
            {
                ++furnaceCookTime;

                if (furnaceCookTime >= furnaceTimeBase)
                {
                    furnaceCookTime = 0;
                    smeltItem();
                    var2 = true;
                }
            }
            else
            {
                furnaceCookTime = 0;
            }

            if (prevIsBurning != furnaceBurnTime > 0)
            {
                var2 = true;
            }
        }

        if (var2)
        {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            onInventoryChanged();
            sendPacket();
            /*
             * int id = worldObj.getBlockId(xCoord, yCoord, zCoord);
             * worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 1, direction);
             * worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 2,
             * furnaceTimeBase); worldObj.addBlockEvent(xCoord, yCoord, zCoord,
             * id, 3, furnaceBurnTime); worldObj.markBlockForUpdate(xCoord,
             * yCoord, zCoord);
             */
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("BurnTime", (short) furnaceBurnTime);
        par1NBTTagCompound.setShort("CookTime", (short) furnaceCookTime);
        par1NBTTagCompound.setShort("Direction", (short) direction);
        par1NBTTagCompound.setShort("TimeBase", (short) furnaceTimeBase);
        final NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < furnaceItemStacks.length; ++var3)
        {
            if (furnaceItemStacks[var3] != null)
            {
                final NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte) var3);
                furnaceItemStacks[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        par1NBTTagCompound.setTag("Items", var2);
    }
}
