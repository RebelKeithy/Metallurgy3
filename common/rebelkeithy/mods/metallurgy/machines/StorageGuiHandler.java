package rebelkeithy.mods.metallurgy.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import rebelkeithy.mods.metallurgy.machines.forge.ContainerNetherForge;
import rebelkeithy.mods.metallurgy.machines.forge.GuiNetherForge;
import rebelkeithy.mods.metallurgy.machines.forge.TileEntityNetherForge;
import rebelkeithy.mods.metallurgy.machines.storage.ContainerStorage;
import rebelkeithy.mods.metallurgy.machines.storage.GuiStorage;
import rebelkeithy.mods.metallurgy.machines.storage.TileEntityStorageAccessor;
import cpw.mods.fml.common.network.IGuiHandler;

public class StorageGuiHandler implements IGuiHandler
{
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        final TileEntity te = world.getBlockTileEntity(x, y, z);
        if (te != null && te instanceof TileEntityNetherForge)
        {
            return new GuiNetherForge(player.inventory, te);
        }

        if (te instanceof TileEntityStorageAccessor && ID == 0)
        {
            final TileEntityStorageAccessor tea = (TileEntityStorageAccessor) te;
            return new GuiStorage(new ContainerStorage(player.inventory, tea.getInventory(ID)), tea, ID);
        }
        else
        {
            return null;
        }

        // return null;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        final TileEntity te = world.getBlockTileEntity(x, y, z);
        if (te != null && te instanceof TileEntityNetherForge)
        {
            final TileEntityNetherForge tef = (TileEntityNetherForge) te;
            return new ContainerNetherForge(player.inventory, tef);
        }

        if (te instanceof TileEntityStorageAccessor && ID == 0)
        {
            final TileEntityStorageAccessor tea = (TileEntityStorageAccessor) te;
            return new ContainerStorage(player.inventory, tea.getInventory(ID));
        }
        else
        {
            return null;
        }
    }
}
