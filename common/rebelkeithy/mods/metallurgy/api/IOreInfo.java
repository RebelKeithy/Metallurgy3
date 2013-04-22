package rebelkeithy.mods.metallurgy.api;

import net.minecraft.item.ItemStack;

public interface IOreInfo 
{
	public String getName();
	public OreType getType();
	
	public ItemStack getOre();
	public ItemStack getBlock();
	public ItemStack getBrick();
	public ItemStack getDust();
	public ItemStack getIngot();
	
	// Returns an array of OreDictionary keys of the dusts the make this if it's an alloy
	// if it's not an alloy, this returns null
	public String[] getAlloyRecipe();
}
