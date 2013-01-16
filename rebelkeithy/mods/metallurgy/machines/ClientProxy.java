package rebelkeithy.mods.metallurgy.machines;

import net.minecraft.client.Minecraft;

import java.io.File;

public class ClientProxy extends CommonProxy
{
	public File getMinecraftDir() 
	{
		return Minecraft.getMinecraftDir();
	}
}
