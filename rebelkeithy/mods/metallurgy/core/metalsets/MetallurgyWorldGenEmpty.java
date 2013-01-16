package rebelkeithy.mods.metallurgy.core.metalsets;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class MetallurgyWorldGenEmpty
{

	public static boolean removeBlocks(World world, int x, int z, int id)
	{
		for(int i = 0; i < 16; i++)
			for(int j = 0; j < world.getActualHeight(); j++)
				for(int k = 0; k < 16; k++)
					if(world.getBlockId(x+i, j, z+k) == id)
						world.setBlock(x+i, j, z+k, 0);
		return true;
	}
}
