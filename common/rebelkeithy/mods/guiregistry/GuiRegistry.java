package rebelkeithy.mods.guiregistry;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.network.IGuiHandler;

import java.util.HashMap;
import java.util.Map;

public class GuiRegistry implements IGuiHandler 
{
	private static Map<Integer, Class<? extends GuiContainer>> guiList = new HashMap<Integer, Class<? extends GuiContainer>>();
	private static Map<Integer, Class<? extends Container>> containerList = new HashMap<Integer, Class<? extends Container>>();
 
	private static GuiRegistry instance;
	
	public static GuiRegistry instance()
	{
		if(instance == null)
			instance = new GuiRegistry();
		return instance;
	}
	
	public static void registerGuiServer(Class<? extends Container> container, Object mod, String guiID)
	{
		ModContainer mc = FMLCommonHandler.instance().findContainerFor(mod);
		String fixedID = guiID + mc.getName();
		containerList.put(fixedID.hashCode(), container);
	}
	
	public static void registerGuiClient(Class<? extends GuiContainer> gui, Class<? extends Container> container, Object mod, String guiID)
	{
		ModContainer mc = FMLCommonHandler.instance().findContainerFor(mod);
		String fixedID = guiID + mc.getName();
		guiList.put(fixedID.hashCode(), gui);
		containerList.put(fixedID.hashCode(), container);
	}
	
	public static void openGui(String guiID, Object mod, EntityPlayer player, World world, int x, int y, int z)
	{
		ModContainer mc = FMLCommonHandler.instance().findContainerFor(mod);
		String fixedID = guiID + mc.getName();
		player.openGui(mod, fixedID.hashCode(), world, x, y, z);
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		Container container = null;
		try {
			container = containerList.get(ID).getConstructor(InventoryPlayer.class, TileEntity.class).newInstance(player.inventory, te);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return container;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		GuiContainer gui = null;
		try {
			gui = guiList.get(ID).getConstructor(InventoryPlayer.class, TileEntity.class).newInstance(player.inventory, te);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gui;
	}
}
