package rebelkeithy.mods.metallurgy.core.metalsets;

import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IArmorTextureProvider;

public class ItemMetallurgyArmor extends ItemArmor implements IArmorTextureProvider{

	public String textureFile;

	public ItemMetallurgyArmor(int par1, EnumArmorMaterial par2EnumArmorMaterial, int par3, int par4) {
		super(par1, par2EnumArmorMaterial, par3, par4);
	}

	public Item setTextureFile(String texture)
	{
		int loc = texture.indexOf(' ');
		while(loc!=-1)
		{
			texture = new StringBuilder(texture).deleteCharAt(loc).toString();
			loc = texture.indexOf(' ');
		}

		textureFile = texture.toLowerCase();
		return this;
	}

	@Override
	public String getArmorTextureFile(ItemStack itemstack)
	{
		return "/armor/" + textureFile + ".png";
	}

}
