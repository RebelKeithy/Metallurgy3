package rebelkeithy.mods.metallurgy.machines.abstractor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.item.ItemStack;

public class AbstractorRecipes
{
    private static final AbstractorRecipes smeltingBase = new AbstractorRecipes();

    /** The list of smelting results. */
    private static Map<Integer, Integer> smeltingList = Maps.newHashMap();
    private static Map metaSmeltingList = new HashMap();
    private static Map fuelList = new HashMap();

    /**
     * Add a metadata-sensitive furnace recipe
     * 
     * @param itemID
     *            The Item ID
     * @param metadata
     *            The Item Metadata
     * @param itemstack
     *            The ItemStack for the result
     */
    public static void addEssence(int itemID, int metadata, int amount)
    {
        metaSmeltingList.put(Arrays.asList(itemID, metadata), amount);
    }

    public static void addFuel(int itemID, int metadata, int amount)
    {
        fuelList.put(Arrays.asList(itemID, metadata), amount);
    }

    /**
     * Used to call methods addSmelting and getSmeltingResult.
     */
    public static final AbstractorRecipes essence()
    {
        return smeltingBase;
    }

    public static int getFuelAmount(ItemStack itemStack)
    {
        if (fuelList.containsKey(Arrays.asList(itemStack.itemID, itemStack.getItemDamage())))
        {
            return (Integer) fuelList.get(Arrays.asList(itemStack.itemID, itemStack.getItemDamage()));
        }
        else
        {
            return 0;
        }
    }

    private AbstractorRecipes()
    {
    }

    /**
     * Adds a smelting recipe.
     */
    public void addEssenceAmount(int itemID, int amount)
    {
        smeltingList.put(Integer.valueOf(itemID), amount);
    }

    public Map getEssenceList()
    {
        return smeltingList;
    }

    /**
     * Used to get the resulting ItemStack form a source ItemStack
     * 
     * @param item
     *            The Source ItemStack
     * @return The result ItemStack
     */
    public int getEssenceResult(ItemStack item)
    {
        if (item == null)
        {
            return 0;
        }
        Integer ret = (Integer) metaSmeltingList.get(Arrays.asList(item.itemID, item.getItemDamage()));
        if (ret != null)
        {
            return ret;
        }
        ret = (Integer) smeltingList.get(Integer.valueOf(item.itemID));
        if (ret != null)
        {
            return ret;
        }

        return 0;
    }

    /**
     * Returns the smelting result of an item. Deprecated in favor of a metadata
     * sensitive version
     */
    @Deprecated
    public int getEssenceResuly(int par1)
    {
        return (Integer) smeltingList.get(Integer.valueOf(par1));
    }
}