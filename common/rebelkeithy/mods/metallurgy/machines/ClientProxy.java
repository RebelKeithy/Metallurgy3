package rebelkeithy.mods.metallurgy.machines;

import java.io.File;

import net.minecraft.client.Minecraft;
import rebelkeithy.mods.keithyutils.Config;
import rebelkeithy.mods.keithyutils.guiregistry.GuiRegistry;
import rebelkeithy.mods.metallurgy.machines.abstractor.ContainerAbstractor;
import rebelkeithy.mods.metallurgy.machines.abstractor.GuiAbstractor;
import rebelkeithy.mods.metallurgy.machines.chests.ContainerPreciousChest;
import rebelkeithy.mods.metallurgy.machines.chests.GuiPreciousChest;
import rebelkeithy.mods.metallurgy.machines.chests.RenderHelperPreciousChest;
import rebelkeithy.mods.metallurgy.machines.chests.TileEntityPreciousChest;
import rebelkeithy.mods.metallurgy.machines.chests.TileEntityPreciousChestRenderer;
import rebelkeithy.mods.metallurgy.machines.crusher.ContainerCrusher;
import rebelkeithy.mods.metallurgy.machines.crusher.CrusherRenderHelper;
import rebelkeithy.mods.metallurgy.machines.crusher.GuiCrusher;
import rebelkeithy.mods.metallurgy.machines.crusher.TileEntityCrusher;
import rebelkeithy.mods.metallurgy.machines.crusher.TileEntityCrusherRenderer;
import rebelkeithy.mods.metallurgy.machines.enchanter.ContainerMetallurgyEnchantment;
import rebelkeithy.mods.metallurgy.machines.enchanter.GuiMetallurgyEnchantment;
import rebelkeithy.mods.metallurgy.machines.enchanter.MetallurgyEnchantmentTableRenderHelper;
import rebelkeithy.mods.metallurgy.machines.enchanter.RenderMetallurgyEnchantmentTable;
import rebelkeithy.mods.metallurgy.machines.enchanter.TileEntityMetallurgyEnchantmentTable;
import rebelkeithy.mods.metallurgy.machines.forge.ContainerNetherForge;
import rebelkeithy.mods.metallurgy.machines.forge.GuiNetherForge;
import rebelkeithy.mods.metallurgy.machines.furnace.ContainerMetalFurnace;
import rebelkeithy.mods.metallurgy.machines.furnace.GuiMetalFurnace;
import rebelkeithy.mods.metallurgy.machines.ladders.BlockMetalLadder;
import rebelkeithy.mods.metallurgy.machines.ladders.LadderRenderer;
import rebelkeithy.mods.metallurgy.machines.lantern.LanternRenderHelper;
import rebelkeithy.mods.metallurgy.machines.lantern.TileEntityLantern;
import rebelkeithy.mods.metallurgy.machines.lantern.TileEntityLanternRenderer;
import rebelkeithy.mods.metallurgy.machines.laser.LaserRenderHelper;
import rebelkeithy.mods.metallurgy.machines.laser.TileEntityLaser;
import rebelkeithy.mods.metallurgy.machines.laser.TileEntityLaserRenderer;
import rebelkeithy.mods.metallurgy.machines.mint.ContainerMintStorage;
import rebelkeithy.mods.metallurgy.machines.mint.GuiMintStorage;
import rebelkeithy.mods.metallurgy.machines.mint.MintRenderHelper;
import rebelkeithy.mods.metallurgy.machines.mint.TileEntityMint;
import rebelkeithy.mods.metallurgy.machines.mint.TileEntityMintRenderer;
import rebelkeithy.mods.metallurgy.machines.pylon.PylonRenderHelper;
import rebelkeithy.mods.metallurgy.machines.pylon.TileEntityPylon;
import rebelkeithy.mods.metallurgy.machines.pylon.TileEntityPylonRenderer;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
    @Override
    public File getMinecraftDir()
    {
        return Minecraft.getMinecraft().mcDataDir;
    }

    @Override
    public void registerGUIs()
    {
    	if(ConfigMachines.enchanterEnabled)
    	{
    		GuiRegistry.registerGuiClient(GuiMetallurgyEnchantment.class, ContainerMetallurgyEnchantment.class, MetallurgyMachines.instance, "Enchanter");
    	}
    	
    	if(ConfigMachines.mintEnabled)
    	{
    		GuiRegistry.registerGuiClient(GuiMintStorage.class, ContainerMintStorage.class, MetallurgyMachines.instance, "MintStorage");
    	}
    	
    	if(ConfigMachines.chestEnabled)
    	{
            GuiRegistry.registerGuiClient(GuiPreciousChest.class, ContainerPreciousChest.class, MetallurgyMachines.instance, "PreciousChest");	
    	}

    	if(ConfigMachines.crusherEnabled)
    	{
    		GuiRegistry.registerGuiClient(GuiCrusher.class, ContainerCrusher.class, MetallurgyMachines.instance, "Crusher");
    	}
    	
    	if(ConfigMachines.furnaceEnabled)
    	{
    		GuiRegistry.registerGuiClient(GuiMetalFurnace.class, ContainerMetalFurnace.class, MetallurgyMachines.instance, "MetalFurnace");		
    	}
    	
    	if(ConfigMachines.forgeEnabled)
    	{
    		GuiRegistry.registerGuiClient(GuiNetherForge.class, ContainerNetherForge.class, MetallurgyMachines.instance, "NetherForge");
    	}
    	
    	if(ConfigMachines.abstractorEnabled)
    	{
    		GuiRegistry.registerGuiClient(GuiAbstractor.class, ContainerAbstractor.class, MetallurgyMachines.instance, "Abstractor");
    	}
//        GuiRegistry.registerGuiClient(GuiXpTank.class, ContainerXpTank.class, MetallurgyMachines.instance, "XpTank");

    }

    @Override
    public void registerTileEntitySpecialRenderer()
    {
    	if(ConfigMachines.crusherEnabled)
	    {
	        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrusher.class, new TileEntityCrusherRenderer());
	        RenderingRegistry.registerBlockHandler(new CrusherRenderHelper());
	    }

    	if(ConfigMachines.chestEnabled)
    	{
	        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPreciousChest.class, new TileEntityPreciousChestRenderer());
	        RenderingRegistry.registerBlockHandler(new RenderHelperPreciousChest());
    	}

    	if(ConfigMachines.mintEnabled)
    	{
	        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMint.class, new TileEntityMintRenderer());
	        RenderingRegistry.registerBlockHandler(new MintRenderHelper());
    	}

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLantern.class, new TileEntityLanternRenderer());
        RenderingRegistry.registerBlockHandler(new LanternRenderHelper());

        if(ConfigMachines.ladderEnabled)
        {
        	RenderingRegistry.registerBlockHandler(BlockMetalLadder.renderType, new LadderRenderer());
        }

    	if(ConfigMachines.enchanterEnabled)
    	{
	        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMetallurgyEnchantmentTable.class, new RenderMetallurgyEnchantmentTable());
	        RenderingRegistry.registerBlockHandler(new MetallurgyEnchantmentTableRenderHelper());
    	}

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLaser.class, new TileEntityLaserRenderer());
        RenderingRegistry.registerBlockHandler(new LaserRenderHelper());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPylon.class, new TileEntityPylonRenderer());
        RenderingRegistry.registerBlockHandler(new PylonRenderHelper());

//        RenderingRegistry.registerEntityRenderingHandler(EntityXpOrbContainer.class, new renderXPOrbContainer());

    }
}
