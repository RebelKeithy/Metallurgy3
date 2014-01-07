package rebelkeithy.mods.metallurgy.machines.chests;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.world.IBlockAccess;
import rebelkeithy.mods.metallurgy.machines.MetallurgyMachines;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import java.util.HashMap;

public class RenderHelperPreciousChest implements ISimpleBlockRenderingHandler
{
    private static HashMap<Integer, TileEntityPreciousChest> tileEntity;

    @Override
    public int getRenderId()
    {
        // TODO Auto-generated method stub
        return MetallurgyMachines.chest.getRenderType();
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        // TODO Auto-generated method stub

        if(tileEntity == null)
        {
            tileEntity = new HashMap<Integer, TileEntityPreciousChest>();
        }

        TileEntityPreciousChest chest = tileEntity.get(metadata);

        if(chest == null)
        {
            chest = new TileEntityPreciousChest();
            chest.setType(metadata);

            tileEntity.put(metadata, chest);
        }

        TileEntityRenderer.instance.renderTileEntityAt(chest, 0.0D, 0.0D, 0.0D, 0.0F);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory()
    {
        // TODO Auto-generated method stub
        return true;
    }
}
