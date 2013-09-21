package rebelkeithy.mods.metallurgy.metals;

import java.io.File;

import net.minecraft.client.Minecraft;
import rebelkeithy.mods.keithyutils.particleregistry.ParticleRegistry;

public class ClientProxy extends CommonProxy
{
    @Override
    public File getMinecraftDir()
    {
        return Minecraft.getMinecraft().mcDataDir;
    }

    @Override
    public void registerParticles()
    {
        ParticleRegistry.registerParticle("NetherOre", EntityNetherOreFX.class);
        ParticleRegistry.registerParticle("FantasyOre", EntityFantasyOreFX.class);
    }
}
