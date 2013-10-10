package rebelkeithy.mods.metallurgy.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import rebelkeithy.mods.metallurgy.core.metalsets.ItemMetallurgy;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.google.common.io.InputSupplier;
import com.google.common.io.Resources;

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

    public static void readItemDataFromClassPath(Configuration config, String resourcePath, CreativeTabs tab)
    {
        final BufferedReader in = bufferedReaderFromClassPathResource(resourcePath);
        readItemData(config, in, tab);
    }

    public static void readMetalDataFromFile(String filepath)
    {
        try
        {
            readOreData(bufferedReaderFromFile(filepath));
        }
        catch (FileNotFoundException e)
        {
            MetallurgyCore.log.log(Level.WARNING, String.format(
                    "User supplied file (%s) not found. Check config file.", filepath), e);
        }
    }

    public static void readMetalDataFromClassPath(String resourcePath)
    {
        readOreData(bufferedReaderFromClassPathResource(resourcePath));
    }

    private static BufferedReader bufferedReaderFromFile(String filePath) throws FileNotFoundException
    {
        return Files.newReader(new File(filePath), Charsets.UTF_8);
    }
    
    private static BufferedReader bufferedReaderFromClassPathResource(String resourcePath)
    {
        URL url = Resources.getResource(resourcePath);
        InputSupplier<InputStreamReader> readerSupplier = Resources.newReaderSupplier(url, Charsets.UTF_8);
        BufferedReader in;
        try
        {
            in = new BufferedReader(readerSupplier.getInput());
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        return in;
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
            final List<String> header = Lists.newArrayList(Splitter.on(',').split(input));
            input = in.readLine();
            while (input != null)
            {
                final List<String> inputArray = Lists.newArrayList(Splitter.on(',').split(input));
                final Map<String, String> oreMap = new HashMap<String, String>();
                for (int n = 0; n < inputArray.size(); n++)
                {
                    String column = inputArray.get(n); 
                    if (column.equals("") || column.equals("-"))
                    {
                        column = "0";
                    }
                    oreMap.put(header.get(n), column);
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
