package rebelkeithy.mods.metallurgy.metals.utilityItems.tnt;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.network.packet.Packet60Explosion;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityLargeTNTPrimed extends Entity
{

    /** How long the fuse is */
    public int fuse;

    public EntityLargeTNTPrimed(World par1World)
    {
        super(par1World);
        fuse = 0;
        preventEntitySpawning = true;
        setSize(0.98F, 0.98F);
        yOffset = height / 2.0F;
    }

    public EntityLargeTNTPrimed(World par1World, double par2, double par4, double par6)
    {
        this(par1World);
        setPosition(par2, par4, par6);
        final float var8 = (float) (Math.random() * Math.PI * 2.0D);
        motionX = -((float) Math.sin(var8)) * 0.02F;
        motionY = 0.20000000298023224D;
        motionZ = -((float) Math.cos(var8)) * 0.02F;
        fuse = 80;
        prevPosX = par2;
        prevPosY = par4;
        prevPosZ = par6;
    }

    /**
     * Returns true if other Entities should be prevented from moving through
     * this Entity.
     */
    @Override
    public boolean canBeCollidedWith()
    {
        return !isDead;
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they
     * walk on. used for spiders and wolves to prevent them from trampling crops
     */
    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    protected void entityInit()
    {
    }

    @SuppressWarnings("unchecked")
    private void explode()
    {
        final float strength = 20.0F;
        final Explosion explosion = new Explosion(worldObj, null, posX, posY, posZ, strength);
        explosion.isFlaming = false;
        explosion.isSmoking = true;
        explosion.doExplosionA();
        explosion.doExplosionB(true);

        if (!worldObj.isRemote)
        {
            final Iterator<?> var12 = worldObj.playerEntities.iterator();

            while (var12.hasNext())
            {
                final EntityPlayer player = (EntityPlayer) var12.next();

                if (player.getDistanceSq(posX, posY, posZ) < 4096.0D)
                {
                    ((EntityPlayerMP) player).playerNetServerHandler.sendPacketToPlayer(new Packet60Explosion(posX, posY, posZ, strength, explosion.affectedBlockPositions,
                            (Vec3) explosion.func_77277_b().get(player)));
                    sendPacket(posX, posY, posZ, strength, explosion.affectedBlockPositions, (Vec3) explosion.func_77277_b().get(player));
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getShadowSize()
    {
        return 0.0F;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate()
    {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        motionY -= 0.03999999910593033D;
        moveEntity(motionX, motionY, motionZ);
        motionX *= 0.9800000190734863D;
        motionY *= 0.9800000190734863D;
        motionZ *= 0.9800000190734863D;

        if (onGround)
        {
            motionX *= 0.699999988079071D;
            motionZ *= 0.699999988079071D;
            motionY *= -0.5D;
        }

        if (fuse-- <= 0)
        {
            if (!worldObj.isRemote)
            {
                setDead();
                explode();
            }

        }
        else
        {
            worldObj.spawnParticle("smoke", posX, posY + 0.5D, posZ, 0.0D, 0.0D, 0.0D);
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        fuse = par1NBTTagCompound.getByte("Fuse");
    }

    public void sendPacket(double posX, double posY, double posZ, float strength, List<ChunkPosition> list, Vec3 vec3)
    {
        if (worldObj.isRemote)
        {
            return;
        }

        final ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
        final DataOutputStream dos = new DataOutputStream(bos);
        try
        {
            dos.writeInt(1);
            dos.writeDouble(posX);
            dos.writeDouble(posY);
            dos.writeDouble(posZ);
            dos.writeFloat(strength);
            dos.write(list.size());

            final int var2 = (int) posX;
            final int var3 = (int) posY;
            final int var4 = (int) posZ;

            for (final ChunkPosition var6 : list)
            {
                final int var7 = var6.x - var2;
                final int var8 = var6.y - var3;
                final int var9 = var6.z - var4;
                dos.writeByte(var7);
                dos.writeByte(var8);
                dos.writeByte(var9);
            }

            if (vec3 != null)
            {
                dos.writeFloat((float) vec3.xCoord);
                dos.writeFloat((float) vec3.yCoord);
                dos.writeFloat((float) vec3.xCoord);
            }
            else
            {

                dos.writeFloat(0.0F);
                dos.writeFloat(0.0F);
                dos.writeFloat(0.0F);
            }
        } catch (final IOException e)
        {
            // UNPOSSIBLE?
        }
        final Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = "MetallurgyBase";
        packet.data = bos.toByteArray();
        packet.length = bos.size();
        packet.isChunkDataPacket = true;

        if (packet != null)
        {
            PacketDispatcher.sendPacketToAllAround(posX, posY, posZ, 16, worldObj.provider.dimensionId, packet);
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    @Override
    protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setByte("Fuse", (byte) fuse);
    }

}