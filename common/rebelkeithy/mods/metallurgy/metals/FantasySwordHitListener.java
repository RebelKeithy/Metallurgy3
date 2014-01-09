package rebelkeithy.mods.metallurgy.metals;

import java.lang.reflect.Method;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import rebelkeithy.mods.metallurgy.core.metalsets.ISwordHitListener;

public class FantasySwordHitListener implements ISwordHitListener
{
    private static int speed = 1;
    private static int haste = 3;
    private static int strength = 5;
    private static int resistance = 11;
    private static int fireResist = 12;
    private static int blindness = 15;
    private static int wither = 20;

    @Override
    public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase player)
    {

        if (Math.random() < 0.7)
        {
            return false;
        }

        if (MetallurgyMetals.fantasySet.getOreInfo("Deep Iron").isEnabled() && itemstack.getItem().itemID == MetallurgyMetals.fantasySet.getOreInfo("Deep Iron").sword.itemID)
        {
            entityliving.addPotionEffect(new PotionEffect(blindness, 80, 0));
        }
        else if (MetallurgyMetals.fantasySet.getOreInfo("Black Steel").isEnabled()
                && itemstack.getItem().itemID == MetallurgyMetals.fantasySet.getOreInfo("Black Steel").sword.itemID)
        {
            entityliving.addPotionEffect(new PotionEffect(blindness, 80, 1));
        }
        else if (MetallurgyMetals.fantasySet.getOreInfo("Oureclase").isEnabled() && itemstack.getItem().itemID == MetallurgyMetals.fantasySet.getOreInfo("Oureclase").sword.itemID)
        {
            player.addPotionEffect(new PotionEffect(resistance, 80, 0));
        }
        else if (MetallurgyMetals.fantasySet.getOreInfo("Aredrite").isEnabled() && itemstack.getItem().itemID == MetallurgyMetals.fantasySet.getOreInfo("Aredrite").sword.itemID)
        {
            player.addPotionEffect(new PotionEffect(haste, 70, 0));
        }
        else if (MetallurgyMetals.fantasySet.getOreInfo("Mithril").isEnabled() && itemstack.getItem().itemID == MetallurgyMetals.fantasySet.getOreInfo("Mithril").sword.itemID)
        {
            player.addPotionEffect(new PotionEffect(haste, 80, 0));
        }
        else if (MetallurgyMetals.fantasySet.getOreInfo("Quicksilver").isEnabled()
                && itemstack.getItem().itemID == MetallurgyMetals.fantasySet.getOreInfo("Quicksilver").sword.itemID)
        {
            player.addPotionEffect(new PotionEffect(speed, 80, 0));
        }
        else if (MetallurgyMetals.fantasySet.getOreInfo("Haderoth").isEnabled() && itemstack.getItem().itemID == MetallurgyMetals.fantasySet.getOreInfo("Haderoth").sword.itemID)
        {
            player.addPotionEffect(new PotionEffect(haste, 80, 0));
            entityliving.setFire(4);
        }
        else if (MetallurgyMetals.fantasySet.getOreInfo("Orichalcum").isEnabled() && itemstack.getItem().itemID == MetallurgyMetals.fantasySet.getOreInfo("Orichalcum").sword.itemID)
        {
            player.addPotionEffect(new PotionEffect(resistance, 80, 1));
        }
        else if (MetallurgyMetals.fantasySet.getOreInfo("Celenegil").isEnabled() && itemstack.getItem().itemID == MetallurgyMetals.fantasySet.getOreInfo("Celenegil").sword.itemID)
        {
            player.addPotionEffect(new PotionEffect(resistance, 80, 3));
        }
        else if (MetallurgyMetals.fantasySet.getOreInfo("Adamantine").isEnabled()
                && itemstack.getItem().itemID == MetallurgyMetals.fantasySet.getOreInfo("Adamantine").sword.itemID)
        {
            player.addPotionEffect(new PotionEffect(fireResist, 80, 0));
            entityliving.setFire(4);
        }
        else if (MetallurgyMetals.fantasySet.getOreInfo("Atlarus").isEnabled() && itemstack.getItem().itemID == MetallurgyMetals.fantasySet.getOreInfo("Atlarus").sword.itemID)
        {
            entityliving.addPotionEffect(new PotionEffect(strength, 80, 1));
        }
        else if (MetallurgyMetals.fantasySet.getOreInfo("Tartarite").isEnabled() && itemstack.getItem().itemID == MetallurgyMetals.fantasySet.getOreInfo("Tartarite").sword.itemID)
        {
            entityliving.addPotionEffect(new PotionEffect(wither, 80, 1));
            entityliving.setFire(4);
        }

        return false;
    }

    @ForgeSubscribe
    public void onDeathEvent(LivingDeathEvent event)
    {
        if (event.source.getEntity() instanceof EntityPlayer)
        {
            final EntityPlayer player = (EntityPlayer) event.source.getEntity();
            if (player.getCurrentEquippedItem() == null)
            {
                return;
            }

            int effect = 0;
            if (MetallurgyMetals.fantasySet.getOreInfo("Astral Silver").isEnabled()
                    && player.getCurrentEquippedItem().itemID == MetallurgyMetals.fantasySet.getOreInfo("Astral Silver").sword.itemID)
            {
                effect = 1;
            }
            if (MetallurgyMetals.fantasySet.getOreInfo("Carmot").isEnabled()
                    && player.getCurrentEquippedItem().itemID == MetallurgyMetals.fantasySet.getOreInfo("Carmot").sword.itemID)
            {
                effect = 2;
            }

            if (effect > 0)
            {
                try
                {
                    final Method m = EntityLiving.class.getDeclaredMethod("dropFewItems", Boolean.TYPE, Integer.TYPE);
                    m.setAccessible(true);
                    m.invoke(event.entityLiving, true, 0);
                    if (effect > 1)
                    {
                        if (Math.random() > 5)
                        {
                            m.invoke(event.entityLiving, true, 0);
                        }
                    }
                    // m.setAccessible(false);
                } catch (final Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
