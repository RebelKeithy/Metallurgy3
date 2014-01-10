package rebelkeithy.mods.metallurgy.machines.furnace;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class GuiMetalFurnace extends GuiContainer
{
    private final TileEntityMetalFurnace furnaceInventory;

    private final String[] names =
    { "Copper", "Bronze", "Iron", "Steel" };
    private final int type;

    private final ResourceLocation background = new ResourceLocation("textures/gui/container/furnace.png");

    public GuiMetalFurnace(InventoryPlayer par1InventoryPlayer, TileEntity par2TileEntityFurnace)
    {
        super(new ContainerMetalFurnace(par1InventoryPlayer, par2TileEntityFurnace));
        furnaceInventory = (TileEntityMetalFurnace) par2TileEntityFurnace;
        type = furnaceInventory.getType();
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the
     * items)
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        // this.mc.renderEngine.bindTexture("/gui/furnace.png"); //Calls
        // bindTexture
        mc.getTextureManager().bindTexture(background);
        final int var5 = (width - xSize) / 2;
        final int var6 = (height - ySize) / 2;
        drawTexturedModalRect(var5, var6, 0, 0, xSize, ySize);
        int var7;

        if (furnaceInventory.isBurning())
        {
            var7 = furnaceInventory.getBurnTimeRemainingScaled(12);
            drawTexturedModalRect(var5 + 56, var6 + 36 + 12 - var7, 176, 12 - var7, 14, var7 + 2);
        }

        var7 = furnaceInventory.getCookProgressScaled(24);
        drawTexturedModalRect(var5 + 79, var6 + 34, 176, 14, var7 + 1, 16);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everythin in front of the
     * items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        fontRenderer.drawString(StatCollector.translateToLocal(names[type] + " Furnace"), xSize / 2 - (names[type] + " Furnace").length() * 5 / 2, 6, 4210752);
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
    }
}
