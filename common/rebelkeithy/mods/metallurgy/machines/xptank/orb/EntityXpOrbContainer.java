package rebelkeithy.mods.metallurgy.machines.xptank.orb;

import java.util.Random;

import rebelkeithy.mods.metallurgy.core.MetallurgyCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityXpOrbContainer extends Entity
{
    /** This is how much XP this orb has. */
    private int xpValue;
    public int xpOrbAge;
    public final int maxXpOrbAge = 60;
	public float xpColor;
	
    public EntityXpOrbContainer(World par1World)
    {
    	super(par1World);
        this.setSize(0.25F, 0.25F);
        this.yOffset = this.height / 2.0F;
        this.xpValue = 1000;
    }
    
    public EntityXpOrbContainer(World par1World, double par2, double par4, double par6, int par8)
    {
        super(par1World);
        this.setSize(0.25F, 0.25F);
        this.yOffset = this.height / 2.0F;
        this.setPosition(par2, par4, par6);
        this.rotationYaw = (float)(Math.random() * 360.0D);
        this.motionX = 0;
        this.motionY = (double)((float)(Math.random() * 0.2D) * 2.0F);
        this.motionZ = 0;
        this.xpValue = par8;
    }

	@SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float par1)
    {
        float f1 = 0.5F;

        if (f1 < 0.0F)
        {
            f1 = 0.0F;
        }

        if (f1 > 1.0F)
        {
            f1 = 1.0F;
        }

        int i = super.getBrightnessForRender(par1);
        int j = i & 255;
        int k = i >> 16 & 255;
        j += (int)(f1 * 15.0F * 16.0F);

        if (j > 240)
        {
            j = 240;
        }

        return j | k << 16;
    }

	@Override
	protected void entityInit() {}


    public void onUpdate()
    {
        super.onUpdate();
        
        this.xpOrbAge++;
        
        if(xpOrbAge >= maxXpOrbAge)
        {
        	spawnXP(this.xpValue);
        	this.setDead();
        }
        
        this.posY += 0.05 * (maxXpOrbAge-xpOrbAge)/(float)maxXpOrbAge;
    }
    
    public void spawnXP(int totalXP)
    {
    	int xpPerOrb = 1;
        
        int orbCount = totalXP;
        
        if(totalXP > 20) {
            xpPerOrb = 2;
            orbCount = (totalXP/2) + 1;
        } 
        if(totalXP > 40) {
            xpPerOrb = 4;
            orbCount = (totalXP/4) + 1;
        }
        if(totalXP > 80) {
            xpPerOrb = 8;
            orbCount = (totalXP/8) + 1;
        }
            
        EntityXPOrb orb;
        for(int n = 0; n < orbCount; n++)
        {
        	double xOffset = 0.5;
        	double zOffset = 0.5;
        	double xMotion = 0;
        	double zMotion = 0;
        	
        	/*if(direction == 2) {
        		zOffset = 0;
        		zOffset = -0.1;
        	} else if(direction == 3) {
        		zOffset = 1;
        		zMotion = 0.1;
        	} else if(direction == 4) {
        		xOffset = 0;
        		xMotion = -0.1;
        	} else if(direction == 5) {
        		xOffset = 1;
        		xMotion = 0.1;
        	}*/
        	
        	Random rand = new Random();
        	xOffset += (rand.nextInt(21) - 10) / 100.0;
          	zOffset += (rand.nextInt(21) - 10) / 100.0;
        	xMotion += (rand.nextInt(11) - 5) / 100.0;
          	zMotion += (rand.nextInt(11) - 5) / 100.0;
          	double yMotion  = (rand.nextInt(11) - 5) / 200.0;
        	
          	MetallurgyCore.proxy.spawnParticle("abstractorSmall", worldObj, this.posX + xOffset, this.posY + 0.75, this.posZ + zOffset, xMotion*0.7f, yMotion, zMotion*0.7f);
            orb = new EntityXPOrb(this.worldObj, this.posX + xOffset, this.posY + 0.5f, this.posZ + zOffset, xpPerOrb);
            orb.motionX = xMotion;
            orb.motionZ = zMotion;
            if(!worldObj.isRemote)
            {
            	this.worldObj.spawnEntityInWorld(orb);
            	this.worldObj.updateEntity(orb);
            }
        }
    }
        
    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setShort("Age", (short)this.xpOrbAge);
        par1NBTTagCompound.setShort("Value", (short)this.xpValue);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        this.xpOrbAge = par1NBTTagCompound.getShort("Age");
        this.xpValue = par1NBTTagCompound.getShort("Value");
    }

    /**
     * Returns the XP value of this XP orb.
     */
    public int getXpValue()
    {
        return this.xpValue;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns a number from 1 to 10 based on how much XP this orb is worth. This is used by RenderXPOrb to determine
     * what texture to use.
     */
    public int getTextureByXP()
    {
        return this.xpValue >= 2477 ? 10 : (this.xpValue >= 1237 ? 9 : (this.xpValue >= 617 ? 8 : (this.xpValue >= 307 ? 7 : (this.xpValue >= 149 ? 6 : (this.xpValue >= 73 ? 5 : (this.xpValue >= 37 ? 4 : (this.xpValue >= 17 ? 3 : (this.xpValue >= 7 ? 2 : (this.xpValue >= 3 ? 1 : 0)))))))));
    }
}
