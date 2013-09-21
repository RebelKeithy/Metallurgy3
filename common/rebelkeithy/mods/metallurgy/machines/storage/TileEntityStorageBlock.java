package rebelkeithy.mods.metallurgy.machines.storage;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityStorageBlock extends TileEntity
{
    int accessorX;
    int accessorY;
    int accessorZ;
    int itemID;
    public boolean isConnected;
    public boolean isActive;

    public TileEntityStorageBlock()
    {
        accessorX = -1;
        accessorY = -1;
        accessorZ = -1;
        isConnected = false;
        isActive = false;
    }

    public void connectToAccessor(TileEntityStorageAccessor tesa)
    {
        accessorX = tesa.xCoord;
        accessorY = tesa.yCoord;
        accessorZ = tesa.zCoord;
        isConnected = true;
    }

    public boolean connectToStorageAccessor(World world, int x, int y, int z)
    {
        accessorX = x;
        accessorY = y;
        accessorZ = z;
        final TileEntityStorageAccessor tesa = (TileEntityStorageAccessor) world.getBlockTileEntity(x, y, z);
        isConnected = true;
        tesa.addBlock(xCoord, yCoord, zCoord);
        return true;
    }

    public boolean connectToStorageBlock(World world, int x, int y, int z)
    {
        final TileEntityStorageBlock tesb = (TileEntityStorageBlock) world.getBlockTileEntity(x, y, z);
        if (tesb.isConnected)
        {
            return connectToStorageAccessor(world, tesb.accessorX, tesb.accessorY, tesb.accessorZ);
        }
        else
        {
            return false;
        }
    }

    public void setTabID(int itemID)
    {
        this.itemID = itemID;
    }

}
