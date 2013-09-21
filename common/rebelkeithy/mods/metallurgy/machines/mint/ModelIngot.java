package rebelkeithy.mods.metallurgy.machines.mint;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelIngot extends ModelBase
{

    ModelRenderer Shape1;

    public ModelIngot()
    {
        textureWidth = 32;
        textureHeight = 16;

        Shape1 = new ModelRenderer(this, 0, 0);
        Shape1.addBox(-12F, 9F, 6F, 8, 2, 4);
        Shape1.setRotationPoint(0F, 0F, 0F);
        Shape1.setTextureSize(64, 32);
        Shape1.mirror = true;
        setRotation(Shape1, 0F, 1.570796F, 0F);
    }

    /**
     * This method renders out all parts of the chest model.
     */
    public void renderAll()
    {
        Shape1.render(0.0625F);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
