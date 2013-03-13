package rebelkeithy.mods.metallurgy.machines.forge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import rebelkeithy.mods.guiregistry.GuiRegistry;
import rebelkeithy.mods.metallurgy.machines.ConfigMachines;
import rebelkeithy.mods.metallurgy.machines.MetallurgyMachines;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockNetherForge extends BlockContainer
{
    /**
     * Is the random generator used by furnace to drop the inventory contents in random directions.
     */
    private Random furnaceRand = new Random();

    /** True if this is an active furnace, false if idle */
    private final boolean isActive;
    
    private static int front = 0;
    private static int side = 5;
    private static int active = 10;
    private static int top = 15;
    private static int bottom = 17;
    
    private Map<Integer, Icon[]> iconMap;

    /**
     * This flag is used to prevent the furnace inventory to be dropped upon block removal, is used internally when the
     * furnace block changes from idle to active and vice-versa.
     */
    private static boolean keepFurnaceInventory = false;

    public BlockNetherForge(int par1, boolean par2)
    {
        super(par1, Material.rock);
        this.isActive = par2;
        //this.setCreativeTab(MetallurgyNether.creativeTab);
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return ConfigMachines.forgeID;
    }
    
    @Override
	public int damageDropped(int metadata)
    {
    	return (metadata < 8) ? metadata : metadata - 8;
    }

    /**
     * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
     */
    @Override
    public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
    	TileEntity tileEntity = par1IBlockAccess.getBlockTileEntity(par2, par3, par4);
    	int metadata = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
    	int type = (metadata < 8) ? metadata : metadata - 8;
    	int	dir = (tileEntity instanceof TileEntityNetherForge) ? ((TileEntityNetherForge)tileEntity).getDirection() : 0;
    	int time = (tileEntity instanceof TileEntityNetherForge) ? (((TileEntityNetherForge)tileEntity).furnaceCookTime * 10) % 2 : 0;

    	int	fuel = (tileEntity instanceof TileEntityNetherForge) ? ((TileEntityNetherForge)tileEntity).getScaledFuel(4) : 0;
    	boolean isBurning = (tileEntity instanceof TileEntityNetherForge) ? ((TileEntityNetherForge)tileEntity).isBurning() : false;
    	
    	
    	//par5 = ((TileEntityMetalFurnace)(par1IBlockAccess.getBlockTileEntity(par2, par3, par4))).getDirection();
        if (par5 == 1)
        {
            return iconMap.get(type)[top];
        } 
        else if(par5 == 0)
        {
        	return iconMap.get(type)[bottom];
        }
        else
        {
            if(par5 != dir)
            	return iconMap.get(type)[side+fuel];
            else if(isBurning)
            	return iconMap.get(type)[active+fuel];
            else
            	return iconMap.get(type)[front+fuel];
        }
    }

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    @Override
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
    	int metadata = par1World.getBlockMetadata(par2, par3, par4);
    	TileEntityNetherForge tef = (TileEntityNetherForge) par1World.getBlockTileEntity(par2, par3, par4);
        if (tef.isBurning())
        {
            //int var6 = par1World.getBlockMetadata(par2, par3, par4);
            int var6 = ((TileEntityNetherForge)(par1World.getBlockTileEntity(par2, par3, par4))).getDirection();
            float var7 = (float)par2 + 0.5F;
            float var8 = (float)par3 + 0.0F + par5Random.nextFloat() * 6.0F / 16.0F;
            float var9 = (float)par4 + 0.5F;
            float var10 = 0.52F;
            float var11 = par5Random.nextFloat() * 0.6F - 0.3F;

            if (var6 == 4)
            {
                par1World.spawnParticle("smoke", (double)(var7 - var10), (double)var8, (double)(var9 + var11), 0.0D, 0.0D, 0.0D);
                par1World.spawnParticle("flame", (double)(var7 - var10), (double)var8, (double)(var9 + var11), 0.0D, 0.0D, 0.0D);
            }
            else if (var6 == 5)
            {
                par1World.spawnParticle("smoke", (double)(var7 + var10), (double)var8, (double)(var9 + var11), 0.0D, 0.0D, 0.0D);
                par1World.spawnParticle("flame", (double)(var7 + var10), (double)var8, (double)(var9 + var11), 0.0D, 0.0D, 0.0D);
            }
            else if (var6 == 2)
            {
                par1World.spawnParticle("smoke", (double)(var7 + var11), (double)var8, (double)(var9 - var10), 0.0D, 0.0D, 0.0D);
                par1World.spawnParticle("flame", (double)(var7 + var11), (double)var8, (double)(var9 - var10), 0.0D, 0.0D, 0.0D);
            }
            else if (var6 == 3)
            {
                par1World.spawnParticle("smoke", (double)(var7 + var11), (double)var8, (double)(var9 + var10), 0.0D, 0.0D, 0.0D);
                par1World.spawnParticle("flame", (double)(var7 + var11), (double)var8, (double)(var9 + var10), 0.0D, 0.0D, 0.0D);
            }
        }
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
        
		ItemStack currentItem = par5EntityPlayer.inventory.getCurrentItem();
		
        TileEntityNetherForge var6 = (TileEntityNetherForge)par1World.getBlockTileEntity(par2, par3, par4);

    	par5EntityPlayer.sendChatToPlayer("Fuel: " + var6.fuel);
    	par5EntityPlayer.sendChatToPlayer("MaxFuel: " + var6.maxFuel);
    	
    	if(currentItem != null)
    	{
    		if(currentItem.itemID == Item.bucketLava.itemID)
    		{
            	if(var6.fuel == var6.maxFuel)
            		return false;
            	
    			var6.addFuelBucket();
    			if(!par5EntityPlayer.capabilities.isCreativeMode)
    				par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, new ItemStack(Item.bucketEmpty, 1));
    			return true;
    		}
    		if(currentItem.itemID == Item.bucketEmpty.itemID)
    		{
            	if(var6.fuel < 1000)
            		return false;
            	
    			var6.addTakeBucket();
    			if(!par5EntityPlayer.capabilities.isCreativeMode)
    				par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, new ItemStack(Item.bucketLava, 1));
    			return true;
    		}
    		
    		/*
			LiquidStack liquid = LiquidManager.getLiquidForFilledItem(currentItem);
			
            if(liquid != null)
            {
            	if(var6.fuel == var6.maxFuel || liquid.itemID == Block.lavaMoving.blockID)
            		return false;
            	
            	if(!par5EntityPlayer.capabilities.isCreativeMode)
            		par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, Utils.consumeItem(currentItem));
            	
            	var6.addFuelBucket();
            	return true;
            }
            */
    	}

        if (var6 != null)
        {
            GuiRegistry.openGui("NetherForge", MetallurgyMachines.instance, par5EntityPlayer, par1World, par2, par3, par4);
        }

        return true;
    }

    /**
     * Update which block ID the furnace is using depending on whether or not it is burning
     */
    public static void updateFurnaceBlockState(boolean par0, World par1World, int par2, int par3, int par4)
    {
        int metadata = par1World.getBlockMetadata(par2, par3, par4);
        keepFurnaceInventory = true;

        if (par0 && metadata < 8)
        {
        	par1World.setBlockMetadataWithNotify(par2, par3, par4, metadata + 8, 2);
        }
        else if(!par0 && metadata >= 8)
        {
        	par1World.setBlockMetadataWithNotify(par2, par3, par4, metadata - 8, 2);
        }

        keepFurnaceInventory = false;
    }

    /**
     * Returns the TileEntity used by this block.
     */
    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileEntityNetherForge();
    }

    /**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
    @Override
    public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) 
    {
    }
    
    /**
     * Called when the block is placed in the world.
     */
    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack par6ItemStack)
    {
        int var6 = MathHelper.floor_double((double)(par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (var6 == 0)
        {
            //par1World.setBlockMetadataWithNotify(par2, par3, par4, 2);
            ((TileEntityNetherForge)(par1World.getBlockTileEntity(par2, par3, par4))).setDirection(2);
        }

        if (var6 == 1)
        {
            //par1World.setBlockMetadataWithNotify(par2, par3, par4, 5);
            ((TileEntityNetherForge)(par1World.getBlockTileEntity(par2, par3, par4))).setDirection(5);
        }

        if (var6 == 2)
        {
            //par1World.setBlockMetadataWithNotify(par2, par3, par4, 3);
            ((TileEntityNetherForge)(par1World.getBlockTileEntity(par2, par3, par4))).setDirection(3);
        }

        if (var6 == 3)
        {
            //par1World.setBlockMetadataWithNotify(par2, par3, par4, 4);
            ((TileEntityNetherForge)(par1World.getBlockTileEntity(par2, par3, par4))).setDirection(4);
        }
        
        
        TileEntityNetherForge var5 = (TileEntityNetherForge)par1World.getBlockTileEntity(par2, par3, par4);
        int metadata = par1World.getBlockMetadata(par2, par3, par4);
        
        var5.setSpeed((int)(20 * ConfigMachines.forgeSpeeds[metadata]));
        var5.setMaxBuckets((int)(ConfigMachines.firgeBuckets[metadata]));
    }

    /**
     * Called whenever the block is removed.
     */
    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {        	
    	boolean spawnLava = false;
        if(((TileEntityNetherForge)par1World.getBlockTileEntity(par2, par3, par4)).getFuelScaled(2) > 0)
        	spawnLava = true;
        
        if (!keepFurnaceInventory)
        {
            TileEntityNetherForge var5 = (TileEntityNetherForge)par1World.getBlockTileEntity(par2, par3, par4);

            if (var5 != null)
            {
                for (int var6 = 0; var6 < var5.getSizeInventory(); ++var6)
                {
                    ItemStack var7 = var5.getStackInSlot(var6);

                    if (var7 != null)
                    {
                        float var8 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;
                        float var9 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;
                        float var10 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;

                        while (var7.stackSize > 0)
                        {
                            int var11 = this.furnaceRand.nextInt(21) + 10;

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
                            var12.motionX = (double)((float)this.furnaceRand.nextGaussian() * var13);
                            var12.motionY = (double)((float)this.furnaceRand.nextGaussian() * var13 + 0.2F);
                            var12.motionZ = (double)((float)this.furnaceRand.nextGaussian() * var13);
                            par1World.spawnEntityInWorld(var12);
                        }
                    }
                }
            }
        }
    

        super.breakBlock(par1World, par2, par3, par4, par5, par6);
        if(ConfigMachines.smelterDropsLava && spawnLava)	
        	par1World.setBlockAndMetadataWithNotify(par2, par3, par4, Block.lavaMoving.blockID, 0, 2);
    }
    
    @SideOnly(Side.CLIENT)
    public void func_94332_a(IconRegister par1IconRegister)
    {
    	iconMap = new HashMap<Integer, Icon[]>();
    	for(int i = 0; i < 10; i++)
    	{
    		Icon[] iArray = new Icon[18];
    		for(int j = 0; j < 5; j++)
    		{
    			iArray[front + j] = par1IconRegister.func_94245_a("Metallurgy:Smelter" + i + "Front" + j);
        		iArray[side + j] = par1IconRegister.func_94245_a("Metallurgy:Smelter" + i + "Side" + j);
        		iArray[active + j] = par1IconRegister.func_94245_a("Metallurgy:Smelter" + i + "Active" + j);
    		}
    		iArray[top] = par1IconRegister.func_94245_a("Metallurgy:Smelter" + i + "Top0");
    		iArray[top+1] = par1IconRegister.func_94245_a("Metallurgy:Smelter" + i + "Top1");
    		iArray[bottom] = par1IconRegister.func_94245_a("Metallurgy:Smelter" + i + "Bottom");
    		iconMap.put(i, iArray);
    	}
    }

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (int n = 0; n < 8; n++) {
			par3List.add(new ItemStack(this, 1, n));
		}
	}
}
