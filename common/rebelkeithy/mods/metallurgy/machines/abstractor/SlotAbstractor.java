package rebelkeithy.mods.metallurgy.machines.abstractor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotAbstractor extends Slot
{

    public SlotAbstractor(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
    {
        super(iinventory, i, j, k);
    }

    @Override
    public boolean isItemValid(ItemStack itemstack)
    {
        return false;
    }
}