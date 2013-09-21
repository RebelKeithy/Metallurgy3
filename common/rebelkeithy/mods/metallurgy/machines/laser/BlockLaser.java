package rebelkeithy.mods.metallurgy.machines.laser;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLaser extends BlockContainer
{
    public int renderId = RenderingRegistry.getNextAvailableRenderId();

    protected BlockLaser(int par1)
    {
        super(par1, Material.rock);
        setBlockBounds(5 / 16F, 5 / 16F, 5 / 16F, 11 / 16F, 11 / 16F, 11 / 16F);
        // this.maxY += 2;

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileEntityLaser(0);
    }

    @Override
    public TileEntity createTileEntity(World par1World, int metadata)
    {
        return createNewTileEntity(par1World);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }

    @SideOnly(Side.CLIENT)
    /**
     * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
     */
    @Override
    public int getRenderBlockPass()
    {
        return 1;
    }

    @Override
    public int getRenderType()
    {
        return renderId;
    }

    @Override
    @SideOnly(Side.CLIENT)
    /**
     * Returns the bounding box of the wired rectangular prism to render.
     */
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
        // return AxisAlignedBB.getAABBPool().getAABB((double)par2 + 5/16F,
        // (double)par3 + 5/16F, (double)par4 + 5/16F, (double)par2 + 11/16F,
        // (double)par3 + 11/16F, (double)par4 + 11/16F);
    }

    @ForgeSubscribe
    public boolean interactEvent(PlayerInteractEvent event)
    {
        if (event.action == Action.RIGHT_CLICK_BLOCK)
        {
            final EntityPlayer player = event.entityPlayer;

            final TileEntity te = player.worldObj.getBlockTileEntity(event.x, event.y, event.z);
            if (te instanceof TileEntityLaser)
            {
                ((TileEntityLaser) te).changeLength(event.face, player.isSneaking());
                if (!player.worldObj.isRemote)
                {
                    player.addChatMessage("Length: " + ((TileEntityLaser) te).getLength(event.face));
                    // event.setCanceled(true);
                }
            }
        }

        return true;
    }

    @Override
    public boolean isCollidable()
    {
        return true;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        return true;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

}
