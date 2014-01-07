package rebelkeithy.mods.metallurgy.machines.orbs;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemOrb extends Item
{
    public static String[] names =
    { "Prometheum", "Deep Iron", "Black Steel", "Oureclase", "Mithril", "Haderoth", "Orichalcum", "Adamantine", "Atlarus", "Tartarite" };
    private Icon[] icons;
    private Icon[] iconsFull;

    private static int[] maxXP =
    { 50, 500, 750, 1000, 1250, 1500, 1750, 2000, 2250, 2500 };

    public ItemOrb(int par1)
    {
        super(par1);
        setHasSubtypes(true);
        setMaxStackSize(1);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        par3List.add("" + getXpFromDamage(par1ItemStack.getItemDamage()) + "/" + maxXP[getTypeFromDamage(par1ItemStack.getItemDamage())]);
    }

    public void addXP(ItemStack stack, int amount)
    {
        final int type = getTypeFromDamage(stack.getItemDamage());
        final int currXP = getXpFromDamage(stack.getItemDamage());

        if (currXP + amount > maxXP[type])
        {
            amount = maxXP[type] - currXP;
        }

        stack.setItemDamage(stack.getItemDamage() + (amount << 4));
    }

    @Override
    public int getDisplayDamage(ItemStack stack)
    {
        return maxXP[getTypeFromDamage(stack.getItemDamage())] - getXpFromDamage(stack.getItemDamage());
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIconFromDamage(int par1)
    {
        final int type = getTypeFromDamage(par1);
        final int xpAmount = getXpFromDamage(par1);

        if (xpAmount == maxXP[type])
        {
            return iconsFull[type];
        }
        else
        {
            return icons[type];
        }
    }

    /**
     * returns the action that specifies what animation to play when the items
     * is being used
     */
    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.bow;
    }

    @Override
    public int getMaxDamage()
    {
        return 50;
    }

    @Override
    public int getMaxDamage(ItemStack stack)
    {
        return maxXP[getTypeFromDamage(stack.getItemDamage())];
    }

    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 72000;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int j = 0; j < 10; ++j)
        {
            par3List.add(new ItemStack(par1, 1, j));
        }
    }

    public int getTypeFromDamage(int damage)
    {
        return damage & 15;
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        final int type = getTypeFromDamage(par1ItemStack.getItemDamage());
        return super.getUnlocalizedName() + "." + names[type];
    }

    public int getXpFromDamage(int damage)
    {
        return damage >> 4;
    }

    @Override
    public boolean isDamaged(ItemStack stack)
    {
        return maxXP[getTypeFromDamage(stack.getItemDamage())] != getXpFromDamage(stack.getItemDamage());
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (par3EntityPlayer.isSneaking())
        {
            par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));
            return par1ItemStack;
        }

        final int type = getTypeFromDamage(par1ItemStack.getItemDamage());
        final int currXP = getXpFromDamage(par1ItemStack.getItemDamage());
        if (currXP < maxXP[type] && par3EntityPlayer.experienceTotal > 0)
        {
            addXP(par1ItemStack, 1);
            par3EntityPlayer.addExperience(-1);

            if (par3EntityPlayer.experience < 0 && par3EntityPlayer.experienceTotal > 0)
            {
                par3EntityPlayer.addExperienceLevel(-1);
                par3EntityPlayer.experience = 1f - 1 / (float) par3EntityPlayer.xpBarCap();
            }

            if (par3EntityPlayer.experienceTotal == 0)
            {
                par3EntityPlayer.experienceLevel = 0;
                par3EntityPlayer.experience = 0;
            }
        }

        return par1ItemStack;

    }

    @Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
    {
        final int j = getMaxItemUseDuration(par1ItemStack) - par4;

        if (j > 0)
        {
            final int xp = getXpFromDamage(par1ItemStack.getItemDamage());

            if (!par3EntityPlayer.worldObj.isRemote)
            {
                par3EntityPlayer.worldObj.spawnEntityInWorld(new EntityXPOrb(par3EntityPlayer.worldObj, par3EntityPlayer.posX, par3EntityPlayer.posY + 0.5D,
                        par3EntityPlayer.posZ + 0.5D, xp));
            }

            setXP(par1ItemStack, 0);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
        icons = new Icon[names.length];
        iconsFull = new Icon[names.length];

        for (int i = 0; i < icons.length; ++i)
        {
            icons[i] = par1IconRegister.registerIcon("Metallurgy:machines/orbs/Orb" + names[i]);
            iconsFull[i] = par1IconRegister.registerIcon("Metallurgy:machines/orbs/OrbFull" + names[i]);
        }
    }

    public void setXP(ItemStack stack, int amount)
    {
        stack.setItemDamage(amount << 4);
    }
}
