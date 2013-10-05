package rebelkeithy.mods.metallurgy.machines.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import rebelkeithy.mods.metallurgy.core.Coord;
import rebelkeithy.mods.metallurgy.machines.MetallurgyMachines;

public class TileEntityStorageAccessor extends TileEntity
{
    public Map<Integer, InventoryStorage> inventories;
    List<Coord> connectedBlocks;
    List<Coord> activatedBlocks;
    Coord corner1;
    Coord corner2;

    public TileEntityStorageAccessor()
    {
        inventories = new HashMap<Integer, InventoryStorage>();
        connectedBlocks = new ArrayList<Coord>();
        activatedBlocks = new ArrayList<Coord>();
        corner1 = new Coord(xCoord, yCoord, zCoord);
        corner2 = new Coord(xCoord, yCoord, zCoord);
        /*
         * addTab(new ItemStack(Item.feather), 5*9); addTab(new
         * ItemStack(Item.feather), 5*9); addTab(new ItemStack(Item.appleRed),
         * 5*9); addTab(new ItemStack(Item.appleRed), 5*9); addTab(new
         * ItemStack(Item.appleRed), 5*9);
         */
    }

    private void activateBlock(Coord c)
    {
        activatedBlocks.add(c);
        if (worldObj.getBlockId(c.x, c.y, c.z) == MetallurgyMachines.storageBlock.blockID)
        {
            final int itemID = ((TileEntityStorageBlock) worldObj.getBlockTileEntity(c.x, c.y, c.z)).itemID;
            addTab(new ItemStack(itemID, 1, 0), 3 * 9);
        }
    }

    public void addBlock(int xCoord, int yCoord, int zCoord)
    {
        if (corner1.equals(new Coord(0, 0, 0)) && corner2.equals(new Coord(0, 0, 0)))
        {
            corner1 = new Coord(xCoord, yCoord, zCoord);
            corner2 = new Coord(xCoord, yCoord, zCoord);
        }

        expandZAxis();
        expandYAxis();
        expandXAxis();
    }

    public void addTab(ItemStack itemstack, int amount)
    {
        // if(worldObj != null && !worldObj.isRemote)
        // /
        // PacketDispatcher.sendPacketToAllPlayers(PacketHandler.getAddTabPacket(itemstack.itemID,
        // amount, xCoord, yCoord, zCoord));

        if (inventories.containsKey(itemstack.itemID))
        {
            inventories.get(itemstack.itemID).addSlots(amount);
        }
        else
        {
            inventories.put(itemstack.itemID, new InventoryStorage(this, amount));
        }
    }

    private boolean checkNewBlock(int x, int y, int z)
    {
        final int id = worldObj.getBlockId(x, y, z);
        return (id == MetallurgyMachines.storageAccessor.blockID || id == MetallurgyMachines.storageBlock.blockID);
    }

    private void expandXAxis()
    {
        List<Coord> iter = Coord.between(new Coord(corner2.x + 1, corner1.y, corner1.z), new Coord(corner2.x + 1, corner2.y, corner2.z));
        if (tryExpandSide(iter))
        {
            corner2.x++;
        }
        iter = Coord.between(new Coord(corner1.x - 1, corner1.y, corner1.z), new Coord(corner1.x - 1, corner2.y, corner2.z));
        if (tryExpandSide(iter))
        {
            corner1.x--;
        }
    }

    private void expandYAxis()
    {
        List<Coord> iter = Coord.between(new Coord(corner1.x, corner2.y + 1, corner1.z), new Coord(corner2.x, corner2.y + 1, corner2.z));
        if (tryExpandSide(iter))
        {
            corner2.y++;
        }
        iter = Coord.between(new Coord(corner1.x, corner1.y - 1, corner1.z), new Coord(corner2.x, corner1.y - 1, corner2.z));
        if (tryExpandSide(iter))
        {
            corner1.y--;
        }
    }

    private void expandZAxis()
    {
        List<Coord> iter = Coord.between(new Coord(corner1.x, corner1.y, corner2.z + 1), new Coord(corner2.x, corner2.y, corner2.z + 1));
        if (tryExpandSide(iter))
        {
            corner2.z++;
        }
        iter = Coord.between(new Coord(corner1.x, corner1.y, corner1.z - 1), new Coord(corner2.x, corner2.y, corner1.z - 1));
        if (tryExpandSide(iter))
        {
            corner1.z--;
        }
    }

    public InventoryStorage getInventory()
    {
        if (inventories.size() == 0)
        {
            return new InventoryStorage(this, 0);
        }

        return null;
    }

    public InventoryStorage getInventory(int id)
    {
        if (id == 0)
        {
            return getInventory();
        }
        else
        {
            return inventories.get(id);
        }
    }

    /**
     * Reads a tile entity from NBT.
     */
    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        final NBTTagList tagTabList = par1NBTTagCompound.getTagList("TabList");
        for (int n = 0; n < tagTabList.tagCount(); n++)
        {
            final NBTTagCompound tag = (NBTTagCompound) tagTabList.tagAt(n);
            final int id = tag.getInteger("ID");
            final int size = tag.getInteger("Size");
            addTab(new ItemStack(id, 1, 0), size);
        }

        for (final int id : inventories.keySet())
        {
            final NBTTagList tagTabItems = par1NBTTagCompound.getTagList("Tab" + id);
            for (int n = 0; n < tagTabItems.tagCount(); n++)
            {
                final NBTTagCompound tagItem = (NBTTagCompound) tagTabItems.tagAt(n);
                final int item = tagItem.getInteger("Slot");
                inventories.get(id).items[item] = ItemStack.loadItemStackFromNBT(tagItem);
            }
        }
    }

    public void setTabSize(ItemStack itemStack, int size)
    {
        if (worldObj.isRemote)
        {
            if (inventories.containsKey(itemStack.itemID))
            {
                final int change = size - inventories.get(itemStack.itemID).items.length;

                addTab(itemStack, change);
            }
            else
            {
                addTab(itemStack, size);
            }
        }
    }

    private boolean tryExpandSide(List<Coord> coords)
    {
        for (final Coord c : coords)
        {
            if (!checkNewBlock(c.x, c.y, c.z))
            {
                return false;
            }
        }

        for (final Coord c : coords)
        {
            activateBlock(c);
        }

        return true;
    }

    /**
     * Writes a tile entity to NBT.
     */
    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        final NBTTagList var2 = new NBTTagList();

        for (final Integer id : inventories.keySet())
        {
            final InventoryStorage inv = inventories.get(id);
            final NBTTagCompound tagID = new NBTTagCompound();
            tagID.setInteger("ID", id);
            tagID.setInteger("Size", inv.getSizeInventory());
            var2.appendTag(tagID);
        }
        par1NBTTagCompound.setTag("TabList", var2);

        for (final Integer id : inventories.keySet())
        {
            final NBTTagList items = new NBTTagList();
            final InventoryStorage inv = inventories.get(id);
            for (int n = 0; n < inv.getSizeInventory(); n++)
            {
                if (inv.items[n] != null)
                {
                    final NBTTagCompound tagSlot = new NBTTagCompound();
                    tagSlot.setInteger("Slot", n);
                    inv.items[n].writeToNBT(tagSlot);
                    items.appendTag(tagSlot);
                }
            }

            par1NBTTagCompound.setTag("Tab" + id, items);
        }

    }

}
