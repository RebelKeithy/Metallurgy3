package rebelkeithy.mods.metallurgy.machines.enchanter;

import java.util.List;
import java.util.Random;

import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import rebelkeithy.mods.metallurgy.metals.MetallurgyMetals;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerMetallurgyEnchantment extends Container
{
    /** SlotEnchantmentTable object with ItemStack to be enchanted */
    public IInventory tableInventory = new SlotMetallurgyEnchantmentTable(this, "Enchant", true, 7);
    // public IInventory catalystInventory = new Slot()

    /** current world (for bookshelf counting) */
    private final World worldPointer;
    private final int posX;
    private final int posY;
    private final int posZ;
    private final Random rand = new Random();

    /** used as seed for EnchantmentNameParts (see GuiEnchantment) */
    public long nameSeed;

    /** 3-member array storing the enchantment levels of each slot */
    public int enchantLevels;

    public EntityPlayer player;

    public ContainerMetallurgyEnchantment(InventoryPlayer par1InventoryPlayer, TileEntity te)
    {
        worldPointer = te.worldObj;
        posX = te.xCoord;
        posY = te.yCoord;
        posZ = te.zCoord;
        addSlotToContainer(new SlotMetallurgyEnchantment(this, tableInventory, 0, 80, 35));
        addSlotToContainer(new Slot(tableInventory, 1, 64, 19));
        addSlotToContainer(new Slot(tableInventory, 2, 80, 9));
        addSlotToContainer(new Slot(tableInventory, 3, 96, 19));
        addSlotToContainer(new Slot(tableInventory, 4, 64, 51));
        addSlotToContainer(new Slot(tableInventory, 5, 80, 61));
        addSlotToContainer(new Slot(tableInventory, 6, 96, 51));

        int l;

        for (l = 0; l < 3; ++l)
        {
            for (int i1 = 0; i1 < 9; ++i1)
            {
                addSlotToContainer(new Slot(par1InventoryPlayer, i1 + l * 9 + 9, 8 + i1 * 18, 84 + l * 18));
            }
        }

        for (l = 0; l < 9; ++l)
        {
            addSlotToContainer(new Slot(par1InventoryPlayer, l, 8 + l * 18, 142));
        }
    }

    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, enchantLevels);
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        player = par1EntityPlayer;
        return true;
        // return this.worldPointer.getBlockId(this.posX, this.posY, this.posZ)
        // != Block.enchantmentTable.blockID ? false :
        // par1EntityPlayer.getDistanceSq((double)this.posX + 0.5D,
        // (double)this.posY + 0.5D, (double)this.posZ + 0.5D) <= 64.0D;
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < crafters.size(); ++i)
        {
            final ICrafting icrafting = (ICrafting) crafters.get(i);
            icrafting.sendProgressBarUpdate(this, 0, enchantLevels);
        }
    }

    /**
     * enchants the item on the table using the specified slot; also deducts XP
     * from player
     */
    @Override
    public boolean enchantItem(EntityPlayer par1EntityPlayer, int slot)
    {
        final ItemStack itemstack = tableInventory.getStackInSlot(0);

        if (enchantLevels > 0 && itemstack != null && (par1EntityPlayer.experienceLevel >= enchantLevels || par1EntityPlayer.capabilities.isCreativeMode))
        {
            if (!worldPointer.isRemote)
            {
                int catalyst = 0;
                for (int i = 1; i < 6; i++)
                {
                    final ItemStack item = tableInventory.getStackInSlot(i);
                    if (item != null && MetallurgyMetals.fantasySet != null)
                    {
                        if (item.itemID == MetallurgyMetals.fantasySet.getOreInfo("Astral Silver").dust.itemID)
                        {
                            catalyst++;
                            tableInventory.setInventorySlotContents(i, null);
                        }
                        else if (item.itemID == MetallurgyMetals.fantasySet.getOreInfo("Carmot").dust.itemID)
                        {
                            catalyst += 2;
                            tableInventory.setInventorySlotContents(i, null);
                        }
                    }
                }

                final List<EnchantmentData> list = MaxEnchanterHelper.buildEnchantmentList(rand, itemstack, enchantLevels, catalyst);
                final boolean flag = itemstack.itemID == Item.book.itemID;

                if (list != null)
                {
                    par1EntityPlayer.addExperienceLevel(-enchantLevels);

                    if (flag)
                    {
                        itemstack.itemID = Item.enchantedBook.itemID;
                    }

                    final int j = flag ? rand.nextInt(list.size()) : -1;

                    for (int k = 0; k < list.size(); ++k)
                    {
                        final EnchantmentData enchantmentdata = (EnchantmentData) list.get(k);

                        if (!flag || k == j)
                        {
                            if (flag)
                            {
                                Item.enchantedBook.addEnchantment(itemstack, enchantmentdata);
                            }
                            else
                            {
                                itemstack.addEnchantment(enchantmentdata.enchantmentobj, enchantmentdata.enchantmentLevel);
                            }
                        }
                    }

                    onCraftMatrixChanged(tableInventory);
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Callback for when the crafting gui is closed.
     */
    @Override
    public void onContainerClosed(EntityPlayer par1EntityPlayer)
    {
        super.onContainerClosed(par1EntityPlayer);

        if (!worldPointer.isRemote)
        {
            for (int i = 0; i < 6; i++)
            {
                final ItemStack itemstack = tableInventory.getStackInSlotOnClosing(i);

                if (itemstack != null)
                {
                    par1EntityPlayer.dropPlayerItem(itemstack);
                }
            }
        }
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    @Override
    public void onCraftMatrixChanged(IInventory par1IInventory)
    {
        if (par1IInventory == tableInventory)
        {
            final ItemStack itemstack = par1IInventory.getStackInSlot(0);
            if (itemstack != null && itemstack.isItemEnchantable())
            {
                nameSeed = rand.nextLong();

                if (!worldPointer.isRemote)
                {
                    int j;
                    float power = 0;

                    for (j = -1; j <= 1; ++j)
                    {
                        for (int k = -1; k <= 1; ++k)
                        {
                            if ((j != 0 || k != 0) && worldPointer.isAirBlock(posX + k, posY, posZ + j) && worldPointer.isAirBlock(posX + k, posY + 1, posZ + j))
                            {
                                power += ForgeHooks.getEnchantPower(worldPointer, posX + k * 2, posY, posZ + j * 2);
                                power += ForgeHooks.getEnchantPower(worldPointer, posX + k * 2, posY + 1, posZ + j * 2);

                                if (k != 0 && j != 0)
                                {
                                    power += ForgeHooks.getEnchantPower(worldPointer, posX + k * 2, posY, posZ + j);
                                    power += ForgeHooks.getEnchantPower(worldPointer, posX + k * 2, posY + 1, posZ + j);
                                    power += ForgeHooks.getEnchantPower(worldPointer, posX + k, posY, posZ + j * 2);
                                    power += ForgeHooks.getEnchantPower(worldPointer, posX + k, posY + 1, posZ + j * 2);
                                }
                            }
                        }
                    }
                    System.out.println(power);
                    enchantLevels = MaxEnchanterHelper.calcItemStackEnchantability(rand, (int) power, itemstack);
                    if (player != null && player.experienceLevel < enchantLevels)
                    {
                        enchantLevels = player.experienceLevel;
                    }
                    detectAndSendChanges();
                }
            }
            else
            {
                enchantLevels = 0;
            }
        }
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or
     * you will crash when someone does that.
     */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack itemstack = null;
        final Slot slot = (Slot) inventorySlots.get(par2);

        if (slot != null && slot.getHasStack())
        {
            final ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (par2 <= 5)
            {
                if (!mergeItemStack(itemstack1, 6, 42, true))
                {
                    return null;
                }
            }
            else
            {
                if (((Slot) inventorySlots.get(0)).getHasStack() || !((Slot) inventorySlots.get(0)).isItemValid(itemstack1))
                {
                    return null;
                }

                if (itemstack1.hasTagCompound() && itemstack1.stackSize == 1)
                {
                    ((Slot) inventorySlots.get(0)).putStack(itemstack1.copy());
                    itemstack1.stackSize = 0;
                }
                else if (itemstack1.stackSize >= 1)
                {
                    ((Slot) inventorySlots.get(0)).putStack(new ItemStack(itemstack1.itemID, 1, itemstack1.getItemDamage()));
                    --itemstack1.stackSize;
                }
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack) null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
        }

        return itemstack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0)
        {
            enchantLevels = par2;
        }
        else
        {
            super.updateProgressBar(par1, par2);
        }
    }
}
