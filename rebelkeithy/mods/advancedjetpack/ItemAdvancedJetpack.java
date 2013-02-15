package rebelkeithy.mods.advancedjetpack;

import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.ElectricItem;
import ic2.api.IElectricItem;

import java.util.List;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemHangingEntity;

public class ItemAdvancedJetpack //extends ItemArmorJetpack
 // implements IElectricItem
{
	/*
  public ItemAdvancedJetpack(int id, int index, int armorrendering)
  {
    super(id, index, armorrendering);

    e(27);
    d(1);
  }

  public int getCharge(ItemHangingEntity itemStack)
  {
    return ElectricItem.discharge(itemStack, 2147483647, 2147483647, true, true);
  }

  public void use(ItemHangingEntity itemStack, int amount)
  {
    ElectricItem.discharge(itemStack, amount, 2147483647, true, false);
  }

  public boolean canProvideEnergy()
  {
    return false;
  }

  public int getChargedItemId()
  {
    return this.recordWard;
  }

  public int getEmptyItemId()
  {
    return this.recordWard;
  }

  public int getMaxCharge()
  {
    return 30000;
  }

  public int getTier()
  {
    return 1;
  }

  public int getTransferLimit()
  {
    return 60;
  }

  @SideOnly(Side.CLIENT)
  public void a(int par1, ItemColored par2CreativeTabs, List itemList)
  {
    if (getChargedItemId() == this.recordWard) {
      ItemHangingEntity charged = new ItemHangingEntity(this, 1);
      ElectricItem.charge(charged, 2147483647, 2147483647, true, false);
      itemList.add(charged);
    }
    if (getEmptyItemId() == this.recordWard) itemList.add(new ItemHangingEntity(this, 1, getMaxDamage()));
  }
  */
}