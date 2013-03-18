package rebelkeithy.mods.metallurgy.core.metalsets;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

public class MetallurgyWorldGenMinable extends WorldGenerator
{

	private List<Integer> replaceableBlocks;
	private int numberOfBlocks;
	private int metadata;
	private int minableBlockId;
	private int density;
	
	private static List<Integer[]> possibleMoves;
	
	static 
	{
		possibleMoves = new ArrayList<Integer[]>();
		possibleMoves.add(new Integer[] {1, 0, 0});
		possibleMoves.add(new Integer[] {0, 1, 0});
		possibleMoves.add(new Integer[] {0, 0, 1});
		possibleMoves.add(new Integer[] {-1, 0, 0});
		possibleMoves.add(new Integer[] {0, -1, 0});
		possibleMoves.add(new Integer[] {0, 0, -1});
		possibleMoves.add(new Integer[] {1, 1, 0});
		possibleMoves.add(new Integer[] {-1, 1, 0});
		possibleMoves.add(new Integer[] {1, -1, 0});
		possibleMoves.add(new Integer[] {-1, -1, 0});
		possibleMoves.add(new Integer[] {1, 0, 1});
		possibleMoves.add(new Integer[] {-1, 0, 1});
		possibleMoves.add(new Integer[] {1, 0, -1});
		possibleMoves.add(new Integer[] {-1, 0, -1});
		possibleMoves.add(new Integer[] {0, 1, 1});
		possibleMoves.add(new Integer[] {0, -1, 1});
		possibleMoves.add(new Integer[] {0, 1, -1});
		possibleMoves.add(new Integer[] {0, -1, -1});
	}
	
	public MetallurgyWorldGenMinable(int id, int meta, int size, int density, Object... replacableIDs)
	{
		minableBlockId = id;
		metadata = meta;
		numberOfBlocks = size;
		this.density = density;
		replaceableBlocks = new ArrayList<Integer>();
		for(Object i : replacableIDs)
		{
			replaceableBlocks.add((Integer) i);
		}
	}
	
	public boolean spawnOre(World world, Integer[] coords)
	{
		return spawnOre(world, coords[0], coords[1], coords[2]);
	}
	
	public boolean spawnOre(World world, int x, int y, int z)
	{
		int currentID = world.getBlockId(x, y, z);
        if(replaceableBlocks.contains(currentID))
        {
        	world.setBlockAndMetadataWithNotify(x, y, z, this.minableBlockId, this.metadata, 2);
        	return true;
        }
        
        return false;
	}
	
	private class CompareCoordinates implements Comparator<Integer[]>
	{
		@Override
		public int compare(Integer[] arg0, Integer[] arg1) {
			return (arg0[0] - arg1[0]) + (arg0[1] - arg1[1]) + (arg0[2] - arg1[2]);
		}
		
	}public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        float f = par2Random.nextFloat() * (float)Math.PI;
        double d0 = (double)((float)(par3 + 8) + MathHelper.sin(f) * (float)this.numberOfBlocks / 8.0F);
        double d1 = (double)((float)(par3 + 8) - MathHelper.sin(f) * (float)this.numberOfBlocks / 8.0F);
        double d2 = (double)((float)(par5 + 8) + MathHelper.cos(f) * (float)this.numberOfBlocks / 8.0F);
        double d3 = (double)((float)(par5 + 8) - MathHelper.cos(f) * (float)this.numberOfBlocks / 8.0F);
        double d4 = (double)(par4 + par2Random.nextInt(3) - 2);
        double d5 = (double)(par4 + par2Random.nextInt(3) - 2);

        for (int l = 0; l <= this.numberOfBlocks; ++l)
        {
            double d6 = d0 + (d1 - d0) * (double)l / (double)this.numberOfBlocks;
            double d7 = d4 + (d5 - d4) * (double)l / (double)this.numberOfBlocks;
            double d8 = d2 + (d3 - d2) * (double)l / (double)this.numberOfBlocks;
            double d9 = par2Random.nextDouble() * (double)this.numberOfBlocks / 16.0D;
            double d10 = (double)(MathHelper.sin((float)l * (float)Math.PI / (float)this.numberOfBlocks) + 1.0F) * d9 + 1.0D;
            double d11 = (double)(MathHelper.sin((float)l * (float)Math.PI / (float)this.numberOfBlocks) + 1.0F) * d9 + 1.0D;
            int i1 = MathHelper.floor_double(d6 - d10 / 2.0D);
            int j1 = MathHelper.floor_double(d7 - d11 / 2.0D);
            int k1 = MathHelper.floor_double(d8 - d10 / 2.0D);
            int l1 = MathHelper.floor_double(d6 + d10 / 2.0D);
            int i2 = MathHelper.floor_double(d7 + d11 / 2.0D);
            int j2 = MathHelper.floor_double(d8 + d10 / 2.0D);

            for (int k2 = i1; k2 <= l1; ++k2)
            {
                double d12 = ((double)k2 + 0.5D - d6) / (d10 / 2.0D);

                if (d12 * d12 < 1.0D)
                {
                    for (int l2 = j1; l2 <= i2; ++l2)
                    {
                        double d13 = ((double)l2 + 0.5D - d7) / (d11 / 2.0D);

                        if (d12 * d12 + d13 * d13 < 1.0D)
                        {
                            for (int i3 = k1; i3 <= j2; ++i3)
                            {
                                double d14 = ((double)i3 + 0.5D - d8) / (d10 / 2.0D);

                                Block block = Block.blocksList[par1World.getBlockId(k2, l2, i3)];
                                if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D && (block != null && (block.isGenMineableReplaceable(par1World, k2, l2, i3, 0) || block.blockID == Block.netherrack.blockID || block.blockID == Block.whiteStone.blockID)))
                                {
                                    par1World.setBlockAndMetadataWithNotify(k2, l2, i3, this.minableBlockId, metadata, 2);
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }
	

	public boolean generate2(World world, Random par2Random, int x, int y, int z)
    {
		List<Integer[]> spawnedCoords = new ArrayList<Integer[]>();
		//SortedList<Integer[]> spawnedCoords = new SortedList<Integer[]>(new CompareCoordinates());
		
		spawnOre(world, x, y, z);
		spawnedCoords.add(new Integer[] {x, y, z});
		
		int trueSize = 1;
		for(int n = 1; n < this.numberOfBlocks; n++)
		{
			List<Integer[]> cpm = new ArrayList<Integer[]>();
			for(Integer[] i : possibleMoves)
				cpm.add(i);
			
			int pickedOre = (int) (((par2Random.nextFloat()*par2Random.nextFloat())) * spawnedCoords.size());
			pickedOre = spawnedCoords.size()-1;
			Integer[] coords = spawnedCoords.get(pickedOre);
			
			Integer[] finalCoords = {coords[0], coords[1], coords[2]};
			do
			{
				if(cpm.size() == 0)
				{
					n--;
					spawnedCoords.remove(pickedOre);
					break;
				}
				int pick = (int) (par2Random.nextFloat() * cpm.size());
				Integer[] offset = cpm.get(pick);
				finalCoords = new Integer[] { coords[0] + offset[0], coords[1] + offset[1], coords[2] + offset[2] };
				cpm.remove(pick);
			} while(spawnedCoords.contains(finalCoords));
			
			if(par2Random.nextInt(100) < density)
			{
				spawnOre(world, finalCoords);
				trueSize++;
			}
			spawnedCoords.add(finalCoords);
		}
		
		//System.out.println("Ore vein size = " + trueSize);
        return true;
    }
}

