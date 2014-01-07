package rebelkeithy.mods.metallurgy.machines.mint;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.world.IBlockAccess;
import rebelkeithy.mods.metallurgy.machines.MetallurgyMachines;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class MintRenderHelper implements ISimpleBlockRenderingHandler
{
    private static TileEntityMint tileEntity;

    @Override
    public int getRenderId()
    {
        // TODO Auto-generated method stub
        return MetallurgyMachines.mint.getRenderType();
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        if(tileEntity == null)
        {
            tileEntity = new TileEntityMint();
        }

        TileEntityRenderer.instance.renderTileEntityAt(tileEntity, 0.0D, 0.0D, 0.0D, 0.0F);
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
