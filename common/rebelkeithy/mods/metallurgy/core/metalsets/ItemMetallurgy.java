package rebelkeithy.mods.metallurgy.core.metalsets;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemMetallurgy extends Item
{
    private float xp;

    public ItemMetallurgy(int par1)
    {
        super(par1);
        xp = -1;
    }

    @Override
    public float getSmeltingExperience(ItemStack item)
    {
        return xp; // -1 will default to the old lookups.
    }

    public ItemMetallurgy setSmeltinExperience(float xp)
    {
        this.xp = xp;
        return this;
    }

    @Override
    public ItemMetallurgy setTextureName(String par1Str)
    {
        super.setTextureName(par1Str);
        return this;
    }

}
