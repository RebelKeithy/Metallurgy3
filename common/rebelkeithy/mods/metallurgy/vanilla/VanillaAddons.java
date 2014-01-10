package rebelkeithy.mods.metallurgy.vanilla;

import java.io.File;
import java.io.IOException;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import rebelkeithy.mods.keithyutils.metablock.MetaBlock;
import rebelkeithy.mods.keithyutils.metablock.SubBlock;
import rebelkeithy.mods.metallurgy.core.MetallurgyCore;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class VanillaAddons
{
    public static SubBlock goldBrick;
    public static SubBlock ironBrick;

    public static Item dustIron;
    public static Item dustGold;

    public static int goldBrickID;
    public static int ironBrickID;
    public static int goldBrickMeta;
    public static int ironBrickMeta;

    public static void init()
    {
        initConfig();
        goldBrick = new SubBlock(goldBrickID, goldBrickMeta, "Metallurgy:Vanilla/GoldBrick").setHardness(3.0F).setResistance(10.0F)
                .setUnlocalizedName("Metallurgy:Vanilla/GoldBricks").setCreativeTab(CreativeTabs.tabBlock);
        ironBrick = new SubBlock(ironBrickID, ironBrickMeta, "Metallurgy:Vanilla/IronBrick").setHardness(5.0F).setResistance(10.0F)
                .setUnlocalizedName("Metallurgy:Vanilla/IronBricks").setCreativeTab(CreativeTabs.tabBlock);
        MetaBlock.registerID(goldBrickID);
        MetaBlock.registerID(ironBrickID);
    }

    public static void initConfig()
    {
        final File fileDir = new File(MetallurgyCore.proxy.getMinecraftDir() + "/config/Metallurgy3");
        fileDir.mkdir();
        final File cfgFile = new File(MetallurgyCore.proxy.getMinecraftDir() + "/config/Metallurgy3/MetallurgyVanilla.cfg");

        try
        {
            cfgFile.createNewFile();
            
        } catch (final IOException e)
        {
        	MetallurgyCore.log.info("[Metallurgy3] Could not create configuration file for Metallurgy 3 metal set Vanilla. Reason:");
        	MetallurgyCore.log.info(e.getLocalizedMessage());
        }

        final Configuration config = new Configuration(cfgFile);
        config.load();

        ironBrickID = Integer.parseInt(config.get("Iron", "Brick ID", "900:3").getString().split(":")[0]);
        ironBrickMeta = Integer.parseInt(config.get("Iron", "Brick ID", "900:3").getString().split(":")[1]);

        goldBrickID = Integer.parseInt(config.get("Gold", "Brick ID", "900:4").getString().split(":")[0]);
        goldBrickMeta = Integer.parseInt(config.get("Gold", "Brick ID", "900:4").getString().split(":")[1]);

        config.save();
    }

    public static void load()
    {
        GameRegistry.addRecipe(new ItemStack(goldBrickID, 4, goldBrickMeta), "XX", "XX", 'X', Item.ingotGold);
        GameRegistry.addRecipe(new ItemStack(ironBrickID, 4, ironBrickMeta), "XX", "XX", 'X', Item.ingotIron);
        GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotGold), new ItemStack(goldBrickID, 1, goldBrickMeta));
        GameRegistry.addShapelessRecipe(new ItemStack(Item.ingotIron), new ItemStack(ironBrickID, 1, ironBrickMeta));

    }

    public static void registerNames()
    {
        LanguageRegistry.addName(new ItemStack(goldBrickID, 1, goldBrickMeta), "Gold Bricks");
        LanguageRegistry.addName(new ItemStack(ironBrickID, 1, ironBrickMeta), "Iron Bricks");
    }
}
