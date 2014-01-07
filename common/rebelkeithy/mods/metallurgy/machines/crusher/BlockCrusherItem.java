package rebelkeithy.mods.metallurgy.machines.crusher;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class BlockCrusherItem extends ItemBlock
{
    public BlockCrusherItem(int i)
    {
        super(i);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int i)
    {
        return i;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
        String name = "";
        switch (itemstack.getItemDamage())
        {
        case 0:
        {
            name = "StoneCrusher";
            break;
        }
        case 1:
        {
            name = "CopperCrusher";
            break;
        }
        case 2:
        {
            name = "BronzeCrusher";
            break;
        }
        case 3:
        {
            name = "IronCrusher";
            break;
        }
        case 4:
        {
            name = "SteelCrusher";
            break;
        }
        default:
            name = "brick";
        }
        return getUnlocalizedName() + "." + name;
    }
}
