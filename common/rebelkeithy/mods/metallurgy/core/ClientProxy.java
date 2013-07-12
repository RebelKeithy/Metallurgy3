package rebelkeithy.mods.metallurgy.core;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

import java.io.File;

import cpw.mods.fml.client.FMLClientHandler;

import rebelkeithy.mods.keithyutils.particleregistry.ParticleRegistry;
import rebelkeithy.mods.metallurgy.core.metalsets.MetalSet;

public class ClientProxy extends CommonProxy
{
	
	public void registerNamesForMetalSet(MetalSet set)
	{
		set.registerNames();
	}

	public File getMinecraftDir() 
	{
		return Minecraft.getMinecraft().mcDataDir;
	}
	
	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}
	
	public void spawnParticle(String string, World par1World, double x, double y, double z, double r, double g, double b)
	{
		ParticleRegistry.spawnParticle(string, par1World, x, y, z, r, g, b);
	}
}
