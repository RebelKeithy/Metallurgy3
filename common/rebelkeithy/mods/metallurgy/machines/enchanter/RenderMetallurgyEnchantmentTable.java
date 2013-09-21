package rebelkeithy.mods.metallurgy.machines.enchanter;

import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMetallurgyEnchantmentTable extends TileEntitySpecialRenderer
{
    private final ModelBook enchantmentBook = new ModelBook();
    private static final ResourceLocation book = new ResourceLocation("Metallurgy:textures/blocks/machines/enchanter/enchanting_table_book.png");

    @Override
    public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
    {
        renderTileEntityEnchantmentTableAt((TileEntityMetallurgyEnchantmentTable) par1TileEntity, par2, par4, par6, par8);
    }

    public void renderTileEntityEnchantmentTableAt(TileEntityMetallurgyEnchantmentTable par1TileEntityEnchantmentTable, double par2, double par4, double par6, float par8)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) par2 + 0.5F, (float) par4 + 0.75F, (float) par6 + 0.5F);
        final float f1 = par1TileEntityEnchantmentTable.tickCount + par8;
        GL11.glTranslatef(0.0F, 0.1F + MathHelper.sin(f1 * 0.1F) * 0.01F, 0.0F);
        float f2;

        for (f2 = par1TileEntityEnchantmentTable.bookRotation2 - par1TileEntityEnchantmentTable.bookRotationPrev; f2 >= (float) Math.PI; f2 -= (float) Math.PI * 2F)
        {
            ;
        }

        while (f2 < -(float) Math.PI)
        {
            f2 += (float) Math.PI * 2F;
        }

        final float f3 = par1TileEntityEnchantmentTable.bookRotationPrev + f2 * par8;
        GL11.glRotatef(-f3 * 180.0F / (float) Math.PI, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(80.0F, 0.0F, 0.0F, 1.0F);
        bindTexture(book);
        float f4 = par1TileEntityEnchantmentTable.pageFlipPrev + (par1TileEntityEnchantmentTable.pageFlip - par1TileEntityEnchantmentTable.pageFlipPrev) * par8 + 0.25F;
        float f5 = par1TileEntityEnchantmentTable.pageFlipPrev + (par1TileEntityEnchantmentTable.pageFlip - par1TileEntityEnchantmentTable.pageFlipPrev) * par8 + 0.75F;
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

        final float f6 = par1TileEntityEnchantmentTable.bookSpreadPrev + (par1TileEntityEnchantmentTable.bookSpread - par1TileEntityEnchantmentTable.bookSpreadPrev) * par8;
        GL11.glEnable(GL11.GL_CULL_FACE);
        enchantmentBook.render((Entity) null, f1, f4, f5, f6, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }
}
