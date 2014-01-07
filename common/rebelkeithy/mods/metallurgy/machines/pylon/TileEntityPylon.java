package rebelkeithy.mods.metallurgy.machines.pylon;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

import com.google.common.collect.Lists;

public class TileEntityPylon extends TileEntity
{
    public float rotationX;
    public float rotationY;
    public float rotationZ;
    public float stepX;
    public float stepY;
    public float stepZ;

    int type;

    public TileEntityPylon()
    {
        final List<Float> primes = Lists.newArrayList(1f, 2f, 3f, 5f, 7f, 9f, -1f, -2f, -3f, -5f, -7f, -9f);
        int choose = (int) (Math.random() * primes.size());
        stepX = primes.get(choose) * 0.0005f;
        primes.remove(choose);
        choose = (int) (Math.random() * primes.size());
        stepY = primes.get(choose) * 0.0005f;
        primes.remove(choose);
        choose = (int) (Math.random() * primes.size());
        stepZ = primes.get(choose) * 0.0005f;
        primes.remove(choose);

    }

    @Override
    public int getBlockMetadata()
    {
        return -1;
    }

    @Override
    public Block getBlockType()
    {
        return Pylon.pylon;
    }

    public int getType()
    {
        if (worldObj != null)
        {
            return worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
        }

        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    @Override
    public void updateEntity()
    {
        rotationX += stepX;
        rotationY += stepY;
        rotationZ += stepZ;
    }
}
