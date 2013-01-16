package rebelkeithy.mods.metallurgy.core;

import net.minecraft.client.Minecraft;

import java.io.File;

import rebelkeithy.mods.metallurgy.core.metalsets.MetalSet;

public class ClientProxy extends CommonProxy
{
	
	public void registerNamesForMetalSet(MetalSet set)
	{
		set.registerNames();
	}

	public File getMinecraftDir() 
	{
		return Minecraft.getMinecraftDir();
	}
}
