package rebelkeithy.mods.metallurgy.machines.ladders;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class LadderRenderer implements ISimpleBlockRenderingHandler
{

    @Override
    public int getRenderId()
    {
        return BlockMetalLadder.renderType;
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {

    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int par2, int par3, int par4, Block par1Block, int modelId, RenderBlocks renderer)
    {

        final Tessellator tessellator = Tessellator.instance;
        int var17 = world.getBlockMetadata(par2, par3, par4);
        final Icon icon = par1Block.getIcon(0, var17);
        var17 = var17 % 4;

        tessellator.setBrightness(par1Block.getMixedBrightnessForBlock(world, par2, par3, par4));
        final float var7 = 1.0F;
        tessellator.setColorOpaque_F(var7, var7, var7);
        final double d0 = icon.getMinU();
        final double d1 = icon.getMinV();
        final double d2 = icon.getMaxU();
        final double d3 = icon.getMaxV();
        final double d4 = 0.0D;
        final double d5 = 0.05D;

        if (var17 == 3)
        {
            tessellator.addVertexWithUV(par2 + d5, par3 + 1 + d4, par4 + 1 + d4, d0, d1);
            tessellator.addVertexWithUV(par2 + d5, par3 + 0 - d4, par4 + 1 + d4, d0, d3);
            tessellator.addVertexWithUV(par2 + d5, par3 + 0 - d4, par4 + 0 - d4, d2, d3);
            tessellator.addVertexWithUV(par2 + d5, par3 + 1 + d4, par4 + 0 - d4, d2, d1);
        }

        if (var17 == 2)
        {
            tessellator.addVertexWithUV(par2 + 1 - d5, par3 + 0 - d4, par4 + 1 + d4, d2, d3);
            tessellator.addVertexWithUV(par2 + 1 - d5, par3 + 1 + d4, par4 + 1 + d4, d2, d1);
            tessellator.addVertexWithUV(par2 + 1 - d5, par3 + 1 + d4, par4 + 0 - d4, d0, d1);
            tessellator.addVertexWithUV(par2 + 1 - d5, par3 + 0 - d4, par4 + 0 - d4, d0, d3);
        }

        if (var17 == 1)
        {
            tessellator.addVertexWithUV(par2 + 1 + d4, par3 + 0 - d4, par4 + d5, d2, d3);
            tessellator.addVertexWithUV(par2 + 1 + d4, par3 + 1 + d4, par4 + d5, d2, d1);
            tessellator.addVertexWithUV(par2 + 0 - d4, par3 + 1 + d4, par4 + d5, d0, d1);
            tessellator.addVertexWithUV(par2 + 0 - d4, par3 + 0 - d4, par4 + d5, d0, d3);
        }

        if (var17 == 0)
        {
            tessellator.addVertexWithUV(par2 + 1 + d4, par3 + 1 + d4, par4 + 1 - d5, d0, d1);
            tessellator.addVertexWithUV(par2 + 1 + d4, par3 + 0 - d4, par4 + 1 - d5, d0, d3);
            tessellator.addVertexWithUV(par2 + 0 - d4, par3 + 0 - d4, par4 + 1 - d5, d2, d3);
            tessellator.addVertexWithUV(par2 + 0 - d4, par3 + 1 + d4, par4 + 1 - d5, d2, d1);
        }

        return true;
    }

    @Override
    public boolean shouldRender3DInInventory()
    {
        return false;
    }

}
