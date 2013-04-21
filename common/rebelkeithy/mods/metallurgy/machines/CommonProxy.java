package rebelkeithy.mods.metallurgy.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.IGuiHandler;

import java.io.File;

import rebelkeithy.mods.guiregistry.GuiRegistry;
import rebelkeithy.mods.metallurgy.machines.abstractor.ContainerAbstractor;
import rebelkeithy.mods.metallurgy.machines.abstractor.GuiAbstractor;
import rebelkeithy.mods.metallurgy.machines.chests.ContainerPreciousChest;
import rebelkeithy.mods.metallurgy.machines.chests.GuiPreciousChest;
import rebelkeithy.mods.metallurgy.machines.crusher.ContainerCrusher;
import rebelkeithy.mods.metallurgy.machines.crusher.GuiCrusher;
import rebelkeithy.mods.metallurgy.machines.enchanter.ContainerMetallurgyEnchantment;
import rebelkeithy.mods.metallurgy.machines.enchanter.GuiMetallurgyEnchantment;
import rebelkeithy.mods.metallurgy.machines.forge.ContainerNetherForge;
import rebelkeithy.mods.metallurgy.machines.forge.GuiNetherForge;
import rebelkeithy.mods.metallurgy.machines.furnace.ContainerMetalFurnace;
import rebelkeithy.mods.metallurgy.machines.furnace.GuiMetalFurnace;
import rebelkeithy.mods.metallurgy.machines.mint.ContainerMintStorage;
import rebelkeithy.mods.metallurgy.machines.mint.GuiMintStorage;
import rebelkeithy.mods.metallurgy.machines.storage.ContainerStorage;
import rebelkeithy.mods.metallurgy.machines.storage.GuiStorage;

public class CommonProxy
{

	public File getMinecraftDir() 
	{
		return new File(".");
	}

	public void registerTileEntitySpecialRenderer() {}

	public void registerGUIs() 
	{
		GuiRegistry.registerGuiServer(ContainerMetallurgyEnchantment.class, MetallurgyMachines.instance, "Enchanter");
		GuiRegistry.registerGuiServer(ContainerMintStorage.class, MetallurgyMachines.instance, "MintStorage");
		GuiRegistry.registerGuiServer(ContainerPreciousChest.class, MetallurgyMachines.instance, "PreciousChest");
		GuiRegistry.registerGuiServer(ContainerCrusher.class, MetallurgyMachines.instance, "Crusher");
		GuiRegistry.registerGuiServer(ContainerMetalFurnace.class, MetallurgyMachines.instance, "MetalFurnace");
		GuiRegistry.registerGuiServer(ContainerNetherForge.class, MetallurgyMachines.instance, "NetherForge");
		GuiRegistry.registerGuiServer(ContainerAbstractor.class, MetallurgyMachines.instance, "Abstractor");
	}
}
