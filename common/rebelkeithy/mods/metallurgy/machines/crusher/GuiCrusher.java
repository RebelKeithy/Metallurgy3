package rebelkeithy.mods.metallurgy.machines.crusher;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;
public class GuiCrusher extends GuiContainer
{
    private TileEntityCrusher furnaceInventory;
    private String[] names = {"Stone", "Copper", "Bronze", "Iron", "Steel"};
    private int type;
    
    public GuiCrusher(InventoryPlayer par1InventoryPlayer, TileEntity par2TileEntityCrusher)
    {
        super(new ContainerCrusher(par1InventoryPlayer, (TileEntityCrusher) par2TileEntityCrusher));
        this.furnaceInventory = (TileEntityCrusher) par2TileEntityCrusher;
        type = furnaceInventory.getType();
    }

    /**
     * Draw the foreground layer for the GuiContainer (everythin in front of the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRenderer.drawString(StatCollector.translateToLocal(names[type] + " Crusher"), this.xSize/2 - ((names[type] + " Crusher").length()*5)/2, 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture("/mods/Metallurgy/textures/guis/crusher.png"); // Calls bindTexture
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
