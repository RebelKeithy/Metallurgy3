package rebelkeithy.mods.metallurgy.core.metalsets;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface ISwordHitListener
{
    public boolean hitEntity(ItemStack itemstack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase);
}
