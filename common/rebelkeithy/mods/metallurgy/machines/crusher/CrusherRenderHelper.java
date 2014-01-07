package rebelkeithy.mods.metallurgy.machines.crusher;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import rebelkeithy.mods.metallurgy.machines.MetallurgyMachines;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import java.util.HashMap;

public class CrusherRenderHelper implements ISimpleBlockRenderingHandler
{
    private static HashMap<Integer, TileEntityCrusher> tileEntity;

    @Override
    public int getRenderId()
    {
        return MetallurgyMachines.crusher.getRenderType();
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {

        if(tileEntity == null)
        {
            tileEntity = new HashMap<Integer, TileEntityCrusher>();
        }

        TileEntityCrusher crusher = tileEntity.get(metadata);

        if(crusher == null)
        {
            crusher = new TileEntityCrusher();
            crusher.setType(metadata);
            tileEntity.put(metadata, crusher);
        }

        TileEntityRenderer.instance.renderTileEntityAt(crusher, 0D, -0.1D, 0D, 0F);
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
