package rebelkeithy.mods.metallurgy.integration;

import cpw.mods.fml.common.Loader;
import net.minecraft.item.ItemStack;
import rebelkeithy.mods.metallurgy.api.*;
import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;

public class ThaumcraftIntegration {

	public static void init(){
		if (Loader.isModLoaded("Thaumcraft")){
		addAspects();
		}
	}

	private static void addAspects() {
		/** This structure iterates through all metals available in the mod and adds some best guess values
		 *  We only need to give aspects to dusts, ingots and ores as Thaumcraft automaticly calculates aspects for blocks that
		 *  are make of blocks which all have aspects
		 * 
		 *  As a baseline i am using what Thaumcraft gives to some non-vanilla blocks:
		 *  	For ores, Copper ore gets 5 metal, 2 stone 1 exhange, Tin gets 5 metal, 2 stone 1 control
		 *  	For bars copper gets 6 metal 2 life, Tin gets 6 metal 2 crystal
		 *  	Dusts get 1 destruction and 5-6 metal + 1-2 other aspect to a total of 8
		 *      Generally easily available stuff gives 8 aspects, but more rare stuff like diamon ore and emerald ore give 20
		 * */
		
		for (String setName : rebelkeithy.mods.metallurgy.api.MetallurgyAPI.getMetalSetNames() ){
			for (IOreInfo oreInfo : MetallurgyAPI.getMetalSet(setName).getOreList().values()){
				ItemStack ore = oreInfo.getOre();
				ItemStack dust = oreInfo.getDust();
				ItemStack ingot = oreInfo.getIngot();
				switch (oreInfo.getType()) {

					case ALLOY: //only need to add ingot and dust..

						if(setName == "base")
						{
							if(dust != null)
								ThaumcraftApi.registerObjectTag(dust.itemID ,dust.getItemDamage(), (new ObjectTags()).add(EnumTag.METAL, 7).add(EnumTag.DESTRUCTION, 1));
							if(ingot != null)
								ThaumcraftApi.registerObjectTag(ingot.itemID ,ingot.getItemDamage(), (new ObjectTags()).add(EnumTag.METAL, 6).add(EnumTag.CRYSTAL, 2));
							break;
						}

						if(dust != null)
							ThaumcraftApi.registerObjectTag(dust.itemID ,dust.getItemDamage(), (new ObjectTags()).add(EnumTag.METAL, 7).add(EnumTag.DESTRUCTION, 1));
						if(ingot != null)
							ThaumcraftApi.registerObjectTag(ingot.itemID ,ingot.getItemDamage(), (new ObjectTags()).add(EnumTag.METAL, 6).add(EnumTag.CRYSTAL, 1));
						// TODO more special cases
						break;
						
					case CATALYST: // doing all three, ill just add soem CRAFT to all of em
						if(ore != null)
							ThaumcraftApi.registerObjectTag(ore.itemID ,ore.getItemDamage(), (new ObjectTags()).add(EnumTag.METAL, 5).add(EnumTag.CRAFT, 1).add(EnumTag.ROCK, 2));
						if(dust != null)
							ThaumcraftApi.registerObjectTag(dust.itemID ,dust.getItemDamage(), (new ObjectTags()).add(EnumTag.METAL, 6).add(EnumTag.DESTRUCTION, 1).add(EnumTag.CRAFT, 1));
						if(ingot != null)
							ThaumcraftApi.registerObjectTag(ingot.itemID ,ingot.getItemDamage(), (new ObjectTags()).add(EnumTag.METAL, 7).add(EnumTag.CRAFT, 1));

						break;
						
					case DROP: // redstone ore is 2 earth 1 power 1 mechanish, gunpowder is 2 fire 2 destruction
						
						if(oreInfo.getName() == "Sulfur" && ore != null){ //because used for gunpowder
							ThaumcraftApi.registerObjectTag(ore.itemID ,ore.getItemDamage(), (new ObjectTags()).add(EnumTag.EARTH, 2).add(EnumTag.FIRE, 1).add(EnumTag.DESTRUCTION, 1));
							ItemStack drop = oreInfo.getDrop();
							if(drop != null)
							{
								ThaumcraftApi.registerObjectTag(ore.itemID ,ore.getItemDamage(), (new ObjectTags()).add(EnumTag.FIRE, 2).add(EnumTag.DESTRUCTION, 2));
							}
							break;
						}
						
						if(oreInfo.getName() == "Saltpeter" && ore != null){ //used for fertilizer and gunpowder
							ThaumcraftApi.registerObjectTag(ore.itemID ,ore.getItemDamage(), (new ObjectTags()).add(EnumTag.EARTH, 2).add(EnumTag.CROP, 1).add(EnumTag.DESTRUCTION, 1));
							ItemStack drop = oreInfo.getDrop();
							if(drop != null){
								ThaumcraftApi.registerObjectTag(ore.itemID ,ore.getItemDamage(), (new ObjectTags()).add(EnumTag.CROP, 2).add(EnumTag.DESTRUCTION, 2));
							}
							break;
						} 
						// TODO add more special cases
						if(ore != null)
						{
							ThaumcraftApi.registerObjectTag(ore.itemID ,ore.getItemDamage(), (new ObjectTags()).add(EnumTag.EARTH, 2).add(EnumTag.CROP, 1).add(EnumTag.DESTRUCTION, 1));
							ItemStack drop = oreInfo.getDrop();
							if(drop != null && ore != null)
							{
								ThaumcraftApi.registerObjectTag(ore.itemID ,ore.getItemDamage(), (new ObjectTags()).add(EnumTag.CROP, 2).add(EnumTag.DESTRUCTION, 2));
							}
						}
						break;
					case ORE:
						if(ore != null)
							ThaumcraftApi.registerObjectTag(ore.itemID ,ore.getItemDamage(), (new ObjectTags()).add(EnumTag.METAL, 5).add(EnumTag.CRAFT, 1).add(EnumTag.ROCK, 2));
						if(dust != null)
							ThaumcraftApi.registerObjectTag(dust.itemID ,dust.getItemDamage(), (new ObjectTags()).add(EnumTag.METAL, 6).add(EnumTag.DESTRUCTION, 1).add(EnumTag.CRAFT, 1));
						if(ingot != null)
							ThaumcraftApi.registerObjectTag(ingot.itemID ,ingot.getItemDamage(), (new ObjectTags()).add(EnumTag.METAL, 7).add(EnumTag.CRAFT, 1));
						break;
					case RESPAWN:
						break;
					default:
						break;		
					
				}
			}	
		}
	}
}
