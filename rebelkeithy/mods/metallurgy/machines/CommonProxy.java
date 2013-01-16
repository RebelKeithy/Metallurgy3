package rebelkeithy.mods.metallurgy.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.IGuiHandler;

import java.io.File;

import rebelkeithy.mods.metallurgy.machines.storage.ContainerStorage;
import rebelkeithy.mods.metallurgy.machines.storage.GuiStorage;

public class CommonProxy implements IGuiHandler
{

	public File getMinecraftDir() 
	{
		return new File(".");
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		System.out.println("getting container");
		if (ID == 0) 
		{
			return new ContainerStorage();
		}
		else 
		{
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		System.out.println("getting gui element");
		if (ID == 0) 
		{
			return new GuiStorage(new ContainerStorage());
		}
		else
		{
			return null;
		}
		
		//return null;
	}
}
