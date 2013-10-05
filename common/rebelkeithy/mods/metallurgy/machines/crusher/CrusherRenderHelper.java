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
    public int getRenderId()
    {
        return MetallurgyMachines.crusher.getRenderType();
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        final TileEntityCrusher tec = new TileEntityCrusher();
        tec.setType(metadata);
        TileEntityRenderer.instance.renderTileEntityAt(tec, 1D, 0D, 0D, 0F);
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
}
