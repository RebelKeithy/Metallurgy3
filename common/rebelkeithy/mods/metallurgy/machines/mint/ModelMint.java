package rebelkeithy.mods.metallurgy.machines.mint;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelMint extends ModelBase
{

    private final ModelRenderer Body;
    private final ModelRenderer Shape2;
    private final ModelRenderer Shape3;
    private final ModelRenderer Shape4;
    private final ModelRenderer Shape5;
    private final ModelRenderer Bar0;
    private final ModelRenderer Bar1;
    private final ModelRenderer Bar2;
    private final ModelRenderer Bar3;
    private final ModelRenderer Head;

    public ModelMint()
    {
        textureWidth = 64;
        textureHeight = 32;

        Body = new ModelRenderer(this, 0, 11);
        Body.addBox(0F, 11F, 0F, 16, 5, 16);
        Body.setRotationPoint(0F, 0F, 0F);
        Body.setTextureSize(64, 32);
        Body.mirror = true;
        setRotation(Body, 0F, 0F, 0F);
        Shape2 = new ModelRenderer(this, 0, 1);
        Shape2.addBox(0F, 10F, 0F, 16, 1, 4);
        Shape2.setRotationPoint(0F, 0F, 0F);
        Shape2.setTextureSize(64, 32);
        Shape2.mirror = true;
        setRotation(Shape2, 0F, 0F, 0F);
        Shape3 = new ModelRenderer(this, 0, 6);
        Shape3.addBox(0F, 10F, 12F, 16, 1, 4);
        Shape3.setRotationPoint(0F, 0F, 0F);
        Shape3.setTextureSize(64, 32);
        Shape3.mirror = true;
        setRotation(Shape3, 0F, 0F, 0F);
        Shape4 = new ModelRenderer(this, 30, 2);
        Shape4.addBox(0F, 10F, 4F, 6, 1, 8);
        Shape4.setRotationPoint(0F, 0F, 0F);
        Shape4.setTextureSize(64, 32);
        Shape4.mirror = true;
        setRotation(Shape4, 0F, 0F, 0F);
        Shape5 = new ModelRenderer(this, 36, 2);
        Shape5.addBox(10F, 10F, 4F, 6, 1, 8);
        Shape5.setRotationPoint(0F, 0F, 0F);
        Shape5.setTextureSize(64, 32);
        Shape5.mirror = true;
        setRotation(Shape5, 0F, 0F, 0F);
        Bar0 = new ModelRenderer(this, 0, 16);
        Bar0.addBox(1F, 0F, 1F, 1, 10, 1);
        Bar0.setRotationPoint(0F, 0F, 0F);
        Bar0.setTextureSize(64, 32);
        Bar0.mirror = true;
        setRotation(Bar0, 0F, 0F, 0F);
        Bar1 = new ModelRenderer(this, 0, 16);
        Bar1.addBox(14F, 0F, 1F, 1, 10, 1);
        Bar1.setRotationPoint(0F, 0F, 0F);
        Bar1.setTextureSize(64, 32);
        Bar1.mirror = true;
        setRotation(Bar1, 0F, 0F, 0F);
        Bar2 = new ModelRenderer(this, 0, 16);
        Bar2.addBox(1F, 0F, 14F, 1, 10, 1);
        Bar2.setRotationPoint(0F, 0F, 0F);
        Bar2.setTextureSize(64, 32);
        Bar2.mirror = true;
        setRotation(Bar2, 0F, 0F, 0F);
        Bar3 = new ModelRenderer(this, 0, 16);
        Bar3.addBox(14F, 0F, 14F, 1, 10, 1);
        Bar3.setRotationPoint(0F, 0F, 0F);
        Bar3.setTextureSize(64, 32);
        Bar3.mirror = true;
        setRotation(Bar3, 0F, 0F, 0F);
        Head = new ModelRenderer(this, 0, 11);
        Head.addBox(0F, 0F, 0F, 16, 4, 16);
        Head.setRotationPoint(0F, 1F, 0F);
        Head.setTextureSize(64, 32);
        Head.mirror = true;
        setRotation(Head, 0F, 0F, 0F);
    }

    /**
     * This method renders out all parts of the chest model.
     */
    public void renderAll()
    {
        Body.render(0.0625F);
        Shape2.render(0.0625F);
        Shape3.render(0.0625F);
        Shape4.render(0.0625F);
        Shape5.render(0.0625F);
        Bar0.render(0.0625F);
        Bar1.render(0.0625F);
        Bar2.render(0.0625F);
        Bar3.render(0.0625F);
        // Head.render(0.0625F);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
