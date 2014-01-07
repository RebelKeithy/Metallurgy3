package rebelkeithy.mods.metallurgy.machines;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import rebelkeithy.mods.metallurgy.core.MetallurgyCore;
import rebelkeithy.mods.metallurgy.machines.abstractor.TileEntityAbstractor;
import rebelkeithy.mods.metallurgy.machines.crusher.TileEntityCrusher;
import rebelkeithy.mods.metallurgy.machines.forge.TileEntityNetherForge;
import rebelkeithy.mods.metallurgy.machines.furnace.TileEntityMetalFurnace;
import rebelkeithy.mods.metallurgy.machines.storage.InventoryStorage;
import rebelkeithy.mods.metallurgy.machines.storage.TileEntityStorageAccessor;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler
{
    public static Packet getAddTabPacket(int itemID, int amount, int x, int y, int z)
    {
        final ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        final DataOutputStream out = new DataOutputStream(byteOut);

        try
        {
            out.writeShort(2);
            out.writeInt(itemID);
            out.writeInt(amount);
            out.writeInt(x);
            out.writeInt(y);
            out.writeInt(z);
        } catch (final IOException e)
        {
            e.printStackTrace();
        }

        final Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = "M3Machines";
        packet.data = byteOut.toByteArray();
        packet.length = byteOut.size();
        packet.isChunkDataPacket = false;
        return packet;
    }

    public static Packet getChangeTabPacket(int x, int y, int z, int itemID)
    {
        final ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        final DataOutputStream out = new DataOutputStream(byteOut);

        try
        {
            out.writeShort(0);
            out.writeInt(itemID);
            out.writeInt(x);
            out.writeInt(y);
            out.writeInt(z);
        } catch (final IOException e)
        {
            e.printStackTrace();
        }

        final Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = "M3Machines";
        packet.data = byteOut.toByteArray();
        packet.length = byteOut.size();
        packet.isChunkDataPacket = false;
        return packet;
    }

    public static Packet getScrollPacket(int itemID, int currentScroll, int x, int y, int z)
    {
        final ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        final DataOutputStream out = new DataOutputStream(byteOut);

        try
        {
            out.writeShort(1);
            out.writeInt(itemID);
            out.writeInt(currentScroll);
            out.writeInt(x);
            out.writeInt(y);
            out.writeInt(z);
        } catch (final IOException e)
        {
            e.printStackTrace();
        }

        final Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = "M3Machines";
        packet.data = byteOut.toByteArray();
        packet.length = byteOut.size();
        packet.isChunkDataPacket = false;
        return packet;
    }

    public static Packet getSetInvSizePacket(int itemID, int size, int x, int y, int z)
    {
        final ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        final DataOutputStream out = new DataOutputStream(byteOut);

        try
        {
            out.writeShort(3);
            out.writeInt(itemID);
            out.writeInt(size);
            out.writeInt(x);
            out.writeInt(y);
            out.writeInt(z);
        } catch (final IOException e)
        {
            e.printStackTrace();
        }

        final Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = "M3Machines";
        packet.data = byteOut.toByteArray();
        packet.length = byteOut.size();
        packet.isChunkDataPacket = false;
        return packet;
    }

    public static Packet getTabListPacket(World world, int x, int y, int z)
    {
        final ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        final DataOutputStream out = new DataOutputStream(byteOut);

        try
        {
            out.writeShort(4);
            final TileEntityStorageAccessor tesa = (TileEntityStorageAccessor) world.getBlockTileEntity(x, y, z);
            out.writeInt(tesa.inventories.size());
            for (final Integer id : tesa.inventories.keySet())
            {
                out.writeInt(id);
            }
            out.writeInt(x);
            out.writeInt(y);
            out.writeInt(z);
        } catch (final IOException e)
        {
            e.printStackTrace();
        }

        final Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = "M3Machines";
        packet.data = byteOut.toByteArray();
        packet.length = byteOut.size();
        packet.isChunkDataPacket = false;
        return packet;
    }

    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
    {
        final ByteArrayDataInput byteIn = ByteStreams.newDataInput(packet.data);
        final short packetID = byteIn.readShort();
        if (packetID == 0)
        {
            final int itemID = byteIn.readInt();
            final int x = byteIn.readInt();
            final int y = byteIn.readInt();
            final int z = byteIn.readInt();

            final EntityPlayer entityPlayer = (EntityPlayer) player;
            final TileEntityStorageAccessor tes = (TileEntityStorageAccessor) entityPlayer.worldObj.getBlockTileEntity(x, y, z);
            PacketDispatcher.sendPacketToPlayer(PacketHandler.getSetInvSizePacket(itemID, tes.inventories.get(itemID).items.length, x, y, z), player);
            entityPlayer.openGui(MetallurgyMachines.instance, itemID, entityPlayer.worldObj, x, y, z);
        }
        else if (packetID == 1)
        {
            final int itemID = byteIn.readInt();
            final int scroll = byteIn.readInt();
            final int x = byteIn.readInt();
            final int y = byteIn.readInt();
            final int z = byteIn.readInt();

            final EntityPlayer entityPlayer = (EntityPlayer) player;
            final TileEntityStorageAccessor tes = (TileEntityStorageAccessor) entityPlayer.worldObj.getBlockTileEntity(x, y, z);
            final InventoryStorage is = tes.inventories.get(itemID);
            if (is != null)
            {
                is.scroll = scroll;
            }
        }
        else if (packetID == 2)
        {
            final int itemID = byteIn.readInt();
            final int amount = byteIn.readInt();
            final int x = byteIn.readInt();
            final int y = byteIn.readInt();
            final int z = byteIn.readInt();

            final EntityPlayer entityPlayer = (EntityPlayer) player;
            final TileEntityStorageAccessor tes = (TileEntityStorageAccessor) entityPlayer.worldObj.getBlockTileEntity(x, y, z);
            if (tes != null)
            {
                tes.addTab(new ItemStack(itemID, 1, 0), amount);
            }
        }
        else if (packetID == 3)
        {
            final int itemID = byteIn.readInt();
            final int size = byteIn.readInt();
            final int x = byteIn.readInt();
            final int y = byteIn.readInt();
            final int z = byteIn.readInt();

            final EntityPlayer entityPlayer = (EntityPlayer) player;
            final TileEntityStorageAccessor tes = (TileEntityStorageAccessor) entityPlayer.worldObj.getBlockTileEntity(x, y, z);
            if (tes != null)
            {
                tes.setTabSize(new ItemStack(itemID, 1, 0), size);
            }
        }
        else if (packetID == 4)
        {
            final int numTabs = byteIn.readInt();
            final List<Integer> tabs = new ArrayList<Integer>();
            for (int n = 0; n < numTabs; n++)
            {
                tabs.add(byteIn.readInt());
            }
            final int x = byteIn.readInt();
            final int y = byteIn.readInt();
            final int z = byteIn.readInt();

            final EntityPlayer entityPlayer = (EntityPlayer) player;
            final TileEntityStorageAccessor tes = (TileEntityStorageAccessor) entityPlayer.worldObj.getBlockTileEntity(x, y, z);
            if (tes != null)
            {
                for (final Integer id : tabs)
                {
                    tes.addTab(new ItemStack(id, 1, 0), 0);
                }
            }
        }
        else if (packetID == 50) // Nether Forge
        {
            final int x = byteIn.readInt();
            final int y = byteIn.readInt();
            final int z = byteIn.readInt();
            final int direction = byteIn.readInt();
            final int speed = byteIn.readInt();
            final int burnTime = byteIn.readInt();
            final int cookTime = byteIn.readInt();
            final int fuel = byteIn.readInt();
            final int maxFuel = byteIn.readInt();

            final World world = MetallurgyCore.proxy.getClientWorld();
            final TileEntity te = world.getBlockTileEntity(x, y, z);
            TileEntityNetherForge icte = null;
            if (te instanceof TileEntityNetherForge)
            {
                icte = (TileEntityNetherForge) te;
                icte.setDirection(direction);
                icte.furnaceTimeBase = speed;
                icte.furnaceBurnTime = burnTime;
                icte.furnaceCookTime = cookTime;
                icte.fuel = fuel;
                icte.maxFuel = maxFuel;
            }
        }
        else if (packetID == 51)
        {
            final int x = byteIn.readInt();
            final int y = byteIn.readInt();
            final int z = byteIn.readInt();
            final int direction = byteIn.readInt();
            final int speed = byteIn.readInt();
            final int burnTime = byteIn.readInt();

            final World world = MetallurgyCore.proxy.getClientWorld();
            final TileEntity te = world.getBlockTileEntity(x, y, z);
            TileEntityAbstractor icte = null;
            if (te instanceof TileEntityAbstractor)
            {
                icte = (TileEntityAbstractor) te;
                icte.setDirection(direction);
                icte.furnaceTimeBase = speed;
                icte.furnaceBurnTime = burnTime;
            }

            world.markBlockForUpdate(x, y, z);
        }
        else if (packetID == 52)
        {
            final int x = byteIn.readInt();
            final int y = byteIn.readInt();
            final int z = byteIn.readInt();
            final int direction = byteIn.readInt();
            final int speed = byteIn.readInt();
            final int burnTime = byteIn.readInt();
            final int cookTime = byteIn.readInt();

            final World world = MetallurgyCore.proxy.getClientWorld();
            final TileEntity te = world.getBlockTileEntity(x, y, z);
            TileEntityCrusher icte = null;
            if (te instanceof TileEntityCrusher)
            {
                icte = (TileEntityCrusher) te;
                icte.setDirection(direction);
                icte.furnaceTimeBase = speed;
                icte.furnaceBurnTime = burnTime;
                icte.furnaceCookTime = cookTime;
            }

            world.markBlockForUpdate(x, y, z);
        }
        else if (packetID == 53) // Metal Furnace
        {
            final int x = byteIn.readInt();
            final int y = byteIn.readInt();
            final int z = byteIn.readInt();
            final int direction = byteIn.readInt();
            final int speed = byteIn.readInt();
            final int burnTime = byteIn.readInt();
            final int cookTime = byteIn.readInt();

            final World world = MetallurgyCore.proxy.getClientWorld();
            final TileEntity te = world.getBlockTileEntity(x, y, z);
            TileEntityMetalFurnace icte = null;
            if (te instanceof TileEntityMetalFurnace)
            {
                icte = (TileEntityMetalFurnace) te;
                icte.setDirection(direction);
                icte.furnaceTimeBase = speed;
                icte.furnaceBurnTime = burnTime;
                icte.furnaceCookTime = cookTime;
            }

            world.markBlockForUpdate(x, y, z);
        }
    }
}
