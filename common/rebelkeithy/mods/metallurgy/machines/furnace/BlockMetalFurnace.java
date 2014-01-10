package rebelkeithy.mods.metallurgy.machines.furnace;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import rebelkeithy.mods.keithyutils.guiregistry.GuiRegistry;
import rebelkeithy.mods.metallurgy.machines.ConfigMachines;
import rebelkeithy.mods.metallurgy.machines.MetallurgyMachines;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMetalFurnace extends BlockContainer
{
    /**
     * Is the random generator used by furnace to drop the inventory contents in
     * random directions.
     */
    private final Random furnaceRand = new Random();

    private static int front = 0;
    private static int side = 1;
    private static int top = 2;
    private static int active = 3;

    private Map<Integer, Icon[]> iconMap;

    /**
     * This flag is used to prevent the furnace inventory to be dropped upon
     * block removal, is used internally when the furnace block changes from
     * idle to active and vice-versa.
     */
    private static boolean keepFurnaceInventory = false;

    /**
     * Update which block ID the furnace is using depending on whether or not it
     * is burning
     */
    public static void updateFurnaceBlockState(boolean par0, World par1World, int x, int y, int z)
    {
        final int meta = par1World.getBlockMetadata(x, y, z);

        if (!par0 && meta >= 8)
        {
            par1World.setBlockMetadataWithNotify(x, y, z, meta - 8, 2);
        }
        else if (par0 && meta < 8)
        {
            par1World.setBlockMetadataWithNotify(x, y, z, meta + 8, 2);
        }
    }

    public BlockMetalFurnace(int par1, boolean par2)
    {
        super(par1, Material.rock);
    }

    /**
     * Called whenever the block is removed.
     */
    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        if (!keepFurnaceInventory)
        {
            final TileEntityMetalFurnace var5 = (TileEntityMetalFurnace) par1World.getBlockTileEntity(par2, par3, par4);

            if (var5 != null)
            {
                for (int var6 = 0; var6 < var5.getSizeInventory(); ++var6)
                {
                    final ItemStack var7 = var5.getStackInSlot(var6);

                    if (var7 != null)
                    {
                        final float var8 = furnaceRand.nextFloat() * 0.8F + 0.1F;
                        final float var9 = furnaceRand.nextFloat() * 0.8F + 0.1F;
                        final float var10 = furnaceRand.nextFloat() * 0.8F + 0.1F;

                        while (var7.stackSize > 0)
                        {
                            int var11 = furnaceRand.nextInt(21) + 10;

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
                            var12.motionX = (float) furnaceRand.nextGaussian() * var13;
                            var12.motionY = (float) furnaceRand.nextGaussian() * var13 + 0.2F;
                            var12.motionZ = (float) furnaceRand.nextGaussian() * var13;
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
    public TileEntity createNewTileEntity(World world)
    {
        return new TileEntityMetalFurnace();
    }

    @Override
    public int damageDropped(int metadata)
    {
        return metadata < 8 ? metadata : metadata - 8;
    }

    /**
     * Retrieves the block texture to use based on the display side. Args:
     * iBlockAccess, x, y, z, side
     */
    @Override
    public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        final TileEntity tileEntity = par1IBlockAccess.getBlockTileEntity(par2, par3, par4);
        final int meta = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        final int dir = tileEntity instanceof TileEntityMetalFurnace ? ((TileEntityMetalFurnace) tileEntity).getDirection() : 0;
        final boolean isBurning = tileEntity instanceof TileEntityMetalFurnace ? ((TileEntityMetalFurnace) tileEntity).isBurning() : false;

        return getFurnaceTexture(par5, meta, dir, isBurning);
    }

    public Icon getFurnaceTexture(int side, int meta, int facing, boolean isActive)
    {
        if (side == 1 || side == 0)
        {
            return iconMap.get(meta)[top];
        }
        else
        {
            if (side != facing)
            {
                return iconMap.get(meta)[BlockMetalFurnace.side];
            }
            else if (isActive)
            {
                return iconMap.get(meta)[active];
            }
            else
            {
                return iconMap.get(meta)[front];
            }
        }
    }

    @Override
    public Icon getIcon(int par1, int par2)
    {
        return getFurnaceTexture(par1, par2, 3, false);
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
        try
        {

            final TileEntityMetalFurnace var6 = (TileEntityMetalFurnace) world.getBlockTileEntity(x, y, z);

            if (var6 != null && var6.isBurning())
            {
                return 12;
            }
        } catch (final ClassCastException e)
        {
            return 0;
        }

        return 0;
    }

    @Override
    @SuppressWarnings(
    { "rawtypes", "unchecked" })
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int n = 0; n < 4; n++)
        {
            par3List.add(new ItemStack(this, 1, n));

        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return ConfigMachines.furnaceID;
    }

    /**
     * Called upon block activation (left or right click on the block.). The
     * three integers represent x,y,z of the block.
     */
    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (par1World.isRemote)
        {
            return true;
        }

        if (par5EntityPlayer.isSneaking())
        {
            return false;
        }

        final TileEntityMetalFurnace var6 = (TileEntityMetalFurnace) par1World.getBlockTileEntity(par2, par3, par4);

        if (var6 != null)
        {
            // par5EntityPlayer.openGui(MetallurgyMachines.instance, 0,
            // par1World, par2, par3, par4);
            GuiRegistry.openGui("MetalFurnace", MetallurgyMachines.instance, par5EntityPlayer, par1World, par2, par3, par4);
        }

        return true;
    }

    /**
     * Called when the block is placed in the world.
     */
    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLiving, ItemStack par6ItemStack)
    {
        final int var6 = MathHelper.floor_double(par5EntityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

        if (var6 == 0)
        {
            // par1World.setBlockMetadataWithNotify(par2, par3, par4, 2);
            ((TileEntityMetalFurnace) par1World.getBlockTileEntity(par2, par3, par4)).setDirection(2);
        }

        if (var6 == 1)
        {
            // par1World.setBlockMetadataWithNotify(par2, par3, par4, 5);
            ((TileEntityMetalFurnace) par1World.getBlockTileEntity(par2, par3, par4)).setDirection(5);
        }

        if (var6 == 2)
        {
            // par1World.setBlockMetadataWithNotify(par2, par3, par4, 3);
            ((TileEntityMetalFurnace) par1World.getBlockTileEntity(par2, par3, par4)).setDirection(3);
        }

        if (var6 == 3)
        {
            // par1World.setBlockMetadataWithNotify(par2, par3, par4, 4);
            ((TileEntityMetalFurnace) par1World.getBlockTileEntity(par2, par3, par4)).setDirection(4);
        }

        final TileEntityMetalFurnace var5 = (TileEntityMetalFurnace) par1World.getBlockTileEntity(par2, par3, par4);
        final int metadata = par1World.getBlockMetadata(par2, par3, par4);
        switch (metadata)
        {
        case 0:
        {
            var5.setSpeed((int) (20 * ConfigMachines.copperFurnaceSpeed));
            break;
        }
        case 1:
        {
            var5.setSpeed((int) (20 * ConfigMachines.bronzeFurnaceSpeed));
            break;
        }
        case 2:
        {
            var5.setSpeed((int) (20 * ConfigMachines.ironFurnaceSpeed));
            break;
        }
        case 3:
        {
            var5.setSpeed((int) (20 * ConfigMachines.steelFurnaceSpeed));
            break;
        }
        default:
            break;
        }

    }

    /**
     * A randomly called display update to be able to add particles or other
     * items for display
     */
    @Override
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        par1World.getBlockMetadata(par2, par3, par4);
        final TileEntityMetalFurnace tef = (TileEntityMetalFurnace) par1World.getBlockTileEntity(par2, par3, par4);
        if (tef.isBurning())
        {
            // int var6 = par1World.getBlockMetadata(par2, par3, par4);
            final int var6 = tef.getDirection();
            final float var7 = par2 + 0.5F;
            final float var8 = par3 + 0.0F + par5Random.nextFloat() * 6.0F / 16.0F;
            final float var9 = par4 + 0.5F;
            final float var10 = 0.52F;
            final float var11 = par5Random.nextFloat() * 0.6F - 0.3F;

            if (var6 == 4)
            {
                par1World.spawnParticle("smoke", var7 - var10, var8, var9 + var11, 0.0D, 0.0D, 0.0D);
                par1World.spawnParticle("flame", var7 - var10, var8, var9 + var11, 0.0D, 0.0D, 0.0D);
            }
            else if (var6 == 5)
            {
                par1World.spawnParticle("smoke", var7 + var10, var8, var9 + var11, 0.0D, 0.0D, 0.0D);
                par1World.spawnParticle("flame", var7 + var10, var8, var9 + var11, 0.0D, 0.0D, 0.0D);
            }
            else if (var6 == 2)
            {
                par1World.spawnParticle("smoke", var7 + var11, var8, var9 - var10, 0.0D, 0.0D, 0.0D);
                par1World.spawnParticle("flame", var7 + var11, var8, var9 - var10, 0.0D, 0.0D, 0.0D);
            }
            else if (var6 == 3)
            {
                par1World.spawnParticle("smoke", var7 + var11, var8, var9 + var10, 0.0D, 0.0D, 0.0D);
                par1World.spawnParticle("flame", var7 + var11, var8, var9 + var10, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        iconMap = new HashMap<Integer, Icon[]>();
        for (int i = 0; i < 4; i++)
        {
            final Icon[] iArray = new Icon[5];
            iArray[front] = par1IconRegister.registerIcon("Metallurgy:machines/furnace/Furnace" + i + "Front");
            iArray[side] = par1IconRegister.registerIcon("Metallurgy:machines/furnace/Furnace" + i + "Side");
            iArray[top] = par1IconRegister.registerIcon("Metallurgy:machines/furnace/Furnace" + i + "Top");
            iArray[active] = par1IconRegister.registerIcon("Metallurgy:machines/furnace/Furnace" + i + "Active");
            iconMap.put(i, iArray);
        }
    }
}
