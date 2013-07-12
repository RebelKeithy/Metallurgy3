package rebelkeithy.mods.metallurgy.machines.storage;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.gui.inventory.CreativeCrafting;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import rebelkeithy.mods.metallurgy.machines.PacketHandler;

public class GuiStorage extends GuiContainer
{
	List<StorageTabs> tabList;
	int tabPage;
	int selectedTabIndex;
	private float currentScroll;
	private int maxPages;
	public TileEntityStorageAccessor tileEntity;
	
	public int x;
	public int y;
	public int z;
	private boolean wasClicking;
	private boolean isScrolling;

	public GuiStorage(ContainerStorage container, TileEntityStorageAccessor ste, int selectedID)
	{
		super(container);
		tabList = new ArrayList<StorageTabs>();
		for(int itemID : ste.inventories.keySet())
			tabList.add(new StorageTabs(new ItemStack(itemID, 1, 0)));
        this.ySize = 194;
        this.xSize = 195;
        tileEntity = ste;
        
        selectedTabIndex = 0;
        for(int n = 0; n < tabList.size(); n++)
        {
        	if(tabList.get(n).icon.itemID == selectedID)
        	{
        		selectedTabIndex = n;
        	}
        }
        tabPage = selectedTabIndex/6;
        if(container.inventory != null)
        	currentScroll = container.inventory.scroll;
        System.out.println("Tab Index Set Too " + selectedTabIndex);
        
        x = ste.xCoord;
        y = ste.yCoord;
        z = ste.zCoord;
	}

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        if (this.mc.playerController.isInCreativeMode())
        {
            super.initGui();
            guiTop += 26;
            // TODO these should not be commented out
            //this.controlList.clear();
            Keyboard.enableRepeatEvents(true);
            int tabCount = tabList.size();
            if (tabCount > 6)
            {
                //controlList.add(new GuiButton(101, guiLeft,              guiTop - 50, 20, 20, "<"));
                //controlList.add(new GuiButton(102, guiLeft + xSize - 20, guiTop - 50, 20, 20, ">"));
                maxPages = (tabCount-1)/6;
            }
        }
        else
        {
            this.mc.displayGuiScreen(new GuiInventory(this.mc.thePlayer));
        }
    }
	
    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        if (par3 == 0)
        {
            int var4 = par1 - this.guiLeft;
            int var5 = par2 - this.guiTop;
            CreativeTabs[] var6 = CreativeTabs.creativeTabArray;
            int var7 = var6.length;

            if(var5 < 0 && var5 > -32)
            {
            	if(var4 >= 0 && var4 < 29*Math.min(6, tabList.size()) && var4%29 < 28)
            	{
            		setSelectedTab((var4+1)/29);
            	}
            }
            
            System.out.println(var4 + " " + var5);
        }

        super.mouseClicked(par1, par2, par3);
    }
	
    private void setSelectedTab(int i) 
    {
    	System.out.println("selected tab = " + i);
    	int selectedTabIndex = tabPage*6 + i;
    	int itemID = tabList.get(selectedTabIndex).icon.itemID;
        PacketDispatcher.sendPacketToServer(PacketHandler.getChangeTabPacket(x, y, z, itemID));
    	//((ContainerStorage)inventorySlots).changeTab(selectedTabIndex, tabList.get(selectedTabIndex).icon);
	}

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        boolean var4 = Mouse.isButtonDown(0);
        int var5 = this.guiLeft;
        int var6 = this.guiTop;
        int var7 = var5 + 175;
        int var8 = var6 + 18;
        int var9 = var7 + 14;
        int var10 = var8 + 112;

        if (!this.wasClicking && var4 && par1 >= var7 && par2 >= var8 && par1 < var9 && par2 < var10)
        {
            this.isScrolling = this.needsScrollBars();
        }

        if (!var4)
        {
            this.isScrolling = false;
        }

        this.wasClicking = var4;

        if (this.isScrolling)
        {
            int invHeight = ((ContainerStorage)this.inventorySlots).inventory.getSizeInventory()/9 - 5;
            float scrollFraction = ((float)(par2 - var8) - 7.5F) / ((float)(var10 - var8) - 15.0F);

            float oldScroll = currentScroll;
            currentScroll = (int) Math.floor(invHeight*scrollFraction +0.5);
            
            if (this.currentScroll < 0.0F)
            {
                this.currentScroll = 0;
            }

            if (this.currentScroll > invHeight)
            {
                this.currentScroll = invHeight;
            }

            if(Math.floor(currentScroll) != Math.floor(oldScroll))
            {
	            //((ContainerStorage)this.inventorySlots).inventory.scroll = currentScroll;
	            PacketDispatcher.sendPacketToServer(PacketHandler.getScrollPacket(tabList.get(selectedTabIndex).icon.itemID, (int)(Math.floor(currentScroll)), x, y, z));
            }
        }

        super.drawScreen(par1, par2, par3);
    }

	/**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderHelper.enableGUIStandardItemLighting();
        int var8 = tabList.size();
        var8 = Math.min(tabList.size(), (tabPage + 1) * 6);
        
        int start = tabPage * 6;
        
        for (int n = start; n < var8; ++n)
        {
            StorageTabs var10 = tabList.get(n);
            //this.mc.renderEngine.bindTexture("/gui/allitems.png");  // Calls bindTexture

            if (var10 != null && n != selectedTabIndex && var8-n < 7)
            {
                this.renderCreativeTab(var10, n);
            }
        }

        //this.mc.renderEngine.bindTexture("/gui/creative_inv/inv_storage.png"); // Calls bindTexture
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int var11 = this.guiLeft + 175;
        var8 = this.guiTop + 18;
        int var9 = var8 + 112;
        //this.mc.renderEngine.bindTexture("/gui/allitems.png");

        int invHeight = ((ContainerStorage)this.inventorySlots).inventory.getSizeInventory()/9 -5;
        this.drawTexturedModalRect(var11, var8 + (int)((currentScroll/(float)invHeight)*96), 232 + (this.needsScrollBars() ? 0 : 12), 0, 12, 15);
        if(tabList.size() >0)
        	renderCreativeTab(tabList.get(selectedTabIndex), selectedTabIndex);
    }
    
    protected void renderCreativeTab(StorageTabs par1CreativeTabs, int index)
    {
        boolean isSelected = index == selectedTabIndex;
        int var4 = index%6;
        int var5 = var4 * 28;
        int var6 = 0;
        int var7 = this.guiLeft + 28 * var4;
        int var8 = this.guiTop;
        byte var9 = 32;

        if (isSelected)
        {
            var6 += 32;
        }

        if (var4 == 5)
        {
            //var7 = this.guiLeft + this.xSize - 28;
        	var7 += var4;
        }
        else if (var4 > 0)
        {
            var7 += var4;
        }

        var8 -= 28;

        GL11.glDisable(GL11.GL_LIGHTING);
        this.drawTexturedModalRect(var7, var8, var5, var6, 28, var9);
        this.zLevel = 100.0F;
        itemRenderer.zLevel = 100.0F;
        var7 += 6;
        var8 += 8;
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        ItemStack var10 = par1CreativeTabs.getIconItemStack();
        itemRenderer.renderItemAndEffectIntoGUI(this.fontRenderer, this.mc.renderEngine, var10, var7, var8 - (isSelected ? 2 : 0));
        itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, var10, var7, var8 - (isSelected ? 2 : 0));
        GL11.glDisable(GL11.GL_LIGHTING);
        itemRenderer.zLevel = 0.0F;
        this.zLevel = 0.0F;
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput()
    {
        super.handleMouseInput();
        int var1 = Mouse.getEventDWheel();

        if (var1 != 0 && this.needsScrollBars())
        {
            int var2 = this.inventorySlots.inventoryItemStacks.size() / 9 - 5 + 1;

            int invHeight = ((ContainerStorage)this.inventorySlots).inventory.getSizeInventory()/9;
            
            if (var1 > 0)
            {
                var1 = 1;
            }

            if (var1 < 0)
            {
                var1 = -1;
            }

            this.currentScroll = this.currentScroll - var1;

            if (this.currentScroll < 0)
            {
                this.currentScroll = 0;
            }

            if (this.currentScroll > invHeight - 5)
            {
                this.currentScroll = invHeight - 5;
            }

            //((ContainerStorage)this.inventorySlots).inventory.scroll = currentScroll;
            PacketDispatcher.sendPacketToServer(PacketHandler.getScrollPacket(tabList.get(selectedTabIndex).icon.itemID, (int) currentScroll, x, y, z));
            //((ContainerCreative)this.inventorySlots).scrollTo(this.currentScroll);
        }
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.id == 0)
        {
            this.mc.displayGuiScreen(new GuiAchievements(this.mc.statFileWriter));
        }

        if (par1GuiButton.id == 1)
        {
            this.mc.displayGuiScreen(new GuiStats(this, this.mc.statFileWriter));
        }

        if (par1GuiButton.id == 101)
        {
        	if(tabPage == 0)
        		return;
            tabPage = Math.max(tabPage - 1, 0);
            setSelectedTab(0);
        }
        else if (par1GuiButton.id == 102)
        {
        	System.out.println("maxPages " + maxPages);
        	if(tabPage == maxPages)
        		return;
            tabPage = Math.min(tabPage + 1, maxPages);
            setSelectedTab(0);
        }
    }

	private boolean needsScrollBars() 
	{
		return ((ContainerStorage)this.inventorySlots).inventory.getSizeInventory()/9 > 5;
	}
}
