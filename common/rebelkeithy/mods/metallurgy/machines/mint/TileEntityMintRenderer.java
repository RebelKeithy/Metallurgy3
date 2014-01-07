package rebelkeithy.mods.metallurgy.machines.mint;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TileEntityMintRenderer extends TileEntitySpecialRenderer
{
    /** The normal small chest model. */
    private final ModelMint mintModel = new ModelMint();
    private final ResourceLocation texture = new ResourceLocation("Metallurgy:textures/blocks/machines/mint/Mint.png");
    private final ResourceLocation textureHead = new ResourceLocation("Metallurgy:textures/blocks/machines/mint/MintHead.png");

    private ModelMintHead mintHead;

    @Override
    public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8)
    {
        renderTileEntityMintAt((TileEntityMint) var1, var2, var4, var6, var8);

    }

    /**
     * Renders the TileEntity for the chest at a position.
     */
    public void renderTileEntityMintAt(TileEntityMint par1TileEntityMint, double par2, double par4, double par6, float par8)
    {
        String ingotImage = new String("");

        par1TileEntityMint.getDirection();
        boolean ingot = false;
        float offset = 0;
        float headOffset = 0;

        int var9;

        if (!par1TileEntityMint.hasWorldObj())
        {
            var9 = 5;
            offset = 0.1f;
        }
        else
        {
            var9 = par1TileEntityMint.getDirection();
        }

        var9 = par1TileEntityMint.getDirection();
        final float time = par1TileEntityMint.resetTime;
        if (time >= 5)
        {
            headOffset = 0.29f * ((10 - time) / 5f);
        }
        if (time < 5 && time > 0)
        {
            headOffset = 0.29f * (time / 5f);
        }

        if (par1TileEntityMint.hasIngot())
        {
            ingotImage = par1TileEntityMint.getIngotImage();
            ingot = true;
        }

        ModelMint var14;

        var14 = mintModel;
        if(mintHead == null)
        {
            mintHead = new ModelMintHead();
        }

        // this.bindTextureByName("/mods/Metallurgy/textures/blocks/machines/mint/Mint.png");
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float) par2, (float) par4 + 1.0F, (float) par6 + 1.0F);
        GL11.glScalef(1.0F, -1.0F, -1.0F);
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        short var11 = 0;

        if (var9 == 2)
        {
            var11 = 180;
        }

        if (var9 == 3)
        {
            var11 = 0;
        }

        if (var9 == 4)
        {
            var11 = 90;
        }

        if (var9 == 5)
        {
            var11 = -90;
        }

        GL11.glRotatef(var11, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F + offset, -0.5F);
        var14.renderAll();

        if (ingot)
        {
            final ResourceLocation ingotResource = new ResourceLocation(ingotImage);
            Minecraft.getMinecraft().getTextureManager().bindTexture(ingotResource);
            // this.bindTextureByName(ingotImage);
            new ModelIngot().renderAll();
        }

        GL11.glTranslatef(0F, headOffset + offset, 0F);

        // this.bindTextureByName("/mods/Metallurgy/textures/blocks/machines/mint/MintHead.png");
        Minecraft.getMinecraft().getTextureManager().bindTexture(textureHead);
        mintHead.renderAll();

        // GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
