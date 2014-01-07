package rebelkeithy.mods.metallurgy.core;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import rebelkeithy.mods.keithyutils.particleregistry.ParticleRegistry;
import rebelkeithy.mods.metallurgy.core.metalsets.MetalSet;
import cpw.mods.fml.client.FMLClientHandler;

public class ClientProxy extends CommonProxy
{

    @Override
    public World getClientWorld()
    {
        return FMLClientHandler.instance().getClient().theWorld;
    }

    @Override
    public File getMinecraftDir()
    {
        return Minecraft.getMinecraft().mcDataDir;
    }

    @Override
    public void registerNamesForMetalSet(MetalSet set)
    {
        set.registerNames();
    }

    @Override
    public void spawnParticle(String string, World par1World, double x, double y, double z, double r, double g, double b)
    {
        ParticleRegistry.spawnParticle(string, par1World, x, y, z, r, g, b);
    }
}
