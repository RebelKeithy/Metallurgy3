package rebelkeithy.mods.metallurgy.machines.lantern;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockColoredGlass extends ItemBlock
{
    public ItemBlockColoredGlass(int i)
    {
        super(i);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int i)
    {
        return i;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @SideOnly(Side.CLIENT)
    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < 8; ++var4)
        {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
        String name = "";
        switch (itemstack.getItemDamage() + 1)
        {
        case 0:
        {
            name = "glass";
            break;
        }
        case 1:
        {
            name = "red";
            break;
        }
        case 2:
        {
            name = "green";
            break;
        }
        case 3:
        {
            name = "blue";
            break;
        }
        case 4:
        {
            name = "orange";
            break;
        }
        case 5:
        {
            name = "yellow";
            break;
        }
        case 6:
        {
            name = "purple";
            break;
        }
        case 7:
        {
            name = "grey";
            break;
        }
        case 8:
        {
            name = "white";
            break;
        }
        default:
            name = "error";
        }
        return getUnlocalizedName() + "." + name;
    }

}
