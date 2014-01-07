package rebelkeithy.mods.metallurgy.machines.mint;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import rebelkeithy.mods.keithyutils.guiregistry.GuiRegistry;
import rebelkeithy.mods.metallurgy.machines.MetallurgyMachines;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMintStorage extends BlockContainer
{
    private final Random random = new Random();

    private static int side = 0;
    private static int top = 1;
    private static int bottom = 2;

    private Icon[] icons;

    public BlockMintStorage(int par1)
    {
        super(par1, Material.rock);
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an
     * update, as appropriate
     */
    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        final TileEntityMintStorage var5 = (TileEntityMintStorage) par1World.getBlockTileEntity(par2, par3, par4);

        if (var5 != null)
        {
            for (int var6 = 0; var6 < var5.getSizeInventory(); ++var6)
            {
                final ItemStack var7 = var5.getStackInSlot(var6);

                if (var7 != null)
                {
                    final float var8 = random.nextFloat() * 0.8F + 0.1F;
                    final float var9 = random.nextFloat() * 0.8F + 0.1F;
                    EntityItem var12;

                    for (final float var10 = random.nextFloat() * 0.8F + 0.1F; var7.stackSize > 0; par1World.spawnEntityInWorld(var12))
                    {
                        int var11 = random.nextInt(21) + 10;

                        if (var11 > var7.stackSize)
                        {
                            var11 = var7.stackSize;
                        }

                        var7.stackSize -= var11;
                        var12 = new EntityItem(par1World, par2 + var8, par3 + var9, par4 + var10, new ItemStack(var7.itemID, var11, var7.getItemDamage()));
                        final float var13 = 0.05F;
                        var12.motionX = (float) random.nextGaussian() * var13;
                        var12.motionY = (float) random.nextGaussian() * var13 + 0.2F;
                        var12.motionZ = (float) random.nextGaussian() * var13;

                        if (var7.hasTagCompound())
                        {
                            var12.getEntityItem().setTagCompound((NBTTagCompound) var7.getTagCompound().copy());
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
        return new TileEntityMintStorage();
    }

    /**
     * Retrieves the block texture to use based on the display side. Args:
     * iBlockAccess, x, y, z, side
     */
    @Override
    public Icon getIcon(int par1, int par2)
    {
        if (par1 == 1)
        {
            return icons[top];
        }
        else if (par1 == 0)
        {
            return icons[bottom];
        }
        else
        {
            return icons[side];
        }
    }

    /**
     * Called upon block activation (left or right click on the block.). The
     * three integers represent x,y,z of the block.
     */
    @Override
    public boolean onBlockActivated(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        final Object var6 = par1World.getBlockTileEntity(x, y, z);

        if (var6 == null)
        {
            return true;
        }
        else
        {
            if (par1World.isRemote)
            {
                return true;
            }
            else
            {
                // par5EntityPlayer.openGui(MetallurgyPrecious.instance, 2,
                // par1World, x, y, z);
                GuiRegistry.openGui("MintStorage", MetallurgyMachines.instance, par5EntityPlayer, par1World, x, y, z);
                return true;
            }
        }
    }

    /**
     * Called when the block is placed in the world.
     */
    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLiving, ItemStack par5ItemStack)
    {
        byte direction = 0;
        final int var11 = MathHelper.floor_double(par5EntityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

        if (var11 == 0)
        {
            direction = 2;
        }

        if (var11 == 1)
        {
            direction = 5;
        }

        if (var11 == 2)
        {
            direction = 3;
        }

        if (var11 == 3)
        {
            direction = 4;
        }
        par1World.getBlockMetadata(par2, par3, par4);
        final TileEntity tileEntity = par1World.getBlockTileEntity(par2, par3, par4);
        if (tileEntity instanceof TileEntityMintStorage)
        {
            ((TileEntityMintStorage) tileEntity).setDirection(direction);
        }

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        icons = new Icon[3];
        icons[side] = par1IconRegister.registerIcon("Metallurgy:machines/mint/MintStorageSide");
        icons[top] = par1IconRegister.registerIcon("Metallurgy:machines/mint/MintStorageTop");
        icons[bottom] = par1IconRegister.registerIcon("Metallurgy:machines/mint/MintStorageBottom");
    }
}
