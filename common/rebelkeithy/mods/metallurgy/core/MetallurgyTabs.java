package rebelkeithy.mods.metallurgy.core;

import net.minecraft.creativetab.CreativeTabs;

public class MetallurgyTabs extends CreativeTabs
{
	int iconID;

	public MetallurgyTabs(String label) 
	{
		super(label);
	}
	
	public void setIconItem(int id)
	{
		iconID = id;
	}
	
	@Override
    public int getTabIconItemIndex()
    {
        return iconID;
    }

}
