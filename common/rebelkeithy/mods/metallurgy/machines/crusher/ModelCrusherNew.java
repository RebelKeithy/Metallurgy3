package rebelkeithy.mods.metallurgy.machines.crusher;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCrusherNew extends ModelBase
{
    // fields
    ModelRenderer base;
    ModelRenderer wallleft;
    ModelRenderer wallright;
    ModelRenderer frontcrusher1;
    ModelRenderer frontcrusher2;
    ModelRenderer middlecrusher1;
    ModelRenderer middlecrusher2;
    ModelRenderer backcrusher1;
    ModelRenderer backcrusher2;
    ModelRenderer topbase;
    ModelRenderer toptop;

    public ModelCrusherNew()
    {
        textureWidth = 128;
        textureHeight = 64;

        base = new ModelRenderer(this, 0, 44);
        base.addBox(-8F, -8F, -8F, 16, 4, 16);
        base.setRotationPoint(0F, 28F, 0F);
        base.setTextureSize(128, 64);
        base.mirror = true;
        setRotation(base, 0F, 0F, 0F);
        wallleft = new ModelRenderer(this, 0, 20);
        wallleft.addBox(0F, 0F, 0F, 1, 8, 16);
        wallleft.setRotationPoint(7F, 12F, -8F);
        wallleft.setTextureSize(128, 64);
        wallleft.mirror = true;
        setRotation(wallleft, 0F, 0F, 0F);
        wallright = new ModelRenderer(this, 0, 20);
        wallright.addBox(0F, 0F, 0F, 1, 8, 16);
        wallright.setRotationPoint(-8F, 12F, -8F);
        wallright.setTextureSize(128, 64);
        wallright.mirror = true;
        setRotation(wallright, 0F, 0F, 0F);
        frontcrusher1 = new ModelRenderer(this, 66, 0);
        frontcrusher1.addBox(-8F, -2F, -2F, 14, 4, 4);
        frontcrusher1.setRotationPoint(1F, 16F, -6F);
        frontcrusher1.setTextureSize(128, 64);
        frontcrusher1.mirror = true;
        setRotation(frontcrusher1, 0.7853982F, 0F, 0F);
        frontcrusher2 = new ModelRenderer(this, 66, 0);
        frontcrusher2.addBox(-8F, -2F, -2F, 14, 4, 4);
        frontcrusher2.setRotationPoint(1F, 16F, -6F);
        frontcrusher2.setTextureSize(128, 64);
        frontcrusher2.mirror = true;
        setRotation(frontcrusher2, 0F, 0F, 0F);
        middlecrusher1 = new ModelRenderer(this, 66, 0);
        middlecrusher1.addBox(-8F, -2F, -2F, 14, 4, 4);
        middlecrusher1.setRotationPoint(1F, 16F, 0F);
        middlecrusher1.setTextureSize(128, 64);
        middlecrusher1.mirror = true;
        setRotation(middlecrusher1, 0F, 0F, 0F);
        middlecrusher2 = new ModelRenderer(this, 66, 0);
        middlecrusher2.addBox(-8F, -2F, -2F, 14, 4, 4);
        middlecrusher2.setRotationPoint(1F, 16F, 0F);
        middlecrusher2.setTextureSize(128, 64);
        middlecrusher2.mirror = true;
        setRotation(middlecrusher2, 0.7853982F, 0F, 0F);
        backcrusher1 = new ModelRenderer(this, 66, 0);
        backcrusher1.addBox(-8F, -2F, -2F, 14, 4, 4);
        backcrusher1.setRotationPoint(1F, 16F, 6F);
        backcrusher1.setTextureSize(128, 64);
        backcrusher1.mirror = true;
        setRotation(backcrusher1, 0F, 0F, 0F);
        backcrusher2 = new ModelRenderer(this, 66, 0);
        backcrusher2.addBox(-8F, -2F, -2F, 14, 4, 4);
        backcrusher2.setRotationPoint(1F, 16F, 6F);
        backcrusher2.setTextureSize(128, 64);
        backcrusher2.mirror = true;
        setRotation(backcrusher2, 0.7853982F, 0F, 0F);
        topbase = new ModelRenderer(this, 0, 0);
        topbase.addBox(-8F, -2F, -8F, 16, 2, 16);
        topbase.setRotationPoint(0F, 12F, 0F);
        topbase.setTextureSize(128, 64);
        topbase.mirror = true;
        setRotation(topbase, 0F, 0F, 0F);
        toptop = new ModelRenderer(this, 36, 20);
        toptop.addBox(-6F, -1F, -6F, 12, 2, 12);
        toptop.setRotationPoint(0F, 9F, 0F);
        toptop.setTextureSize(128, 64);
        toptop.mirror = true;
        setRotation(toptop, 0F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        base.render(f5);
        wallleft.render(f5);
        wallright.render(f5);
        frontcrusher1.render(f5);
        frontcrusher2.render(f5);
        middlecrusher1.render(f5);
        middlecrusher2.render(f5);
        backcrusher1.render(f5);
        backcrusher2.render(f5);
        topbase.render(f5);
        toptop.render(f5);
    }

    public void renderAll()
    {
        final float f5 = 1 / 16f;
        base.render(f5);
        wallleft.render(f5);
        wallright.render(f5);
        frontcrusher1.render(f5);
        frontcrusher2.render(f5);
        middlecrusher1.render(f5);
        middlecrusher2.render(f5);
        backcrusher1.render(f5);
        backcrusher2.render(f5);
        topbase.render(f5);
        toptop.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity par7Entity)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, par7Entity);
    }

    public void spin(float amount)
    {
        frontcrusher1.rotateAngleX = amount;
        frontcrusher2.rotateAngleX = amount + 0.75f;
        middlecrusher1.rotateAngleX = -amount;
        middlecrusher2.rotateAngleX = -amount + 0.75f;
        backcrusher1.rotateAngleX = amount;
        backcrusher2.rotateAngleX = amount + 0.75f;
    }
}
