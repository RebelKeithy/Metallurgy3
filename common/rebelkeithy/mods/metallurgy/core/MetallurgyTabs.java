package rebelkeithy.mods.metallurgy.core;

import net.minecraft.creativetab.CreativeTabs;

public class MetallurgyTabs extends CreativeTabs
{
    int iconID = 1;

    public MetallurgyTabs(String label)
    {
        super(label);
    }

    @Override
    public int getTabIconItemIndex()
    {
        return iconID;
    }

    public void setIconItem(int id)
    {
        iconID = id;
    }

}
