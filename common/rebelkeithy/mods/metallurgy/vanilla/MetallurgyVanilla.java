package rebelkeithy.mods.metallurgy.vanilla;

import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.Icon;
import net.minecraftforge.common.MinecraftForge;
import rebelkeithy.mods.metallurgy.core.MetalInfoDatabase;
import rebelkeithy.mods.metallurgy.core.metalsets.MetalSet;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.relauncher.ReflectionHelper;

@Mod(modid="Metallurgy3Vanilla", name="Metallurgy 3 Vanilla", version="3.0.0.0.10")
@NetworkMod(channels = {"MetallurgyVanilla"}, clientSideRequired = true, serverSideRequired = false)
public class MetallurgyVanilla 
{
	@SidedProxy(clientSide = "rebelkeithy.mods.metallurgy.vanilla.ClientProxy", serverSide = "rebelkeithy.mods.metallurgy.vanilla.CommonProxy")
	public static CommonProxy proxy;
	
	public static MetalSet vanillaSet;
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		
		Map<String, Map<String, String>> vanillaList = MetalInfoDatabase.getSpreadsheetDataForSet("Vanilla");
		vanillaList.remove("Wood/Leather");
		vanillaList.remove("Stone/Chainmail");
		vanillaSet = new MetalSet("Vanilla", vanillaList, CreativeTabs.tabBlock);
		
		VanillaAddons.init();

		MinecraftForge.ORE_GEN_BUS.register(new VanillaTextureReplacer());
		//MinecraftForge.ORE_GEN_BUS.register(new VanillaOreInhibitor());
		
	}
	
	@Init
	public void Init(FMLInitializationEvent event)
	{		
		VanillaAddons.load();
		proxy.registerNames();
	}
	
	@PostInit
	public void PostInit(FMLPostInitializationEvent event)
	{

		//Icon icon = event.map.registerIcon("Metallurgy:HelmetDiamond");
		Icon icon = Minecraft.getMinecraft().renderEngine.textureMapItems.registerIcon("Metallurgy:HelmetDiamond");
		ReflectionHelper.setPrivateValue(ItemArmor.class, Item.helmetDiamond, icon, "field_94604_cx");
		//Item.helmetDiamond.field_94604_cx = icon;
	}
}
