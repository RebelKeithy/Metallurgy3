package rebelkeithy.mods.metallurgy.vanilla;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import rebelkeithy.mods.keithyutils.reflection.Reflector;
import rebelkeithy.mods.metallurgy.core.MetalInfoDatabase;
import rebelkeithy.mods.metallurgy.core.MetallurgyCore;
import rebelkeithy.mods.metallurgy.core.metalsets.MetalSet;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "Metallurgy3Vanilla", name = "Metallurgy 3 Vanilla", version = "3.2.3", dependencies = "required-after:Metallurgy3Core")
@NetworkMod(channels =
{ "MetallurgyVanilla" }, clientSideRequired = true, serverSideRequired = false)
public class MetallurgyVanilla
{
    @SidedProxy(clientSide = "rebelkeithy.mods.metallurgy.vanilla.ClientProxy", serverSide = "rebelkeithy.mods.metallurgy.vanilla.CommonProxy")
    public static CommonProxy proxy;

    public static MetalSet vanillaSet;

    @EventHandler
    public void Init(FMLInitializationEvent event)
    {

        vanillaSet.init();
        VanillaAddons.load();
        proxy.registerNames();
    }

    @EventHandler
    public void PostInit(FMLPostInitializationEvent event)
    {
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {

        final ModMetadata metadata = event.getModMetadata();

        metadata.name = "Metallurgy 3 Vanilla";
        metadata.description = "Stuffs";
        metadata.authorList = Arrays.asList(new String[]
        { "Team Metallurgy" });
        metadata.parent = "Metallurgy3Core";

        final Map<String, Map<String, String>> vanillaList = MetalInfoDatabase.getSpreadsheetDataForSet("Vanilla");
        vanillaList.remove("Wood/Leather");
        vanillaList.remove("Stone/Chainmail");
        vanillaSet = new MetalSet("Vanilla", vanillaList, CreativeTabs.tabBlock);

        VanillaAddons.init();

        vanillaSet.initConfig();

        final File fileDir = new File(MetallurgyCore.proxy.getMinecraftDir() + "/config/Metallurgy3");
        fileDir.mkdir();
        final File cfgFile = new File(MetallurgyCore.proxy.getMinecraftDir() + "/config/Metallurgy3/MetallurgyVanilla.cfg");

        try
        {
            cfgFile.createNewFile();
        } catch (final IOException e)
        {
            System.out.println(e);
        }

        final Configuration config = new Configuration(cfgFile);

        if (config.get("!enable", "Enable Texture Overrides", true).getBoolean(true))
        {
            Reflector.setItemTexture(Item.swordWood, "Metallurgy:Vanilla/SwordWood");
            Reflector.setItemTexture(Item.shovelWood, "Metallurgy:Vanilla/ShovelWood");
            Reflector.setItemTexture(Item.hoeWood, "Metallurgy:Vanilla/HoeWood");
            Reflector.setItemTexture(Item.axeWood, "Metallurgy:Vanilla/AxeWood");
            Reflector.setItemTexture(Item.pickaxeWood, "Metallurgy:Vanilla/PickaxeWood");
            Reflector.setItemTexture(Item.swordStone, "Metallurgy:Vanilla/SwordStone");
            Reflector.setItemTexture(Item.shovelStone, "Metallurgy:Vanilla/ShovelStone");
            Reflector.setItemTexture(Item.hoeStone, "Metallurgy:Vanilla/HoeStone");
            Reflector.setItemTexture(Item.axeStone, "Metallurgy:Vanilla/AxeStone");
            Reflector.setItemTexture(Item.pickaxeStone, "Metallurgy:Vanilla/PickaxeStone");
            Reflector.setItemTexture(Item.swordIron, "Metallurgy:Vanilla/SwordIron");
            Reflector.setItemTexture(Item.shovelIron, "Metallurgy:Vanilla/ShovelIron");
            Reflector.setItemTexture(Item.hoeIron, "Metallurgy:Vanilla/HoeIron");
            Reflector.setItemTexture(Item.axeIron, "Metallurgy:Vanilla/AxeIron");
            Reflector.setItemTexture(Item.pickaxeIron, "Metallurgy:Vanilla/PickaxeIron");
            Reflector.setItemTexture(Item.swordGold, "Metallurgy:Vanilla/SwordGold");
            Reflector.setItemTexture(Item.shovelGold, "Metallurgy:Vanilla/ShovelGold");
            Reflector.setItemTexture(Item.hoeGold, "Metallurgy:Vanilla/HoeGold");
            Reflector.setItemTexture(Item.axeGold, "Metallurgy:Vanilla/AxeGold");
            Reflector.setItemTexture(Item.pickaxeGold, "Metallurgy:Vanilla/PickaxeGold");
            Reflector.setItemTexture(Item.swordDiamond, "Metallurgy:Vanilla/SwordDiamond");
            Reflector.setItemTexture(Item.shovelDiamond, "Metallurgy:Vanilla/ShovelDiamond");
            Reflector.setItemTexture(Item.hoeDiamond, "Metallurgy:Vanilla/HoeDiamond");
            Reflector.setItemTexture(Item.axeDiamond, "Metallurgy:Vanilla/AxeDiamond");
            Reflector.setItemTexture(Item.pickaxeDiamond, "Metallurgy:Vanilla/PickaxeDiamond");

            Reflector.setItemTexture(Item.helmetIron, "Metallurgy:Vanilla/HelmetIron");
            Reflector.setItemTexture(Item.plateIron, "Metallurgy:Vanilla/ChestIron");
            Reflector.setItemTexture(Item.legsIron, "Metallurgy:Vanilla/LegsIron");
            Reflector.setItemTexture(Item.bootsIron, "Metallurgy:Vanilla/BootsIron");
            Reflector.setItemTexture(Item.helmetGold, "Metallurgy:Vanilla/HelmetGold");
            Reflector.setItemTexture(Item.plateGold, "Metallurgy:Vanilla/ChestGold");
            Reflector.setItemTexture(Item.legsGold, "Metallurgy:Vanilla/LegsGold");
            Reflector.setItemTexture(Item.bootsGold, "Metallurgy:Vanilla/BootsGold");
            Reflector.setItemTexture(Item.helmetDiamond, "Metallurgy:Vanilla/HelmetDiamond");
            Reflector.setItemTexture(Item.plateDiamond, "Metallurgy:Vanilla/ChestDiamond");
            Reflector.setItemTexture(Item.legsDiamond, "Metallurgy:Vanilla/LegsDiamond");
            Reflector.setItemTexture(Item.bootsDiamond, "Metallurgy:Vanilla/BootsDiamond");
        }

        config.save();

        // MinecraftForge.ORE_GEN_BUS.register(new VanillaTextureReplacer());
        MinecraftForge.ORE_GEN_BUS.register(new VanillaOreInhibitor());

    }
}
