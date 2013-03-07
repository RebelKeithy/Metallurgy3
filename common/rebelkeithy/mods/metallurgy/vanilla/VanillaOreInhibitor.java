package rebelkeithy.mods.metallurgy.vanilla;

import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable;

public class VanillaOreInhibitor 
{
	@ForgeSubscribe
	public void stopOre(GenerateMinable event)
	{
		if(event.type != GenerateMinable.EventType.CUSTOM)
			event.setResult(Result.DENY);
	}
}
