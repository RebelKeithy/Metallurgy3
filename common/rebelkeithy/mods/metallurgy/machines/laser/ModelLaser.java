package rebelkeithy.mods.metallurgy.machines.laser;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.ForgeDirection;

//import rebelkeithy.mods.keithyutils.rendering.SimpleBox;

public class ModelLaser extends ModelBase
{
    ModelRenderer Shape1;

    public ModelLaser()
    {
        textureWidth = 64;
        textureHeight = 32;

        Shape1 = new ModelRenderer(this, 0, 0);
        Shape1.addBox(0F, 0F, 0F, 6, 6, 6);
        Shape1.setRotationPoint(9F, 9F, 9F);
        Shape1.setTextureSize(64, 32);
        Shape1.mirror = true;
        setRotation(Shape1, 0F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        Shape1.render(f5);
    }

    public void renderAll()
    {
        Shape1.render(1 / 16F);
    }

    public void renderBeam(float length, ForgeDirection direction)
    {
        // TODO fix eventually
        // if(length == 0)
        // return;
        //
        // int ilength = (int) (length * 16);
        // Tessellator t = Tessellator.instance;
        // SimpleBox beam = new SimpleBox(0, 0, 10, 10, 11.5F, 11.5F, 11.5F, 1,
        // 1, 1, 0.0F);
        // if(direction == ForgeDirection.UP)
        // beam = new SimpleBox(0, 0, 1, 1, 11.5F, 6F - ilength, 11.5F, 1, 6 +
        // ilength, 1, 0.0F);
        // else if(direction == ForgeDirection.DOWN)
        // beam = new SimpleBox(0, 0, 1, 1, 11.5F, 11.5F, 11.5F, 1, 6 + ilength,
        // 1, 0.0F);
        // else if(direction == ForgeDirection.EAST)
        // beam = new SimpleBox(0, 0, 10, 10, 7F - ilength, 11.5F, 11.5F, 2 +
        // ilength, 1, 1, 0.0F);
        // else if(direction == ForgeDirection.WEST)
        // beam = new SimpleBox(0, 0, 10, 10, 11.5F, 11.5F, 11.5F, 6 + ilength,
        // 1, 1, 0.0F);
        // else if(direction == ForgeDirection.NORTH)
        // beam = new SimpleBox(0, 0, 10, 10, 11.5F, 11.5F, 7F - ilength, 1, 1,
        // 2 + ilength, 0.0F);
        // else if(direction == ForgeDirection.SOUTH)
        // beam = new SimpleBox(0, 0, 10, 10, 11.5F, 11.5F, 11.5F, 1, 1, 6 +
        // ilength, 0.0F);
        //
        // //beam = new SimpleBox(0, 0, 10, 10, 11F, 11F, 11F, 6 + ilength, 2,
        // 2, 0.0F);
        // beam.render(t, 1/16F);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

}
