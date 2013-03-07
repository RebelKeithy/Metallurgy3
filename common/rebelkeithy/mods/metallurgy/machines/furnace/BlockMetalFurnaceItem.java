package rebelkeithy.mods.metallurgy.machines.furnace;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class BlockMetalFurnaceItem extends ItemBlock {
	
	public BlockMetalFurnaceItem(int i) {
		super(i);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int i) {
		return i;
	}

	@Override
	public String getItemNameIS(ItemStack itemstack) {
		String name = "";
		switch (itemstack.getItemDamage()) {
		case 0: {
			name = "CopperFurnace";
			break;
		}
		case 1: {
			name = "BronzeFurnace";
			break;
		}
		case 2: {
			name = "IronFurnace";
			break;
		}
		case 3: {
			name = "SteelFurnace";
			break;
		}
		default:
			name = "brick";
		}
		return getItemName() + "." + name;
	}
}