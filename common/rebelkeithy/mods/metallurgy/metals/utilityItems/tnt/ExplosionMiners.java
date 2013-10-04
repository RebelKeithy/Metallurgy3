package rebelkeithy.mods.metallurgy.metals.utilityItems.tnt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class ExplosionMiners extends Explosion
{

    private static ArrayList<Integer> oreBlockIDs;

    public static void initializeOreList()
    {
        oreBlockIDs = Lists.newArrayList();
        oreBlockIDs.add(Block.oreCoal.blockID);
        oreBlockIDs.add(Block.oreIron.blockID);
        oreBlockIDs.add(Block.oreGold.blockID);
        oreBlockIDs.add(Block.oreDiamond.blockID);
        oreBlockIDs.add(Block.oreRedstone.blockID);
        oreBlockIDs.add(Block.oreLapis.blockID);
        oreBlockIDs.add(Block.oreNetherQuartz.blockID);

        final String[] names = OreDictionary.getOreNames();

        for (final String name : names)
        {
            if (name.contains("ore"))
            {
                final List<ItemStack> ores = OreDictionary.getOres(name);

                for (final ItemStack ore : ores)
                {
                    oreBlockIDs.add(ore.itemID);
                }
            }
        }
    }

    /** whether or not the explosion sets fire to blocks around it */
    public boolean isFlaming = false;
    public boolean isSmoking = true;
    private final int field_77289_h = 16;
    private final Random explosionRNG = new Random();
    private final World worldObj;
    public double explosionX;
    public double explosionY;
    public double explosionZ;
    public Entity exploder;
    public float explosionSize;
    public List<ChunkPosition> affectedBlockPositions = new ArrayList<ChunkPosition>();

    private final Map<Entity, Vec3> field_77288_k = new HashMap<Entity, Vec3>();

    public ExplosionMiners(World par1World, Entity par2Entity, double par3, double par5, double par7, float par9)
    {
        super(par1World, par2Entity, par3, par5, par7, par9);
        worldObj = par1World;
        exploder = par2Entity;
        explosionSize = par9;
        explosionX = par3;
        explosionY = par5;
        explosionZ = par7;
    }

    /**
     * Does the first part of the explosion (destroy blocks)
     */
    @Override
    public void doExplosionA()
    {
        final float var1 = explosionSize;
        final HashSet<ChunkPosition> var2 = new HashSet<ChunkPosition>();
        int var3;
        int var4;
        int var5;
        double var15;
        double var17;
        double var19;

        for (var3 = 0; var3 < field_77289_h; ++var3)
        {
            for (var4 = 0; var4 < field_77289_h; ++var4)
            {
                for (var5 = 0; var5 < field_77289_h; ++var5)
                {
                    if (var3 == 0 || var3 == field_77289_h - 1 || var4 == 0 || var4 == field_77289_h - 1 || var5 == 0 || var5 == field_77289_h - 1)
                    {
                        double var6 = var3 / (field_77289_h - 1.0F) * 2.0F - 1.0F;
                        double var8 = var4 / (field_77289_h - 1.0F) * 2.0F - 1.0F;
                        double var10 = var5 / (field_77289_h - 1.0F) * 2.0F - 1.0F;
                        final double var12 = Math.sqrt(var6 * var6 + var8 * var8 + var10 * var10);
                        var6 /= var12;
                        var8 /= var12;
                        var10 /= var12;
                        float var14 = explosionSize * (0.7F + worldObj.rand.nextFloat() * 0.6F);
                        var15 = explosionX;
                        var17 = explosionY;
                        var19 = explosionZ;

                        for (final float var21 = 0.3F; var14 > 0.0F; var14 -= var21 * 0.75F)
                        {
                            final int var22 = MathHelper.floor_double(var15);
                            final int var23 = MathHelper.floor_double(var17);
                            final int var24 = MathHelper.floor_double(var19);
                            final int var25 = worldObj.getBlockId(var22, var23, var24);

                            if (var25 > 0)
                            {
                                final Block var26 = Block.blocksList[var25];
                                float var27 = exploder != null ? exploder.getBlockExplosionResistance(this, worldObj, var22, var23, var24, var26) : var26.getExplosionResistance(
                                        exploder, worldObj, var22, var23, var24, explosionX, explosionY, explosionZ);
                                if (isOre(var25))
                                {
                                    var27 = 2000;
                                }
                                var14 -= (var27 + 0.3F) * var21;
                            }

                            if (var14 > 0.0F)
                            {
                                var2.add(new ChunkPosition(var22, var23, var24));
                            }

                            var15 += var6 * var21;
                            var17 += var8 * var21;
                            var19 += var10 * var21;
                        }
                    }
                }
            }
        }

        affectedBlockPositions.addAll(var2);
        explosionSize *= 2.0F;
        var3 = MathHelper.floor_double(explosionX - explosionSize - 1.0D);
        var4 = MathHelper.floor_double(explosionX + explosionSize + 1.0D);
        var5 = MathHelper.floor_double(explosionY - explosionSize - 1.0D);
        final int var28 = MathHelper.floor_double(explosionY + explosionSize + 1.0D);
        final int var7 = MathHelper.floor_double(explosionZ - explosionSize - 1.0D);
        final int var29 = MathHelper.floor_double(explosionZ + explosionSize + 1.0D);
        final List<?> var9 = worldObj.getEntitiesWithinAABBExcludingEntity(exploder, AxisAlignedBB.getAABBPool().getAABB(var3, var5, var7, var4, var28, var29));
        final Vec3 var30 = worldObj.getWorldVec3Pool().getVecFromPool(explosionX, explosionY, explosionZ);

        for (int var11 = 0; var11 < var9.size(); ++var11)
        {
            final Entity var31 = (Entity) var9.get(var11);
            final double var13 = var31.getDistance(explosionX, explosionY, explosionZ) / explosionSize;

            if (var13 <= 1.0D)
            {
                var15 = var31.posX - explosionX;
                var17 = var31.posY + var31.getEyeHeight() - explosionY;
                var19 = var31.posZ - explosionZ;
                final double var33 = MathHelper.sqrt_double(var15 * var15 + var17 * var17 + var19 * var19);

                if (var33 != 0.0D)
                {
                    var15 /= var33;
                    var17 /= var33;
                    var19 /= var33;
                    final double var32 = worldObj.getBlockDensity(var30, var31.boundingBox);
                    final double var34 = (1.0D - var13) * var32;
                    var31.attackEntityFrom(DamageSource.setExplosionSource(this), (int) ((var34 * var34 + var34) / 2.0D * 8.0D * explosionSize + 1.0D));
                    var31.motionX += var15 * var34;
                    var31.motionY += var17 * var34;
                    var31.motionZ += var19 * var34;

                    if (var31 instanceof EntityPlayer)
                    {
                        field_77288_k.put(var31, worldObj.getWorldVec3Pool().getVecFromPool(var15 * var34, var17 * var34, var19 * var34));
                    }
                }
            }
        }

        explosionSize = var1;
    }

    /**
     * Does the second part of the explosion (sound, particles, drop spawn)
     */
    @Override
    public void doExplosionB(boolean par1)
    {
        worldObj.playSoundEffect(explosionX, explosionY, explosionZ, "random.explode", 4.0F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);

        if (explosionSize >= 2.0F && isSmoking)
        {
            worldObj.spawnParticle("hugeexplosion", explosionX, explosionY, explosionZ, 1.0D, 0.0D, 0.0D);
        }
        else
        {
            worldObj.spawnParticle("largeexplode", explosionX, explosionY, explosionZ, 1.0D, 0.0D, 0.0D);
        }

        Iterator<ChunkPosition> var2;
        ChunkPosition var3;
        int var4;
        int var5;
        int var6;
        int var7;

        if (isSmoking)
        {
            var2 = affectedBlockPositions.iterator();

            while (var2.hasNext())
            {
                var3 = var2.next();
                var4 = var3.x;
                var5 = var3.y;
                var6 = var3.z;
                var7 = worldObj.getBlockId(var4, var5, var6);

                if (par1)
                {
                    final double var8 = var4 + worldObj.rand.nextFloat();
                    final double var10 = var5 + worldObj.rand.nextFloat();
                    final double var12 = var6 + worldObj.rand.nextFloat();
                    double var14 = var8 - explosionX;
                    double var16 = var10 - explosionY;
                    double var18 = var12 - explosionZ;
                    final double var20 = MathHelper.sqrt_double(var14 * var14 + var16 * var16 + var18 * var18);
                    var14 /= var20;
                    var16 /= var20;
                    var18 /= var20;
                    double var22 = 0.5D / (var20 / explosionSize + 0.1D);
                    var22 *= worldObj.rand.nextFloat() * worldObj.rand.nextFloat() + 0.3F;
                    var14 *= var22;
                    var16 *= var22;
                    var18 *= var22;
                    worldObj.spawnParticle("explode", (var8 + explosionX * 1.0D) / 2.0D, (var10 + explosionY * 1.0D) / 2.0D, (var12 + explosionZ * 1.0D) / 2.0D, var14, var16,
                            var18);
                    worldObj.spawnParticle("smoke", var8, var10, var12, var14, var16, var18);
                }

                if (var7 > 0)
                {
                    Block.blocksList[var7].dropBlockAsItemWithChance(worldObj, var4, var5, var6, worldObj.getBlockMetadata(var4, var5, var6), 0.3F, 0);

                    if (worldObj.setBlockToAir(var4, var5, var6))
                    {
                        worldObj.notifyBlocksOfNeighborChange(var4, var5, var6, 0);
                    }

                    Block.blocksList[var7].onBlockDestroyedByExplosion(worldObj, var4, var5, var6, this);
                }
            }
        }

        if (isFlaming)
        {
            var2 = affectedBlockPositions.iterator();

            while (var2.hasNext())
            {
                var3 = var2.next();
                var4 = var3.x;
                var5 = var3.y;
                var6 = var3.z;
                var7 = worldObj.getBlockId(var4, var5, var6);
                final int var24 = worldObj.getBlockId(var4, var5 - 1, var6);

                if (var7 == 0 && Block.opaqueCubeLookup[var24] && explosionRNG.nextInt(3) == 0)
                {
                    worldObj.setBlock(var4, var5, var6, Block.fire.blockID, 0, 2);
                }
            }
        }
    }

    @Override
    public Map<Entity, Vec3> func_77277_b()
    {
        return field_77288_k;
    }

    private boolean isOre(int var25)
    {
        if (oreBlockIDs == null)
        {
            initializeOreList();
        }

        return oreBlockIDs.contains(var25);
    }
}