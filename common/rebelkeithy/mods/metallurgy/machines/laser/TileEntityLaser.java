package rebelkeithy.mods.metallurgy.machines.laser;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityLaser extends TileEntity
{
    private int[] length;
    private int cooldown;
    private int sync;

    public TileEntityLaser()
    {
        length = new int[6];
    }

    public TileEntityLaser(int i)
    {
        length = new int[6];
        length[0] = i;
    }

    public void changeLength(int side, boolean isSneaking)
    {
        if (side < 0 || side >= 6)
        {
            return;
        }

        int lengthChangeAmount = 1;
        if (cooldown > 0)
        {
            lengthChangeAmount = 10;
        }
        cooldown = 6;

        if (isSneaking)
        {
            setLength(length[side] - lengthChangeAmount, side);
            if (length[side] < 0)
            {
                setLength(0, side);
            }
        }
        else
        {
            setLength(length[side] + lengthChangeAmount, side);
        }
    }

    @Override
    public int getBlockMetadata()
    {
        return 0;
    }

    @Override
    public Block getBlockType()
    {
        return Laser.laser;
    }

    public int getClipedLength(int side)
    {
        /*
         * if(worldObj != null) { if(side == 1) { MovingObjectPosition clip =
         * worldObj.clip(Vec3.createVectorHelper(xCoord, yCoord, zCoord),
         * Vec3.createVectorHelper(xCoord, yCoord + length[side], zCoord));
         * if(clip != null) return (int) (clip.hitVec.yCoord - yCoord); } else
         * if(side == 0) { MovingObjectPosition clip =
         * worldObj.clip(Vec3.createVectorHelper(xCoord, yCoord, zCoord),
         * Vec3.createVectorHelper(xCoord, (yCoord) - length[side], zCoord));
         * if(clip != null) { int blockID = worldObj.getBlockId(clip.blockX,
         * clip.blockY, clip.blockZ); int meta =
         * worldObj.getBlockMetadata(clip.blockX, clip.blockY, clip.blockZ);
         * if(Block.blocksList[blockID].isOpaqueCube()) return (int) (yCoord -
         * clip.hitVec.yCoord + 1); } } else if(side == 2) {
         * MovingObjectPosition clip =
         * worldObj.clip(Vec3.createVectorHelper(xCoord, yCoord, zCoord),
         * Vec3.createVectorHelper(xCoord, yCoord, zCoord - length[side]));
         * if(clip != null) { int blockID = worldObj.getBlockId(clip.blockX,
         * clip.blockY, clip.blockZ); int meta =
         * worldObj.getBlockMetadata(clip.blockX, clip.blockY, clip.blockZ);
         * if(Block.blocksList[blockID].isOpaqueCube()) return (int) (zCoord -
         * clip.hitVec.zCoord + 1); } } }
         */
        return length[side];
    }

    public int getLength(int side)
    {
        return length[side];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
        return AxisAlignedBB.getAABBPool().getAABB(xCoord - length[4], yCoord - length[0], zCoord - length[2], xCoord + 1 + length[5], yCoord + 1 + length[1],
                zCoord + 1 + length[3]);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);

        length = tag.getIntArray("lengths");
    }

    @Override
    public boolean receiveClientEvent(int par1, int par2)
    {
        length[par1] = par2;
        return true;
    }

    public void sendLength()
    {
        for (int i = 0; i < 6; i++)
        {
            worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType().blockID, i, length[i]);
        }

    }

    public void setLength(int length, int side)
    {
        this.length[side] = length;
        sendLength();
    }

    @Override
    public void updateEntity()
    {
        if (cooldown > 0)
        {
            cooldown--;
        }

        if (sync == 0)
        {
            sendLength();
            sync = 60;
        }
        sync--;
    }

    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);

        tag.setIntArray("lengths", length);
    }
}
