package rebelkeithy.mods.metallurgy.vanilla;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.Icon;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class VanillaTextureReplacer 
{
	@ForgeSubscribe
	public void onTextureLoad(TextureStitchEvent event)
	{
		Icon icon = event.map.registerIcon("Metallurgy:HelmetDiamond");
		//Minecraft.getMinecraft().renderEngine.textureMapItems.registerIcon("Metallurgy:HelmetDiamond");
		ReflectionHelper.setPrivateValue(ItemArmor.class, Item.helmetDiamond, icon, "field_94604_cx");
		//Item.helmetDiamond.field_94604_cx = icon;
	}
}
