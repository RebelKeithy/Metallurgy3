package rebelkeithy.mods.metallurgy.machines.forge;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;
public class GuiNetherForge extends GuiContainer
{
    private TileEntityNetherForge furnaceInventory;
    public static String[] names = {"Ignatius", "Shadow Iron", "Shadow Steel", "Vyroxeres", "Inolashite", "Kalendrite", "Vulcanite", "Sanguinite"};
    private int type;
    
    public GuiNetherForge(InventoryPlayer par1InventoryPlayer, TileEntity par2TileEntityFurnace)
    {
        super(new ContainerNetherForge(par1InventoryPlayer, par2TileEntityFurnace));
        this.furnaceInventory = (TileEntityNetherForge) par2TileEntityFurnace;
        this.type = furnaceInventory.getType();
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer()
    {
        this.fontRenderer.drawString(names[type] + " Smelter", 30 - (names[type] + " Smelter").length()/2, 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.func_98187_b("/mods/Metallurgy/textures/guis/lavafurnace.png"); //Calls bindTexture
        int leftSide = (this.width - this.xSize) / 2;
        int topSide = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(leftSide, topSide, 0, 0, this.xSize, this.ySize);
        int var7;

        int fuelHeight = this.furnaceInventory.getFuelScaled(63) ;
        this.drawTexturedModalRect(leftSide + 144, topSide + 11 + 63 - fuelHeight, 176, 31 + 63 - fuelHeight, 17, 31 + fuelHeight);

        var7 = this.furnaceInventory.getCookProgressScaled(24);
        this.drawTexturedModalRect(leftSide + 59, topSide + 33, 176, 14, var7 + 1, 16);
    }
}
