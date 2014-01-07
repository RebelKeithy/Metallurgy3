package rebelkeithy.mods.metallurgy.machines.lantern;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockColoredGlass extends Block
{
    public int renderId = RenderingRegistry.getNextAvailableRenderId();
    private Icon[] icons;

    public BlockColoredGlass(int par1)
    {
        super(par1, Material.glass);
    }

    /**
     * Return true if a player with Silk Touch can harvest this block directly,
     * and not its normal drops.
     */
    @Override
    protected boolean canSilkHarvest()
    {
        return true;
    }

    /**
     * Get the block's damage value (for use with pick block).
     */
    @Override
    public int getDamageValue(World par1World, int par2, int par3, int par4)
    {
        return par1World.getBlockMetadata(par2, par3, par4);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture.
     * Args: side, metadata
     */
    @Override
    public Icon getIcon(int par1, int par2)
    {
        return icons[par2];
    }

    @Override
    @SideOnly(Side.CLIENT)
    /**
     * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
     */
    public int getRenderBlockPass()
    {
        return 1;
    }

    @Override
    @SuppressWarnings(
    { "rawtypes", "unchecked" })
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int n = 0; n < 8; n++)
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
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(Random par1Random)
    {
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        icons = new Icon[8];
        icons[0] = par1IconRegister.registerIcon("Metallurgy:machines/lantern/GlassRed");
        icons[1] = par1IconRegister.registerIcon("Metallurgy:machines/lantern/GlassGreen");
        icons[2] = par1IconRegister.registerIcon("Metallurgy:machines/lantern/GlassBlue");
        icons[3] = par1IconRegister.registerIcon("Metallurgy:machines/lantern/GlassOrange");
        icons[4] = par1IconRegister.registerIcon("Metallurgy:machines/lantern/GlassYellow");
        icons[5] = par1IconRegister.registerIcon("Metallurgy:machines/lantern/GlassPurple");
        icons[6] = par1IconRegister.registerIcon("Metallurgy:machines/lantern/GlassGrey");
        icons[7] = par1IconRegister.registerIcon("Metallurgy:machines/lantern/GlassWhite");
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
