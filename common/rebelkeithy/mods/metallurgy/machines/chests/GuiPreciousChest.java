package rebelkeithy.mods.metallurgy.machines.chests;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

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
    
    private ResourceLocation background = new ResourceLocation("Metallurgy:textures/guis/ironcontainer.png");
	private ResourceLocation backgroundBrass = new ResourceLocation("Metallurgy:textures/guis/ironcontainer.png");
	private ResourceLocation backgroundSilver = new ResourceLocation("Metallurgy:textures/guis/silvercontainer.png");
	private ResourceLocation backgroundGold = new ResourceLocation("Metallurgy:textures/guis/goldcontainer.png");
	private ResourceLocation backgroundElectrum = new ResourceLocation("Metallurgy:textures/guis/electrumcontainer.png");
	private ResourceLocation backgroundPlatinum = new ResourceLocation("Metallurgy:textures/guis/diamondcontainer.png");

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
        
        background = backgroundBrass;
        switch(type)
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
     * Draw the foreground layer for the GuiContainer (everythin in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
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
        //this.mc.renderEngine.bindTexture(image); // Calls bindTexture
        this.mc.getTextureManager().bindTexture(background);
        int imageWidth = (11 + 18 *inventoryCols + 11);
        int imageHeight = (7 + 18 *inventoryRows + 4 + 18 * 3 + 4 + 18 + 7);
        
        int var5 = (this.width / 2) - (imageWidth/2);
        int var6 = (this.height / 2) - (imageHeight / 2);
        
        this.drawTexturedModalRect(var5, var6, 0, 0, imageWidth, imageHeight);
        //this.drawTexturedModalRect(var5, var6 + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    }
}
