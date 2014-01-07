package rebelkeithy.mods.metallurgy.api;

import java.lang.reflect.Field;

public class MetallurgyAPI
{

    // Values for name: "base", "precious", "nether", "fantasy", "ender",
    // "utility"
    public static IMetalSet getMetalSet(String name)
    {
        try
        {
            final Class<?> metallurgyMetals = Class.forName("rebelkeithy.mods.metallurgy.metals.MetallurgyMetals");
            final Field set = metallurgyMetals.getField(name + "Set");
            return (IMetalSet) set.get(null);
        } catch (final NoSuchFieldException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final SecurityException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final ClassNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final IllegalArgumentException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final IllegalAccessException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public static String[] getMetalSetNames()
    {
        // TODO maybe put something to get runtime list here
        final String[] names =
        { "base", "precious", "nether", "fantasy", "ender", "utility" };
        return names;
    }
}
