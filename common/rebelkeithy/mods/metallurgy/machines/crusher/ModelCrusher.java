package rebelkeithy.mods.metallurgy.machines.crusher;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelCrusher extends ModelBase
{
    /** The chest lid in the chest's model. */
    public ModelRenderer crusherTop = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);

    /** The model of the bottom of the chest. */
    public ModelRenderer chrusherBelow;

    /** The chest's knob in the chest model. */
    public ModelRenderer chestKnob;

    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    ModelRenderer Shape4;
    ModelRenderer Shape5;
    ModelRenderer Shape6;
    ModelRenderer Shape7;
    ModelRenderer Shape8;
    ModelRenderer Shape9;
    ModelRenderer Shape10;

    public ModelCrusher()
    {
        textureWidth = 64;
        textureHeight = 64;

        Shape1 = new ModelRenderer(this, 0, 20);
        Shape1.addBox(0F, 6F, 0F, 16, 10, 16);
        Shape1.setRotationPoint(0F, 0F, 0F);
        Shape1.setTextureSize(64, 64);
        Shape1.mirror = true;
        Shape2 = new ModelRenderer(this, 0, 0);
        Shape2.addBox(0F, 0F, 0F, 16, 4, 16);
        Shape2.setRotationPoint(0F, 0F, 0F);
        Shape2.setTextureSize(64, 64);
        Shape2.mirror = true;
        Shape3 = new ModelRenderer(this, 24, 45);
        Shape3.addBox(0F, 0F, 0F, 1, 2, 1);
        Shape3.setRotationPoint(0F, 4F, 0F);
        Shape3.setTextureSize(64, 64);
        Shape3.mirror = true;
        Shape4 = new ModelRenderer(this, 24, 45);
        Shape4.addBox(0F, 0F, 0F, 1, 2, 1);
        Shape4.setRotationPoint(0F, 4F, 15F);
        Shape4.setTextureSize(64, 64);
        Shape4.mirror = true;
        Shape5 = new ModelRenderer(this, 24, 45);
        Shape5.addBox(0F, 0F, 0F, 1, 2, 1);
        Shape5.setRotationPoint(15F, 4F, 0F);
        Shape5.setTextureSize(64, 64);
        Shape5.mirror = true;
        Shape6 = new ModelRenderer(this, 24, 45);
        Shape6.addBox(0F, 0F, 0F, 1, 2, 1);
        Shape6.setRotationPoint(15F, 4F, 15F);
        Shape6.setTextureSize(64, 64);
        Shape6.mirror = true;
        Shape7 = new ModelRenderer(this, 0, 40);
        Shape7.addBox(0F, 0F, 0F, 6, 2, 6);
        Shape7.setRotationPoint(1F, 4F, 1F);
        Shape7.setTextureSize(64, 64);
        Shape7.mirror = true;
        Shape8 = new ModelRenderer(this, 0, 40);
        Shape8.addBox(0F, 0F, 0F, 6, 2, 6);
        Shape8.setRotationPoint(9F, 4F, 1F);
        Shape8.setTextureSize(64, 64);
        Shape8.mirror = true;
        Shape9 = new ModelRenderer(this, 0, 40);
        Shape9.addBox(0F, 0F, 0F, 6, 2, 6);
        Shape9.setRotationPoint(1F, 4F, 9F);
        Shape9.setTextureSize(64, 64);
        Shape9.mirror = true;
        Shape10 = new ModelRenderer(this, 0, 40);
        Shape10.addBox(0F, 0F, 0F, 6, 2, 6);
        Shape10.setRotationPoint(9F, 4F, 9F);
        Shape10.setTextureSize(64, 64);
        Shape10.mirror = true;
    }

    /**
     * This method renders out all parts of the chest model.
     */
    public void renderAll()
    {
        Shape1.render(0.0625F);
        Shape2.render(0.0625F);
        Shape3.render(0.0625F);
        Shape4.render(0.0625F);
        Shape5.render(0.0625F);
        Shape6.render(0.0625F);
        Shape7.render(0.0625F);
        Shape8.render(0.0625F);
        Shape9.render(0.0625F);
        Shape10.render(0.0625F);
    }
}
