package rebelkeithy.mods.metallurgy.machines.mint;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import rebelkeithy.mods.metallurgy.machines.MetallurgyMachines;
import rebelkeithy.mods.metallurgy.metals.MetallurgyMetals;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TileEntityMint extends TileEntity
{
    public int direction = 0;
    protected int ingotId = 0;
    public int amount = 0;

    public int resetTime = 0;

    public boolean powered = false;

    private int timeSinceSinc;

    public ItemStack currentIngot()
    {
        final ItemStack ret = new ItemStack(ingotId, 1, 0);
        if (ingotId != 0 && amount == MintRecipes.minting().getMintingResult(ret))
        {
            return ret;
        }
        else
        {
            return null;
        }
    }

    public int getDirection()
    {
        return direction;
    }

    public String getIngotImage()
    {
        if (ingotId == MetallurgyMetals.preciousSet.getOreInfo("Silver").ingot.itemID)
        {
            return "metallurgy:textures/blocks/machines/mint/MintSilver.png";
        }
        else if (ingotId == MetallurgyMetals.preciousSet.getOreInfo("Brass").ingot.itemID)
        {
            return "metallurgy:textures/blocks/machines/mint/MintBrass.png";
        }
        else if (ingotId == MetallurgyMetals.preciousSet.getOreInfo("Electrum").ingot.itemID)
        {
            return "metallurgy:textures/blocks/machines/mint/MintElectrum.png";
        }
        else if (ingotId == MetallurgyMetals.preciousSet.getOreInfo("Platinum").ingot.itemID)
        {
            return "metallurgy:textures/blocks/machines/mint/MintPlatinum.png";
        }
        else if (ingotId == Item.ingotGold.itemID)
        {
            return "metallurgy:textures/blocks/machines/mint/MintGold.png";
        }

        if (!MintRecipes.minting().getImage(ingotId).equals(""))
        {
            return MintRecipes.minting().getImage(ingotId);
        }
        else
        {
            return "metallurgy:textures/blocks/machines/mint/MintBrass.png";
        }
    }

    public boolean hasIngot()
    {
        if (ingotId > 0)
        {
            return true;
        }
        return false;
    }

    public void increaseIngotMintCount()
    {
        if (amount > MintRecipes.minting().getMintingResult(new ItemStack(ingotId, 1, 0)))
        {
            amount = MintRecipes.minting().getMintingResult(new ItemStack(ingotId, 1, 0));
        }

        if (--amount <= 0)
        {
            ingotId = 0;
            final int id = worldObj.getBlockId(xCoord, yCoord, zCoord);
            worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 3, ingotId);
        }
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes
     * with Container
     */
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this ? false : par1EntityPlayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
    }

    public void mint()
    {
        final ItemStack var7 = new ItemStack(MetallurgyMachines.coin, 1, 0);
        final Random rand = new Random();

        if (!worldObj.isRemote && hasIngot())
        {
            for (int x = -1; x <= 1; x += 2)
            {
                final TileEntity te = worldObj.getBlockTileEntity(xCoord + x, yCoord, zCoord);
                if (te instanceof IInventory && !(te instanceof TileEntityMintStorage))
                {
                    final IInventory tei = (IInventory) te;
                    for (int i = 0; i < tei.getSizeInventory(); i++)
                    {
                        final ItemStack chestItem = tei.getStackInSlot(i);
                        if (chestItem == null)
                        {
                            tei.setInventorySlotContents(i, var7);
                            increaseIngotMintCount();
                            return;
                        }
                        else if (chestItem.itemID == var7.itemID && chestItem.stackSize < 64)
                        {
                            chestItem.stackSize++;
                            increaseIngotMintCount();
                            return;
                        }
                    }
                }
            }
            for (int z = -1; z <= 1; z += 2)
            {
                final TileEntity te = worldObj.getBlockTileEntity(xCoord, yCoord, zCoord + z);
                if (te instanceof IInventory && !(te instanceof TileEntityMintStorage))
                {
                    final IInventory tei = (IInventory) te;
                    for (int i = 0; i < tei.getSizeInventory(); i++)
                    {
                        final ItemStack chestItem = tei.getStackInSlot(i);
                        if (chestItem == null)
                        {
                            tei.setInventorySlotContents(i, var7);
                            increaseIngotMintCount();
                            return;
                        }
                        else if (chestItem.itemID == var7.itemID && chestItem.stackSize < 64)
                        {
                            chestItem.stackSize++;
                            increaseIngotMintCount();
                            return;
                        }
                    }
                }
            }

            final float var8 = rand.nextFloat() * 0.8F + 0.1F;
            final float var9 = rand.nextFloat() * 0.8F + 0.1F;
            final float var10 = rand.nextFloat() * 0.8F + 0.1F;

            final EntityItem var12 = new EntityItem(worldObj, xCoord + var8, yCoord + var9, zCoord + var10, new ItemStack(var7.itemID, 1, var7.getItemDamage()));

            if (var7.hasTagCompound())
            {
                var12.getEntityItem().setTagCompound((NBTTagCompound) var7.getTagCompound().copy());
            }

            final float var13 = 0.05F;
            var12.motionX = (float) rand.nextGaussian() * var13;
            var12.motionY = (float) rand.nextGaussian() * var13 + 0.2F;
            var12.motionZ = (float) rand.nextGaussian() * var13;
            var12.delayBeforeCanPickup = 20;
            worldObj.spawnEntityInWorld(var12);
            increaseIngotMintCount();
        }
    }

    public void power()
    {
        if (ingotId == 0)
        {
            for (int x = -1; x <= 1; x += 2)
            {
                final TileEntity te = worldObj.getBlockTileEntity(xCoord + x, yCoord, zCoord);
                if (te instanceof TileEntityMintStorage)
                {
                    final IInventory tei = (IInventory) te;
                    for (int i = 0; i < tei.getSizeInventory(); i++)
                    {
                        final ItemStack chestItem = tei.getStackInSlot(i);
                        if (chestItem != null)
                        {
                            if (MintRecipes.minting().getMintingResult(chestItem) > 0)
                            {
                                setIngot(i, tei);
                                return;
                            }
                        }
                    }
                }
            }
            for (int z = -1; z <= 1; z += 2)
            {
                final TileEntity te = worldObj.getBlockTileEntity(xCoord, yCoord, zCoord + z);
                if (te instanceof TileEntityMintStorage)
                {
                    final IInventory tei = (IInventory) te;
                    for (int i = 0; i < tei.getSizeInventory(); i++)
                    {
                        final ItemStack chestItem = tei.getStackInSlot(i);
                        if (chestItem != null)
                        {
                            if (MintRecipes.minting().getMintingResult(chestItem) > 0)
                            {
                                setIngot(i, tei);
                                return;
                            }
                        }
                    }
                }
            }
        }

        if (resetTime == 0)
        {
            resetTime = 10;
        }
        powered = true;
    }

    /**
     * Reads a tile entity from NBT.
     */
    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);

        direction = par1NBTTagCompound.getShort("Direction");
        ingotId = par1NBTTagCompound.getShort("Ingot");
        amount = par1NBTTagCompound.getShort("Amount");
    }

    @Override
    public boolean receiveClientEvent(int i, int j)
    {
        if (i == 1)
        {
            direction = j;
        }
        else if (i == 2)
        {
            resetTime = j;
        }
        else if (i == 3)
        {
            ingotId = j;
        }
        else if (i == 4)
        {
            amount = j;
        }
        else
        {
            return false;
        }

        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        return true;
    }

    public void removeIngot()
    {
        ingotId = 0;
        amount = 0;
        final int id = worldObj.getBlockId(xCoord, yCoord, zCoord);
        worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 3, ingotId);
    }

    public void sendPacket()
    {
        if (worldObj.isRemote)
        {
            return;
        }

        final ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
        final DataOutputStream dos = new DataOutputStream(bos);
        try
        {
            dos.writeInt(2);
            dos.writeInt(xCoord);
            dos.writeInt(yCoord);
            dos.writeInt(zCoord);
            dos.writeInt(direction);
        } catch (final IOException e)
        {
            // UNPOSSIBLE?
        }
        final Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = "MetallurgyFantas";
        packet.data = bos.toByteArray();
        packet.length = bos.size();
        packet.isChunkDataPacket = true;

        if (packet != null)
        {
            PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 16, worldObj.provider.dimensionId, packet);
        }
    }

    public void setDirection(int par1)
    {
        direction = par1;
    }

    public void setIngot(int index, IInventory inv)
    {
        if (ingotId == 0 && resetTime == 0)
        {
            amount = MintRecipes.minting().getMintingResult(inv.getStackInSlot(index));
            if (amount != 0)
            {
                ingotId = inv.getStackInSlot(index).itemID;
                inv.decrStackSize(index, 1);
                final int id = worldObj.getBlockId(xCoord, yCoord, zCoord);
                worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 3, ingotId);
            }
        }
    }

    public void unpower()
    {
        powered = false;
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses,
     * e.g. the mob spawner uses this to count ticks and creates a new spawn
     * inside its implementation.
     */
    @Override
    public void updateEntity()
    {
        if (timeSinceSinc-- == 0 && !worldObj.isRemote)
        {
            timeSinceSinc = 80;
            final int id = worldObj.getBlockId(xCoord, yCoord, zCoord);
            worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 2, resetTime);
            worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 3, ingotId);
            worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 4, amount);
        }

        timeSinceSinc = timeSinceSinc < 0 ? 0 : timeSinceSinc;

        if (resetTime > 0)
        {
            resetTime--;

            final int id = worldObj.getBlockId(xCoord, yCoord, zCoord);
            worldObj.addBlockEvent(xCoord, yCoord, zCoord, id, 2, resetTime);

            if (resetTime == 6)
            {
                mint();
            }
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("Direction", (short) direction);
        par1NBTTagCompound.setShort("Ingot", (short) ingotId);
        par1NBTTagCompound.setShort("Amount", (short) amount);
    }
}
