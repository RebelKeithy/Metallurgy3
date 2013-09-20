package rebelkeithy.mods.metallurgy.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMachineBase extends TileEntity
{
	protected int direction;
	protected boolean active;
	
	public void setDirection(int direction) 
	{
		this.direction = direction;
	}

	public int getDirection() 
	{
		return direction;
	}

	public boolean isActive() 
	{
		return active;
	}

}
