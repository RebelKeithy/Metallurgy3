package rebelkeithy.mods.metallurgy.machines.chests;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import rebelkeithy.mods.keithyutils.guiregistry.GuiRegistry;
import rebelkeithy.mods.metallurgy.machines.MetallurgyMachines;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPreciousChest extends BlockContainer
{
    /**
     * Looks for a sitting ocelot within certain bounds. Such an ocelot is
     * considered to be blocking access to the chest.
     */
    private static boolean isOcelotBlockingChest(World par0World, int par1, int par2, int par3)
    {
        final Iterator<?> var4 = par0World.getEntitiesWithinAABB(EntityOcelot.class, AxisAlignedBB.getAABBPool().getAABB(par1, par2 + 1, par3, par1 + 1, par2 + 2, par3 + 1))
                .iterator();
        EntityOcelot var6;

        do
        {
            if (!var4.hasNext())
            {
                return false;
            }

            final Entity var5 = (Entity) var4.next();
            var6 = (EntityOcelot) var5;
        } while (!var6.isSitting());

        return true;
    }

    private final Random random = new Random();
    private final int renderId = RenderingRegistry.getNextAvailableRenderId();

    private Icon[] iconArray;

    public BlockPreciousChest(int par1)
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
        final TileEntityPreciousChest var5 = (TileEntityPreciousChest) par1World.getBlockTileEntity(par2, par3, par4);

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
        return new TileEntityPreciousChest();
    }

    @Override
    public int damageDropped(int metadata)
    {
        return metadata;
    }

    @Override
    public Icon getIcon(int par1, int par2)
    {
        return iconArray[par2];
    }

    /**
     * The type of render function that is called for this block
     */
    @Override
    public int getRenderType()
    {
        return renderId;
    }

    @Override
    @SuppressWarnings(
    { "rawtypes", "unchecked" })
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int n = 0; n < 5; n++)
        {
            par3List.add(new ItemStack(this, 1, n));

        }
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube? This determines whether
     * or not to render the shared face of two adjacent blocks and also whether
     * the player can attach torches, redstone wire, etc to this block.
     */
    @Override
    public boolean isOpaqueCube()
    {
        return false;
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
        else if (par1World.isBlockSolidOnSide(x, y + 1, z, ForgeDirection.DOWN))
        {
            return true;
        }
        else if (isOcelotBlockingChest(par1World, x, y, z))
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
                // par5EntityPlayer.displayGUIChest((IInventory)var6);
                // par5EntityPlayer.openGui(MetallurgyPrecious.instance, -1,
                // par1World, x, y, z);
                // ModLoader.openGUI(par5EntityPlayer, new
                // FC_GuiChest(par5EntityPlayer.inventory, (IInventory) var6));
                // TODO open gui
                GuiRegistry.openGui("PreciousChest", MetallurgyMachines.instance, par5EntityPlayer, par1World, x, y, z);
                return true;
            }
        }
    }

    /**
     * Called when the block is placed in the world.
     */
    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLiving, ItemStack par6ItemStack)
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
        final int type = par1World.getBlockMetadata(par2, par3, par4);
        final TileEntity tileEntity = par1World.getBlockTileEntity(par2, par3, par4);
        if (tileEntity instanceof TileEntityPreciousChest)
        {
            ((TileEntityPreciousChest) tileEntity).setDirection(direction);
            ((TileEntityPreciousChest) tileEntity).setType(type);
        }

        // par1World.setBlockMetadataWithNotify(par2, par3, par4, direction);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        iconArray = new Icon[5];
        iconArray[0] = par1IconRegister.registerIcon("Metallurgy:Precious/BrassBlock");
        iconArray[1] = par1IconRegister.registerIcon("Metallurgy:Precious/SilverBlock");
        iconArray[2] = par1IconRegister.registerIcon("Metallurgy:Precious/ElectrumBlock");
        iconArray[3] = par1IconRegister.registerIcon("Metallurgy:Precious/ElectrumBlock");
        iconArray[4] = par1IconRegister.registerIcon("Metallurgy:Precious/PlatinumBlock");
    }

    /**
     * If this block doesn't render as an ordinary block it will return False
     * (examples: signs, buttons, stairs, etc)
     */
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

}
