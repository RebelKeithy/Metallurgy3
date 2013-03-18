package rebelkeithy.mods.metallurgy.machines.enchanter;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
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

public class CopyOfContainerMetallurgyEnchantment extends Container
{
    /** SlotEnchantmentTable object with ItemStack to be enchanted */
    public IInventory tableInventory;// = new SlotMetallurgyEnchantmentTable(this, "Enchant", true, 1);

    /** current world (for bookshelf counting) */
    private World worldPointer;
    private int posX;
    private int posY;
    private int posZ;
    private Random rand = new Random();

    /** used as seed for EnchantmentNameParts (see GuiEnchantment) */
    public long nameSeed;

    /** 3-member array storing the enchantment levels of each slot */
    public int[] enchantLevels = new int[3];

    public CopyOfContainerMetallurgyEnchantment(InventoryPlayer par1InventoryPlayer, TileEntity te)
    {
        this.worldPointer = te.worldObj;
        this.posX = te.xCoord;
        this.posY = te.yCoord;
        this.posZ = te.zCoord;
        //this.addSlotToContainer(new SlotMetallurgyEnchantment(this, this.tableInventory, 0, 25, 47));
        int l;
        
        for (l = 0; l < 3; ++l)
        {
            for (int i1 = 0; i1 < 9; ++i1)
            {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, i1 + l * 9 + 9, 8 + i1 * 18, 84 + l * 18));
            }
        }

        for (l = 0; l < 9; ++l)
        {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, l, 8 + l * 18, 142));
        }
    }

    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.enchantLevels[0]);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.enchantLevels[1]);
        par1ICrafting.sendProgressBarUpdate(this, 2, this.enchantLevels[2]);
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);
            icrafting.sendProgressBarUpdate(this, 0, this.enchantLevels[0]);
            icrafting.sendProgressBarUpdate(this, 1, this.enchantLevels[1]);
            icrafting.sendProgressBarUpdate(this, 2, this.enchantLevels[2]);
        }
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 >= 0 && par1 <= 2)
        {
            this.enchantLevels[par1] = par2;
        }
        else
        {
            super.updateProgressBar(par1, par2);
        }
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void onCraftMatrixChanged(IInventory par1IInventory)
    {
        if (par1IInventory == this.tableInventory)
        {
            ItemStack itemstack = par1IInventory.getStackInSlot(0);
            int i;

            if (itemstack != null && itemstack.isItemEnchantable())
            {
                this.nameSeed = this.rand.nextLong();

                if (!this.worldPointer.isRemote)
                {
                    i = 0;
                    int j;

                    for (j = -1; j <= 1; ++j)
                    {
                        for (int k = -1; k <= 1; ++k)
                        {
                            if ((j != 0 || k != 0) && this.worldPointer.isAirBlock(this.posX + k, this.posY, this.posZ + j) && this.worldPointer.isAirBlock(this.posX + k, this.posY + 1, this.posZ + j))
                            {
                                if (this.worldPointer.getBlockId(this.posX + k * 2, this.posY, this.posZ + j * 2) == Block.bookShelf.blockID)
                                {
                                    ++i;
                                }

                                if (this.worldPointer.getBlockId(this.posX + k * 2, this.posY + 1, this.posZ + j * 2) == Block.bookShelf.blockID)
                                {
                                    ++i;
                                }

                                if (k != 0 && j != 0)
                                {
                                    if (this.worldPointer.getBlockId(this.posX + k * 2, this.posY, this.posZ + j) == Block.bookShelf.blockID)
                                    {
                                        ++i;
                                    }

                                    if (this.worldPointer.getBlockId(this.posX + k * 2, this.posY + 1, this.posZ + j) == Block.bookShelf.blockID)
                                    {
                                        ++i;
                                    }

                                    if (this.worldPointer.getBlockId(this.posX + k, this.posY, this.posZ + j * 2) == Block.bookShelf.blockID)
                                    {
                                        ++i;
                                    }

                                    if (this.worldPointer.getBlockId(this.posX + k, this.posY + 1, this.posZ + j * 2) == Block.bookShelf.blockID)
                                    {
                                        ++i;
                                    }
                                }
                            }
                        }
                    }

                    for (j = 0; j < 3; ++j)
                    {
                        this.enchantLevels[j] = EnchantmentHelper.calcItemStackEnchantability(this.rand, j, i, itemstack);
                    }

                    this.detectAndSendChanges();
                }
            }
            else
            {
                for (i = 0; i < 3; ++i)
                {
                    this.enchantLevels[i] = 0;
                }
            }
        }
    }

    /**
     * enchants the item on the table using the specified slot; also deducts XP from player
     */
    public boolean enchantItem(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack itemstack = this.tableInventory.getStackInSlot(0);

        if (this.enchantLevels[par2] > 0 && itemstack != null && (par1EntityPlayer.experienceLevel >= this.enchantLevels[par2] || par1EntityPlayer.capabilities.isCreativeMode))
        {
            if (!this.worldPointer.isRemote)
            {
                List list = EnchantmentHelper.buildEnchantmentList(this.rand, itemstack, this.enchantLevels[par2]);
                boolean flag = itemstack.itemID == Item.book.itemID;

                if (list != null)
                {
                    par1EntityPlayer.addExperienceLevel(-this.enchantLevels[par2]);

                    if (flag)
                    {
                        itemstack.itemID = Item.enchantedBook.itemID;
                    }

                    int j = flag ? this.rand.nextInt(list.size()) : -1;

                    for (int k = 0; k < list.size(); ++k)
                    {
                        EnchantmentData enchantmentdata = (EnchantmentData)list.get(k);

                        if (!flag || k == j)
                        {
                            if (flag)
                            {
                                Item.enchantedBook.func_92115_a(itemstack, enchantmentdata);
                            }
                            else
                            {
                                itemstack.addEnchantment(enchantmentdata.enchantmentobj, enchantmentdata.enchantmentLevel);
                            }
                        }
                    }

                    this.onCraftMatrixChanged(this.tableInventory);
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
    public void onCraftGuiClosed(EntityPlayer par1EntityPlayer)
    {
        super.onCraftGuiClosed(par1EntityPlayer);

        if (!this.worldPointer.isRemote)
        {
            ItemStack itemstack = this.tableInventory.getStackInSlotOnClosing(0);

            if (itemstack != null)
            {
                par1EntityPlayer.dropPlayerItem(itemstack);
            }
        }
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.worldPointer.getBlockId(this.posX, this.posY, this.posZ) != Block.enchantmentTable.blockID ? false : par1EntityPlayer.getDistanceSq((double)this.posX + 0.5D, (double)this.posY + 0.5D, (double)this.posZ + 0.5D) <= 64.0D;
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(par2);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (par2 == 0)
            {
                if (!this.mergeItemStack(itemstack1, 1, 37, true))
                {
                    return null;
                }
            }
            else
            {
                if (((Slot)this.inventorySlots.get(0)).getHasStack() || !((Slot)this.inventorySlots.get(0)).isItemValid(itemstack1))
                {
                    return null;
                }

                if (itemstack1.hasTagCompound() && itemstack1.stackSize == 1)
                {
                    ((Slot)this.inventorySlots.get(0)).putStack(itemstack1.copy());
                    itemstack1.stackSize = 0;
                }
                else if (itemstack1.stackSize >= 1)
                {
                    ((Slot)this.inventorySlots.get(0)).putStack(new ItemStack(itemstack1.itemID, 1, itemstack1.getItemDamage()));
                    --itemstack1.stackSize;
                }
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
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
}
