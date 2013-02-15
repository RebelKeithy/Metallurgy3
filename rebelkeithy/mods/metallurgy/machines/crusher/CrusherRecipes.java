package rebelkeithy.mods.metallurgy.machines.crusher;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrusherRecipes
{
    private static final CrusherRecipes smeltingBase = new CrusherRecipes();

    /** The list of smelting results. */
    private static Map smeltingList = new HashMap();
    private static Map metaSmeltingList = new HashMap();

    /**
     * Used to call methods addSmelting and getSmeltingResult.
     */
    public static final CrusherRecipes smelting()
    {
        return smeltingBase;
    }

    private CrusherRecipes()
    {
    }

    /**
     * Adds a smelting recipe.
     */
    public void addCrushing(int par1, ItemStack par2ItemStack)
    {
        this.smeltingList.put(Integer.valueOf(par1), par2ItemStack);
    }

    /**
     * Returns the smelting result of an item.
     * Deprecated in favor of a metadata sensitive version
     */
    @Deprecated
    public ItemStack getCrushingResult(int par1)
    {
        return (ItemStack)this.smeltingList.get(Integer.valueOf(par1));
    }

    public Map getCrushingList()
    {
        return this.smeltingList;
    }
    
    /**
     * Add a metadata-sensitive furnace recipe
     * @param itemID The Item ID
     * @param metadata The Item Metadata
     * @param itemstack The ItemStack for the result
     */
    public static void addCrushing(int itemID, int metadata, ItemStack itemstack)
    {
        metaSmeltingList.put(Arrays.asList(itemID, metadata), itemstack);
    }
    
    /**
     * Used to get the resulting ItemStack form a source ItemStack
     * @param item The Source ItemStack
     * @return The result ItemStack
     */
    public ItemStack getCrushingResult(ItemStack item) 
    {
        if (item == null)
        {
            return null;
        }
        
        ItemStack ret = (ItemStack)metaSmeltingList.get(Arrays.asList(item.itemID, item.getItemDamage()));
        
        if (ret != null) 
        {
            return ret;
        }
		
		ret = (ItemStack)smeltingList.get(Integer.valueOf(item.itemID));
		if(ret != null)
		{
			return ret;
		}
		

		for(String name : OreDictionary.getOreNames())
		{
			for(ItemStack oreItem : OreDictionary.getOres(name))
			{
				if(oreItem.itemID == item.itemID && oreItem.getItemDamage() == item.getItemDamage())
				{
					String replacement = "";
					replacement = name.contains("ore") ? "ore" : replacement;
					replacement = name.contains("ingot") ? "ingot" : replacement;
					replacement = name.contains("item") ? "item" : replacement;
					if(name.contains("dust"))
						return null;
					name = name.replace(replacement, "dust");

					//ret = MetallurgyItems.getItem(name);
					if(ret != null)
					{
						if(replacement.equals("ore"))
							ret.stackSize = 2;
						return ret;
					}
					
					List<ItemStack> retList = OreDictionary.getOres(name);
					if(retList.size() > 0)
					{
						ret = retList.get(0).copy();
						if(replacement.equals("ore"))
							ret.stackSize = 2;
						return ret;
					}
				}
			}
		}
		
		return null;
    }
}
