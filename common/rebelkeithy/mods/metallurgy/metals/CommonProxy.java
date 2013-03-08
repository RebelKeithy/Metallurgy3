package rebelkeithy.mods.metallurgy.metals;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.world.World;

import java.io.File;

import rebelkeithy.mods.metallurgy.core.metalsets.MetalSet;
import rebelkeithy.mods.metallurgy.machines.storage.ContainerStorage;
import rebelkeithy.mods.metallurgy.machines.storage.GuiStorage;

public class CommonProxy
{
	
	public File getMinecraftDir() 
	{
		return new File(".");
	}
}
