package rebelkeithy.mods.metallurgy.core.metalsets;

import net.minecraft.entity.Entity;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemMetallurgyArmor extends ItemArmor
{

    public String textureFile;

    public ItemMetallurgyArmor(int par1, EnumArmorMaterial par2EnumArmorMaterial, int par3, int par4)
    {
        super(par1, par2EnumArmorMaterial, par3, par4);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer)
    {
        return "metallurgy:armor/" + textureFile + ".png";
    }

    public ItemMetallurgyArmor setTextureFile(String texture)
    {
        textureFile = texture;
        return this;
    }

    @Override
    public ItemMetallurgyArmor setTextureName(String par1Str)
    {
        super.setTextureName(par1Str);
        return this;
    }

}
