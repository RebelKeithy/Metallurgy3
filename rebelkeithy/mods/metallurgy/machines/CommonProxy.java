package rebelkeithy.mods.metallurgy.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.IGuiHandler;

import java.io.File;

import rebelkeithy.mods.metallurgy.machines.storage.ContainerStorage;
import rebelkeithy.mods.metallurgy.machines.storage.GuiStorage;

public class CommonProxy
{

	public File getMinecraftDir() 
	{
		return new File(".");
	}

	public void registerTileEntitySpecialRenderer() {}
}
