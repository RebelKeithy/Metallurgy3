package rebelkeithy.mods.metallurgy.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import rebelkeithy.mods.metallurgy.machines.storage.ContainerStorage;
import rebelkeithy.mods.metallurgy.machines.storage.GuiStorage;
import rebelkeithy.mods.metallurgy.machines.storage.TileEntityStorageAccessor;
import cpw.mods.fml.common.network.IGuiHandler;

public class StorageGuiHandler implements IGuiHandler 
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		System.out.println("getting container");
		if (ID == 0 || true) 
		{
			TileEntityStorageAccessor te = (TileEntityStorageAccessor) world.getBlockTileEntity(x, y, z);
			return new ContainerStorage(player.inventory, te.getInventory(ID));
		}
		else 
		{
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		System.out.println("getting gui element");
		if (ID == 0 || true) 
		{
			TileEntityStorageAccessor te = (TileEntityStorageAccessor) world.getBlockTileEntity(x, y, z);
			return new GuiStorage(new ContainerStorage(player.inventory, te.getInventory(ID)), te, ID);
		}
		else
		{
			return null;
		}
		
		//return null;
	}
}
