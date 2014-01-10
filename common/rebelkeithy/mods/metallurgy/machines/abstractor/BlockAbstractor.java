package rebelkeithy.mods.metallurgy.machines.abstractor;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import rebelkeithy.mods.metallurgy.core.MetallurgyCore;
import rebelkeithy.mods.metallurgy.machines.BlockMachineBase;
import rebelkeithy.mods.metallurgy.machines.ConfigMachines;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAbstractor extends BlockMachineBase
{
    /**
     * Is the random generator used by furnace to drop the inventory contents in
     * random directions.
     */
    private final Random rand = new Random();

    /**
     * This flag is used to prevent the furnace inventory to be dropped upon
     * block removal, is used internally when the furnace block changes from
     * idle to active and vice-versa.
     */
    private static boolean keepFurnaceInventory = false;

    public BlockAbstractor(int par1, boolean par2)
    {
        super(par1, Material.rock);
        // setRequiresSelfNotify();
        setGui("Abstractor");
        setNumSubtypes(11);
    }

    /**
     * s Called whenever the block is removed.
     */
    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        if (!keepFurnaceInventory)
        {
            final TileEntityAbstractor var5 = (TileEntityAbstractor) par1World.getBlockTileEntity(par2, par3, par4);

            if (var5 != null)
            {
                for (int var6 = 0; var6 < var5.getSizeInventory(); ++var6)
                {
                    final ItemStack var7 = var5.getStackInSlot(var6);

                    if (var7 != null)
                    {
                        final float var8 = rand.nextFloat() * 0.8F + 0.1F;
                        final float var9 = rand.nextFloat() * 0.8F + 0.1F;
                        final float var10 = rand.nextFloat() * 0.8F + 0.1F;

                        while (var7.stackSize > 0)
                        {
                            int var11 = rand.nextInt(21) + 10;

                            if (var11 > var7.stackSize)
                            {
                                var11 = var7.stackSize;
                            }

                            var7.stackSize -= var11;
                            final EntityItem var12 = new EntityItem(par1World, par2 + var8, par3 + var9, par4 + var10, new ItemStack(var7.itemID, var11, var7.getItemDamage()));

                            if (var7.hasTagCompound())
                            {
                                var12.getEntityItem().setTagCompound((NBTTagCompound) var7.getTagCompound().copy());
                            }

                            final float var13 = 0.05F;
                            var12.motionX = (float) rand.nextGaussian() * var13;
                            var12.motionY = (float) rand.nextGaussian() * var13 + 0.2F;
                            var12.motionZ = (float) rand.nextGaussian() * var13;
                            par1World.spawnEntityInWorld(var12);
                        }
                    }
                }
            }
        }

        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }

    /**
     * Returns the TileEntity used by this block.
     */
    @Override
    public TileEntity createNewTileEntity(World var1)
    {
        return new TileEntityAbstractor();
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
        try
        {
            final TileEntityAbstractor tea = (TileEntityAbstractor) world.getBlockTileEntity(x, y, z);
            if (tea != null && tea.isActive())
            {
                return 5;
            }
        } catch (final ClassCastException e)
        {
            return 0;
        }

        return 0;
    }

    /**
     * Called when the block is placed in the world.
     */
    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLiving, ItemStack par6ItemStack)
    {
        super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLiving, par6ItemStack);

        final TileEntityAbstractor tileEntity = (TileEntityAbstractor) par1World.getBlockTileEntity(par2, par3, par4);
        final int metadata = par1World.getBlockMetadata(par2, par3, par4);
        tileEntity.setSpeed(20 * ConfigMachines.extractorSpeeds[metadata]);
    }

    /**
     * A randomly called display update to be able to add particles or other
     * items for display
     */
    @Override
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (Math.random() > 0.5f)
        {
            return;
        }

        par1World.getBlockMetadata(par2, par3, par4);
        final TileEntityAbstractor te = (TileEntityAbstractor) par1World.getBlockTileEntity(par2, par3, par4);
        if (te.isActive())
        {
            // int var6 = par1World.getBlockMetadata(par2, par3, par4);
            final int var6 = te.getDirection();
            final float var7 = par2 + 0.5F;
            float var8 = par3 + 0.0F + par5Random.nextFloat() * 6.0F / 16.0F + 0.5F;
            final float var9 = par4 + 0.5F;
            final float var10 = 0.52F;
            float var11 = par5Random.nextFloat() * 0.6F - 0.3F;

            if (var6 == 4)
            {
                MetallurgyCore.proxy.spawnParticle("abstractorLarge", par1World, var7 - var10, var8, var9 + var11, 0.0D, 0.0D, 0.0D);
                var8 = par3 + 0.0F + par5Random.nextFloat() * 6.0F / 16.0F + 0.5F;
                var11 = par5Random.nextFloat() * 0.6F - 0.3F;
                MetallurgyCore.proxy.spawnParticle("abstractorSmall", par1World, var7 - var10, var8, var9 + var11, 0.0D, 0.0D, 0.0D);
            }
            else if (var6 == 5)
            {
                MetallurgyCore.proxy.spawnParticle("abstractorLarge", par1World, var7 + var10, var8, var9 + var11, 0.0D, 0.0D, 0.0D);
                var8 = par3 + 0.0F + par5Random.nextFloat() * 6.0F / 16.0F + 0.5F;
                var11 = par5Random.nextFloat() * 0.6F - 0.3F;
                MetallurgyCore.proxy.spawnParticle("abstractorSmall", par1World, var7 + var10, var8, var9 + var11, 0.0D, 0.0D, 0.0D);
            }
            else if (var6 == 2)
            {
                MetallurgyCore.proxy.spawnParticle("abstractorLarge", par1World, var7 + var11, var8, var9 - var10, 0.0D, 0.0D, 0.0D);
                var8 = par3 + 0.0F + par5Random.nextFloat() * 6.0F / 16.0F + 0.5F;
                var11 = par5Random.nextFloat() * 0.6F - 0.3F;
                MetallurgyCore.proxy.spawnParticle("abstractorSmall", par1World, var7 + var11, var8, var9 - var10, 0.0D, 0.0D, 0.0D);
            }
            else if (var6 == 3)
            {
                MetallurgyCore.proxy.spawnParticle("abstractorLarge", par1World, var7 + var11, var8, var9 + var10, 0.0D, 0.0D, 0.0D);
                var8 = par3 + 0.0F + par5Random.nextFloat() * 6.0F / 16.0F + 0.5F;
                var11 = par5Random.nextFloat() * 0.6F - 0.3F;
                MetallurgyCore.proxy.spawnParticle("abstractorSmall", par1World, var7 + var11, var8, var9 + var10, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        for (int i = 0; i < 11; i++)
        {
            setFrontIcon(par1IconRegister.registerIcon("Metallurgy:machines/abstractor/Abstractor" + i + "Front"), i, false);
            setFrontIcon(par1IconRegister.registerIcon("Metallurgy:machines/abstractor/Abstractor" + i + "Active"), i, true);
            setSideIcon(par1IconRegister.registerIcon("Metallurgy:machines/abstractor/Abstractor" + i + "Side"), i);
            setTopIcon(par1IconRegister.registerIcon("Metallurgy:machines/abstractor/Abstractor" + i + "Top"), i);
            setBottomIcon(par1IconRegister.registerIcon("Metallurgy:machines/abstractor/Abstractor" + i + "Bottom"), i);
        }
    }
}