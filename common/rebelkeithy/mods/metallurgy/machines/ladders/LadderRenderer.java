package rebelkeithy.mods.metallurgy.machines.ladders;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class LadderRenderer implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int par2, int par3, int par4, Block par1Block, int modelId, RenderBlocks renderer) {

		Tessellator tessellator = Tessellator.instance;
		int var17 = world.getBlockMetadata(par2, par3, par4);
		Icon icon = par1Block.getBlockTextureFromSideAndMetadata(0, var17);
		var17 = var17 % 4;
		
		tessellator.setBrightness(par1Block.getMixedBrightnessForBlock(world, par2, par3, par4));
		float var7 = 1.0F;
		tessellator.setColorOpaque_F(var7, var7, var7);
		double d0 = icon.func_94209_e();
		double d1 = icon.func_94206_g();
		double d2 = icon.func_94212_f();
		double d3 = icon.func_94210_h();
		double d4 = 0.0D;
		double d5 = 0.05D;

		if (var17 == 3) {
            tessellator.addVertexWithUV((double)par2 + d5, (double)(par3 + 1) + d4, (double)(par4 + 1) + d4, d0, d1);
            tessellator.addVertexWithUV((double)par2 + d5, (double)(par3 + 0) - d4, (double)(par4 + 1) + d4, d0, d3);
            tessellator.addVertexWithUV((double)par2 + d5, (double)(par3 + 0) - d4, (double)(par4 + 0) - d4, d2, d3);
            tessellator.addVertexWithUV((double)par2 + d5, (double)(par3 + 1) + d4, (double)(par4 + 0) - d4, d2, d1);
		}

		if (var17 == 2) {
            tessellator.addVertexWithUV((double)(par2 + 1) - d5, (double)(par3 + 0) - d4, (double)(par4 + 1) + d4, d2, d3);
            tessellator.addVertexWithUV((double)(par2 + 1) - d5, (double)(par3 + 1) + d4, (double)(par4 + 1) + d4, d2, d1);
            tessellator.addVertexWithUV((double)(par2 + 1) - d5, (double)(par3 + 1) + d4, (double)(par4 + 0) - d4, d0, d1);
            tessellator.addVertexWithUV((double)(par2 + 1) - d5, (double)(par3 + 0) - d4, (double)(par4 + 0) - d4, d0, d3);
		}

		if (var17 == 1) {
            tessellator.addVertexWithUV((double)(par2 + 1) + d4, (double)(par3 + 0) - d4, (double)par4 + d5, d2, d3);
            tessellator.addVertexWithUV((double)(par2 + 1) + d4, (double)(par3 + 1) + d4, (double)par4 + d5, d2, d1);
            tessellator.addVertexWithUV((double)(par2 + 0) - d4, (double)(par3 + 1) + d4, (double)par4 + d5, d0, d1);
            tessellator.addVertexWithUV((double)(par2 + 0) - d4, (double)(par3 + 0) - d4, (double)par4 + d5, d0, d3);
		}

		if (var17 == 0) {
            tessellator.addVertexWithUV((double)(par2 + 1) + d4, (double)(par3 + 1) + d4, (double)(par4 + 1) - d5, d0, d1);
            tessellator.addVertexWithUV((double)(par2 + 1) + d4, (double)(par3 + 0) - d4, (double)(par4 + 1) - d5, d0, d3);
            tessellator.addVertexWithUV((double)(par2 + 0) - d4, (double)(par3 + 0) - d4, (double)(par4 + 1) - d5, d2, d3);
            tessellator.addVertexWithUV((double)(par2 + 0) - d4, (double)(par3 + 1) + d4, (double)(par4 + 1) - d5, d2, d1);
		}

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() 
	{
		return false;
	}

	@Override
	public int getRenderId() 
	{
		return BlockMetalLadder.renderType;
	}

}
