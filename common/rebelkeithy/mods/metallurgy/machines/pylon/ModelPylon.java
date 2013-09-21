package rebelkeithy.mods.metallurgy.machines.pylon;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPylon extends ModelBase
{
    ModelRenderer base;
    ModelRenderer basetop;
    ModelRenderer cube;

    public ModelPylon()
    {
        textureWidth = 128;
        textureHeight = 64;

        base = new ModelRenderer(this, 0, 38);
        base.addBox(-8F, -1F, -8F, 16, 2, 16);
        base.setRotationPoint(0F, 23F, 0F);
        base.setTextureSize(64, 32);
        base.mirror = true;
        setRotation(base, 0F, 0F, 0F);
        basetop = new ModelRenderer(this, 0, 20);
        basetop.addBox(-6F, -1F, -6F, 12, 2, 12);
        basetop.setRotationPoint(0F, 21F, 0F);
        basetop.setTextureSize(64, 32);
        basetop.mirror = true;
        setRotation(basetop, 0F, 0F, 0F);
        cube = new ModelRenderer(this, 0, 0);
        cube.addBox(-4F, -4F, -4F, 8, 8, 8);
        cube.setRotationPoint(0F, 12F, 0F);
        cube.setTextureSize(64, 32);
        cube.mirror = true;
        setRotation(cube, 0F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        base.render(f5);
        basetop.render(f5);
        cube.render(f5);
    }

    public void renderAll()
    {
        base.render(1 / 16f);
        basetop.render(1 / 16f);
        cube.render(1 / 16f);
    }

    public void setCubeRotation(float x, float y, float z)
    {
        cube.rotateAngleX = x;
        cube.rotateAngleY = y;
        cube.rotateAngleZ = z;
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

}
