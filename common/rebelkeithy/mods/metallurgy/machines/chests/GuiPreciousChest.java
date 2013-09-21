package rebelkeithy.mods.metallurgy.machines.chests;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiPreciousChest extends GuiContainer
{
    /**
     * window height is calculated with this values, the more rows, the heigher
     */
    private int inventoryRows = 0;
    private int inventoryCols = 0;

    private ResourceLocation background = new ResourceLocation("Metallurgy:textures/guis/ironcontainer.png");
    private final ResourceLocation backgroundBrass = new ResourceLocation("Metallurgy:textures/guis/ironcontainer.png");
    private final ResourceLocation backgroundSilver = new ResourceLocation("Metallurgy:textures/guis/silvercontainer.png");
    private final ResourceLocation backgroundGold = new ResourceLocation("Metallurgy:textures/guis/goldcontainer.png");
    private final ResourceLocation backgroundElectrum = new ResourceLocation("Metallurgy:textures/guis/electrumcontainer.png");
    private final ResourceLocation backgroundPlatinum = new ResourceLocation("Metallurgy:textures/guis/diamondcontainer.png");

    public GuiPreciousChest(InventoryPlayer playerInv, TileEntity chestInv)
    {
        super(new ContainerPreciousChest(playerInv, chestInv));
        allowUserInput = false;

        final short var3 = 222;
        final int var4 = var3 - 108;
        // this.inventoryRows = chestInv.getSizeInventory() / 9;
        inventoryRows = ((TileEntityPreciousChest) chestInv).getNumRows();
        inventoryCols = ((TileEntityPreciousChest) chestInv).getNumCols();
        ySize = var4 + inventoryRows * 18;
        xSize = inventoryCols * 18 + 20;

        final int type = ((TileEntityPreciousChest) chestInv).getType();

        background = backgroundBrass;
        switch (type)
        {
        case 0:
        {
            background = backgroundBrass;
            break;
        }
        case 1:
        {
            background = backgroundSilver;
            break;
        }
        case 2:
        {
            background = backgroundGold;
            break;
        }
        case 3:
        {
            background = backgroundElectrum;
            break;
        }
        case 4:
        {
            background = backgroundPlatinum;
            break;
        }
        }

    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the
     * items)
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        // this.mc.renderEngine.bindTexture(image); // Calls bindTexture
        mc.getTextureManager().bindTexture(background);
        final int imageWidth = 11 + 18 * inventoryCols + 11;
        final int imageHeight = 7 + 18 * inventoryRows + 4 + 18 * 3 + 4 + 18 + 7;

        final int var5 = width / 2 - imageWidth / 2;
        final int var6 = height / 2 - imageHeight / 2;

        drawTexturedModalRect(var5, var6, 0, 0, imageWidth, imageHeight);
        // this.drawTexturedModalRect(var5, var6 + this.inventoryRows * 18 + 17,
        // 0, 126, this.xSize, 96);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everythin in front of the
     * items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        // this.fontRenderer.drawString(StatCollector.translateToLocal(this.lowerChestInventory.getInvName()),
        // 8, 6, 4210752);
        // this.fontRenderer.drawString(StatCollector.translateToLocal(this.upperChestInventory.getInvName()),
        // 8, this.ySize - 96 + 2, 4210752);
    }
}
