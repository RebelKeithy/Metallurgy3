package rebelkeithy.mods.metallurgy.machines.abstractor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import rebelkeithy.mods.keithyutils.guiregistry.GuiRegistry;
import rebelkeithy.mods.metallurgy.core.MetallurgyCore;
import rebelkeithy.mods.metallurgy.machines.BlockMachineBase;
import rebelkeithy.mods.metallurgy.machines.ConfigMachines;
import rebelkeithy.mods.metallurgy.machines.MetallurgyMachines;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class BlockAbstractor extends BlockMachineBase
{
    /**
     * Is the random generator used by furnace to drop the inventory contents in random directions.
     */
    private Random rand = new Random();

    /**
     * This flag is used to prevent the furnace inventory to be dropped upon block removal, is used internally when the
     * furnace block changes from idle to active and vice-versa.
     */
    private static boolean keepFurnaceInventory = false;

    public BlockAbstractor(int par1, boolean par2)
    {
        super(par1, Material.rock);
        //setRequiresSelfNotify();
        this.setGui("Abstractor");
        this.setNumSubtypes(10);
    }
    
    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
    	try
    	{
	        TileEntityAbstractor tea = (TileEntityAbstractor) world.getBlockTileEntity(x, y, z);
	        if(tea != null && tea.isActive())
	        	return 5;
		}
	    catch(ClassCastException e)
	    {
	    	return 0;
	    }
    	
        return 0;
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
        if (te.isActive())
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
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLiving, ItemStack par6ItemStack)
    {
        super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLiving, par6ItemStack);
        
        TileEntityAbstractor tileEntity = (TileEntityAbstractor)par1World.getBlockTileEntity(par2, par3, par4);
        int metadata = par1World.getBlockMetadata(par2, par3, par4);
        tileEntity.setSpeed((int) (20 * ConfigMachines.extractorSpeeds[metadata]));
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
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
    	for(int i = 0; i < 10; i++)
    	{
    		Icon icon;
    		this.setFrontIcon(par1IconRegister.registerIcon("Metallurgy:machines/abstractor/Abstractor" + i + "Front"), i, false);
    		this.setFrontIcon(par1IconRegister.registerIcon("Metallurgy:machines/abstractor/Abstractor" + i + "Active"), i, true);
    		this.setSideIcon(par1IconRegister.registerIcon("Metallurgy:machines/abstractor/Abstractor" + i + "Side"), i);
    		this.setTopIcon(par1IconRegister.registerIcon("Metallurgy:machines/abstractor/Abstractor" + i + "Top"), i);
    		this.setBottomIcon(par1IconRegister.registerIcon("Metallurgy:machines/abstractor/Abstractor" + i + "Bottom"), i);
    	}
    }
}