package rebelkeithy.mods.metallurgy.machines.enchanter;

import java.util.Random;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnchantmentNameParts;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.glu.GLU;

import rebelkeithy.mods.metallurgy.metals.MetallurgyMetals;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMetallurgyEnchantment extends GuiContainer
{
    private static final ResourceLocation background = new ResourceLocation("metallurgy:textures/guis/enchant.png");
    private static final ResourceLocation book = new ResourceLocation("textures/entity/enchanting_table_book.png");

    private final Random rand = new Random();

    /** ContainerEnchantment object associated with this gui */
    private final ContainerMetallurgyEnchantment containerEnchantment;
    public int field_74214_o;
    public float field_74213_p;
    public float field_74212_q;
    public float field_74211_r;
    public float field_74210_s;
    public float field_74209_t;
    public float field_74208_u;
    ItemStack theItemStack;

    public GuiMetallurgyEnchantment(InventoryPlayer par1InventoryPlayer, TileEntity tileEntity)
    {
        super(new ContainerMetallurgyEnchantment(par1InventoryPlayer, tileEntity));
        containerEnchantment = (ContainerMetallurgyEnchantment) inventorySlots;
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the
     * items)
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        // this.mc.renderEngine.bindTexture("/mods/Metallurgy/textures/guis/enchant.png");
        mc.getTextureManager().bindTexture(background);
        final int k = (width - xSize) / 2;
        final int l = (height - ySize) / 2;
        drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
        GL11.glPushMatrix();
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        final ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        GL11.glViewport((scaledresolution.getScaledWidth() - 320) / 2 * scaledresolution.getScaleFactor(),
                (scaledresolution.getScaledHeight() - 240) / 2 * scaledresolution.getScaleFactor(), 320 * scaledresolution.getScaleFactor(),
                240 * scaledresolution.getScaleFactor());
        GL11.glTranslatef(-0.34F, 0.23F, 0.0F);
        GLU.gluPerspective(90.0F, 1.3333334F, 9.0F, 80.0F);
        final float f1 = 1.0F;
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        RenderHelper.enableStandardItemLighting();
        GL11.glTranslatef(0.0F, 3.3F, -16.0F);
        GL11.glScalef(f1, f1, f1);
        final float f2 = 5.0F;
        GL11.glScalef(f2, f2, f2);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        // this.mc.renderEngine.bindTexture("/item/book.png");

        mc.getTextureManager().bindTexture(book);
        GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
        final float f3 = field_74208_u + (field_74209_t - field_74208_u) * par1;
        GL11.glTranslatef((1.0F - f3) * 0.2F, (1.0F - f3) * 0.1F, (1.0F - f3) * 0.25F);
        GL11.glRotatef(-(1.0F - f3) * 90.0F - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
        float f4 = field_74212_q + (field_74213_p - field_74212_q) * par1 + 0.25F;
        float f5 = field_74212_q + (field_74213_p - field_74212_q) * par1 + 0.75F;
        f4 = (f4 - MathHelper.truncateDoubleToInt(f4)) * 1.6F - 0.3F;
        f5 = (f5 - MathHelper.truncateDoubleToInt(f5)) * 1.6F - 0.3F;

        if (f4 < 0.0F)
        {
            f4 = 0.0F;
        }

        if (f5 < 0.0F)
        {
            f5 = 0.0F;
        }

        if (f4 > 1.0F)
        {
            f4 = 1.0F;
        }

        if (f5 > 1.0F)
        {
            f5 = 1.0F;
        }

        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        // bookModel.render((Entity)null, 0.0F, f4, f5, f3, 0.0F, 0.0625F);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);
        GL11.glPopMatrix();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        // this.mc.renderEngine.bindTexture("/mods/Metallurgy/textures/guis/enchant.png");
        mc.getTextureManager().bindTexture(background);
        EnchantmentNameParts.instance.setRandSeed(containerEnchantment.nameSeed);

        for (int i1 = 0; i1 < 6; i1++)
        {
            // int i1 = 0;
            String s = EnchantmentNameParts.instance.generateRandomEnchantName();
            if (s.length() > 12)
            {
                s = s.substring(0, 12);
            }

            zLevel = 0.0F;
            // this.mc.renderEngine.bindTexture("/mods/Metallurgy/textures/guis/enchant.png");
            mc.getTextureManager().bindTexture(background);
            int j1 = (int) ((containerEnchantment.enchantLevels - 1) * i1 / 5.0f + 1);

            if (containerEnchantment.enchantLevels == 0)
            {
                j1 = 0;
            }

            final int xOffset = i1 < 3 ? 5 : 123;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            final int[] offsetArrayX =
            { 64, 80, 96, 64, 80, 96 };
            final int[] offsetArrayY =
            { 19, 9, 19, 51, 61, 51 };
            for (int i = 1; i < 7; i++)
            {
                if (containerEnchantment.tableInventory.getStackInSlot(i) != null && MetallurgyMetals.fantasySet != null)
                {
                    // this.mc.renderEngine.func_98187_b("/mods/metallurgy/textures/guis/enchant.png");
                    if (containerEnchantment.tableInventory.getStackInSlot(i).itemID == MetallurgyMetals.fantasySet.getOreInfo("Astral Silver").dust.itemID)
                    {
                        drawTexturedModalRect(k + offsetArrayX[i - 1], l + offsetArrayY[i - 1], 163, 167, 16, 16);
                    }
                    else if (containerEnchantment.tableInventory.getStackInSlot(i).itemID == MetallurgyMetals.fantasySet.getOreInfo("Carmot").dust.itemID)
                    {
                        drawTexturedModalRect(k + 69 + 18 * (i - 1), l + 46, 126, 166, 18, 18);
                    }
                }
            }

            if (j1 == 0)
            {
                drawTexturedModalRect(k + xOffset, l + 15 + 19 * (i1 < 3 ? i1 : i1 - 3), 109, 203, 48, 18);
            }
            else
            {
                final String s1 = "" + j1;
                FontRenderer fontrenderer = mc.standardGalacticFontRenderer;
                int k1 = 6839882;

                if (mc.thePlayer.experienceLevel < j1 && !mc.thePlayer.capabilities.isCreativeMode)
                {
                    drawTexturedModalRect(k + xOffset, l + 15 + 19 * (i1 < 3 ? i1 : i1 - 3), 109, 203, 48, 18);
                    fontrenderer.drawSplitString(s, k + 62, l + 16 + 19 * (i1 < 3 ? i1 : i1 - 3), 48, (k1 & 16711422) >> 1);
                    fontrenderer = mc.fontRenderer;
                    k1 = 4226832;
                    fontrenderer.drawStringWithShadow(s1, k + 62 + 104 - fontrenderer.getStringWidth(s1), l + 16 + 19 * (i1 < 3 ? i1 : i1 - 3) + 7, k1);
                }
                else
                {
                    final int l1 = par2 - (k + xOffset);
                    final int i2 = par3 - (l + 14 + 19 * (i1 < 3 ? i1 : i1 - 3));

                    if (l1 >= 0 && i2 >= 0 && l1 < 48 && i2 < 19)
                    {
                        drawTexturedModalRect(k + xOffset, l + 15 + 19 * (i1 < 3 ? i1 : i1 - 3), 109, 203, 48, 18);
                        k1 = 16777088;
                    }
                    else
                    {
                        drawTexturedModalRect(k + xOffset, l + 15 + 19 * (i1 < 3 ? i1 : i1 - 3), 109, 184, 48, 18);
                    }

                    k1 = 0xCE6000;
                    fontrenderer.drawSplitString(s, k + xOffset, l + 16 + 19 * (i1 < 3 ? i1 : i1 - 3), 48, k1);
                    fontrenderer = mc.fontRenderer;
                    k1 = 8453920;
                    fontrenderer.drawStringWithShadow(s1, k + xOffset + 43 - fontrenderer.getStringWidth(s1), l + 16 + 19 * (i1 < 3 ? i1 : i1 - 3) + 7, k1);
                }
            }

        }

    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of
     * the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        // this.fontRenderer.drawString(this.field_94079_C == null ?
        // StatCollector.translateToLocal("container.enchant") :
        // this.field_94079_C, 12, 5, 4210752);
        // this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"),
        // 8, this.ySize - 96 + 2, 4210752);
    }

    public void func_74205_h()
    {
        final ItemStack itemstack = inventorySlots.getSlot(0).getStack();

        if (!ItemStack.areItemStacksEqual(itemstack, theItemStack))
        {
            theItemStack = itemstack;

            do
            {
                field_74211_r += rand.nextInt(4) - rand.nextInt(4);
            } while (field_74213_p <= field_74211_r + 1.0F && field_74213_p >= field_74211_r - 1.0F);
        }

        ++field_74214_o;
        field_74212_q = field_74213_p;
        field_74208_u = field_74209_t;
        boolean flag = false;

        if (containerEnchantment.enchantLevels != 0)
        {
            flag = true;
        }

        if (flag)
        {
            field_74209_t += 0.2F;
        }
        else
        {
            field_74209_t -= 0.2F;
        }

        if (field_74209_t < 0.0F)
        {
            field_74209_t = 0.0F;
        }

        if (field_74209_t > 1.0F)
        {
            field_74209_t = 1.0F;
        }

        float f = (field_74211_r - field_74213_p) * 0.4F;
        final float f1 = 0.2F;

        if (f < -f1)
        {
            f = -f1;
        }

        if (f > f1)
        {
            f = f1;
        }

        field_74210_s += (f - field_74210_s) * 0.9F;
        field_74213_p += field_74210_s;
    }

    /**
     * Called when the mouse is clicked.
     */
    @Override
    protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
        final int l = (width - xSize) / 2;
        final int i1 = (height - ySize) / 2;

        final int k1 = par1 - (l + 60);
        final int l1 = par2 - (i1 + 14);

        if (k1 >= 0 && l1 >= 0 && k1 < 108 && l1 < 19 && containerEnchantment.enchantItem(mc.thePlayer, 0))
        {
            mc.playerController.sendEnchantPacket(containerEnchantment.windowId, 0);
        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    @Override
    public void updateScreen()
    {
        super.updateScreen();
        func_74205_h();
    }
}
