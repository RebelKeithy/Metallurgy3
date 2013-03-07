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
        	world.setBlock(x, y, z, this.minableBlockId);
        	world.setBlockMetadata(x, y, z, this.metadata);
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
		
	}

	@Override
	public boolean generate(World world, Random par2Random, int x, int y, int z)
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

