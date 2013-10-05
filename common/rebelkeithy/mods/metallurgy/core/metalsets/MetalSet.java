package rebelkeithy.mods.metallurgy.core.metalsets;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.Configuration;
import rebelkeithy.mods.metallurgy.api.IMetalSet;
import rebelkeithy.mods.metallurgy.api.IOreInfo;
import rebelkeithy.mods.metallurgy.core.MetallurgyCore;

public class MetalSet implements IMetalSet
{
    private final String setName;
    private final Map<String, IOreInfo> metals;
    private Configuration config;

    public MetalSet(String setName, Map<String, Map<String, String>> baseData, CreativeTabs tab)
    {
        this.setName = setName;

        metals = new HashMap<String, IOreInfo>();

        for (final Map<String, String> metalInfo : baseData.values())
        {
            metals.put(metalInfo.get("Name"), new OreInfo(metalInfo, tab));
        }

        MetallurgyCore.getMetalSetList().add(this);

        initConfig();
        init();
    }

    @Override
    public OreInfo getOreInfo(int meta)
    {
        for (final IOreInfo oreInfo : metals.values())
        {
            if (((OreInfo) oreInfo).oreMeta == meta)
            {
                return (OreInfo) oreInfo;
            }
        }
        return null;
    }

    @Override
    public OreInfo getOreInfo(String name)
    {
        if (metals.containsKey(name))
        {
            return (OreInfo) metals.get(name);
        }
        else
        {
            return new OreInfo();
        }
    }

    @Override
    public Map<String, IOreInfo> getOreList()
    {
        return metals;
    }

    public void init()
    {
        for (final IOreInfo oreInfo : metals.values())
        {
            ((OreInfo) oreInfo).init();
        }
    }

    public void initConfig()
    {
        final File fileDir = new File(MetallurgyCore.proxy.getMinecraftDir() + "/config/Metallurgy3");
        fileDir.mkdir();
        final File cfgFile = new File(MetallurgyCore.proxy.getMinecraftDir() + "/config/Metallurgy3/Metallurgy" + setName + ".cfg");

        try
        {
            cfgFile.createNewFile();
        } catch (final IOException e)
        {
            MetallurgyCore.log.warning("[Metallurgy3] Could not create configuration file for Metallurgy 3 metal set " + setName + ". Reason:");
            MetallurgyCore.log.warning(e.getLocalizedMessage());
        }

        config = new Configuration(cfgFile);
        config.load();

        for (final IOreInfo oreInfo : metals.values())
        {
            ((OreInfo) oreInfo).initConfig(config);
        }

        config.save();
    }

    public void load()
    {
        for (final IOreInfo oreInfo : metals.values())
        {
            ((OreInfo) oreInfo).load();
        }
    }

    public void registerNames()
    {
        for (final IOreInfo oreInfo : metals.values())
        {
            ((OreInfo) oreInfo).registerNames();
        }
    }

}
