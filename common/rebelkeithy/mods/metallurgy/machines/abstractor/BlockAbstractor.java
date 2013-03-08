package rebelkeithy.mods.metallurgy.machines.abstractor;

import java.util.List;
import java.util.Random;

import rebelkeithy.mods.guiregistry.GuiRegistry;
import rebelkeithy.mods.metallurgy.core.MetallurgyCore;
import rebelkeithy.mods.metallurgy.machines.MetallurgyMachines;
import rebelkeithy.mods.metallurgy.metals.MetallurgyMetals;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class BlockAbstractor extends BlockContainer
{
    /**
     * Is the random generator used by furnace to drop the inventory contents in random directions.
     */
    private Random rand = new Random();

    /** True if this is an active furnace, false if idle */
    private final boolean isActive;

    /**
     * This flag is used to prevent the furnace inventory to be dropped upon block removal, is used internally when the
     * furnace block changes from idle to active and vice-versa.
     */
    private static boolean keepFurnaceInventory = false;

    public BlockAbstractor(int par1, boolean par2)
    {
        super(par1, Material.rock);
        this.isActive = par2;
        setRequiresSelfNotify();
    }
    
    @Override
	public int damageDropped(int metadata)
    {
    	return metadata;
    }
    
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
    public int getBlockTextureFromSideAndMetadata(int side, int metadata)
    {
        if (side == 1 || side == 0)
        {
            return 2 + (metadata * 16);
        }
        else
        {
            if(side != 3)
            	return 1 + (metadata * 16);
            else
            	return 0 + (metadata * 16);
        }
    }

    /**
     * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
     */
    @Override
    public int getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
    	TileEntity tileEntity = par1IBlockAccess.getBlockTileEntity(par2, par3, par4);
    	int meta = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
    	int	dir = (tileEntity instanceof TileEntityAbstractor) ? ((TileEntityAbstractor)tileEntity).getDirection() : 0;
    	int time = (tileEntity instanceof TileEntityAbstractor) ? (((TileEntityAbstractor)tileEntity).furnaceCookTime * 10) % 2 : 0;
    	boolean isBurning = (tileEntity instanceof TileEntityAbstractor) ? (((TileEntityAbstractor)tileEntity).isBurning()) : false;
    			    	
        if (par5 == 1 || par5 == 0)
        {
            return 2 + ((meta) * 16);
        }
        else
        {
            if(par5 != dir)
            	return 1 + ((meta) * 16);
            else if(isBurning)
            	return 4 + ((meta) * 16);
            else
            	return 0 + ((meta) * 16);
        }
    }

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    @Override
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
    	if(Math.random() > 0.5f)
    		return;
    	
    	int meta = par1World.getBlockMetadata(par2, par3, par4);
    	TileEntityAbstractor te = (TileEntityAbstractor)(par1World.getBlockTileEntity(par2, par3, par4));
        if (te.isBurning())
        {
            //int var6 = par1World.getBlockMetadata(par2, par3, par4);
            int var6 = te.getDirection();
            float var7 = (float)par2 + 0.5F;
            float var8 = (float)par3 + 0.0F + par5Random.nextFloat() * 6.0F / 16.0F + 0.5F;
            float var9 = (float)par4 + 0.5F;
            float var10 = 0.52F;
            float var11 = par5Random.nextFloat() * 0.6F - 0.3F;

            
        	if (var6 == 4)
            {
        		MetallurgyCore.proxy.spawnParticle("abstractorLarge", par1World, (double)(var7 - var10), (double)var8, (double)(var9 + var11), 0.0D, 0.0D, 0.0D);
                var8 = (float)par3 + 0.0F + par5Random.nextFloat() * 6.0F / 16.0F + 0.5F;
                var11 = par5Random.nextFloat() * 0.6F - 0.3F;
                MetallurgyCore.proxy.spawnParticle("abstractorSmall", par1World, (double)(var7 - var10), (double)var8, (double)(var9 + var11), 0.0D, 0.0D, 0.0D);
            }
            else if (var6 == 5)
            {
            	MetallurgyCore.proxy.spawnParticle("abstractorLarge", par1World, (double)(var7 + var10), (double)var8, (double)(var9 + var11), 0.0D, 0.0D, 0.0D);
                var8 = (float)par3 + 0.0F + par5Random.nextFloat() * 6.0F / 16.0F + 0.5F;
                var11 = par5Random.nextFloat() * 0.6F - 0.3F;
                MetallurgyCore.proxy.spawnParticle("abstractorSmall", par1World, (double)(var7 + var10), (double)var8, (double)(var9 + var11), 0.0D, 0.0D, 0.0D);
            }
            else if (var6 == 2)
            {
            	MetallurgyCore.proxy.spawnParticle("abstractorLarge", par1World, (double)(var7 + var11), (double)var8, (double)(var9 - var10), 0.0D, 0.0D, 0.0D);
                var8 = (float)par3 + 0.0F + par5Random.nextFloat() * 6.0F / 16.0F + 0.5F;
                var11 = par5Random.nextFloat() * 0.6F - 0.3F;
                MetallurgyCore.proxy.spawnParticle("abstractorSmall", par1World, (double)(var7 + var11), (double)var8, (double)(var9 - var10), 0.0D, 0.0D, 0.0D);
            }
            else if (var6 == 3)
            {
            	MetallurgyCore.proxy.spawnParticle("abstractorLarge", par1World, (double)(var7 + var11), (double)var8, (double)(var9 + var10), 0.0D, 0.0D, 0.0D);
                var8 = (float)par3 + 0.0F + par5Random.nextFloat() * 6.0F / 16.0F + 0.5F;
                var11 = par5Random.nextFloat() * 0.6F - 0.3F;
                MetallurgyCore.proxy.spawnParticle("abstractorSmall", par1World, (double)(var7 + var11), (double)var8, (double)(var9 + var10), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    @Override
    public int getBlockTextureFromSide(int par1)
    {
        return par1 == 1 ? this.blockIndexInTexture + 17 : (par1 == 0 ? this.blockIndexInTexture + 17 : (par1 == 3 ? this.blockIndexInTexture - 1 : this.blockIndexInTexture));
    }

    /**
     * Called upon block activation (left or right click on the block.). The three integers represent x,y,z of the
     * block.
     */
    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (par1World.isRemote)
        {
            return true;
        }

        if(par5EntityPlayer.isSneaking())
        {
        	return false;
        }

        TileEntityAbstractor var6 = (TileEntityAbstractor)par1World.getBlockTileEntity(par2, par3, par4);

        if (var6 != null)
        {
        	GuiRegistry.openGui("Abstractor", MetallurgyMachines.instance, par5EntityPlayer, par1World, par2, par3, par4);
        }

        return true;

    }

    /**
     * Returns the TileEntity used by this block.
     */
    @Override
    public TileEntity createNewTileEntity(World var1)
    {
        return new TileEntityAbstractor();
    }

    /**
     * Called when the block is placed in the world.
     */
    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
    {
        int var6 = MathHelper.floor_double((double)(par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (var6 == 0)
        {
            ((TileEntityAbstractor)(par1World.getBlockTileEntity(par2, par3, par4))).setDirection(2);
        }

        if (var6 == 1)
        {
            ((TileEntityAbstractor)(par1World.getBlockTileEntity(par2, par3, par4))).setDirection(5);
        }

        if (var6 == 2)
        {
            ((TileEntityAbstractor)(par1World.getBlockTileEntity(par2, par3, par4))).setDirection(3);
        }

        if (var6 == 3)
        {
            ((TileEntityAbstractor)(par1World.getBlockTileEntity(par2, par3, par4))).setDirection(4);
        }
        
        
        TileEntityAbstractor var5 = (TileEntityAbstractor)par1World.getBlockTileEntity(par2, par3, par4);
        int metadata = par1World.getBlockMetadata(par2, par3, par4);
        var5.setSpeed((int) (20 * MetallurgyMachines.extractorSpeeds[metadata]));
        
    }

    /**s
     * Called whenever the block is removed.
     */
    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        if (!keepFurnaceInventory)
        {
            TileEntityAbstractor var5 = (TileEntityAbstractor)par1World.getBlockTileEntity(par2, par3, par4);

            if (var5 != null)
            {
                for (int var6 = 0; var6 < var5.getSizeInventory(); ++var6)
                {
                    ItemStack var7 = var5.getStackInSlot(var6);

                    if (var7 != null)
                    {
                        float var8 = this.rand.nextFloat() * 0.8F + 0.1F;
                        float var9 = this.rand.nextFloat() * 0.8F + 0.1F;
                        float var10 = this.rand.nextFloat() * 0.8F + 0.1F;

                        while (var7.stackSize > 0)
                        {
                            int var11 = this.rand.nextInt(21) + 10;

                            if (var11 > var7.stackSize)
                            {
                                var11 = var7.stackSize;
                            }

                            var7.stackSize -= var11;
                            EntityItem var12 = new EntityItem(par1World, (double)((float)par2 + var8), (double)((float)par3 + var9), (double)((float)par4 + var10), new ItemStack(var7.itemID, var11, var7.getItemDamage()));

                            if (var7.hasTagCompound())
                            {
                                var12.getEntityItem().setTagCompound((NBTTagCompound)var7.getTagCompound().copy());
                            }

                            float var13 = 0.05F;
                            var12.motionX = (double)((float)this.rand.nextGaussian() * var13);
                            var12.motionY = (double)((float)this.rand.nextGaussian() * var13 + 0.2F);
                            var12.motionZ = (double)((float)this.rand.nextGaussian() * var13);
                            par1World.spawnEntityInWorld(var12);
                        }
                    }
                }
            }
        }

        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (int n = 0; n < 11; n++) {
			par3List.add(new ItemStack(this, 1, n));
		}
	}
}