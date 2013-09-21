package rebelkeithy.mods.metallurgy.machines.lantern;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import rebelkeithy.mods.metallurgy.machines.MetallurgyMachines;

public class ItemBlockLantern extends ItemBlock
{
    public ItemBlockLantern(int i)
    {
        super(i);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int i)
    {
        return i;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
        String name = "";
        switch (itemstack.getItemDamage())
        {
        case 0:
        {
            name = "red";
            break;
        }
        case 1:
        {
            name = "green";
            break;
        }
        case 2:
        {
            name = "blue";
            break;
        }
        case 3:
        {
            name = "orange";
            break;
        }
        case 4:
        {
            name = "yellow";
            break;
        }
        case 5:
        {
            name = "purple";
            break;
        }
        case 6:
        {
            name = "grey";
            break;
        }
        case 7:
        {
            name = "white";
            break;
        }
        default:
            name = "red";
        }
        return getUnlocalizedName() + "." + name;
    }

    /**
     * Called to actually place the block, after the location is determined and
     * all permission checks have been made.
     * 
     * @param stack
     *            The item stack that was used to place the block. This can be
     *            changed inside the method.
     * @param player
     *            The player who is placing the block. Can be null if the block
     *            is not being placed by a player.
     * @param side
     *            The side the player (or machine) right-clicked on.
     */
    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
    {
        if (!world.setBlock(x, y, z, MetallurgyMachines.lantern.blockID, getMetadata(stack.getItemDamage()), 2))
        {
            return false;
        }

        if (world.getBlockId(x, y, z) == MetallurgyMachines.lantern.blockID)
        {
            // Block.blocksList[MetallurgyBaseMetals.lantern.blockID].func_85104_a(par1World,
            // par2, par3, par4, par5, par6, par7, par8, par9)
            Block.blocksList[MetallurgyMachines.lantern.blockID].onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, metadata);
            Block.blocksList[MetallurgyMachines.lantern.blockID].onBlockPlacedBy(world, x, y, z, player, stack);
            final TileEntity le = new TileEntityLantern(stack.getItemDamage());
            world.setBlockTileEntity(x, y, z, le);
        }

        return true;
    }

}
