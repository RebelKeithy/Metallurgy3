package rebelkeithy.mods.metallurgy.machines.chests;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockPreciousChest extends ItemBlock
{
    public ItemBlockPreciousChest(int i)
    {
        super(i);
        setHasSubtypes(true);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        switch (par1ItemStack.getItemDamage())
        {
        case 0:
        {
            par3List.add("6x9");
            break;
        }
        case 1:
        {
            par3List.add("8x9");
            break;
        }
        case 2:
        {
            par3List.add("9x9");
            break;
        }
        case 3:
        {
            par3List.add("9x10");
            break;
        }
        case 4:
        {
            par3List.add("9x12");
            break;
        }
        }
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
            name = "BrassChest";
            break;
        }
        case 1:
        {
            name = "SilverChest";
            break;
        }
        case 2:
        {
            name = "GoldChest";
            break;
        }
        case 3:
        {
            name = "ElectrumChest";
            break;
        }
        case 4:
        {
            name = "PlatinumChest";
            break;
        }
        default:
            name = "chest";
        }
        return getUnlocalizedName() + "." + name;
    }
}
