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
    private float flameScale;

    public EntityFantasyOreFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
    {
        super(par1World, par2, par4, par6, par8, par10, par12);
        this.motionX = this.motionX * 0.01D;
        this.motionY = this.motionY * 0.01D;
        this.motionZ = this.motionZ * 0.01D;

        this.flameScale = this.particleScale * 0.8f;
        float var12 = (float)Math.random() * 0.4F + 0.6F;
        //this.particleRed = (float) (((Math.random() * 0.2D) + 0.8F) * par8 * var12);
        //this.particleGreen = (float) (((Math.random() * 0.2D) + 0.8F) * par10 * var12);
        //this.particleBlue = (float) (((Math.random() * 0.2D) + 0.8F) * par12 * var12);
        this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
        this.noClip = true;
        this.setParticleTextureIndex(Math.random() > 0.5f ? 1 : 0);
    }

	public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5)
	{
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

	    Tessellator tessellator1 = new Tessellator();
	    tessellator1.startDrawingQuads();
	    tessellator1.setBrightness(getBrightnessForRender(f));

	    float f6 = (((float)particleAge + f) / (float)particleMaxAge) * 32F;
	    if(f6 < 0.0F)
	    {
	        f6 = 0.0F;
	    }
	    if(f6 > 1.0F)
	    {
	        f6 = 1.0F;
	    }

	    float var8 = ((float)this.particleAge + f) / (float)this.particleMaxAge;
	    this.particleScale = this.flameScale * (1.0F - var8 * var8 * 0.5F);

	    //TODO: Fix this
	    //GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, FMLClientHandler.instance().getClient().renderEngine.getTexture("/mods/Metallurgy/textures/particles/FantasyMetalsParticle.png"));

    	ResourceLocation texture = new ResourceLocation("Metallurgy:textures/particles/FantasyMetalsParticle.png");   
    	Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
	    
	    float f0 = 0;//(float)(getParticleTextureIndex() % 16) / 16F;
	    float f7 = f0 + 1/16F;
	    float f8 = 1;//(float)(getParticleTextureIndex() / 16) / 16F;
	    float f9 = f8 + 1/16F;
	    float f10 = 0.1F * particleScale;
	    float f11 = (float)((prevPosX + (posX - prevPosX) * (double)f) - interpPosX);
	    float f12 = (float)((prevPosY + (posY - prevPosY) * (double)f) - interpPosY);
	    float f13 = (float)((prevPosZ + (posZ - prevPosZ) * (double)f) - interpPosZ);
	    float f14 = 1.0F;
	    //tessellator1.setColorOpaque_F(particleRed * f14, particleGreen * f14, particleBlue * f14);
	    //tessellator1.setColorOpaque_F(this.particleRed, this.particleGreen, this.particleBlue);
	    tessellator1.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, 0.9f);
        tessellator1.addVertexWithUV(f11 - f1 * f10 - f4 * f10, f12 - f2 * f10, f13 - f3 * f10 - f5 * f10, f7, f9);
	    tessellator1.addVertexWithUV((f11 - f1 * f10) + f4 * f10, f12 + f2 * f10, (f13 - f3 * f10) + f5 * f10, f7, f8);
	    tessellator1.addVertexWithUV(f11 + f1 * f10 + f4 * f10, f12 + f2 * f10, f13 + f3 * f10 + f5 * f10, f0, f8);
	    tessellator1.addVertexWithUV((f11 + f1 * f10) - f4 * f10, f12 - f2 * f10, (f13 + f3 * f10) - f5 * f10, f0, f9);

	    tessellator1.draw();

	    //TODO: Fix this
    	ResourceLocation defaultParticles = new ResourceLocation("textures/particle/particles.png");   
    	Minecraft.getMinecraft().getTextureManager().bindTexture(defaultParticles);
	    //GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, ModLoader.getMinecraftInstance().renderEngine.getTexture("/particles.png"));
	}

    public int getBrightnessForRender(float par1)
    {
        float var2 = ((float)this.particleAge + par1) / (float)this.particleMaxAge;

        if (var2 < 0.0F)
        {
            var2 = 0.0F;
        }

        if (var2 > 1.0F)
        {
            var2 = 1.0F;
        }
        var2 = var2/2;

        int var3 = super.getBrightnessForRender(par1);
        int var4 = var3 & 255;
        int var5 = var3 >> 16 & 255;
        var4 += (int)(var2 * 15.0F * 16.0F);

        if (var4 > 240)
        {
            var4 = 240;
        }

        return var4 | var5 << 16;
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness(float par1)
    {
        float var2 = ((float)this.particleAge + par1) / (float)this.particleMaxAge;

        if (var2 < 0.0F)
        {
            var2 = 0.0F;
        }

        if (var2 > 1.0F)
        {
            var2 = 0.5F;
        }
        var2 = var2/2;

        float var3 = super.getBrightness(par1);
        return var3 * var2 + (1.0F - var2);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setDead();
        }

        if(particleAge == particleMaxAge/2)
        {
        	//TODO fix this
        	//if(getParticleTextureIndex() == 1)
        	//	setParticleTextureIndex(0);
        	//else
        		setParticleTextureIndex(1);
        }
        //this.setParticleTextureIndex(this.particleAge * 2 / this.particleMaxAge);
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        if (this.posY == this.prevPosY)
        {
            this.motionX *= 1.1D;
            this.motionZ *= 1.1D;
        }
        
        this.motionX *= 0.96D;
        this.motionY *= 0.96D;
        this.motionZ *= 0.96D;

        if (this.onGround)
        {
            this.motionX *= 0.7D;
            this.motionZ *= 0.7D;
        }
    }

}
