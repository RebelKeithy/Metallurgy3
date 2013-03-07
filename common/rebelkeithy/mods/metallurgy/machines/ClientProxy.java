package rebelkeithy.mods.metallurgy.machines;

import net.minecraft.client.Minecraft;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

import java.io.File;

import rebelkeithy.mods.metallurgy.machines.crusher.CrusherRenderHelper;
import rebelkeithy.mods.metallurgy.machines.crusher.TileEntityCrusher;
import rebelkeithy.mods.metallurgy.machines.crusher.TileEntityCrusherRenderer;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerTileEntitySpecialRenderer() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrusher.class, new TileEntityCrusherRenderer());
		RenderingRegistry.registerBlockHandler(new CrusherRenderHelper());
	}
	
	public File getMinecraftDir() 
	{
		return Minecraft.getMinecraftDir();
	}
}
