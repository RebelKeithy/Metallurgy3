package rebelkeithy.mods.metallurgy.machines.lantern;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TileEntityLantern extends TileEntity
{
    public short color;
    public boolean canUpdate = true;

    public TileEntityLantern()
    {

    }

    public TileEntityLantern(int color)
    {
        this.color = (short) color;
        canUpdate = true;
    }

    @Override
    public boolean canUpdate()
    {
        return canUpdate;
    }

    /**
     * Reads a tile entity from NBT.
     */
    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        color = par1NBTTagCompound.getShort("color");

        if (worldObj != null)
        {
            final int id = worldObj.getBlockId(xCoord, yCoord, zCoord);
            worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 1, color);
        }
        // sendPacket();
    }

    @Override
    public boolean receiveClientEvent(int i, int j)
    {
        color = (short) j;
        return true;
    }

    public void sendPacket()
    {
        if (worldObj != null && worldObj.isRemote)
        {
            return;
        }

        final ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
        final DataOutputStream dos = new DataOutputStream(bos);
        try
        {
            dos.writeShort(2);
            dos.writeInt(xCoord);
            dos.writeInt(yCoord);
            dos.writeInt(zCoord);
            dos.writeShort(color);
        } catch (final IOException e)
        {
            // UNPOSSIBLE?
        }
        final Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = "MetallurgyBase";
        packet.data = bos.toByteArray();
        packet.length = bos.size();
        packet.isChunkDataPacket = true;

        if (packet != null)
        {
            PacketDispatcher.sendPacketToAllPlayers(packet);
            // PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord,
            // 16, worldObj.provider.dimensionId, packet);
        }
    }

    @Override
    public void updateEntity()
    {
        // if(worldObj != null && canUpdate)
        {
            final int id = worldObj.getBlockId(xCoord, yCoord, zCoord);
            worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 1, color);
            canUpdate = false;
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("color", color);
    }
}
