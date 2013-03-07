package rebelkeithy.mods.metallurgy.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rebelkeithy.mods.metallurgy.machines.storage.InventoryStorage;
import rebelkeithy.mods.metallurgy.machines.storage.TileEntityStorageAccessor;

public class PacketHandler implements IPacketHandler
{
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
		ByteArrayDataInput byteIn = ByteStreams.newDataInput(packet.data);
		short packetID = byteIn.readShort();
		if(packetID == 0)
		{
			int itemID = byteIn.readInt();
			int x = byteIn.readInt();
			int y = byteIn.readInt();
			int z = byteIn.readInt();
			
			EntityPlayer entityPlayer = (EntityPlayer)player;
			TileEntityStorageAccessor tes = (TileEntityStorageAccessor) entityPlayer.worldObj.getBlockTileEntity(x, y, z);
	    	PacketDispatcher.sendPacketToPlayer(PacketHandler.getSetInvSizePacket(itemID, tes.inventories.get(itemID).items.length, x, y, z), player);
			entityPlayer.openGui(MetallurgyMachines.instance, itemID, entityPlayer.worldObj, x, y, z);
		}
		else if(packetID == 1)
		{
			int itemID = byteIn.readInt();
			int scroll = byteIn.readInt();
			int x = byteIn.readInt();
			int y = byteIn.readInt();
			int z = byteIn.readInt();

			EntityPlayer entityPlayer = (EntityPlayer)player;
			TileEntityStorageAccessor tes = (TileEntityStorageAccessor) entityPlayer.worldObj.getBlockTileEntity(x, y, z);
			InventoryStorage is = tes.inventories.get(itemID);
			if(is != null)
			{
				is.scroll = scroll;
			}
		}
		else if(packetID == 2)
		{
			int itemID = byteIn.readInt();
			int amount = byteIn.readInt();
			int x = byteIn.readInt();
			int y = byteIn.readInt();
			int z = byteIn.readInt();

			EntityPlayer entityPlayer = (EntityPlayer)player;
			TileEntityStorageAccessor tes = (TileEntityStorageAccessor) entityPlayer.worldObj.getBlockTileEntity(x, y, z);
			if(tes != null)
			{
				tes.addTab(new ItemStack(itemID, 1, 0), amount);
			}
		}
		else if(packetID == 3)
		{
			int itemID = byteIn.readInt();
			int size = byteIn.readInt();
			int x = byteIn.readInt();
			int y = byteIn.readInt();
			int z = byteIn.readInt();

			EntityPlayer entityPlayer = (EntityPlayer)player;
			TileEntityStorageAccessor tes = (TileEntityStorageAccessor) entityPlayer.worldObj.getBlockTileEntity(x, y, z);
			if(tes != null)
			{
				tes.setTabSize(new ItemStack(itemID, 1, 0), size);
			}
		}
		else if(packetID == 4)
		{
			int numTabs = byteIn.readInt();
			List<Integer> tabs = new ArrayList<Integer>();
			for(int n = 0; n < numTabs; n++)
				tabs.add(byteIn.readInt());
			int x = byteIn.readInt();
			int y = byteIn.readInt();
			int z = byteIn.readInt();

			EntityPlayer entityPlayer = (EntityPlayer)player;
			TileEntityStorageAccessor tes = (TileEntityStorageAccessor) entityPlayer.worldObj.getBlockTileEntity(x, y, z);
			if(tes != null)
			{
				for(Integer id : tabs)
					tes.addTab(new ItemStack(id, 1, 0), 0);
			}
		}
	}
	
	public static Packet getChangeTabPacket(int x, int y, int z, int itemID)
	{
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(byteOut);
		
		try
		{
			out.writeShort(0);
			out.writeInt(itemID);
			out.writeInt(x);
			out.writeInt(y);
			out.writeInt(z);
		} 
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "M3Machines";
		packet.data = byteOut.toByteArray();
		packet.length = byteOut.size();
		packet.isChunkDataPacket = false;
		return packet;
	}

	public static Packet getScrollPacket(int itemID, int currentScroll, int x, int y, int z) {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(byteOut);
		
		try
		{
			out.writeShort(1);
			out.writeInt(itemID);
			out.writeInt(currentScroll);
			out.writeInt(x);
			out.writeInt(y);
			out.writeInt(z);
		} 
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "M3Machines";
		packet.data = byteOut.toByteArray();
		packet.length = byteOut.size();
		packet.isChunkDataPacket = false;
		return packet;
	}

	public static Packet getAddTabPacket(int itemID, int amount, int x, int y, int z) {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(byteOut);
		
		try
		{
			out.writeShort(2);
			out.writeInt(itemID);
			out.writeInt(amount);
			out.writeInt(x);
			out.writeInt(y);
			out.writeInt(z);
		} 
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "M3Machines";
		packet.data = byteOut.toByteArray();
		packet.length = byteOut.size();
		packet.isChunkDataPacket = false;
		return packet;
	}

	public static Packet getSetInvSizePacket(int itemID, int size, int x, int y, int z) {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(byteOut);
		
		try
		{
			out.writeShort(3);
			out.writeInt(itemID);
			out.writeInt(size);
			out.writeInt(x);
			out.writeInt(y);
			out.writeInt(z);
		} 
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "M3Machines";
		packet.data = byteOut.toByteArray();
		packet.length = byteOut.size();
		packet.isChunkDataPacket = false;
		return packet;
	}

	public static Packet getTabListPacket(World world, int x, int y, int z) {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(byteOut);
		
		try
		{
			out.writeShort(4);
			TileEntityStorageAccessor tesa = (TileEntityStorageAccessor) world.getBlockTileEntity(x, y, z);
			out.writeInt(tesa.inventories.size());
			for(Integer id : tesa.inventories.keySet())
			{
				out.writeInt(id);
			}
			out.writeInt(x);
			out.writeInt(y);
			out.writeInt(z);
		} 
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "M3Machines";
		packet.data = byteOut.toByteArray();
		packet.length = byteOut.size();
		packet.isChunkDataPacket = false;
		return packet;
	}
}
