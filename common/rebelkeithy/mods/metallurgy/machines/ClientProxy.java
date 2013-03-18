package rebelkeithy.mods.metallurgy.machines;

import java.io.File;

import net.minecraft.client.Minecraft;
import rebelkeithy.mods.metallurgy.machines.chests.RenderHelperPreciousChest;
import rebelkeithy.mods.metallurgy.machines.chests.TileEntityPreciousChest;
import rebelkeithy.mods.metallurgy.machines.chests.TileEntityPreciousChestRenderer;
import rebelkeithy.mods.metallurgy.machines.crusher.CrusherRenderHelper;
import rebelkeithy.mods.metallurgy.machines.crusher.TileEntityCrusher;
import rebelkeithy.mods.metallurgy.machines.crusher.TileEntityCrusherRenderer;
import rebelkeithy.mods.metallurgy.machines.enchanter.MetallurgyEnchantmentTableRenderHelper;
import rebelkeithy.mods.metallurgy.machines.enchanter.RenderMetallurgyEnchantmentTable;
import rebelkeithy.mods.metallurgy.machines.enchanter.TileEntityMetallurgyEnchantmentTable;
import rebelkeithy.mods.metallurgy.machines.ladders.BlockMetalLadder;
import rebelkeithy.mods.metallurgy.machines.ladders.LadderRenderer;
import rebelkeithy.mods.metallurgy.machines.lantern.LanternRenderHelper;
import rebelkeithy.mods.metallurgy.machines.lantern.TileEntityLantern;
import rebelkeithy.mods.metallurgy.machines.lantern.TileEntityLanternRenderer;
import rebelkeithy.mods.metallurgy.machines.mint.MintRenderHelper;
import rebelkeithy.mods.metallurgy.machines.mint.TileEntityMint;
import rebelkeithy.mods.metallurgy.machines.mint.TileEntityMintRenderer;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerTileEntitySpecialRenderer() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrusher.class, new TileEntityCrusherRenderer());
		RenderingRegistry.registerBlockHandler(new CrusherRenderHelper());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPreciousChest.class, new TileEntityPreciousChestRenderer());
		RenderingRegistry.registerBlockHandler(new RenderHelperPreciousChest());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMint.class, new TileEntityMintRenderer());
		RenderingRegistry.registerBlockHandler(new MintRenderHelper());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLantern.class, new TileEntityLanternRenderer());
		RenderingRegistry.registerBlockHandler(new LanternRenderHelper());

		RenderingRegistry.registerBlockHandler(BlockMetalLadder.renderType, new LadderRenderer());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMetallurgyEnchantmentTable.class, new RenderMetallurgyEnchantmentTable());
		RenderingRegistry.registerBlockHandler(new MetallurgyEnchantmentTableRenderHelper());
	}
	
	public File getMinecraftDir() 
	{
		return Minecraft.getMinecraftDir();
	}
}
