package rebelkeithy.mods.metallurgy.metals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class ItemOreFinder extends Item
{

	public ItemOreFinder(int par1) {
		super(par1);
		// TODO Auto-generated constructor stub
	}
	
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
    	if(par3World.isRemote)
    		return false;
    	Map<String, Integer> oreCount = new HashMap<String, Integer>();
    	System.out.println("Checking");

    	//System.out.println(OreDictionary.getOres(OreDictionary.getOreID("orePlatinum")).get(0).itemID);
    	
    	for(int y = 0; y < 128; y++)
    	{
    		for(int x = par4 - par4%16; x < par4 - par4%16 + 16; x++)
    		{
    			for(int z = par6 - par6%16; z < par6 - par6%16 + 16; z++)
    			{
    				int id = par3World.getBlockId(x, y, z);
    				int meta = par3World.getBlockMetadata(x, y, z);
    				ItemStack stack = new ItemStack(id, 1, meta);
    				String name = null;
    				int oreID = OreDictionary.getOreID(stack);
    				if(oreID != -1)
    				{
    					name = OreDictionary.getOreName(oreID);
    				}
    				if(id == Block.oreIron.blockID) {
    					name = "oreIron";
    				} else if(id == Block.oreGold.blockID) {
    					name = "oreGold";
    				} else if(id == Block.oreDiamond.blockID) {
    					name = "oreDiamond";
    				}

    				if(id == MetallurgyMetals.preciousSet.getOreInfo("Platinum").oreID)
    				{
    					//if(meta == MetallurgyMetals.preciousSet.getOreInfo("Platinum").oreMeta)
    					{
    						System.out.println("found platinum!! " + stack.itemID + ":" + meta);
    						System.out.println(MetallurgyMetals.preciousSet.getOreInfo("Platinum").oreID);
    					}
    				}
    				if(name != null)
    				{
    					if(oreCount.containsKey(name))
    					{
    						int count = oreCount.get(name);
    						oreCount.put(name,  count + 1);
    					} else {
    						oreCount.put(name,  1);
    					}
    				}
    				
    			}
    		}
    	}
    	
    	Set<String> names = oreCount.keySet();
    	String[] sort = names.toArray(new String[names.size()]);
    	Arrays.sort(sort);
    	par2EntityPlayer.sendChatToPlayer("In Area (" + (par4 - par4%16) + ", " + (par6 - par6%16) + ") to (" + (par4 - par4%16 + 16) + ", " + (par6 - par6%16 + 16) + ")");
    	for(String name : names)
    	{
    		int amount = oreCount.get(name);
    		par2EntityPlayer.sendChatToPlayer("Found " + amount + " " + name);
    	}
    			
        return false;
    }

}
