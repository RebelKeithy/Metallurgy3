package rebelkeithy.mods.metallurgy.metals;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import rebelkeithy.mods.keithyutils.metablock.ICollisionListener;

public class VyroxeresCollisionListener implements ICollisionListener
{

    @Override
    public void collide(World par1World, int par2, int par3, int par4, Entity par5Entity, int meta)
    {

        if (par5Entity instanceof EntityLivingBase)
        {
            ((EntityLivingBase) par5Entity).addPotionEffect(new PotionEffect(19, 120, 1));
        }
    }

}
