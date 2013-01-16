package rebelkeithy.mods.metallurgy.machines.storage;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiStorage extends GuiContainer
{
	List<StorageTabs> tabList;
	int tabPage;
	int selectedTabIndex;
	private float currentScroll;

	public GuiStorage(Container container)
	{
		super(container);
		tabList = new ArrayList<StorageTabs>();
	}
	
    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderHelper.enableGUIStandardItemLighting();
        int tabImage = this.mc.renderEngine.getTexture("/gui/allitems.png");
        int backgroundImage = this.mc.renderEngine.getTexture("/gui/creative_inv/list_items.png");
        int var8 = tabList.size();
        var8 = Math.min(tabList.size(), (tabPage + 1) * 10);
        
        int start = tabPage * 10;
        
        for (int n = start; n < var8; ++n)
        {
            StorageTabs var10 = tabList.get(n);
            this.mc.renderEngine.bindTexture(tabImage);

            if (var10 != null && n != selectedTabIndex)
            {
                this.renderCreativeTab(var10, n);
            }
        }

        this.mc.renderEngine.bindTexture(tabImage);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int var11 = this.guiLeft + 175;
        var8 = this.guiTop + 18;
        int var9 = var8 + 112;
        this.mc.renderEngine.bindTexture(backgroundImage);

        this.drawTexturedModalRect(var11, var8 + (int)((float)(var9 - var8 - 17) * this.currentScroll), 232 + (this.needsScrollBars() ? 0 : 12), 0, 12, 15);
    }
    
    protected void renderCreativeTab(StorageTabs par1CreativeTabs, int index)
    {
        boolean isSelected = index == selectedTabIndex;
        boolean var3 = index%10 < 6;
        int var4 = index%5;
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
            var7 = this.guiLeft + this.xSize - 28;
        }
        else if (var4 > 0)
        {
            var7 += var4;
        }

        if (var3)
        {
            var8 -= 28;
        }
        else
        {
            var6 += 64;
            var8 += this.ySize - 4;
        }

        GL11.glDisable(GL11.GL_LIGHTING);
        this.drawTexturedModalRect(var7, var8, var5, var6, 28, var9);
        this.zLevel = 100.0F;
        itemRenderer.zLevel = 100.0F;
        var7 += 6;
        var8 += 8 + (var3 ? 1 : -1);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        ItemStack var10 = par1CreativeTabs.getIconItemStack();
        itemRenderer.renderItemAndEffectIntoGUI(this.fontRenderer, this.mc.renderEngine, var10, var7, var8);
        itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, var10, var7, var8);
        GL11.glDisable(GL11.GL_LIGHTING);
        itemRenderer.zLevel = 0.0F;
        this.zLevel = 0.0F;
    }

	private boolean needsScrollBars() {
		// TODO Auto-generated method stub
		return true;
	}
}
