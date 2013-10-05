package rebelkeithy.mods.metallurgy.metals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemOreFinder extends Item
{

    public ItemOreFinder(int par1)
    {
        super(par1);
        setMaxDamage(64);
        maxStackSize = 1;
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        int mode = 0;
        if (par1ItemStack.hasTagCompound())
        {
            mode = par1ItemStack.getTagCompound().getInteger("mode");
        }
        else
        {
            par1ItemStack.setTagCompound(new NBTTagCompound());
            par1ItemStack.getTagCompound().setInteger("mode", mode);
        }


        if (par2EntityPlayer.isSneaking())
        {
            mode++;
            mode = mode % 3;
            par1ItemStack.getTagCompound().setInteger("mode", mode);
            par2EntityPlayer.addChatMessage("Radius: " + (mode + 1) + " chunk" + (mode != 0 ? "s" : ""));
            return false;
        }

        if (par3World.isRemote)
        {
            return false;
        }

        final Map<String, Integer> oreCount = new HashMap<String, Integer>();

        for (int y = 0; y < 128; y++)
        {
            for (int x = par4 - par4 % 16 - 16 * mode; x < par4 - par4 % 16 + 16 * (mode + 1); x++)
            {
                for (int z = par6 - par6 % 16 - 16 * mode; z < par6 - par6 % 16 + 16 * (mode + 1); z++)
                {
                    final int id = par3World.getBlockId(x, y, z);
                    final int meta = par3World.getBlockMetadata(x, y, z);
                    final ItemStack stack = new ItemStack(id, 1, meta);
                    String name = null;
                    final int oreID = OreDictionary.getOreID(stack);
                    if (oreID != -1)
                    {
                        name = OreDictionary.getOreName(oreID);
                    }
                    if (id == Block.oreIron.blockID)
                    {
                        name = "oreIron";
                    }
                    else if (id == Block.oreGold.blockID)
                    {
                        name = "oreGold";
                    }
                    else if (id == Block.oreDiamond.blockID)
                    {
                        name = "oreDiamond";
                    }
                    else if (id == Block.oreNetherQuartz.blockID)
                    {
                        name = "oreNetherQuartz";
                    }

                    if (name != null)
                    {
                        if (oreCount.containsKey(name))
                        {
                            final int count = oreCount.get(name);
                            oreCount.put(name, count + 1);
                        }
                        else
                        {
                            oreCount.put(name, 1);
                        }
                    }

                }
            }
        }

        final Set<String> names = oreCount.keySet();
        final String[] sort = names.toArray(new String[names.size()]);
        Arrays.sort(sort);
        par2EntityPlayer.addChatMessage("In Area (" + (par4 - par4 % 16 - 16 * mode) + ", " + (par6 - par6 % 16 - 16 * mode) + ") to (" + (par4 - par4 % 16 + 16 * (mode + 1))
                + ", " + (par6 - par6 % 16 + 16 * (mode + 1)) + ")");
        for (final String name : names)
        {
            final int amount = oreCount.get(name);
            par2EntityPlayer.addChatMessage("Found " + amount + " " + name);
        }

        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        itemIcon = par1IconRegister.registerIcon("Metallurgy:machines/OreFinder");
    }
}
