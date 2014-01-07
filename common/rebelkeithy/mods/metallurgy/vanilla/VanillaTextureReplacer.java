package rebelkeithy.mods.metallurgy.vanilla;

import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class VanillaTextureReplacer
{
    @ForgeSubscribe
    public void onTextureLoad(TextureStitchEvent event)
    {
        // Icon icon = event.map.registerIcon("Metallurgy:HelmetDiamond");
        // Minecraft.getMinecraft().renderEngine.textureMapItems.registerIcon("Metallurgy:HelmetDiamond");
        // ReflectionHelper.setPrivateValue(ItemArmor.class, Item.helmetDiamond,
        // icon, "field_94604_cx");
        // Item.helmetDiamond.field_94604_cx = icon;
    }
}
