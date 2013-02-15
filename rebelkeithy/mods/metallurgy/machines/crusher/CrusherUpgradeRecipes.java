package rebelkeithy.mods.metallurgy.machines.crusher;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.src.ModLoader;

import rebelkeithy.mods.metallurgy.machines.MetallurgyMachines;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class CrusherUpgradeRecipes {

	public static void addRecipes()
	{

		GameRegistry.addRecipe(new ItemStack(MetallurgyMachines.instance.crusher, 1, 0),
			"XSX", "SFS", "XSX", 'X', Block.cobblestone, 'S', Item.stick, 'F', Block.stoneOvenIdle);
	    
		ShapedOreRecipe copperRecipe = new ShapedOreRecipe(new ItemStack(MetallurgyMachines.instance.crusher, 1, 1),
				"XXX", "XFX", "XXX", 'X', "ingotCopper", 'F', new ItemStack(MetallurgyMachines.instance.crusher, 1, 0));
		GameRegistry.addRecipe(copperRecipe);
		
		ShapedOreRecipe bronzeRecipe = new ShapedOreRecipe(new ItemStack(MetallurgyMachines.instance.crusher, 1, 2),
				"XXX", "XFX", "XXX", 'X', "ingotBronze", 'F', new ItemStack(MetallurgyMachines.instance.crusher, 1, 1));
		GameRegistry.addRecipe(bronzeRecipe);
		
		GameRegistry.addRecipe(new ItemStack(MetallurgyMachines.instance.crusher, 1, 3),
				"XXX", "XFX", "XXX", 'X', Item.ingotIron, 'F', new ItemStack(MetallurgyMachines.instance.crusher, 1, 2));
		
		ShapedOreRecipe steelRecipe = new ShapedOreRecipe(new ItemStack(MetallurgyMachines.instance.crusher, 1, 4),
				"XXX", "XFX", "XXX", 'X', "ingotSteel", 'F', new ItemStack(MetallurgyMachines.instance.crusher, 1, 3));
		GameRegistry.addRecipe(steelRecipe);
	}
	
    @ForgeSubscribe
    public void oreRegistered(OreRegisterEvent event)
    {    

    	if(event.Name.equals("ingotCopper"))
    	{
			ModLoader.addRecipe(new ItemStack(MetallurgyMachines.instance.crusher, 1, 1), new Object[] {
				"XXX", "XFX", "XXX", Character.valueOf('X'), event.Ore, Character.valueOf('F'), new ItemStack(MetallurgyMachines.instance.crusher, 1, 0)
			});
    	}

    	if(event.Name.equals("ingotBronze"))
    	{
			ModLoader.addRecipe(new ItemStack(MetallurgyMachines.instance.crusher, 1, 2), new Object[] {
				"XXX", "XFX", "XXX", Character.valueOf('X'), event.Ore, Character.valueOf('F'), new ItemStack(MetallurgyMachines.instance.crusher, 1, 1)
			});
    	}

    	if(event.Name.equals("ingotSteel"))
    	{
			ModLoader.addRecipe(new ItemStack(MetallurgyMachines.instance.crusher, 1, 4), new Object[] {
				"XXX", "XFX", "XXX", Character.valueOf('X'), event.Ore, Character.valueOf('F'), new ItemStack(MetallurgyMachines.instance.crusher, 1, 3)
			});
    	}
	}
}
