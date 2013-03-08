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

public class EntityLargeTNTPrimed extends Entity {

    /** How long the fuse is */
    public int fuse;

    public EntityLargeTNTPrimed(World par1World)
    {
        super(par1World);
        this.fuse = 0;
        this.preventEntitySpawning = true;
        this.setSize(0.98F, 0.98F);
        this.yOffset = this.height / 2.0F;
    }

    public EntityLargeTNTPrimed(World par1World, double par2, double par4, double par6)
    {
        this(par1World);
        this.setPosition(par2, par4, par6);
        float var8 = (float)(Math.random() * Math.PI * 2.0D);
        this.motionX = (double)(-((float)Math.sin((double)var8)) * 0.02F);
        this.motionY = 0.20000000298023224D;
        this.motionZ = (double)(-((float)Math.cos((double)var8)) * 0.02F);
        this.fuse = 80;
        this.prevPosX = par2;
        this.prevPosY = par4;
        this.prevPosZ = par6;
    }

    protected void entityInit() {}

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
        return !this.isDead;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.03999999910593033D;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863D;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= 0.9800000190734863D;

        if (this.onGround)
        {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
            this.motionY *= -0.5D;
        }

        if (this.fuse-- <= 0)
        {
            if (!this.worldObj.isRemote)
            {
                this.setDead();
                this.explode();
            }
            
        }
        else
        {
            this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
        }
    }
    
	private void explode()
    {
        float strength = 20.0F;
        Explosion explosion = new Explosion(worldObj, null, this.posX, this.posY, this.posZ, strength);
        explosion.isFlaming = false;
        explosion.isSmoking = true;
        explosion.doExplosionA();
        explosion.doExplosionB(true);
        
		if(!worldObj.isRemote)
		{
	        Iterator var12 = worldObj.playerEntities.iterator();

	        while (var12.hasNext())
	        {
	            EntityPlayer player = (EntityPlayer)var12.next();

	            if (player.getDistanceSq(this.posX, this.posY, this.posZ) < 4096.0D)
	            {
	                ((EntityPlayerMP)player).playerNetServerHandler.sendPacketToPlayer(new Packet60Explosion(this.posX, this.posY, this.posZ, strength, explosion.affectedBlockPositions, (Vec3)explosion.func_77277_b().get(player)));
	                sendPacket(this.posX, this.posY, this.posZ, strength, explosion.affectedBlockPositions, (Vec3)explosion.func_77277_b().get(player));
	            }
	        }
		}
    }
    
	public void sendPacket(double posX, double posY, double posZ, float strength, List list, Vec3 vec3)
	{
		if(worldObj.isRemote)
			return;

		ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeInt(1);
			dos.writeDouble(posX);
			dos.writeDouble(posY);
			dos.writeDouble(posZ);
			dos.writeFloat(strength);
			dos.write(list.size());

			int var2 = (int)posX;
			int var3 = (int)posY;
			int var4 = (int)posZ;
	        Iterator var5 = list.iterator();

	        while (var5.hasNext())
	        {
	            ChunkPosition var6 = (ChunkPosition)var5.next();
	            int var7 = var6.x - var2;
	            int var8 = var6.y - var3;
	            int var9 = var6.z - var4;
	            dos.writeByte(var7);
	            dos.writeByte(var8);
	            dos.writeByte(var9);
	        }

	        if(vec3 != null)
	        {
	        dos.writeFloat((float)(vec3.xCoord));
	        dos.writeFloat((float)(vec3.yCoord));
	        dos.writeFloat((float)(vec3.xCoord));
	        } else {

		        dos.writeFloat(0.0F);
		        dos.writeFloat(0.0F);
		        dos.writeFloat(0.0F);
	        }
		} catch (IOException e) {
			// UNPOSSIBLE?
		}
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "MetallurgyUtility";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		packet.isChunkDataPacket = true;

		if (packet != null) {
			PacketDispatcher.sendPacketToAllAround(posX, posY, posZ, 16, worldObj.provider.dimensionId, packet);
		}
	}

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setByte("Fuse", (byte)this.fuse);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        this.fuse = par1NBTTagCompound.getByte("Fuse");
    }

    @SideOnly(Side.CLIENT)
    public float getShadowSize()
    {
        return 0.0F;
    }

}