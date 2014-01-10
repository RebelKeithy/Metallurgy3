//package rebelkeithy.mods.metallurgy.machines.xptank;
//
//import net.minecraft.block.material.Material;
//import net.minecraft.client.renderer.texture.IconRegister;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.world.World;
//import rebelkeithy.mods.metallurgy.machines.BlockMachineBase;
//import cpw.mods.fml.relauncher.Side;
//import cpw.mods.fml.relauncher.SideOnly;
//
//public class BlockXpTank extends BlockMachineBase
//{
//
//    public BlockXpTank(int par1)
//    {
//        super(par1, Material.iron);
//        setGui("XpTank");
//    }
//
//    @Override
//    public TileEntity createNewTileEntity(World world)
//    {
//        return new TileEntityXpTank();
//    }
//
//    @Override
//    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
//    {
//        if (par5EntityPlayer.isSneaking())
//        {
//            final TileEntityXpTank te = (TileEntityXpTank) par1World.getBlockTileEntity(par2, par3, par4);
//            te.releaseXP();
//            return true;
//        }
//        else
//        {
//            return super.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9);
//        }
//    }
//
//    @Override
//    @SideOnly(Side.CLIENT)
//    public void registerIcons(IconRegister par1IconRegister)
//    {
//        for (int i = 0; i < 1; i++)
//        {
//            setFrontIcon(par1IconRegister.registerIcon("Metallurgy:machines/abstractor/Abstractor" + i + "Front"), i, false);
//            setFrontIcon(par1IconRegister.registerIcon("Metallurgy:machines/abstractor/Abstractor" + i + "Active"), i, true);
//            setSideIcon(par1IconRegister.registerIcon("Metallurgy:machines/abstractor/Abstractor" + i + "Side"), i);
//            setTopIcon(par1IconRegister.registerIcon("Metallurgy:machines/abstractor/Abstractor" + i + "Top"), i);
//            setBottomIcon(par1IconRegister.registerIcon("Metallurgy:machines/abstractor/Abstractor" + i + "Bottom"), i);
//        }
//    }
//}
