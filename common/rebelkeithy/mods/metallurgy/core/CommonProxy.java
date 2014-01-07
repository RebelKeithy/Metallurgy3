package rebelkeithy.mods.metallurgy.core;

import java.io.File;

import net.minecraft.world.World;
import rebelkeithy.mods.metallurgy.core.metalsets.MetalSet;

public class CommonProxy
{
    public World getClientWorld()
    {
        return null;
    }

    public File getMinecraftDir()
    {
        return new File(".");
    }

    public void registerNamesForMetalSet(MetalSet baseSet)
    {
    }

    public void spawnParticle(String string, World par1World, double x, double y, double z, double r, double g, double b)
    {
    }
}
