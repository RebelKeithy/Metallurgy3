package rebelkeithy.mods.metallurgy.machines.abstractor;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class GuiAbstractor extends GuiContainer
{
    private TileEntityAbstractor abstractorInventory;

    String[] names = {"Prometheum", "Deep Iron", "Black Steel", "Oureclase", "Aredrite", "Mithril", "Haderoth", "Orichalcum", "Adamantine", "Atlarus", "Tartarite"};
    int type;
    
    public GuiAbstractor(InventoryPlayer par1InventoryPlayer, TileEntity par2TileEntityFurnace)
    {
        super(new ContainerAbstractor(par1InventoryPlayer, par2TileEntityFurnace));
        this.abstractorInventory = (TileEntityAbstractor) par2TileEntityFurnace;
        type = abstractorInventory.getType();
    }

    /**
     * Draw the foreground layer for the GuiContainer (everythin in front of the items)
     */
    protected void drawGuiContainerForegroundLayer()
    {
        this.fontRenderer.drawString(StatCollector.translateToLocal(names[type] + " Abstractor"), this.xSize/2 - ((names[type] + " Abstractor").length()*5)/2, 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture("/mods/Metallurgy/textures/guis/abstracter.png"); // Calls bindTexture
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
        int var7;

        if (this.abstractorInventory.isBurning())
        {
            var7 = this.abstractorInventory.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(var5 + 56, var6 + 36 + 12 - var7, 176, 12 - var7, 14, var7 + 2);
        }

        var7 = this.abstractorInventory.getCookProgressScaled(24);
        this.drawTexturedModalRect(var5 + 79, var6 + 34, 176, 14, var7 + 1, 16);
    }
}