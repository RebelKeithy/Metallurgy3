package rebelkeithy.mods.metallurgy.metals;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

public class MetallurgyPotion extends Potion
{

    public MetallurgyPotion(int par1, boolean par2, int par3)
    {
        super(par1, par2, par3);
    }

    @Override
    public boolean isReady(int par1, int par2)
    {
        // System.out.println("is ready?");
        return true;
        /*
         * if (this.id != regeneration.id && this.id != poison.id) { if (this.id
         * == wither.id) { k = 40 >> par2; return k > 0 ? par1 % k == 0 : true;
         * } else { return this.id == hunger.id; } } else { k = 25 >> par2;
         * return k > 0 ? par1 % k == 0 : true; }
         */
    }

    @Override
    public void performEffect(EntityLivingBase par1EntityLiving, int par2)
    {
        // System.out.println("Metallurgy Potion");
        final double vx = par1EntityLiving.motionX;
        double vy = par1EntityLiving.motionY;
        final double vz = par1EntityLiving.motionZ;
        // TODO: access isJumping
        // if(par1EntityLiving.isJumping || vy < 0)
        // if (false)
        // {
            // vy += 0.0734000015258789;
            // vy += 0.0734000015258789;
            // vy *= 1.260504;
            // System.out.println(vy);
        // }
        // else
        // {
            vy += 0.0734000015258789 / 1.5;
        // }

        if (par1EntityLiving.isSprinting() && par1EntityLiving.onGround)
        {
            System.out.println("leaping");
            vy += 0.3;
            // par1EntityLiving;
        }
        else if ((Math.abs(par1EntityLiving.motionX) > 0.1 || Math.abs(par1EntityLiving.motionZ) > 0.1) && par1EntityLiving.onGround)
        {
            final double distance = Math.sqrt(Math.pow(par1EntityLiving.motionX, 2) + Math.pow(par1EntityLiving.motionZ, 2));

            System.out.println(par1EntityLiving.motionX);
            vy += 0.15 * (distance - 0.05) * 10;
        }

        par1EntityLiving.setVelocity(vx, vy, vz);
    }
}
