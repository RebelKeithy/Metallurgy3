package rebelkeithy.mods.metallurgy.machines.pylon;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import java.util.HashMap;

public class PylonRenderHelper implements ISimpleBlockRenderingHandler
{

    private static HashMap<Integer, TileEntityPylon> tileEntity;

    @Override
    public int getRenderId()
    {
        return Pylon.pylon.getRenderType();
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        if(tileEntity == null)
        {
            tileEntity = new HashMap<Integer, TileEntityPylon>();
        }

        TileEntityPylon pylon = tileEntity.get(metadata);

        if(pylon == null)
        {
            pylon = new TileEntityPylon();
            pylon.setType(metadata);
            tileEntity.put(metadata, pylon);
        }

        TileEntityRenderer.instance.renderTileEntityAt(pylon, 0.0D, 0.0D, 0.0D, 0.0F);
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
