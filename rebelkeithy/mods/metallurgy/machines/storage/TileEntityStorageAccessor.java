package rebelkeithy.mods.metallurgy.machines.storage;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.network.PacketDispatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rebelkeithy.mods.metallurgy.core.Coord;
import rebelkeithy.mods.metallurgy.machines.MetallurgyMachines;
import rebelkeithy.mods.metallurgy.machines.PacketHandler;

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
		addTab(new ItemStack(Item.feather), 5*9);
		addTab(new ItemStack(Item.feather), 5*9);
		addTab(new ItemStack(Item.appleRed), 5*9);
		addTab(new ItemStack(Item.appleRed), 5*9);
		addTab(new ItemStack(Item.appleRed), 5*9);
		*/
	}
	
	public void addTab(ItemStack itemstack, int amount)
	{
		//if(worldObj != null && !worldObj.isRemote)
		///	PacketDispatcher.sendPacketToAllPlayers(PacketHandler.getAddTabPacket(itemstack.itemID, amount, xCoord, yCoord, zCoord));
		if(worldObj == null)
			System.out.println("[NULL] adding tab " + itemstack.itemID + " size " + amount);
		else if(worldObj.isRemote)
			System.out.println("[CLIENT] adding tab " + itemstack.itemID + " size " + amount);
		else if(worldObj.isRemote)
			System.out.println("[SERVER] adding tab " + itemstack.itemID + " size " + amount);
		if(inventories.containsKey(itemstack.itemID))
		{
			inventories.get(itemstack.itemID).addSlots(amount);
		} else {
			inventories.put(itemstack.itemID, new InventoryStorage(this, amount));
		}
	}

	public InventoryStorage getInventory(int id) {
		if(id == 0)
			return getInventory();
		else
		{
			//System.out.println("getting inventory " + id);
			return inventories.get(id);
		}
	}

	public InventoryStorage getInventory() {
		if(inventories.size() == 0)
		{
			return new InventoryStorage(this, 0);
		}
		
		/*for(InventoryStorage si : inventories.values())
		{
			System.out.println("getting default inventory ");
			System.out.println("number of invenotries = " + inventories.size());
			System.out.println("inventory " + si);
			System.out.println("inventory items " + si.items);
			System.out.println("inventory size " + si.getSizeInventory());
			return si;
		}*/
		return null;
	}

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        System.out.println("Reading from NBT");
        NBTTagList tagTabList = par1NBTTagCompound.getTagList("TabList");
        for(int n = 0; n < tagTabList.tagCount(); n++)
        {
        	System.out.println("reading ID from NBT");
        	NBTTagCompound tag = (NBTTagCompound) tagTabList.tagAt(n);
        	int id = tag.getInteger("ID");
        	int size = tag.getInteger("Size");
        	addTab(new ItemStack(id, 1, 0), size);
        }
        
        for(int id : inventories.keySet())
        {        
            NBTTagList tagTabItems = par1NBTTagCompound.getTagList("Tab" + id);
            for(int n = 0; n < tagTabItems.tagCount(); n++)
            {
            	NBTTagCompound tagItem = (NBTTagCompound) tagTabItems.tagAt(n);
            	int item = tagItem.getInteger("Slot");
            	inventories.get(id).items[item] = ItemStack.loadItemStackFromNBT(tagItem);
            }
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        NBTTagList var2 = new NBTTagList();
        
        for(Integer id : inventories.keySet())
        {
        	InventoryStorage inv = inventories.get(id);
        	NBTTagCompound tagID = new NBTTagCompound();
        	tagID.setInteger("ID", id);
        	tagID.setInteger("Size", inv.getSizeInventory());
        	var2.appendTag(tagID);
        }
        par1NBTTagCompound.setTag("TabList", var2);
        
        for(Integer id : inventories.keySet())
        {
        	NBTTagList items = new NBTTagList();
        	InventoryStorage inv = inventories.get(id);
        	for(int n = 0; n < inv.getSizeInventory(); n++)
        	{
        		if(inv.items[n] != null)
        		{
        			NBTTagCompound tagSlot = new NBTTagCompound();
        			tagSlot.setInteger("Slot", n);
        			inv.items[n].writeToNBT(tagSlot);
        			items.appendTag(tagSlot);
        		}
        	}
        	
        	par1NBTTagCompound.setTag("Tab" + id, items);
        }
        
    }

	public void addBlock(int xCoord, int yCoord, int zCoord) 
	{
		if(corner1.equals(new Coord(0, 0, 0)) && corner2.equals(new Coord(0, 0, 0)))
		{
			corner1 = new Coord(xCoord, yCoord, zCoord);
			corner2 = new Coord(xCoord, yCoord, zCoord);
		}
		
		expandZAxis();
		expandYAxis();
		expandXAxis();
	}
	
	private void expandXAxis()
	{
		List<Coord> iter = Coord.between(new Coord(corner2.x+1, corner1.y, corner1.z), new Coord(corner2.x+1, corner2.y, corner2.z));
		if(tryExpandSide(iter))
			corner2.x++;
		iter = Coord.between(new Coord(corner1.x-1, corner1.y, corner1.z), new Coord(corner1.x-1, corner2.y, corner2.z));
		if(tryExpandSide(iter))
			corner1.x--;
	}
	
	private void expandYAxis()
	{
		List<Coord> iter = Coord.between(new Coord(corner1.x, corner2.y+1, corner1.z), new Coord(corner2.x, corner2.y+1, corner2.z));
		if(tryExpandSide(iter))
			corner2.y++;
		iter = Coord.between(new Coord(corner1.x, corner1.y-1, corner1.z), new Coord(corner2.x, corner1.y-1, corner2.z));
		if(tryExpandSide(iter))
			corner1.y--;
	}
	
	private void expandZAxis()
	{		
		List<Coord> iter = Coord.between(new Coord(corner1.x, corner1.y, corner2.z+1), new Coord(corner2.x, corner2.y, corner2.z+1));
		if(tryExpandSide(iter))
			corner2.z++;
		iter = Coord.between(new Coord(corner1.x, corner1.y, corner1.z-1), new Coord(corner2.x, corner2.y, corner1.z-1));
		if(tryExpandSide(iter))
			corner1.z--;
	}
	
	private boolean tryExpandSide(List<Coord> coords)
	{
		for(Coord c : coords)
			if(!this.checkNewBlock(c.x, c.y, c.z))
				return false;

		for(Coord c : coords)
			activateBlock(c);
		
		return true;
	}
	
	
	private void activateBlock(int x, int y, int z)
	{
		activateBlock(new Coord(x, y, z));
	}
	
	private void activateBlock(Coord c)
	{
		activatedBlocks.add(c);
		if(worldObj.getBlockId(c.x, c.y, c.z) == MetallurgyMachines.instance.storageBlock.blockID)
		{
			int itemID = ((TileEntityStorageBlock)worldObj.getBlockTileEntity(c.x, c.y, c.z)).itemID;
			System.out.println("Activating block and adding tab " + itemID);
			this.addTab(new ItemStack(itemID, 1, 0), 3*9);
		}
	}
	
	private boolean checkNewBlock(int x, int y, int z)
	{
		int id = worldObj.getBlockId(x, y, z);
		if(id == MetallurgyMachines.instance.storageAccessor.blockID || id == MetallurgyMachines.instance.storageBlock.blockID)
		{
			System.out.println("Checking bock id " + id + " at " + x + " " + y + " " + z + "... True");
			return true;
		} else {
			System.out.println("Checking bock " + id + " at " + x + " " + y + " " + z + "... False");
			return false;
		}
	}

	public void setTabSize(ItemStack itemStack, int size) 
	{
		if(worldObj.isRemote)
		{
			if(inventories.containsKey(itemStack.itemID))
			{
				int change = size - inventories.get(itemStack.itemID).items.length;
				//System.out.println("[CLIENT] Adding " + change + " to inventory "+ itemStack.itemID);
				addTab(itemStack, change);
			} else {
				addTab(itemStack, size);
			}
		}
	}

}
