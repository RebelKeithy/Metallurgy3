//package rebelkeithy.mods.metallurgy.machines.xptank.orb;
//
//import java.util.Random;
//
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.item.EntityXPOrb;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.world.World;
//import rebelkeithy.mods.metallurgy.core.MetallurgyCore;
//import cpw.mods.fml.relauncher.Side;
//import cpw.mods.fml.relauncher.SideOnly;
//
//public class EntityXpOrbContainer extends Entity
//{
//    /** This is how much XP this orb has. */
//    private int xpValue;
//    public int xpOrbAge;
//    public final int maxXpOrbAge = 60;
//    public float xpColor;
//
//    public EntityXpOrbContainer(World par1World)
//    {
//        super(par1World);
//        setSize(0.25F, 0.25F);
//        yOffset = height / 2.0F;
//        xpValue = 1000;
//    }
//
//    public EntityXpOrbContainer(World par1World, double par2, double par4, double par6, int par8)
//    {
//        super(par1World);
//        setSize(0.25F, 0.25F);
//        yOffset = height / 2.0F;
//        setPosition(par2, par4, par6);
//        rotationYaw = (float) (Math.random() * 360.0D);
//        motionX = 0;
//        motionY = (float) (Math.random() * 0.2D) * 2.0F;
//        motionZ = 0;
//        xpValue = par8;
//    }
//
//    @Override
//    protected void entityInit()
//    {
//    }
//
//    @Override
//    @SideOnly(Side.CLIENT)
//    public int getBrightnessForRender(float par1)
//    {
//        float f1 = 0.5F;
//
//        if (f1 < 0.0F)
//        {
//            f1 = 0.0F;
//        }
//
//        if (f1 > 1.0F)
//        {
//            f1 = 1.0F;
//        }
//
//        final int i = super.getBrightnessForRender(par1);
//        int j = i & 255;
//        final int k = i >> 16 & 255;
//        j += (int) (f1 * 15.0F * 16.0F);
//
//        if (j > 240)
//        {
//            j = 240;
//        }
//
//        return j | k << 16;
//    }
//
//    @SideOnly(Side.CLIENT)
//    /**
//     * Returns a number from 1 to 10 based on how much XP this orb is worth. This is used by RenderXPOrb to determine
//     * what texture to use.
//     */
//    public int getTextureByXP()
//    {
//        return xpValue >= 2477 ? 10 : xpValue >= 1237 ? 9 : xpValue >= 617 ? 8 : xpValue >= 307 ? 7 : xpValue >= 149 ? 6 : xpValue >= 73 ? 5 : xpValue >= 37 ? 4
//                : xpValue >= 17 ? 3 : xpValue >= 7 ? 2 : xpValue >= 3 ? 1 : 0;
//    }
//
//    /**
//     * Returns the XP value of this XP orb.
//     */
//    public int getXpValue()
//    {
//        return xpValue;
//    }
//
//    @Override
//    public void onUpdate()
//    {
//        super.onUpdate();
//
//        xpOrbAge++;
//
//        if (xpOrbAge >= maxXpOrbAge)
//        {
//            spawnXP(xpValue);
//            setDead();
//        }
//
//        posY += 0.05 * (maxXpOrbAge - xpOrbAge) / maxXpOrbAge;
//    }
//
//    /**
//     * (abstract) Protected helper method to read subclass entity data from NBT.
//     */
//    @Override
//    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
//    {
//        xpOrbAge = par1NBTTagCompound.getShort("Age");
//        xpValue = par1NBTTagCompound.getShort("Value");
//    }
//
//    public void spawnXP(int totalXP)
//    {
//        int xpPerOrb = 1;
//
//        int orbCount = totalXP;
//
//        if (totalXP > 20)
//        {
//            xpPerOrb = 2;
//            orbCount = totalXP / 2 + 1;
//        }
//        if (totalXP > 40)
//        {
//            xpPerOrb = 4;
//            orbCount = totalXP / 4 + 1;
//        }
//        if (totalXP > 80)
//        {
//            xpPerOrb = 8;
//            orbCount = totalXP / 8 + 1;
//        }
//
//        EntityXPOrb orb;
//        for (int n = 0; n < orbCount; n++)
//        {
//            double xOffset = 0.5;
//            double zOffset = 0.5;
//            double xMotion = 0;
//            double zMotion = 0;
//
//            /*
//             * if(direction == 2) { zOffset = 0; zOffset = -0.1; } else
//             * if(direction == 3) { zOffset = 1; zMotion = 0.1; } else
//             * if(direction == 4) { xOffset = 0; xMotion = -0.1; } else
//             * if(direction == 5) { xOffset = 1; xMotion = 0.1; }
//             */
//
//            final Random rand = new Random();
//            xOffset += (rand.nextInt(21) - 10) / 100.0;
//            zOffset += (rand.nextInt(21) - 10) / 100.0;
//            xMotion += (rand.nextInt(11) - 5) / 100.0;
//            zMotion += (rand.nextInt(11) - 5) / 100.0;
//            final double yMotion = (rand.nextInt(11) - 5) / 200.0;
//
//            MetallurgyCore.proxy.spawnParticle("abstractorSmall", worldObj, posX + xOffset, posY + 0.75, posZ + zOffset, xMotion * 0.7f, yMotion, zMotion * 0.7f);
//            orb = new EntityXPOrb(worldObj, posX + xOffset, posY + 0.5f, posZ + zOffset, xpPerOrb);
//            orb.motionX = xMotion;
//            orb.motionZ = zMotion;
//            if (!worldObj.isRemote)
//            {
//                worldObj.spawnEntityInWorld(orb);
//                worldObj.updateEntity(orb);
//            }
//        }
//    }
//
//    /**
//     * (abstract) Protected helper method to write subclass entity data to NBT.
//     */
//    @Override
//    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
//    {
//        par1NBTTagCompound.setShort("Age", (short) xpOrbAge);
//        par1NBTTagCompound.setShort("Value", (short) xpValue);
//    }
//}
