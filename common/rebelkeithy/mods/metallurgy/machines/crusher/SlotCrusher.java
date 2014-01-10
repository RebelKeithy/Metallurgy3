package rebelkeithy.mods.metallurgy.machines.crusher;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

//Referenced classes of package net.minecraft.src:
//                   Slot, EntityPlayer, ItemStack, Item, 
//                   AchievementList, ModLoader, IInventory

public class SlotCrusher extends Slot
{

    public SlotCrusher(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
    {
        super(iinventory, i, j, k);
    }

    @Override
    public boolean isItemValid(ItemStack itemstack)
    {
        return false;
    }
}