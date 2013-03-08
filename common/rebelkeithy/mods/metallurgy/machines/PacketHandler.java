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
		else if(packetID == 50) // Nether Forge
		{
			int x = byteIn.readInt();
			int y = byteIn.readInt();
			int z = byteIn.readInt();
			int direction = byteIn.readInt();
			int speed = byteIn.readInt();
			int burnTime = byteIn.readInt();
			int cookTime = byteIn.readInt();
			int fuel = byteIn.readInt();
			int maxFuel = byteIn.readInt();

			World world = MetallurgyCore.proxy.getClientWorld();
			TileEntity te = world.getBlockTileEntity(x, y, z);
			TileEntityNetherForge icte = null;
			if (te instanceof TileEntityNetherForge) {
				icte = (TileEntityNetherForge) te;
				icte.setDirection(direction);
				icte.furnaceTimeBase = speed;
				icte.furnaceBurnTime = burnTime;
				icte.furnaceCookTime = cookTime;
				icte.fuel = fuel;
				icte.maxFuel= maxFuel;
			}
		}
		else if(packetID == 51)
		{
			int x = byteIn.readInt();
			int y = byteIn.readInt();
			int z = byteIn.readInt();
			int direction = byteIn.readInt();
			int speed = byteIn.readInt();
			int burnTime = byteIn.readInt();

			World world = MetallurgyCore.proxy.getClientWorld();
			TileEntity te = world.getBlockTileEntity(x, y, z);
			TileEntityAbstractor icte = null;
			if (te instanceof TileEntityAbstractor) {
				icte = (TileEntityAbstractor) te;
				icte.setDirection(direction);
				icte.furnaceTimeBase = speed;
				icte.furnaceBurnTime = burnTime;
			}

			world.markBlockForUpdate(x, y, z);
		}
		else if(packetID == 52)
		{
			int x = byteIn.readInt();
			int y = byteIn.readInt();
			int z = byteIn.readInt();
			int direction = byteIn.readInt();
			int speed = byteIn.readInt();
			int burnTime = byteIn.readInt();
			int cookTime = byteIn.readInt();

			World world = MetallurgyCore.proxy.getClientWorld();
			TileEntity te = world.getBlockTileEntity(x, y, z);
			TileEntityCrusher icte = null;
			if (te instanceof TileEntityCrusher) {
				icte = (TileEntityCrusher) te;
				icte.setDirection(direction);
				icte.furnaceTimeBase = speed;
				icte.furnaceBurnTime = burnTime;
				icte.furnaceCookTime = cookTime;
			}

			world.markBlockForUpdate(x, y, z);
		}
		else if(packetID == 53)	//Metal Furnace
		{
			int x = byteIn.readInt();
			int y = byteIn.readInt();
			int z = byteIn.readInt();
			int direction = byteIn.readInt();
			int speed = byteIn.readInt();
			int burnTime = byteIn.readInt();
			int cookTime = byteIn.readInt();

			World world = MetallurgyCore.proxy.getClientWorld();
			TileEntity te = world.getBlockTileEntity(x, y, z);
			TileEntityMetalFurnace icte = null;
			if (te instanceof TileEntityMetalFurnace) {
				icte = (TileEntityMetalFurnace) te;
				icte.setDirection(direction);
				icte.furnaceTimeBase = speed;
				icte.furnaceBurnTime = burnTime;
				icte.furnaceCookTime = cookTime;
			}

			world.markBlockForUpdate(x, y, z);
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
