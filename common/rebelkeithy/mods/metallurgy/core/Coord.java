package rebelkeithy.mods.metallurgy.core;

import java.util.ArrayList;
import java.util.List;

public class Coord
{
    public static final List<Coord> adjacent;

    public static List<Coord> between(Coord c1, Coord c2)
    {
        final List<Coord> between = new ArrayList<Coord>();
        for (int x = Math.min(c1.x, c2.x); x <= Math.max(c1.x, c2.x); x++)
        {
            for (int y = Math.min(c1.y, c2.y); y <= Math.max(c1.y, c2.y); y++)
            {
                for (int z = Math.min(c1.z, c2.z); z <= Math.max(c1.z, c2.z); z++)
                {
                    between.add(new Coord(x, y, z));
                }
            }
        }
        return between;
    }

    public static List<Coord> getAdjacentCoords(Coord c)
    {
        final List<Coord> newAdjacent = new ArrayList<Coord>();
        for (final Coord a : adjacent)
        {
            newAdjacent.add(a.add(c));
        }
        return newAdjacent;
    }

    public int x;

    public int y;

    public int z;

    static
    {
        adjacent = new ArrayList<Coord>();
        adjacent.add(new Coord(-1, 0, 0));
        adjacent.add(new Coord(1, 0, 0));
        adjacent.add(new Coord(0, -1, 0));
        adjacent.add(new Coord(0, 1, 0));
        adjacent.add(new Coord(0, 0, -1));
        adjacent.add(new Coord(0, 0, 1));
    }

    public Coord(Coord coord)
    {
        x = coord.x;
        y = coord.y;
        z = coord.z;
    }

    public Coord(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    private Coord add(Coord c)
    {
        return new Coord(x + c.x, y + c.y, z + c.z);
    }

    @Override
    public Coord clone()
    {
        return new Coord(this);
    }

    public boolean equals(int x2, int y2, int z2)
    {
        return x == x2 && y == y2 && z == z2;
    }

    @Override
    public boolean equals(Object o)
    {
        final Coord coord = (Coord) o;
        return x == coord.x && y == coord.y && z == coord.z;
    }

    public Coord subtract(Coord c)
    {
        return new Coord(x - c.x, y - c.y, z - c.z);
    }

    @Override
    public String toString()
    {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
