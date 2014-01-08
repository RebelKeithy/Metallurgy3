package rebelkeithy.mods.metallurgy.machines.pylon;

import java.util.List;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class BlockPylon extends BlockContainer
{
    public static String[] names =
    { "Prometheum", "Deep Iron", "Black Steel", "Oureclase", "Aredrite", "Mithril", "Haderoth", "Orichalcum", "Adamantine", "Atlarus", "Tartarite" };
    public static float[] enchantability =
    { 0.5f, 1f, 2f, 3f, 4f, 5f, 7.5f, 10f, 12.5f, 15f, 17.5f };

    public int renderId = RenderingRegistry.getNextAvailableRenderId();

    public BlockPylon(int par1)
    {
        super(par1, Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileEntityPylon();
    }

    @Override
    public int damageDropped(int par1)
    {
        return par1;
    }

    @Override
    public float getEnchantPowerBonus(World world, int x, int y, int z)
    {
        final int meta = world.getBlockMetadata(x, y, z);
        return enchantability[meta];
    }

    @Override
    public int getRenderType()
    {
        return renderId;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < names.length; i++)
        {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
}
