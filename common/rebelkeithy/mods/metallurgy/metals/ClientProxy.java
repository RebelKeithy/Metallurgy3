package rebelkeithy.mods.metallurgy.metals;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

import java.io.File;

import rebelkeithy.mods.metallurgy.core.metalsets.MetalSet;
import rebelkeithy.mods.particleregistry.ParticleRegistry;

public class ClientProxy extends CommonProxy
{
	public File getMinecraftDir() 
	{
		return Minecraft.getMinecraftDir();
	}
}
