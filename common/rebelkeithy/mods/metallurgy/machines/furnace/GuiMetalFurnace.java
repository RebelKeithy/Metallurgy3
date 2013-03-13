package rebelkeithy.mods.metallurgy.machines.furnace;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;
public class GuiMetalFurnace extends GuiContainer
{
    private TileEntityMetalFurnace furnaceInventory;

    private String[] names = {"Copper", "Bronze", "Iron", "Steel"};
    private int type;
    
    public GuiMetalFurnace(InventoryPlayer par1InventoryPlayer, TileEntity par2TileEntityFurnace)
    {
        super(new ContainerMetalFurnace(par1InventoryPlayer, (TileEntityMetalFurnace) par2TileEntityFurnace));
        this.furnaceInventory = (TileEntityMetalFurnace) par2TileEntityFurnace;
        type = furnaceInventory.getType();
    }

    /**
     * Draw the foreground layer for the GuiContainer (everythin in front of the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRenderer.drawString(StatCollector.translateToLocal(names[type] + " Furnace"), this.xSize/2 - ((names[type] + " Furnace").length()*5)/2, 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.func_98187_b("/gui/furnace.png"); //Calls bindTexture
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
        int var7;

        if (this.furnaceInventory.isBurning())
        {
            var7 = this.furnaceInventory.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(var5 + 56, var6 + 36 + 12 - var7, 176, 12 - var7, 14, var7 + 2);
        }

        var7 = this.furnaceInventory.getCookProgressScaled(24);
        this.drawTexturedModalRect(var5 + 79, var6 + 34, 176, 14, var7 + 1, 16);
    }
}
