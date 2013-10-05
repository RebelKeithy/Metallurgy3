package rebelkeithy.mods.metallurgy.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import rebelkeithy.mods.metallurgy.core.metalsets.ItemMetallurgy;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class MetalInfoDatabase
{
    private static List<Map<String, String>> spreadsheet;
    private static Map<String, Item> items;
    private static Map<String, String> oreDictNames;

    public static ItemStack getItem(String itemName)
    {
        if (items == null || !items.containsKey(itemName))
        {
            return null;
        }

        return new ItemStack(items.get(itemName));
    }

    public static Map<String, Map<String, String>> getSpreadsheetDataForSet(String name)
    {
        final Map<String, Map<String, String>> returnData = new HashMap<String, Map<String, String>>();

        if (spreadsheet == null)
        {
            return returnData;
        }

        for (final Map<String, String> data : spreadsheet)
        {

            if (data.get("Metal Set").equals(name))
            {
                returnData.put(data.get("Name"), data);
            }
        }
        return returnData;
    }

    private static void readItemData(Configuration config, BufferedReader in, CreativeTabs tab)
    {
        if (items == null)
        {
            items = new HashMap<String, Item>();
        }
        if (oreDictNames == null)
        {
            oreDictNames = new HashMap<String, String>();
        }

        try
        {
            String input = in.readLine();
            final String[] header = input.split(",");
            input = in.readLine();
            while (input != null)
            {
                final String[] inputArray = input.split(",");
                final Map<String, String> itemMap = new HashMap<String, String>();
                for (int n = 0; n < inputArray.length; n++)
                {
                    if (inputArray[n].equals(""))
                    {
                        inputArray[n] = "0";
                    }
                    itemMap.put(header[n], inputArray[n]);
                }

                int id = Integer.parseInt(itemMap.get("Item ID"));

                id = config.get("Item IDs", itemMap.get("Item Name"), id).getInt();

                final Item item = new ItemMetallurgy(id).setTextureName("Metallurgy:" + itemMap.get("Set Name") + "/" + itemMap.get("Item Name"))
                        .setUnlocalizedName("Metallurgy:" + itemMap.get("Set Name") + "/" + itemMap.get("Item Name")).setCreativeTab(tab);
                LanguageRegistry.addName(item, itemMap.get("Item Name"));

                items.put(itemMap.get("Item Name"), item);
                if (!itemMap.get("Ore Dictionary Name").equals("0"))
                {
                    oreDictNames.put(itemMap.get("Item Name"), itemMap.get("Ore Dictionary Name"));
                }
                input = in.readLine();
            }
            in.close();
        } catch (final IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void readItemDataFromFile(Configuration config, String filepath, CreativeTabs tab)
    {
        try
        {
            final File file = new File(filepath);
            final BufferedReader in = new BufferedReader(new FileReader(file));
            readItemData(config, in, tab);
        } catch (final FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public static void readItemDataFromJar(Configuration config, String filename, String jarpath, CreativeTabs tab) throws IOException
    {
        final ZipFile zip = new ZipFile(jarpath);
        final ZipEntry entry = zip.getEntry(filename);
        final BufferedReader in = new BufferedReader(new InputStreamReader(zip.getInputStream(entry)));
        readItemData(config, in, tab);

        zip.close();

    }

    public static void readMetalDataFromFile(String filepath)
    {
        try
        {
            final File file = new File(filepath);
            final BufferedReader in = new BufferedReader(new FileReader(file));
            readOreData(in);
        } catch (final FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public static void readMetalDataFromJar(String filename, String jarpath) throws IOException
    {
        final ZipFile zip = new ZipFile(jarpath);
        final ZipEntry entry = zip.getEntry(filename);
        final BufferedReader in = new BufferedReader(new InputStreamReader(zip.getInputStream(entry)));
        readOreData(in);

        zip.close();
    }

    private static void readOreData(BufferedReader in)
    {
        if (spreadsheet == null)
        {
            spreadsheet = new ArrayList<Map<String, String>>();
        }

        try
        {
            String input = in.readLine();
            final String[] header = input.split(",");
            input = in.readLine();
            while (input != null)
            {
                final String[] inputArray = input.split(",");
                final Map<String, String> oreMap = new HashMap<String, String>();
                for (int n = 0; n < inputArray.length; n++)
                {
                    if (inputArray[n].equals("") || inputArray[n].equals("-"))
                    {
                        inputArray[n] = "0";
                    }
                    oreMap.put(header[n], inputArray[n]);
                }
                spreadsheet.add(oreMap);
                input = in.readLine();
            }
            in.close();
        } catch (final IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void registerItemsWithOreDict()
    {
        for (final String name : oreDictNames.keySet())
        {
            OreDictionary.registerOre(oreDictNames.get(name), items.get(name));
        }
    }
}
