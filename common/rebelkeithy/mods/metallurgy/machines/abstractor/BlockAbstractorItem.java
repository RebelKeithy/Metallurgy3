package rebelkeithy.mods.metallurgy.machines.abstractor;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class BlockAbstractorItem extends ItemBlock
{
    public BlockAbstractorItem(int i)
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
            name = "PrometheumFurnace";
            break;
        }
        case 1:
        {
            name = "DeepIronFurnace";
            break;
        }
        case 2:
        {
            name = "BlackSteelFurnace";
            break;
        }
        case 3:
        {
            name = "OureclaseFurnace";
            break;
        }
        case 4:
        {
            name = "MithrilFurnace";
            break;
        }
        case 5:
        {
            name = "HaderothFurnace";
            break;
        }
        case 6:
        {
            name = "OrichalcumFurnace";
            break;
        }
        case 7:
        {
            name = "AdamantineFurnace";
            break;
        }
        case 8:
        {
            name = "AtlarusFurnace";
            break;
        }
        case 9:
        {
            name = "TartariteFurnace";
            break;
        }
        default:
            name = "brick";
        }
        return getUnlocalizedName() + "." + name;
    }
}