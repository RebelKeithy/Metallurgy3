package rebelkeithy.mods.metallurgy.core.metalsets;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import rebelkeithy.mods.metallurgy.core.MetallurgyCore;

public class MetallurgyWorldGenMinable extends WorldGenerator
{

    private final List<Integer> replaceableBlocks;
    private final int numberOfBlocks;
    private final int metadata;
    private final int minableBlockId;
    private final int density;

    private static List<Integer[]> possibleMoves;

    static
    {
        possibleMoves = new ArrayList<Integer[]>();
        possibleMoves.add(new Integer[]
        { 1, 0, 0 });
        possibleMoves.add(new Integer[]
        { 0, 1, 0 });
        possibleMoves.add(new Integer[]
        { 0, 0, 1 });
        possibleMoves.add(new Integer[]
        { -1, 0, 0 });
        possibleMoves.add(new Integer[]
        { 0, -1, 0 });
        possibleMoves.add(new Integer[]
        { 0, 0, -1 });
        possibleMoves.add(new Integer[]
        { 1, 1, 0 });
        possibleMoves.add(new Integer[]
        { -1, 1, 0 });
        possibleMoves.add(new Integer[]
        { 1, -1, 0 });
        possibleMoves.add(new Integer[]
        { -1, -1, 0 });
        possibleMoves.add(new Integer[]
        { 1, 0, 1 });
        possibleMoves.add(new Integer[]
        { -1, 0, 1 });
        possibleMoves.add(new Integer[]
        { 1, 0, -1 });
        possibleMoves.add(new Integer[]
        { -1, 0, -1 });
        possibleMoves.add(new Integer[]
        { 0, 1, 1 });
        possibleMoves.add(new Integer[]
        { 0, -1, 1 });
        possibleMoves.add(new Integer[]
        { 0, 1, -1 });
        possibleMoves.add(new Integer[]
        { 0, -1, -1 });
    }

    public MetallurgyWorldGenMinable(int id, int meta, int size, int density, Object... replacableIDs)
    {
        minableBlockId = id;
        metadata = meta;
        numberOfBlocks = size;
        this.density = density;
        replaceableBlocks = new ArrayList<Integer>();
        for (final Object i : replacableIDs)
        {
            replaceableBlocks.add((Integer) i);
        }
    }

    @Override
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        final float f = par2Random.nextFloat() * (float) Math.PI;
        final double d0 = par3 + 8 + MathHelper.sin(f) * numberOfBlocks / 8.0F;
        final double d1 = par3 + 8 - MathHelper.sin(f) * numberOfBlocks / 8.0F;
        final double d2 = par5 + 8 + MathHelper.cos(f) * numberOfBlocks / 8.0F;
        final double d3 = par5 + 8 - MathHelper.cos(f) * numberOfBlocks / 8.0F;
        final double d4 = par4 + par2Random.nextInt(3) - 2;
        final double d5 = par4 + par2Random.nextInt(3) - 2;

        for (int l = 0; l <= numberOfBlocks; ++l)
        {
            final double d6 = d0 + (d1 - d0) * l / numberOfBlocks;
            final double d7 = d4 + (d5 - d4) * l / numberOfBlocks;
            final double d8 = d2 + (d3 - d2) * l / numberOfBlocks;
            final double d9 = par2Random.nextDouble() * numberOfBlocks / 16.0D;
            final double d10 = (MathHelper.sin(l * (float) Math.PI / numberOfBlocks) + 1.0F) * d9 + 1.0D;
            final double d11 = (MathHelper.sin(l * (float) Math.PI / numberOfBlocks) + 1.0F) * d9 + 1.0D;
            final int i1 = MathHelper.floor_double(d6 - d10 / 2.0D);
            final int j1 = MathHelper.floor_double(d7 - d11 / 2.0D);
            final int k1 = MathHelper.floor_double(d8 - d10 / 2.0D);
            final int l1 = MathHelper.floor_double(d6 + d10 / 2.0D);
            final int i2 = MathHelper.floor_double(d7 + d11 / 2.0D);
            final int j2 = MathHelper.floor_double(d8 + d10 / 2.0D);

            for (int k2 = i1; k2 <= l1; ++k2)
            {
                final double d12 = (k2 + 0.5D - d6) / (d10 / 2.0D);

                if (d12 * d12 < 1.0D)
                {
                    for (int l2 = j1; l2 <= i2; ++l2)
                    {
                        final double d13 = (l2 + 0.5D - d7) / (d11 / 2.0D);

                        if (d12 * d12 + d13 * d13 < 1.0D)
                        {
                            for (int i3 = k1; i3 <= j2; ++i3)
                            {
                                final double d14 = (i3 + 0.5D - d8) / (d10 / 2.0D);

                                final Block block = Block.blocksList[par1World.getBlockId(k2, l2, i3)];
                                if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D)
                                {
                                    if (MetallurgyCore.spawnInAir)
                                    {
                                        par1World.setBlock(k2, l2, i3, minableBlockId, metadata, 2);
                                    }
                                    else if (block != null)
                                    {
                                        if (block.blockID == Block.stone.blockID || block.isGenMineableReplaceable(par1World, k2, l2, i3, Block.stone.blockID)
                                                || block.blockID == Block.netherrack.blockID || block.blockID == Block.whiteStone.blockID)
                                        {
                                            par1World.setBlock(k2, l2, i3, minableBlockId, metadata, 2);
                                        }
                                    }
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
        final List<Integer[]> spawnedCoords = new ArrayList<Integer[]>();
        // SortedList<Integer[]> spawnedCoords = new SortedList<Integer[]>(new
        // CompareCoordinates());

        spawnOre(world, x, y, z);
        spawnedCoords.add(new Integer[]
        { x, y, z });

        for (int n = 1; n < numberOfBlocks; n++)
        {
            final List<Integer[]> cpm = new ArrayList<Integer[]>();
            for (final Integer[] i : possibleMoves)
            {
                cpm.add(i);
            }

            int pickedOre = (int) (par2Random.nextFloat() * par2Random.nextFloat() * spawnedCoords.size());
            pickedOre = spawnedCoords.size() - 1;
            final Integer[] coords = spawnedCoords.get(pickedOre);

            Integer[] finalCoords =
            { coords[0], coords[1], coords[2] };
            do
            {
                if (cpm.size() == 0)
                {
                    n--;
                    spawnedCoords.remove(pickedOre);
                    break;
                }
                final int pick = (int) (par2Random.nextFloat() * cpm.size());
                final Integer[] offset = cpm.get(pick);
                finalCoords = new Integer[]
                { coords[0] + offset[0], coords[1] + offset[1], coords[2] + offset[2] };
                cpm.remove(pick);
            } while (spawnedCoords.contains(finalCoords));

            if (par2Random.nextInt(100) < density)
            {
                spawnOre(world, finalCoords);
            }
            spawnedCoords.add(finalCoords);
        }

        return true;
    }

    public boolean spawnOre(World world, int x, int y, int z)
    {
        final int currentID = world.getBlockId(x, y, z);
        if (replaceableBlocks.contains(currentID))
        {
            world.setBlock(x, y, z, minableBlockId, metadata, 2);
            return true;
        }

        return false;
    }

    public boolean spawnOre(World world, Integer[] coords)
    {
        return spawnOre(world, coords[0], coords[1], coords[2]);
    }
}
