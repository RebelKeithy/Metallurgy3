package rebelkeithy.mods.metallurgy.machines.chests;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;


public class GuiPreciousChest extends GuiContainer
{
    private IInventory upperChestInventory;
    private IInventory lowerChestInventory;

    /**
     * window height is calculated with this values, the more rows, the heigher
     */
    private int inventoryRows = 0;
    private int inventoryCols = 0;
    private String image;

    public GuiPreciousChest(InventoryPlayer playerInv, TileEntity chestInv)
    {
        super(new ContainerPreciousChest(playerInv, chestInv));
        this.upperChestInventory = playerInv;
        this.lowerChestInventory = (IInventory) chestInv;
        this.allowUserInput = false;
        
        short var3 = 222;
        int var4 = var3 - 108;
        //this.inventoryRows = chestInv.getSizeInventory() / 9;
        this.inventoryRows = ((TileEntityPreciousChest)chestInv).getNumRows();
        this.inventoryCols = ((TileEntityPreciousChest)chestInv).getNumCols();
        this.ySize = var4 + this.inventoryRows * 18;
        this.xSize = this.inventoryCols * 18 + 20;
        
        int type = ((TileEntityPreciousChest)chestInv).getType();
        
        image = "/mods/Metallurgy/textures/guis/ironcontainer.png";
        switch(type)
        {
	        case 0:
	        {
	        	image = "/mods/Metallurgy/textures/guis/ironcontainer.png";
	        	break;
	        }	
	        case 1:
	        {
	        	image = "/mods/Metallurgy/textures/guis/silvercontainer.png";
	        	break;
	        }	
	        case 2:
	        {
	        	image = "/mods/Metallurgy/textures/guis/goldcontainer.png";
	        	break;
	        }	
	        case 3:
	        {
	        	image = "/mods/Metallurgy/textures/guis/electrumcontainer.png";
	        	break;
	        }	
	        case 4:
	        {
	        	image = "/mods/Metallurgy/textures/guis/diamondcontainer.png";
	        	break;
	        }	
        }
        		
    }

    /**
     * Draw the foreground layer for the GuiContainer (everythin in front of the items)
     */
    protected void drawGuiContainerForegroundLayer()
    {
        //this.fontRenderer.drawString(StatCollector.translateToLocal(this.lowerChestInventory.getInvName()), 8, 6, 4210752);
        //this.fontRenderer.drawString(StatCollector.translateToLocal(this.upperChestInventory.getInvName()), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.func_98187_b(image); // Calls bindTexture
        int imageWidth = (11 + 18 *inventoryCols + 11);
        int imageHeight = (7 + 18 *inventoryRows + 4 + 18 * 3 + 4 + 18 + 7);
        
        int var5 = (this.width / 2) - (imageWidth/2);
        int var6 = (this.height / 2) - (imageHeight / 2);
        
        this.drawTexturedModalRect(var5, var6, 0, 0, imageWidth, imageHeight);
        //this.drawTexturedModalRect(var5, var6 + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    }
}
