package rebelkeithy.mods.metallurgy.core;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.world.World;

import java.io.File;

import rebelkeithy.mods.keithyutils.particleregistry.ParticleRegistry;
import rebelkeithy.mods.metallurgy.core.metalsets.MetalSet;
import rebelkeithy.mods.metallurgy.machines.storage.ContainerStorage;
import rebelkeithy.mods.metallurgy.machines.storage.GuiStorage;

public class CommonProxy
{
	public File getMinecraftDir() 
	{
		return new File(".");
	}

	public void registerNamesForMetalSet(MetalSet baseSet) {}

	
	public void spawnParticle(String string, World par1World, double x, double y, double z, double r, double g, double b) {}

	public World getClientWorld()
	{
		return null;
	}
}
