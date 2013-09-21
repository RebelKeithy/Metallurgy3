package rebelkeithy.mods.metallurgy.machines.mint;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiMintStorage extends GuiContainer
{
    /**
     * window height is calculated with this values, the more rows, the heigher
     */
    private int inventoryRows = 0;
    private final ResourceLocation background = new ResourceLocation("Metallurgy:textures/guis/mintstorage.png");

    public GuiMintStorage(InventoryPlayer playerInv, TileEntity chestInv)
    {
        super(new ContainerMintStorage(playerInv, chestInv));
        allowUserInput = false;

        final short var3 = 222;
        final int var4 = var3 - 108;
        // this.inventoryRows = chestInv.getSizeInventory() / 9;
        inventoryRows = 2;
        ySize = var4 + inventoryRows * 18;

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
        final int imageWidth = 176;
        final int imageHeight = 168;

        final int var5 = width / 2 - imageWidth / 2;
        final int var6 = height / 2 - imageHeight / 2;

        drawTexturedModalRect(var5, var6, 0, 0, imageWidth, imageHeight);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everythin in front of the
     * items)
     */
    protected void drawGuiContainerForegroundLayer()
    {
        // this.fontRenderer.drawString(StatCollector.translateToLocal(this.lowerChestInventory.getInvName()),
        // 8, 6, 4210752);
        // this.fontRenderer.drawString(StatCollector.translateToLocal(this.upperChestInventory.getInvName()),
        // 8, this.ySize - 96 + 2, 4210752);
    }
}
