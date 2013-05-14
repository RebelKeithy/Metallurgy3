package rebelkeithy.mods.metallurgy.integration;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import mods.railcraft.api.carts.bore.IBoreHead;
public class BoreHead extends Item implements IBoreHead
{
	private String texture;
	private Float digModifier;
	private int harvestLevel;
	
	public BoreHead(int id, int maxD, String unlocname, String texLoc, Float digmod, int harvest) {
		    super(id);
		    this.maxStackSize = 1;
		    setMaxDamage(maxD);
		    setUnlocalizedName(unlocname);
		    setCreativeTab(CreativeTabs.tabMisc);
		    texture = texLoc;
		    digModifier = digmod;
		    harvestLevel = harvest;
		  }

	public void registerIcons(IconRegister iconRegister)
    {
		this.itemIcon = iconRegister.registerIcon("Metallurgy:Integration/BoreHead/" + getUnlocalizedName().replace("item.",""));
    }

	@Override
	public String getBoreTexture() {
		//return "/mods/Metallurgy/textures/misc/tunnel_bore_mithril.png";
		return texture;
	}

	@Override
	public float getDigModifier() {
		return digModifier;
	}

	@Override
	public int getHarvestLevel() {
		return harvestLevel;
	}

}
