//package rebelkeithy.mods.metallurgy.machines.xptank;
//
//import net.minecraft.client.gui.inventory.GuiContainer;
//import net.minecraft.entity.player.InventoryPlayer;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.StatCollector;
//
//import org.lwjgl.opengl.GL11;
//
//public class GuiXpTank extends GuiContainer
//{
//    private final TileEntityXpTank tileEntity;
//
//    private final ResourceLocation background = new ResourceLocation("Metallurgy:textures/guis/lavafurnace.png");
//
//    private static String[] names =
//    { "" };
//    private final int type = 0;
//
//    public GuiXpTank(InventoryPlayer par1InventoryPlayer, TileEntity par2TileEntity)
//    {
//        super(new ContainerXpTank(par1InventoryPlayer, par2TileEntity));
//        tileEntity = (TileEntityXpTank) par2TileEntity;
//        // this.type = tileEntity.getType();
//    }
//
//    /**
//     * Draw the background layer for the GuiContainer (everything behind the
//     * items)
//     */
//    @Override
//    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
//    {
//        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//        // this.mc.renderEngine.bindTexture("/mods/Metallurgy/textures/guis/lavafurnace.png");
//        // //Calls bindTexture
//        mc.getTextureManager().bindTexture(background);
//        final int leftSide = (width - xSize) / 2;
//        final int topSide = (height - ySize) / 2;
//        drawTexturedModalRect(leftSide, topSide, 0, 0, xSize, ySize);
//        final int fuelHeight = tileEntity.getFuelScaled(63);
//        drawTexturedModalRect(leftSide + 144, topSide + 11 + 63 - fuelHeight, 176, 31 + 63 - fuelHeight, 17, 31 + fuelHeight);
//    }
//
//    /**
//     * Draw the foreground layer for the GuiContainer (everything in front of
//     * the items)
//     */
//    protected void drawGuiContainerForegroundLayer()
//    {
//        fontRenderer.drawString(names[type] + " Xp Tank", 30 - (names[type] + " Xp Tank").length() / 2, 6, 4210752);
//        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
//    }
//}
