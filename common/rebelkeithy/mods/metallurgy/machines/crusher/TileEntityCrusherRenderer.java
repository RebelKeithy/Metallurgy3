package rebelkeithy.mods.metallurgy.machines.crusher;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TileEntityCrusherRenderer extends TileEntitySpecialRenderer
{
    /** The normal small chest model. */
    private final ModelCrusher crusherModel = new ModelCrusher();

    @Override
    public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
    {
        renderTileEntityCrusherAt((TileEntityCrusher) par1TileEntity, par2, par4, par6, par8);
    }

    /**
     * Renders the TileEntity for the chest at a position.
     */
    public void renderTileEntityCrusherAt(TileEntityCrusher par1TileEntityCrusher, double par2, double par4, double par6, float par8)
    {
        int var9;
        float offset = 0;

        if (par1TileEntityCrusher.worldObj == null)
        {
            var9 = 5;
            offset = 0.1f;
        }
        else
        {
            var9 = par1TileEntityCrusher.direction;
        }

        //crusherModel.spin(par1TileEntityCrusher.getCrusherAngles());

        String type = "";
        if (par1TileEntityCrusher.getType() == 0)
        {
            type = "Stone";
        }
        else if (par1TileEntityCrusher.getType() == 1)
        {
            type = "Copper";
        }
        else if (par1TileEntityCrusher.getType() == 2)
        {
            type = "Bronze";
        }
        else if (par1TileEntityCrusher.getType() == 3)
        {
            type = "Iron";
        }
        else if (par1TileEntityCrusher.getType() == 4)
        {
            type = "Steel";
        }

        ResourceLocation texture = new ResourceLocation("Metallurgy:textures/blocks/machines/crusher/ModelCrusher" + type + ".png");

        if (par1TileEntityCrusher.isBurning())
        {
        	texture = new ResourceLocation("Metallurgy:textures/blocks/machines/crusher/ModelCrusher" + type + "Burning.png");
            // this.bindTextureByName("/mods/Metallurgy/textures/blocks/machines/crusher/ModelCrusher"
            // + type + "Burning.png");
        }

        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float) par2, (float) par4, (float) par6 + 1.0F);
        GL11.glScalef(1.0F, -1.0F, -1.0F);
        short var11 = 0;
        if (var9 == 2)
        {
            var11 = 180;
            GL11.glTranslatef(1F, -1.0F, 1F);
        }

        if (var9 == 3)
        {
            var11 = 0;
            GL11.glTranslatef(0F, -1.0F, 0.0F);
        }

        if (var9 == 4)
        {
            var11 = 90;
            GL11.glTranslatef(0F, -1.0F, 1F);
        }

        if (var9 == 5)
        {
            var11 = -90;
            GL11.glTranslatef(1F, -1.0F, 0.0F);
        }

        GL11.glRotatef(var11, 0.0F, 1.0F, 0.0F);
        System.out.println(var11);
        crusherModel.renderAll();
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
