package rebelkeithy.mods.metallurgy.compatibility;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import rebelkeithy.mods.metallurgy.machines.mint.TileEntityMintStorage;

public class TileEntityMintStorageCompatibility extends TileEntityMintStorage {
	
	/**
     * Reads a tile entity from NBT.
     */
    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
    	try {
    		super.readFromNBT(par1NBTTagCompound);
        } catch (Exception e) {
        	direction = par1NBTTagCompound.getShort ("Direction");
        	
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
        
        

    }
}
