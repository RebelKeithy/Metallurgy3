package rebelkeithy.mods.metallurgy.machines.mint;

import java.util.Map;

import net.minecraft.item.ItemStack;

import com.google.common.collect.Maps;

public class MintRecipes
{
    private static final MintRecipes smeltingBase = new MintRecipes();

    /** The list of smelting results. */
    private static Map<Integer, Integer> metaMintingList = Maps.newHashMap();
    private static Map<Integer, String> mintingImage = Maps.newHashMap();

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
    public static void addMinting(int itemID, int amount, String image)
    {
        metaMintingList.put(itemID, amount);
        mintingImage.put(itemID, image);
    }

    /**
     * Used to call methods addSmelting and getSmeltingResult.
     */
    public static final MintRecipes minting()
    {
        return smeltingBase;
    }

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
    public void addMinting(int itemID, int metadata, int amount)
    {
        metaMintingList.put(itemID, amount);
    }

    public String getImage(int ingotID)
    {
        if (mintingImage.containsKey(ingotID))
        {
            return mintingImage.get(ingotID);
        }
        return "";
    }

    /**
     * Used to get the resulting ItemStack form a source ItemStack
     * 
     * @param item
     *            The Source ItemStack
     * @return The result ItemStack
     */
    public int getMintingResult(ItemStack item)
    {
        if (item == null)
        {
            return 0;
        }
        final Integer ret = metaMintingList.get(item.itemID);
        if (ret == null)
        {
            return 0;
        }
        return ret;
    }
}
