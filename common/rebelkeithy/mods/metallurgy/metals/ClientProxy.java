package rebelkeithy.mods.metallurgy.metals;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

import java.io.File;

import rebelkeithy.mods.keithyutils.particleregistry.ParticleRegistry;
import rebelkeithy.mods.metallurgy.core.metalsets.MetalSet;

public class ClientProxy extends CommonProxy
{
	public File getMinecraftDir() 
	{
		return Minecraft.getMinecraft().mcDataDir;
	}
	
	public void registerParticles()
	{
		ParticleRegistry.registerParticle("NetherOre", EntityNetherOreFX.class);
		ParticleRegistry.registerParticle("FantasyOre", EntityFantasyOreFX.class);
	}
}
