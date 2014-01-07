package rebelkeithy.mods.metallurgy.metals;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public class EntityFantasyOreFX extends EntityFX
{

    /** the scale of the flame FX */
    private final float flameScale;

    public EntityFantasyOreFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
    {
        super(par1World, par2, par4, par6, par8, par10, par12);
        motionX = motionX * 0.01D;
        motionY = motionY * 0.01D;
        motionZ = motionZ * 0.01D;

        flameScale = particleScale * 0.8f;
        // this.particleRed = (float) (((Math.random() * 0.2D) + 0.8F) * par8 *
        // var12);
        // this.particleGreen = (float) (((Math.random() * 0.2D) + 0.8F) * par10
        // * var12);
        // this.particleBlue = (float) (((Math.random() * 0.2D) + 0.8F) * par12
        // * var12);
        particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D));
        noClip = true;
        setParticleTextureIndex(Math.random() > 0.5f ? 1 : 0);
    }

    /**
     * Gets how bright this entity is.
     */
    @Override
    public float getBrightness(float par1)
    {
        float var2 = (particleAge + par1) / particleMaxAge;

        if (var2 < 0.0F)
        {
            var2 = 0.0F;
        }

        if (var2 > 1.0F)
        {
            var2 = 0.5F;
        }
        var2 = var2 / 2;

        final float var3 = super.getBrightness(par1);
        return var3 * var2 + (1.0F - var2);
    }

    @Override
    public int getBrightnessForRender(float par1)
    {
        float var2 = (particleAge + par1) / particleMaxAge;

        if (var2 < 0.0F)
        {
            var2 = 0.0F;
        }

        if (var2 > 1.0F)
        {
            var2 = 1.0F;
        }
        var2 = var2 / 2;

        final int var3 = super.getBrightnessForRender(par1);
        int var4 = var3 & 255;
        final int var5 = var3 >> 16 & 255;
        var4 += (int) (var2 * 15.0F * 16.0F);

        if (var4 > 240)
        {
            var4 = 240;
        }

        return var4 | var5 << 16;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate()
    {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;

        if (particleAge++ >= particleMaxAge)
        {
            setDead();
        }

        if (particleAge == particleMaxAge / 2)
        {
            // TODO fix this
            // if(getParticleTextureIndex() == 1)
            // setParticleTextureIndex(0);
            // else
            setParticleTextureIndex(1);
        }
        // this.setParticleTextureIndex(this.particleAge * 2 /
        // this.particleMaxAge);
        moveEntity(motionX, motionY, motionZ);
        if (posY == prevPosY)
        {
            motionX *= 1.1D;
            motionZ *= 1.1D;
        }

        motionX *= 0.96D;
        motionY *= 0.96D;
        motionZ *= 0.96D;

        if (onGround)
        {
            motionX *= 0.7D;
            motionZ *= 0.7D;
        }
    }

    @Override
    public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        final Tessellator tessellator1 = new Tessellator();
        tessellator1.startDrawingQuads();
        tessellator1.setBrightness(getBrightnessForRender(f));

        float f6 = (particleAge + f) / particleMaxAge * 32F;
        if (f6 < 0.0F)
        {
            f6 = 0.0F;
        }
        if (f6 > 1.0F)
        {
            f6 = 1.0F;
        }

        final float var8 = (particleAge + f) / particleMaxAge;
        particleScale = flameScale * (1.0F - var8 * var8 * 0.5F);

        // TODO: Fix this
        // GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/,
        // FMLClientHandler.instance().getClient().renderEngine.getTexture("/mods/Metallurgy/textures/particles/FantasyMetalsParticle.png"));

        final ResourceLocation texture = new ResourceLocation("Metallurgy:textures/particles/FantasyMetalsParticle.png");
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

        final float f0 = 0;// (float)(getParticleTextureIndex() % 16) / 16F;
        final float f7 = f0 + 1 / 16F;
        final float f8 = 1;// (float)(getParticleTextureIndex() / 16) / 16F;
        final float f9 = f8 + 1 / 16F;
        final float f10 = 0.1F * particleScale;
        final float f11 = (float) (prevPosX + (posX - prevPosX) * f - interpPosX);
        final float f12 = (float) (prevPosY + (posY - prevPosY) * f - interpPosY);
        final float f13 = (float) (prevPosZ + (posZ - prevPosZ) * f - interpPosZ);
        // tessellator1.setColorOpaque_F(particleRed * f14, particleGreen * f14,
        // particleBlue * f14);
        // tessellator1.setColorOpaque_F(this.particleRed, this.particleGreen,
        // this.particleBlue);
        tessellator1.setColorRGBA_F(particleRed, particleGreen, particleBlue, 0.9f);
        tessellator1.addVertexWithUV(f11 - f1 * f10 - f4 * f10, f12 - f2 * f10, f13 - f3 * f10 - f5 * f10, f7, f9);
        tessellator1.addVertexWithUV(f11 - f1 * f10 + f4 * f10, f12 + f2 * f10, f13 - f3 * f10 + f5 * f10, f7, f8);
        tessellator1.addVertexWithUV(f11 + f1 * f10 + f4 * f10, f12 + f2 * f10, f13 + f3 * f10 + f5 * f10, f0, f8);
        tessellator1.addVertexWithUV(f11 + f1 * f10 - f4 * f10, f12 - f2 * f10, f13 + f3 * f10 - f5 * f10, f0, f9);

        tessellator1.draw();

        // TODO: Fix this
        final ResourceLocation defaultParticles = new ResourceLocation("textures/particle/particles.png");
        Minecraft.getMinecraft().getTextureManager().bindTexture(defaultParticles);
        // GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/,
        // ModLoader.getMinecraftInstance().renderEngine.getTexture("/particles.png"));
    }

}
