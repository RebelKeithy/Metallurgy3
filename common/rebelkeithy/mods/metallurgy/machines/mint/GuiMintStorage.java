package rebelkeithy.mods.metallurgy.machines.mint;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;


public class GuiMintStorage extends GuiContainer
{
    private IInventory upperChestInventory;
    private IInventory lowerChestInventory;

    /**
     * window height is calculated with this values, the more rows, the heigher
     */
    private int inventoryRows = 0;
    private int inventoryCols = 0;
	private ResourceLocation background = new ResourceLocation("Metallurgy:textures/guis/mintstorage.png");

    public GuiMintStorage(InventoryPlayer playerInv, TileEntity chestInv)
    {
        super(new ContainerMintStorage(playerInv, chestInv));
        this.upperChestInventory = playerInv;
        this.lowerChestInventory = (IInventory) chestInv;
        this.allowUserInput = false;
        
        short var3 = 222;
        int var4 = var3 - 108;
        //this.inventoryRows = chestInv.getSizeInventory() / 9;
        this.inventoryRows = 2;
        this.inventoryCols = 3;
        this.ySize = var4 + this.inventoryRows * 18;
        		
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
        //this.mc.renderEngine.bindTexture(image); // Calls bindTexture
        this.mc.func_110434_K().func_110577_a(background);
        int imageWidth = 176;
        int imageHeight = 168;
        
        int var5 = (this.width / 2) - (imageWidth/2);
        int var6 = (this.height / 2) - (imageHeight / 2);
        
        this.drawTexturedModalRect(var5, var6, 0, 0, imageWidth, imageHeight);
    }
}
