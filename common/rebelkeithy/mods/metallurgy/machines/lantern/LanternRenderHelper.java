package rebelkeithy.mods.metallurgy.machines.lantern;

import rebelkeithy.mods.metallurgy.machines.MetallurgyMachines;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class LanternRenderHelper implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) 
	{
		TileEntityLantern tec = new TileEntityLantern(metadata);
		TileEntityRenderer.instance.renderTileEntityAt(tec, 0.0D, 0.0D, 0.0D, 0.0F);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) 
	{
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() 
	{
		return true;
	}

	@Override
	public int getRenderId() 
	{
		return MetallurgyMachines.lantern.getRenderType();
	}

}
