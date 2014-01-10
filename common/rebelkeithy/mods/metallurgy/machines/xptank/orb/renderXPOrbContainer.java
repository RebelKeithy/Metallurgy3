//package rebelkeithy.mods.metallurgy.machines.xptank.orb;
//
//import net.minecraft.client.renderer.OpenGlHelper;
//import net.minecraft.client.renderer.Tessellator;
//import net.minecraft.client.renderer.entity.Render;
//import net.minecraft.client.renderer.texture.TextureMap;
//import net.minecraft.entity.Entity;
//import net.minecraft.util.MathHelper;
//import net.minecraft.util.ResourceLocation;
//
//import org.lwjgl.opengl.GL11;
//import org.lwjgl.opengl.GL12;
//
//import cpw.mods.fml.relauncher.Side;
//import cpw.mods.fml.relauncher.SideOnly;
//
//@SideOnly(Side.CLIENT)
//public class renderXPOrbContainer extends Render
//{
//    private static final ResourceLocation field_110785_a = new ResourceLocation("textures/entity/experience_orb.png");
//
//    public renderXPOrbContainer()
//    {
//        shadowSize = 0.15F;
//        shadowOpaque = 0.75F;
//    }
//
//    /**
//     * Actually renders the given argument. This is a synthetic bridge method,
//     * always casting down its argument and then handing it off to a worker
//     * function which does the actual work. In all probabilty, the class Render
//     * is generic (Render<T extends Entity) and this method has signature public
//     * void doRender(T entity, double d, double d1, double d2, float f, float
//     * f1). But JAD is pre 1.5 so doesn't do that.
//     */
//    @Override
//    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
//    {
//        renderTheXPOrb((EntityXpOrbContainer) par1Entity, par2, par4, par6, par8, par9);
//    }
//
//    protected ResourceLocation func_110775_a(Entity par1Entity)
//    {
//        return func_110784_a((EntityXpOrbContainer) par1Entity);
//    }
//
//    protected ResourceLocation func_110784_a(EntityXpOrbContainer par1EntityXPOrb)
//    {
//        return field_110785_a;
//    }
//
//    @Override
//    protected ResourceLocation getEntityTexture(Entity entity)
//    {
//        return TextureMap.locationItemsTexture;
//    }
//
//    /**
//     * Renders the XP Orb.
//     */
//    public void renderTheXPOrb(EntityXpOrbContainer par1EntityXPOrb, double par2, double par4, double par6, float par8, float par9)
//    {
//        GL11.glPushMatrix();
//        GL11.glTranslatef((float) par2, (float) par4, (float) par6);
//        getEntityTexture(par1EntityXPOrb);
//        final int i = par1EntityXPOrb.getTextureByXP();
//        final float f2 = (i % 4 * 16 + 0) / 64.0F;
//        final float f3 = (i % 4 * 16 + 16) / 64.0F;
//        final float f4 = (i / 4 * 16 + 0) / 64.0F;
//        final float f5 = (i / 4 * 16 + 16) / 64.0F;
//        final float f6 = 1.0F;
//        final float f7 = 0.5F;
//        final float f8 = 0.25F;
//        final int j = par1EntityXPOrb.getBrightnessForRender(par9);
//        final int k = j % 65536;
//        int l = j / 65536;
//        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, k / 1.0F, l / 1.0F);
//        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//        final float f9 = 255.0F;
//        final float f10 = (par1EntityXPOrb.xpColor + par9) / 2.0F;
//        l = (int) ((MathHelper.sin(f10 + 0.0F) + 1.0F) * 0.5F * f9);
//        final int i1 = (int) f9;
//        final int j1 = (int) ((MathHelper.sin(f10 + 4.1887903F) + 1.0F) * 0.1F * f9);
//        final int k1 = l << 16 | i1 << 8 | j1;
//        GL11.glRotatef(180.0F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
//        GL11.glRotatef(-renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
//        final float f11 = 0.3F;
//        GL11.glScalef(f11, f11, f11);
//        final Tessellator tessellator = Tessellator.instance;
//        tessellator.startDrawingQuads();
//        tessellator.setColorRGBA_I(k1, 128);
//        tessellator.setNormal(0.0F, 1.0F, 0.0F);
//        tessellator.addVertexWithUV(0.0F - f7, 0.0F - f8, 0.0D, f2, f5);
//        tessellator.addVertexWithUV(f6 - f7, 0.0F - f8, 0.0D, f3, f5);
//        tessellator.addVertexWithUV(f6 - f7, 1.0F - f8, 0.0D, f3, f4);
//        tessellator.addVertexWithUV(0.0F - f7, 1.0F - f8, 0.0D, f2, f4);
//        tessellator.draw();
//        GL11.glDisable(GL11.GL_BLEND);
//        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
//        GL11.glPopMatrix();
//    }
//}
