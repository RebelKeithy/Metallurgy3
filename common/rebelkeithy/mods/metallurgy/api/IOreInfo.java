package rebelkeithy.mods.metallurgy.api;

import net.minecraft.item.ItemStack;

public interface IOreInfo 
{
	public String getName();
	
	public ItemStack getOre();
	public ItemStack getBlock();
	public ItemStack getBrick();
	public ItemStack getDust();
	public ItemStack getIngot();
	
}
