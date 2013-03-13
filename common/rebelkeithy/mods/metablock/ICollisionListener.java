package rebelkeithy.mods.metablock;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public interface ICollisionListener 
{
	
	public void collide(World par1World, int par2, int par3, int par4, Entity par5Entity, int meta);

}
