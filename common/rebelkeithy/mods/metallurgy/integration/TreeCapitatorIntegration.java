package rebelkeithy.mods.metallurgy.integration;

import net.minecraft.nbt.NBTTagCompound;
import rebelkeithy.mods.metallurgy.api.IOreInfo;
import rebelkeithy.mods.metallurgy.core.MetallurgyCore;
import rebelkeithy.mods.metallurgy.core.metalsets.MetalSet;
import rebelkeithy.mods.metallurgy.core.metalsets.OreInfo;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInterModComms;

public class TreeCapitatorIntegration
{
    public static void init()
    {
        if (Loader.isModLoaded("TreeCapitator"))
        {
            String axeList = "";
            
            for (MetalSet ms : MetallurgyCore.getMetalSetList())
                for (IOreInfo oi : ms.getOreList().values())
                    if (oi.isEnabled() && ((OreInfo) oi).axe != null)
                        axeList += "; " + ((OreInfo) oi).axe.itemID;
            
            NBTTagCompound tpModCfg = new NBTTagCompound();
            tpModCfg.setString("modID", "Metallurgy3Base");
            tpModCfg.setString("axeIDList", axeList);
            
            FMLInterModComms.sendMessage("TreeCapitator", "ThirdPartyModConfig", tpModCfg);
        }
    }
}
