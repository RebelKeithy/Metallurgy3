package rebelkeithy.mods.metallurgy.integration;

import rebelkeithy.mods.metallurgy.api.IMetalSet;
import rebelkeithy.mods.metallurgy.api.MetallurgyAPI;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class RailcraftIntegration {
	
	public static Item mithrilBoreHead;
	public static Item celenegilBoreHead;
	public static Item kalendriteBoreHead;
	public static Item vulcaniteBoreHead;
	public static Item tartariteBoreHead;
	
	public static void init(){
		if (Loader.isModLoaded("Railcraft")){
			// TODO fix item ids
			String texLocBase = "/mods/Metallurgy/textures/misc/";
			mithrilBoreHead = new BoreHead(7000, 2000, "borehead.mithril", texLocBase + "tunnel_bore_mithril.png", 1.6F, 5);
			celenegilBoreHead = new BoreHead(7001, 4000, "borehead.celenegil", texLocBase + "tunnel_bore_celenegil.png", 1.7F, 6);
			kalendriteBoreHead = new BoreHead(7002, 2000, "borehead.kalendrite", texLocBase + "tunnel_bore_kalendrite.png", 1.6F, 5);
			vulcaniteBoreHead = new BoreHead(7003, 4000, "borehead.vulcanite", texLocBase + "tunnel_bore_vulcanite.png", 1.7F, 6);
			tartariteBoreHead = new BoreHead(7004, 12000, "borehead.tartarite", texLocBase + "tunnel_bore_tartarite.png", 1.9F, 8);
			
			LanguageRegistry.addName(mithrilBoreHead, "Mithril Bore Head");
			LanguageRegistry.addName(celenegilBoreHead, "Celenegil Bore Head");
			LanguageRegistry.addName(kalendriteBoreHead, "Kaledrite Bore Head");
			LanguageRegistry.addName(vulcaniteBoreHead, "Vulcanite Bore Head");
			LanguageRegistry.addName(tartariteBoreHead, "Tartarite Bore Head");
			
			GameRegistry.addRecipe(new ShapedOreRecipe(mithrilBoreHead, new Object[] {"XXX","XBX","XXX",Character.valueOf('X'),"ingotSteel",Character.valueOf('B'), "blockMithril"}));
			GameRegistry.addRecipe(new ShapedOreRecipe(celenegilBoreHead, new Object[] {"XXX","XBX","XXX",Character.valueOf('X'),"ingotSteel",Character.valueOf('B'), "blockCelenegil"}));
			GameRegistry.addRecipe(new ShapedOreRecipe(kalendriteBoreHead, new Object[] {"XXX","XBX","XXX",Character.valueOf('X'),"ingotSteel",Character.valueOf('B'), "blockKalendrite"}));
			GameRegistry.addRecipe(new ShapedOreRecipe(vulcaniteBoreHead, new Object[] {"XXX","XBX","XXX",Character.valueOf('X'),"ingotSteel",Character.valueOf('B'), "blockVulcanite"}));
			GameRegistry.addRecipe(new ShapedOreRecipe(tartariteBoreHead, new Object[] {"XXX","XBX","XXX",Character.valueOf('X'),"ingotBlackSteel",Character.valueOf('B'), "blockTartarite"}));
		}
	}
}
