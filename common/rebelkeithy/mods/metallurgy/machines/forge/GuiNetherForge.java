package rebelkeithy.mods.metallurgy.machines.forge;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class GuiNetherForge extends GuiContainer
{
    private final TileEntityNetherForge furnaceInventory;
    public static String[] names =
    { "Ignatius", "Shadow Iron", "Shadow Steel", "Vyroxeres", "Inolashite", "Kalendrite", "Vulcanite", "Sanguinite" };
    private final int type;
    private final ResourceLocation background = new ResourceLocation("Metallurgy:textures/guis/lavafurnace.png");

    public GuiNetherForge(InventoryPlayer par1InventoryPlayer, TileEntity par2TileEntityFurnace)
    {
        super(new ContainerNetherForge(par1InventoryPlayer, par2TileEntityFurnace));
        furnaceInventory = (TileEntityNetherForge) par2TileEntityFurnace;
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
        // this.mc.renderEngine.bindTexture("/mods/Metallurgy/textures/guis/lavafurnace.png");
        // //Calls bindTexture
        mc.getTextureManager().bindTexture(background);
        final int leftSide = (width - xSize) / 2;
        final int topSide = (height - ySize) / 2;
        drawTexturedModalRect(leftSide, topSide, 0, 0, xSize, ySize);
        int var7;

        final int fuelHeight = furnaceInventory.getFuelScaled(63);
        drawTexturedModalRect(leftSide + 144, topSide + 11 + 63 - fuelHeight, 176, 31 + 63 - fuelHeight, 17, 31 + fuelHeight);

        var7 = furnaceInventory.getCookProgressScaled(24);
        drawTexturedModalRect(leftSide + 59, topSide + 33, 176, 14, var7 + 1, 16);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of
     * the items)
     */
    protected void drawGuiContainerForegroundLayer()
    {
        fontRenderer.drawString(names[type] + " Smelter", 30 - (names[type] + " Smelter").length() / 2, 6, 4210752);
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
    }
}
