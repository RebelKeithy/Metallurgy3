package rebelkeithy.mods.metallurgy.machines.ladders;

import java.util.HashMap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemBlockMetalLadder extends ItemBlock {

	private Icon[] icons;
	
	public ItemBlockMetalLadder(int par1) {
		super(par1);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int metadata) {
		return metadata * 4;
	}
	
	@Override
    public Icon getIconFromDamage(int par1)
    {
    	return icons[par1];
    }

	@Override
    @SideOnly(Side.CLIENT)
    public void updateIcons(IconRegister par1IconRegister)
    {
		icons = new Icon[4];
    	for(int i = 0; i < 4; i++)
    	{
    		icons[i] = par1IconRegister.registerIcon("Metallurgy:machines/ladder/Ladder_" + i);
    	}
    }

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		String name = "";
		switch (itemstack.getItemDamage()) {
		case 0: {
			name = "Copper";
			break;
		}
		case 1: {
			name = "Bronze";
			break;
		}
		case 2: {
			name = "Iron";
			break;
		}
		case 3: {
			name = "Steel";
			break;
		}
		default:
			name = "Copper";
		}
		return getUnlocalizedName() + "." + name;
	}
}
