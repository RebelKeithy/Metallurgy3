package rebelkeithy.mods.metallurgy.machines.storage;

import net.minecraft.item.ItemStack;

public class StorageTabs
{
    ItemStack icon;

    public StorageTabs(ItemStack itemStack)
    {
        icon = itemStack.copy();
    }

    public ItemStack getIconItemStack()
    {
        return icon.copy();
    }

}
