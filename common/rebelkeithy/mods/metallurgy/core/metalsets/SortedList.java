package rebelkeithy.mods.metallurgy.core.metalsets;

import java.util.ArrayList;
import java.util.Comparator;

public class SortedList<T> extends ArrayList<T>
{
    /**
     * 
     */
    private static final long serialVersionUID = -3816680929059512706L;
    Comparator comp;

    public SortedList(Comparator comp)
    {
        this.comp = comp;
    }

    @Override
    public boolean add(T o)
    {
        for (int n = 0; n < size(); n++)
        {
            if (comp.compare(get(n), o) >= 0)
            {
                this.add(n + 1, o);
                return true;
            }
        }

        this.add(size(), o);
        return true;
    }
}