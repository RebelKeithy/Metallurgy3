package rebelkeithy.mods.metallurgy.machines;

import net.minecraft.tileentity.TileEntity;

public class TileEntityMachineBase extends TileEntity
{
    protected int direction;
    protected boolean active;

    public int getDirection()
    {
        return direction;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setDirection(int direction)
    {
        this.direction = direction;
    }

}
