package rebelkeithy.mods.metallurgy.machines.pylon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TileEntityPylonRenderer extends TileEntitySpecialRenderer
{
    private final ModelPylon model = new ModelPylon();

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f)
    {
        renderTileEntityPylonAt((TileEntityPylon) tileentity, d0, d1, d2, f);
    }

    public void renderTileEntityPylonAt(TileEntityPylon te, double x, double y, double z, float par8)
    {

        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float) x + 0.5f, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glScalef(1.0F, -1.0F, -1.0F);
        // GL11.glTranslatef(-0.25F, -0.25F, -0.25F);

        final int meta = te.getType();

        final String name = BlockPylon.names[meta];
        final ResourceLocation texture = new ResourceLocation("Metallurgy:textures/blocks/machines/pylon/" + name.replace(" ", "") + "Pylon.png");
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        model.setCubeRotation(te.rotationX, te.rotationY, te.rotationZ);
        model.renderAll();
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

}
