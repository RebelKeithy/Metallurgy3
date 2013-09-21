package rebelkeithy.mods.metallurgy.machines.storage;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import rebelkeithy.mods.metallurgy.machines.MetallurgyMachines;
import rebelkeithy.mods.metallurgy.machines.PacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class BlockStorageAccessor extends BlockContainer
{

    public BlockStorageAccessor(int par1)
    {
        super(par1, Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World var1)
    {
        return new TileEntityStorageAccessor();
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (world.isRemote)
        {
            return true;
        }
        else
        {
            final TileEntityStorageAccessor var10 = (TileEntityStorageAccessor) world.getBlockTileEntity(x, y, z);

            if (!var10.worldObj.isRemote)
            {
                if (var10.inventories.size() > 0)
                {
                    final int id = var10.inventories.keySet().iterator().next();
                    final int size = var10.getInventory(id).items.length;
                    PacketDispatcher.sendPacketToPlayer(PacketHandler.getSetInvSizePacket(id, size, x, y, z), (Player) par5EntityPlayer);
                    PacketDispatcher.sendPacketToPlayer(PacketHandler.getTabListPacket(world, x, y, z), (Player) par5EntityPlayer);
                }
            }

            if (var10 != null)
            {
                if (var10.inventories.size() > 0)
                {
                    var10.inventories.keySet().iterator().next();
                }
                par5EntityPlayer.openGui(MetallurgyMachines.instance, 0, world, x, y, z);
            }

            return true;
        }
    }
}
