package rebelkeithy.mods.metallurgy.machines.enchanter;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMetallurgyEnchantmentTable extends TileEntity
{
    /** Used by the render to make the book 'bounce' */
    public int tickCount;

    /** Value used for determining how the page flip should look. */
    public float pageFlip;

    /** The last tick's pageFlip value. */
    public float pageFlipPrev;
    public float field_70373_d;
    public float field_70374_e;

    /** The amount that the book is open. */
    public float bookSpread;

    /** The amount that the book is open. */
    public float bookSpreadPrev;
    public float bookRotation2;
    public float bookRotationPrev;
    public float bookRotation;
    private static Random rand = new Random();
    private String field_94136_s;

    public String func_94133_a()
    {
        return func_94135_b() ? field_94136_s : "container.enchant";
    }

    public void func_94134_a(String par1Str)
    {
        field_94136_s = par1Str;
    }

    public boolean func_94135_b()
    {
        return field_94136_s != null && field_94136_s.length() > 0;
    }

    /**
     * Reads a tile entity from NBT.
     */
    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);

        if (par1NBTTagCompound.hasKey("CustomName"))
        {
            field_94136_s = par1NBTTagCompound.getString("CustomName");
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
        super.updateEntity();
        bookSpreadPrev = bookSpread;
        bookRotationPrev = bookRotation2;
        final EntityPlayer entityplayer = worldObj.getClosestPlayer(xCoord + 0.5F, yCoord + 0.5F, zCoord + 0.5F, 3.0D);

        if (entityplayer != null)
        {
            final double d0 = entityplayer.posX - (xCoord + 0.5F);
            final double d1 = entityplayer.posZ - (zCoord + 0.5F);
            bookRotation = (float) Math.atan2(d1, d0);
            bookSpread += 0.1F;

            if (bookSpread < 0.5F || rand.nextInt(40) == 0)
            {
                final float f = field_70373_d;

                do
                {
                    field_70373_d += rand.nextInt(4) - rand.nextInt(4);
                } while (f == field_70373_d);
            }
        }
        else
        {
            bookRotation += 0.02F;
            bookSpread -= 0.1F;
        }

        while (bookRotation2 >= (float) Math.PI)
        {
            bookRotation2 -= (float) Math.PI * 2F;
        }

        while (bookRotation2 < -(float) Math.PI)
        {
            bookRotation2 += (float) Math.PI * 2F;
        }

        while (bookRotation >= (float) Math.PI)
        {
            bookRotation -= (float) Math.PI * 2F;
        }

        while (bookRotation < -(float) Math.PI)
        {
            bookRotation += (float) Math.PI * 2F;
        }

        float f1;

        for (f1 = bookRotation - bookRotation2; f1 >= (float) Math.PI; f1 -= (float) Math.PI * 2F)
        {
            ;
        }

        while (f1 < -(float) Math.PI)
        {
            f1 += (float) Math.PI * 2F;
        }

        bookRotation2 += f1 * 0.4F;

        if (bookSpread < 0.0F)
        {
            bookSpread = 0.0F;
        }

        if (bookSpread > 1.0F)
        {
            bookSpread = 1.0F;
        }

        ++tickCount;
        pageFlipPrev = pageFlip;
        float f2 = (field_70373_d - pageFlip) * 0.4F;
        final float f3 = 0.2F;

        if (f2 < -f3)
        {
            f2 = -f3;
        }

        if (f2 > f3)
        {
            f2 = f3;
        }

        field_70374_e += (f2 - field_70374_e) * 0.9F;
        pageFlip += field_70374_e;
    }

    /**
     * Writes a tile entity to NBT.
     */
    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);

        if (func_94135_b())
        {
            par1NBTTagCompound.setString("CustomName", field_94136_s);
        }
    }
}
