package rebelkeithy.mods.metallurgy.machines.lantern;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TileEntityLanternRenderer extends TileEntitySpecialRenderer
{

    /** The normal small chest model. */
    private final ModelLantern lanternModel = new ModelLantern();
    int direction = 0;

    ResourceLocation[] textures =
    { new ResourceLocation("Metallurgy:textures/blocks/machines/lantern/LanternRed.png"), new ResourceLocation("Metallurgy:textures/blocks/machines/lantern/LanternGreen.png"),
            new ResourceLocation("Metallurgy:textures/blocks/machines/lantern/LanternBlue.png"),
            new ResourceLocation("Metallurgy:textures/blocks/machines/lantern/LanternOrange.png"),
            new ResourceLocation("Metallurgy:textures/blocks/machines/lantern/LanternYellow.png"),
            new ResourceLocation("Metallurgy:textures/blocks/machines/lantern/LanternPurple.png"),
            new ResourceLocation("Metallurgy:textures/blocks/machines/lantern/LanternGrey.png"),
            new ResourceLocation("Metallurgy:textures/blocks/machines/lantern/LanternWhite.png"), };

    @Override
    public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
    {
        renderTileEntityLanternAt((TileEntityLantern) par1TileEntity, par2, par4, par6, par8);
    }

    /**
     * Renders the TileEntity for the lantern at a position.
     */
    public void renderTileEntityLanternAt(TileEntityLantern par1TileEntityLantern, double x, double y, double z, float par8)
    {
        final int color = par1TileEntityLantern.color;
        /*
         * if(color == 0) this.bindTextureByName(
         * "/mods/Metallurgy/textures/blocks/machines/lantern/LanternRed.png");
         * else if(color == 1) this.bindTextureByName(
         * "/mods/Metallurgy/textures/blocks/machines/lantern/LanternGreen.png"
         * ); else if(color == 2) this.bindTextureByName(
         * "/mods/Metallurgy/textures/blocks/machines/lantern/LanternBlue.png");
         * else if(color == 3) this.bindTextureByName(
         * "/mods/Metallurgy/textures/blocks/machines/lantern/LanternOrange.png"
         * ); else if(color == 4) this.bindTextureByName(
         * "/mods/Metallurgy/textures/blocks/machines/lantern/LanternYellow.png"
         * ); else if(color == 5) this.bindTextureByName(
         * "/mods/Metallurgy/textures/blocks/machines/lantern/LanternPurple.png"
         * ); else if(color == 6) this.bindTextureByName(
         * "/mods/Metallurgy/textures/blocks/machines/lantern/LanternGrey.png");
         * else if(color == 7) this.bindTextureByName(
         * "/mods/Metallurgy/textures/blocks/machines/lantern/LanternWhite.png"
         * );
         */
        if (color < textures.length)
        {
            Minecraft.getMinecraft().getTextureManager().bindTexture(textures[color]);
        }

        if (par1TileEntityLantern.worldObj != null)
        {
            direction = par1TileEntityLantern.getBlockMetadata();
        }
        else
        {
            direction = 0;
        }

        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
        GL11.glScalef(1.0F, -1.0F, -1.0F);
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        final short var11 = 0;

        GL11.glRotatef(var11, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

        lanternModel.renderAll(direction);
        // GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

}
