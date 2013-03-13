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
		System.out.println("rendering inventory");

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int par2, int par3, int par4, Block par1Block, int modelId, RenderBlocks renderer) {

		Tessellator var5 = Tessellator.instance;
		int var17 = world.getBlockMetadata(par2, par3, par4);
		Icon var6 = par1Block.getBlockTextureFromSideAndMetadata(0, var17);
		var17 = var17 % 4;

		var5.setBrightness(par1Block.getMixedBrightnessForBlock(world, par2, par3, par4));
		float var7 = 1.0F;
		var5.setColorOpaque_F(var7, var7, var7);
		//TODO fix this
		int var22 = 1;//(var6 & 15) << 4;
		int var8 = 1;//var6 & 240;
		double var9 = (double) ((float) var22 / 256.0F);
		double var11 = (double) (((float) var22 + 15.99F) / 256.0F);
		double var13 = (double) ((float) var8 / 256.0F);
		double var15 = (double) (((float) var8 + 15.99F) / 256.0F);
		double var18 = 0.0D;
		double var20 = 0.05000000074505806D;

		if (var17 == 3) {
			var5.addVertexWithUV((double) par2 + var20, (double) (par3 + 1)
					+ var18, (double) (par4 + 1) + var18, var9, var13);
			var5.addVertexWithUV((double) par2 + var20, (double) (par3 + 0)
					- var18, (double) (par4 + 1) + var18, var9, var15);
			var5.addVertexWithUV((double) par2 + var20, (double) (par3 + 0)
					- var18, (double) (par4 + 0) - var18, var11, var15);
			var5.addVertexWithUV((double) par2 + var20, (double) (par3 + 1)
					+ var18, (double) (par4 + 0) - var18, var11, var13);
		}

		if (var17 == 2) {
			var5.addVertexWithUV((double) (par2 + 1) - var20,
					(double) (par3 + 0) - var18, (double) (par4 + 1) + var18,
					var11, var15);
			var5.addVertexWithUV((double) (par2 + 1) - var20,
					(double) (par3 + 1) + var18, (double) (par4 + 1) + var18,
					var11, var13);
			var5.addVertexWithUV((double) (par2 + 1) - var20,
					(double) (par3 + 1) + var18, (double) (par4 + 0) - var18,
					var9, var13);
			var5.addVertexWithUV((double) (par2 + 1) - var20,
					(double) (par3 + 0) - var18, (double) (par4 + 0) - var18,
					var9, var15);
		}

		if (var17 == 1) {
			var5.addVertexWithUV((double) (par2 + 1) + var18,
					(double) (par3 + 0) - var18, (double) par4 + var20, var11,
					var15);
			var5.addVertexWithUV((double) (par2 + 1) + var18,
					(double) (par3 + 1) + var18, (double) par4 + var20, var11,
					var13);
			var5.addVertexWithUV((double) (par2 + 0) - var18,
					(double) (par3 + 1) + var18, (double) par4 + var20, var9,
					var13);
			var5.addVertexWithUV((double) (par2 + 0) - var18,
					(double) (par3 + 0) - var18, (double) par4 + var20, var9,
					var15);
		}

		if (var17 == 0) {
			var5.addVertexWithUV((double) (par2 + 1) + var18,
					(double) (par3 + 1) + var18, (double) (par4 + 1) - var20,
					var9, var13);
			var5.addVertexWithUV((double) (par2 + 1) + var18,
					(double) (par3 + 0) - var18, (double) (par4 + 1) - var20,
					var9, var15);
			var5.addVertexWithUV((double) (par2 + 0) - var18,
					(double) (par3 + 0) - var18, (double) (par4 + 1) - var20,
					var11, var15);
			var5.addVertexWithUV((double) (par2 + 0) - var18,
					(double) (par3 + 1) + var18, (double) (par4 + 1) - var20,
					var11, var13);
		}

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getRenderId() {
		// TODO Auto-generated method stub
		return BlockMetalLadder.renderType;
	}

}
