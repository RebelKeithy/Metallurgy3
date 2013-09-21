package rebelkeithy.mods.metallurgy.metals;

import java.util.Random;

import net.minecraft.world.World;
import rebelkeithy.mods.keithyutils.metablock.IDisplayListener;

public class DisplayListenerVyroxeresOreParticles implements IDisplayListener
{
    @Override
    public void randomDisplayTick(World par1World, int x, int y, int z, Random rand)
    {
        final double var6 = 0.0625D;

        for (int var8 = 0; var8 < 6; ++var8)
        {
            if (Math.random() < 0.3)
            {
                continue;
            }

            double var9 = x + rand.nextDouble();
            double var11 = y + rand.nextDouble();
            double var13 = z + rand.nextDouble();

            if (var8 == 0 && !par1World.isBlockOpaqueCube(x, y + 1, z))
            {
                var11 = y + 1 + var6;
            }

            if (var8 == 1 && !par1World.isBlockOpaqueCube(x, y - 1, z))
            {
                var11 = y + 0 - var6;
            }

            if (var8 == 2 && !par1World.isBlockOpaqueCube(x, y, z + 1))
            {
                var13 = z + 1 + var6;
            }

            if (var8 == 3 && !par1World.isBlockOpaqueCube(x, y, z - 1))
            {
                var13 = z + 0 - var6;
            }

            if (var8 == 4 && !par1World.isBlockOpaqueCube(x + 1, y, z))
            {
                var9 = x + 1 + var6;
            }

            if (var8 == 5 && !par1World.isBlockOpaqueCube(x - 1, y, z))
            {
                var9 = x + 0 - var6;
            }

            if (var9 < x || var9 > x + 1 || var11 < 0.0D || var11 > y + 1 || var13 < z || var13 > z + 1)
            {
                // MetallurgyFantasy.proxy.spawnParticle(particle, par1World,
                // var9, var11, var13, r, g, b);
                // ParticleRegistry.spawnParticle(name, par1World, var9, var11,
                // var13, r, g, b);
                par1World.spawnParticle("mobSpell", var9, var11, var13, 0.0D, 0.7D, 0.0D);
            }
        }

    }

}
