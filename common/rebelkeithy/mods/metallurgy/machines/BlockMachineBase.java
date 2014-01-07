package rebelkeithy.mods.metallurgy.machines;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import rebelkeithy.mods.keithyutils.guiregistry.GuiRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockMachineBase extends BlockContainer
{
    private static int front = 0;
    private static int side = 1;
    private static int top = 2;
    private static int bottom = 3;

    String guiID;

    private final Map<Integer, Icon[]> iconMapActive;
    private final Map<Integer, Icon[]> iconMapInactive;

    private int numSubtypes;

    protected BlockMachineBase(int par1, Material par2Material)
    {
        super(par1, par2Material);
        iconMapActive = new HashMap<Integer, Icon[]>();
        iconMapInactive = new HashMap<Integer, Icon[]>();
        guiID = "";
    }

    @Override
    public int damageDropped(int metadata)
    {
        return metadata;
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
        final int dir = tileEntity instanceof TileEntityMachineBase ? ((TileEntityMachineBase) tileEntity).getDirection() : 0;
        final boolean isBurning = tileEntity instanceof TileEntityMachineBase ? ((TileEntityMachineBase) tileEntity).isActive() : false;

        return getMachineTexture(par5, meta, dir, isBurning);
    }

    @Override
    public Icon getIcon(int par1, int par2)
    {
        return getMachineTexture(par1, par2, 3, false);
    }

    public Icon getMachineTexture(int side, int meta, int facing, boolean active)
    {
        Map<Integer, Icon[]> iconMap = null;
        if (active)
        {
            iconMap = iconMapActive;
        }
        else
        {
            iconMap = iconMapInactive;
        }

        if (side == 1 || side == 0)
        {
            return iconMap.get(meta)[top];
        }
        else
        {
            if (side != facing)
            {
                return iconMap.get(meta)[BlockMachineBase.side];
            }
            else
            {
                return iconMap.get(meta)[front];
            }
        }
    }

    @Override
    @SuppressWarnings(
    { "rawtypes", "unchecked" })
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int n = 0; n < numSubtypes; n++)
        {
            par3List.add(new ItemStack(this, 1, n));
        }
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

        final TileEntity var6 = par1World.getBlockTileEntity(par2, par3, par4);

        if (var6 != null)
        {
            GuiRegistry.openGui(guiID, MetallurgyMachines.instance, par5EntityPlayer, par1World, par2, par3, par4);
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
            ((TileEntityMachineBase) par1World.getBlockTileEntity(par2, par3, par4)).setDirection(2);
        }

        if (var6 == 1)
        {
            ((TileEntityMachineBase) par1World.getBlockTileEntity(par2, par3, par4)).setDirection(5);
        }

        if (var6 == 2)
        {
            ((TileEntityMachineBase) par1World.getBlockTileEntity(par2, par3, par4)).setDirection(3);
        }

        if (var6 == 3)
        {
            ((TileEntityMachineBase) par1World.getBlockTileEntity(par2, par3, par4)).setDirection(4);
        }
    }

    public void setBottomIcon(Icon icon, int meta, boolean... active)
    {
        if (active.length > 0)
        {
            setIcon(icon, meta, bottom, active[0]);
        }
        else
        {
            setIcon(icon, meta, bottom, true);
            setIcon(icon, meta, bottom, false);
        }
    }

    public void setFrontIcon(Icon icon, int meta, boolean... active)
    {
        if (active.length > 0)
        {
            setIcon(icon, meta, front, active[0]);
        }
        else
        {
            setIcon(icon, meta, front, true);
            setIcon(icon, meta, front, false);
        }
    }

    public void setGui(String guiID)
    {
        this.guiID = guiID;
    }

    public void setIcon(Icon icon, int meta, int side, boolean active)
    {

        Map<Integer, Icon[]> iconMap = null;
        if (active)
        {
            iconMap = iconMapActive;
        }
        else
        {
            iconMap = iconMapInactive;
        }

        Icon[] array = iconMap.get(meta);
        if (array == null)
        {
            array = new Icon[4];
        }

        array[side] = icon;
        iconMap.put(meta, array);
    }

    protected void setNumSubtypes(int num)
    {
        numSubtypes = num;
    }

    public void setSideIcon(Icon icon, int meta, boolean... active)
    {
        if (active.length > 0)
        {
            setIcon(icon, meta, side, active[0]);
        }
        else
        {
            setIcon(icon, meta, side, true);
            setIcon(icon, meta, side, false);
        }
    }

    public void setTopIcon(Icon icon, int meta, boolean... active)
    {
        if (active.length > 0)
        {
            setIcon(icon, meta, top, active[0]);
        }
        else
        {
            setIcon(icon, meta, top, true);
            setIcon(icon, meta, top, false);
        }
    }
}
