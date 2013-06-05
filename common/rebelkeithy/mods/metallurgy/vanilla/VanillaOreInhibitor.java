package rebelkeithy.mods.metallurgy.vanilla;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable;

public class VanillaOreInhibitor 
{	
	@ForgeSubscribe
	public void stopOre(GenerateMinable event)
	{
		if(event.type != GenerateMinable.EventType.DIRT && event.type != GenerateMinable.EventType.GRAVEL &&event.type != GenerateMinable.EventType.CUSTOM)
		{
			event.setResult(Result.DENY);
		}
	}
}
