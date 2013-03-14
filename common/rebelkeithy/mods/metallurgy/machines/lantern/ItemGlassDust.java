package rebelkeithy.mods.metallurgy.machines.lantern;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemGlassDust extends Item 
{
	private Map<Integer, Icon> iconMap;
	private static String[] names = {"Glass", "Red", "Green", "Blue", "Orange", "Yellow", "Purple", "Grey", "White"};
	
	public ItemGlassDust(int i) 
	{
		super(i);
		setHasSubtypes(true);
        this.setMaxDamage(0);
	}

	@Override
	public int getMetadata(int i) {
		return i;
	}
	
    /**
     * Gets an icon index based on an item's damage value
     */
	@Override
    public Icon getIconFromDamage(int par1)
    {
        return iconMap.get(par1);
    }

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		String name = "";
		if(itemstack.getItemDamage() < 9)
		 return getUnlocalizedName() + "." + names[itemstack.getItemDamage()];
		
		return getUnlocalizedName();
	}

	@Override
    @SideOnly(Side.CLIENT)
    public void func_94581_a(IconRegister par1IconRegister)
    {
		iconMap = new HashMap<Integer, Icon>();
		iconMap.put(0, par1IconRegister.func_94245_a("Metallurgy:machines/lantern/GlassDust"));
    	for(int i = 1; i < 9; i++)
    	{
    		Icon icon = par1IconRegister.func_94245_a("Metallurgy:machines/lantern/GlassDust" + names[i]);
    		iconMap.put(i, icon);
    	}
    }
    
    @SideOnly(Side.CLIENT)

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < 9; ++var4)
        {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }
}
