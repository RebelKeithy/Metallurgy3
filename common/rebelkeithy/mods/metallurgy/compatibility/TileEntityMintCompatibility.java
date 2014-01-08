package rebelkeithy.mods.metallurgy.compatibility;

import net.minecraft.nbt.NBTTagCompound;
import rebelkeithy.mods.metallurgy.machines.mint.TileEntityMint;

public class TileEntityMintCompatibility extends TileEntityMint {
	
	@Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        try {
        	super.readFromNBT(par1NBTTagCompound);
        } catch (Exception e) {
        	direction = par1NBTTagCompound.getInteger ("Direction");
	        ingotId = par1NBTTagCompound.getInteger("Ingot");
	        amount = par1NBTTagCompound.getInteger("Amount");
        }
    }
	
}
