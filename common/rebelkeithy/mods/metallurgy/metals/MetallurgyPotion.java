package rebelkeithy.mods.metallurgy.metals;

import net.minecraft.entity.EntityLiving;
import net.minecraft.potion.Potion;

public class MetallurgyPotion extends Potion
{

    public MetallurgyPotion(int par1, boolean par2, int par3) 
    {
		super(par1, par2, par3);
	}

    @Override
	public void performEffect(EntityLiving par1EntityLiving, int par2)
    {
		//System.out.println("Metallurgy Potion");
		double vx = par1EntityLiving.motionX;
		double vy = par1EntityLiving.motionY;
		double vz = par1EntityLiving.motionZ;
		double gravity = 0.7;
		if(vy < 0)
			vy += 0.02/gravity;
		if(vy > 0)
		{
			//vy += 0.05;
			vy *= 1.260504;
		}
    	par1EntityLiving.setVelocity(vx, vy, vz);
    }
    
    @Override
    public void affectEntity(EntityLiving par1EntityLiving, EntityLiving par2EntityLiving, int par3, double par4)
    {
    	System.out.println("Metallurg wrong");
    }
    
    public boolean isReady(int par1, int par2)
    {
    	System.out.println("is ready?");
    	return true;
        /*
        if (this.id != regeneration.id && this.id != poison.id)
        {
            if (this.id == wither.id)
            {
                k = 40 >> par2;
                return k > 0 ? par1 % k == 0 : true;
            }
            else
            {
                return this.id == hunger.id;
            }
        }
        else
        {
            k = 25 >> par2;
            return k > 0 ? par1 % k == 0 : true;
        }
        */
    }
}
