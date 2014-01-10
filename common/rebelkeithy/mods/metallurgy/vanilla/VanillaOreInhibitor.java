package rebelkeithy.mods.metallurgy.vanilla;

import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable;

public class VanillaOreInhibitor
{
    @ForgeSubscribe
    public void stopOre(GenerateMinable event)
    {
        if (event.type != GenerateMinable.EventType.DIRT && event.type != GenerateMinable.EventType.GRAVEL && event.type != GenerateMinable.EventType.CUSTOM)
        {
            if (event.type == GenerateMinable.EventType.COAL && MetallurgyVanilla.vanillaSet.getOreInfo("Coal").isEnabled())
            {
                event.setResult(Result.DENY);
            }
            if (event.type == GenerateMinable.EventType.DIAMOND && MetallurgyVanilla.vanillaSet.getOreInfo("Diamond").isEnabled())
            {
                event.setResult(Result.DENY);
            }
            if (event.type == GenerateMinable.EventType.GOLD && MetallurgyVanilla.vanillaSet.getOreInfo("Gold").isEnabled())
            {
                event.setResult(Result.DENY);
            }
            if (event.type == GenerateMinable.EventType.IRON && MetallurgyVanilla.vanillaSet.getOreInfo("Iron").isEnabled())
            {
                event.setResult(Result.DENY);
            }
            if (event.type == GenerateMinable.EventType.LAPIS && MetallurgyVanilla.vanillaSet.getOreInfo("Lapis Lazuli").isEnabled())
            {
                event.setResult(Result.DENY);
            }
            if (event.type == GenerateMinable.EventType.REDSTONE && MetallurgyVanilla.vanillaSet.getOreInfo("Redstone").isEnabled())
            {
                event.setResult(Result.DENY);
            }
        }
    }
}
