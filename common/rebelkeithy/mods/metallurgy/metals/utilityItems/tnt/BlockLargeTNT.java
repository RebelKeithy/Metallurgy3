package rebelkeithy.mods.metallurgy.metals.utilityItems.tnt;

import net.minecraft.block.BlockTNT;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import rebelkeithy.mods.metallurgy.metals.MetallurgyMetals;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLargeTNT extends BlockTNT
{

    private Icon field_94393_a;
    private Icon field_94392_b;

    public BlockLargeTNT(int par1)
    {
        super(par1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int par1, int par2)
    {
        return par1 == 0 ? field_94392_b : par1 == 1 ? field_94393_a : blockIcon;
    }

    public boolean isActivator(int id)
    {
        if (id == Item.flintAndSteel.itemID || id == MetallurgyMetals.match.itemID || id == MetallurgyMetals.magnesiumIgniter.itemID)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (par5EntityPlayer.getCurrentEquippedItem() != null && isActivator(par5EntityPlayer.getCurrentEquippedItem().itemID))
        {
            onBlockDestroyedByPlayer(par1World, par2, par3, par4, 1);
            par1World.setBlockToAir(par2, par3, par4);
            return true;
        }
        else
        {
            return super.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9);
        }
    }

    /**
     * Called upon the block being destroyed by an explosion
     */
    @Override
    public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4, Explosion par5Explosion)
    {
        if (!par1World.isRemote)
        {
            final EntityLargeTNTPrimed var5 = new EntityLargeTNTPrimed(par1World, par2 + 0.5F, par3 + 0.5F, par4 + 0.5F);
            var5.fuse = par1World.rand.nextInt(var5.fuse / 4) + var5.fuse / 8;
            par1World.spawnEntityInWorld(var5);
        }
    }
    
    @Override
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        if (par5Entity instanceof EntityArrow && !par1World.isRemote)
        {
            EntityArrow entityarrow = (EntityArrow)par5Entity;

            if (entityarrow.isBurning())
            {
            	onBlockDestroyedByPlayer(par1World, par2, par3, par4, 1);
                par1World.setBlockToAir(par2, par3, par4);
            }
        }
    }

    /**
     * Called right before the block is destroyed by a player. Args: world, x,
     * y, z, metaData
     */
    @Override
    public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5)
    {
        if (!par1World.isRemote)
        {
            if ((par5 & 1) == 1)
            {
                final EntityLargeTNTPrimed var6 = new EntityLargeTNTPrimed(par1World, par2 + 0.5F, par3 + 0.5F, par4 + 0.5F);
                par1World.spawnEntityInWorld(var6);
                par1World.playSoundAtEntity(var6, "random.fuse", 1.0F, 1.0F);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        blockIcon = par1IconRegister.registerIcon("Metallurgy:Utility/HETNTSide");
        field_94393_a = par1IconRegister.registerIcon("Metallurgy:Utility/HETNTTop");
        field_94392_b = par1IconRegister.registerIcon("Metallurgy:Utility/HETNTBottom");
    }

}