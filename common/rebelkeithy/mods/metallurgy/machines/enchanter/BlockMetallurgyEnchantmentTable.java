package rebelkeithy.mods.metallurgy.machines.enchanter;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import rebelkeithy.mods.keithyutils.guiregistry.GuiRegistry;
import rebelkeithy.mods.metallurgy.machines.MetallurgyMachines;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMetallurgyEnchantmentTable extends BlockContainer
{
    @SideOnly(Side.CLIENT)
    private Icon field_94461_a;
    @SideOnly(Side.CLIENT)
    private Icon field_94460_b;
    private Icon field_94336_cN;

    public BlockMetallurgyEnchantmentTable(int par1)
    {
        super(par1, Material.rock);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
        setLightOpacity(0);
        setCreativeTab(CreativeTabs.tabDecorations);
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing
     * the block.
     */
    @Override
    public TileEntity createNewTileEntity(World par1World)
    {
        return new TileEntityMetallurgyEnchantmentTable();
    }

    @Override
    @SideOnly(Side.CLIENT)
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int par1, int par2)
    {
        return par1 == 0 ? field_94460_b : par1 == 1 ? field_94461_a : field_94336_cN;
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
     * Called upon block activation (right click on the block.)
     */
    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (par1World.isRemote)
        {
            return true;
        }
        else
        {
            final TileEntityMetallurgyEnchantmentTable tileentityenchantmenttable = (TileEntityMetallurgyEnchantmentTable) par1World.getBlockTileEntity(par2, par3, par4);
            // par5EntityPlayer.displayGUIEnchantment(par2, par3, par4,
            // tileentityenchantmenttable.func_94135_b() ?
            // tileentityenchantmenttable.func_94133_a() : null);
            if (tileentityenchantmenttable != null)
            {
                GuiRegistry.openGui("Enchanter", MetallurgyMachines.instance, par5EntityPlayer, par1World, par2, par3, par4);
            }
            return true;
        }
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack par6ItemStack)
    {
        super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLiving, par6ItemStack);

        if (par6ItemStack.hasDisplayName())
        {
            ((TileEntityMetallurgyEnchantmentTable) par1World.getBlockTileEntity(par2, par3, par4)).func_94134_a(par6ItemStack.getDisplayName());
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        super.randomDisplayTick(par1World, par2, par3, par4, par5Random);

        for (int l = par2 - 2; l <= par2 + 2; ++l)
        {
            for (int i1 = par4 - 2; i1 <= par4 + 2; ++i1)
            {
                if (l > par2 - 2 && l < par2 + 2 && i1 == par4 - 1)
                {
                    i1 = par4 + 2;
                }

                if (par5Random.nextInt(16) == 0)
                {
                    for (int j1 = par3; j1 <= par3 + 1; ++j1)
                    {
                        if (par1World.getBlockId(l, j1, i1) == Block.bookShelf.blockID)
                        {
                            if (!par1World.isAirBlock((l - par2) / 2 + par2, j1, (i1 - par4) / 2 + par4))
                            {
                                break;
                            }

                            par1World.spawnParticle("enchantmenttable", par2 + 0.5D, par3 + 2.0D, par4 + 0.5D, l - par2 + par5Random.nextFloat() - 0.5D,
                                    j1 - par3 - par5Random.nextFloat() - 1.0F, i1 - par4 + par5Random.nextFloat() - 0.5D);
                        }
                    }
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        field_94336_cN = par1IconRegister.registerIcon("Metallurgy:machines/enchanter/enchanting_table_side");
        field_94461_a = par1IconRegister.registerIcon("Metallurgy:machines/enchanter/enchanting_table_top");
        field_94460_b = par1IconRegister.registerIcon("Metallurgy:machines/enchanter/enchanting_table_bottom");
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
