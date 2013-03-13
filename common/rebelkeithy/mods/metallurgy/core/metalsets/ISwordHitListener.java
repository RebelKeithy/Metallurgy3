package rebelkeithy.mods.metallurgy.core.metalsets;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;

public interface ISwordHitListener 
{
    public boolean hitEntity(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1);
}
