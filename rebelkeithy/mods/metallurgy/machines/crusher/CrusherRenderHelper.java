package rebelkeithy.mods.metallurgy.machines.crusher;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.world.IBlockAccess;

import rebelkeithy.mods.metallurgy.machines.MetallurgyMachines;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class CrusherRenderHelper implements ISimpleBlockRenderingHandler
{	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		// TODO Auto-generated method stub
		TileEntityCrusher tec = new TileEntityCrusher();
		tec.setType(metadata);
		TileEntityRenderer.instance.renderTileEntityAt(tec, 0.0D, 0.0D, 0.0D, 0.0F);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int getRenderId() {
		// TODO Auto-generated method stub
		return MetallurgyMachines.instance.crusher.getRenderType();
	}
}
