package rebelkeithy.mods.metallurgy.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.IGuiHandler;
import rebelkeithy.mods.metallurgy.machines.crusher.ContainerCrusher;
import rebelkeithy.mods.metallurgy.machines.crusher.GuiCrusher;
import rebelkeithy.mods.metallurgy.machines.crusher.TileEntityCrusher;
import rebelkeithy.mods.metallurgy.machines.forge.ContainerNetherForge;
import rebelkeithy.mods.metallurgy.machines.forge.GuiNetherForge;
import rebelkeithy.mods.metallurgy.machines.forge.TileEntityNetherForge;
import rebelkeithy.mods.metallurgy.machines.furnace.ContainerMetalFurnace;
import rebelkeithy.mods.metallurgy.machines.furnace.GuiMetalFurnace;
import rebelkeithy.mods.metallurgy.machines.furnace.TileEntityMetalFurnace;
import rebelkeithy.mods.metallurgy.machines.storage.ContainerStorage;
import rebelkeithy.mods.metallurgy.machines.storage.GuiStorage;
import rebelkeithy.mods.metallurgy.machines.storage.TileEntityStorageAccessor;

public class StorageGuiHandler implements IGuiHandler 
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		System.out.println("getting container");
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityNetherForge) 
		{
			TileEntityNetherForge tef = (TileEntityNetherForge) te;
			return new ContainerNetherForge(player.inventory, tef);
		}
		
		if (te instanceof TileEntityStorageAccessor && ID == 0) 
		{
			TileEntityStorageAccessor tea = (TileEntityStorageAccessor) te;
			return new ContainerStorage(player.inventory, tea.getInventory(ID));
		}
		else 
		{
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		System.out.println("getting gui element");
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityNetherForge) 
		{
			return new GuiNetherForge(player.inventory, (TileEntityNetherForge)te);
		}
		
		if (te instanceof TileEntityStorageAccessor && ID == 0) 
		{
			TileEntityStorageAccessor tea = (TileEntityStorageAccessor) te;
			return new GuiStorage(new ContainerStorage(player.inventory, tea.getInventory(ID)), tea, ID);
		}
		else
		{
			return null;
		}
		
		//return null;
	}
}
