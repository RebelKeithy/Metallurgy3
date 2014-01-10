package rebelkeithy.mods.metallurgy.machines.laser;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TileEntityLaserRenderer extends TileEntitySpecialRenderer
{
    private final ModelLaser model = new ModelLaser();

    @Override
    public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
    {
        renderTileEntityLanternAt((TileEntityLaser) par1TileEntity, par2, par4, par6, par8);
    }

    public void renderTileEntityLanternAt(TileEntityLaser te, double x, double y, double z, float par8)
    {

        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
        GL11.glScalef(1.0F, -1.0F, -1.0F);
        GL11.glTranslatef(-0.25F, -0.25F, -0.25F);

        ResourceLocation texture = new ResourceLocation("Metallurgy:textures/blocks/machines/laser/Laser.png");
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        model.renderAll();

        if (te.worldObj != null && te.worldObj.getBlockPowerInput(te.xCoord, te.yCoord, te.zCoord) == 0)
        {
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
            final char c0 = 61680;
            final int j = c0 % 65536;
            final int k = c0 / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
            texture = new ResourceLocation("Metallurgy:textures/blocks/machines/laser/Beam.png");
            Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
            for (int i = 0; i < 6; i++)
            {
                if (i == 0 || i == 1)
                {
                    model.renderBeam(te.getClipedLength(i), ForgeDirection.VALID_DIRECTIONS[i]);
                }
                else
                {
                    model.renderBeam(te.getClipedLength(i), ForgeDirection.VALID_DIRECTIONS[ForgeDirection.OPPOSITES[i]]);
                }
            }
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_LIGHTING);
        }

        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
