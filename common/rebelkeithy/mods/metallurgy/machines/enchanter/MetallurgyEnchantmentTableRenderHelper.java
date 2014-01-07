package rebelkeithy.mods.metallurgy.machines.enchanter;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class MetallurgyEnchantmentTableRenderHelper implements ISimpleBlockRenderingHandler
{
    private static TileEntityMetallurgyEnchantmentTable tileEntity;

    @Override
    public int getRenderId()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        if(tileEntity == null)
        {
            tileEntity = new TileEntityMetallurgyEnchantmentTable();
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
        return false;
    }

}
