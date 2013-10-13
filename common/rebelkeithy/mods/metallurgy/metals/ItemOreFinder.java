package rebelkeithy.mods.metallurgy.metals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemOreFinder extends Item
{	
    private ExecutorService exec = Executors.newCachedThreadPool();


	public ItemOreFinder(int par1)
    {
        super(par1);
        setMaxDamage(64);
        maxStackSize = 1;
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
    {
        int mode = 0;
        if (par1ItemStack.hasTagCompound())
        {
            mode = par1ItemStack.getTagCompound().getInteger("mode");
        }
        else
        {
            par1ItemStack.setTagCompound(new NBTTagCompound());
            par1ItemStack.getTagCompound().setInteger("mode", mode);
        }


        if (par2EntityPlayer.isSneaking())
        {
            mode++;
            mode = mode % 10;
            par1ItemStack.getTagCompound().setInteger("mode", mode);
            par2EntityPlayer.addChatMessage("Radius: " + (mode + 1) + " chunk" + (mode != 0 ? "s" : ""));
            return false;
        }

        if (world.isRemote)
        {
            return false;
        }
        
        int minX = x - x % 16 - 16 * mode;
        int minZ = z - z % 16 - 16 * mode;
        int minY = 0;
        
        int maxX = x - x % 16 + 16 * (mode + 1);
        int maxZ = z - z % 16 + 16 * (mode + 1);
        int maxY = 128;
        
        try {
			getOresInArea(world, par2EntityPlayer, minX, minY, minZ, maxX, maxY, maxZ);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
        
        return false;
    }
    
    public class OreGeneratorm implements Callable<Map<String, Integer>> 
    {
    	private final World world;
    	private final int minX, minY, minZ;
    	private final int maxX, maxY, maxZ;
		private EntityPlayer player;
    	
    	public OreGeneratorm(World world, EntityPlayer player, int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
    	{
    		this.world = world;
    		this.player = player;
    		
    		this.minX = minX;
    		this.minY = minY;
    		this.minZ = minZ;
    		
    		this.maxX = maxX;
    		this.maxY = maxY;
    		this.maxZ = maxZ;

    	}

		@Override
		public Map<String, Integer> call() throws Exception {
			Map<String, Integer> oreCount = new HashMap<String, Integer>();

	        for (int y = minY; y < maxY; y++)
	        {
	            for (int x = minX; x <= maxX; x++)
	            {
	                for (int z = minZ; z <= maxZ; z++)
	                {
	                    final int id = world.getBlockId(x, y, z);
	                    final int meta = world.getBlockMetadata(x, y, z);
	                    final ItemStack stack = new ItemStack(id, 1, meta);
	                    String name = null;
	                    final int oreID = OreDictionary.getOreID(stack);
	                    if (oreID != -1)
	                    {
	                        name = OreDictionary.getOreName(oreID);
	                    }
	                    if (id == Block.oreIron.blockID)
	                    {
	                        name = "oreIron";
	                    }
	                    else if (id == Block.oreGold.blockID)
	                    {
	                        name = "oreGold";
	                    }
	                    else if (id == Block.oreDiamond.blockID)
	                    {
	                        name = "oreDiamond";
	                    }
	                    else if (id == Block.oreNetherQuartz.blockID)
	                    {
	                        name = "oreNetherQuartz";
	                    }

	                    if (name != null)
	                    {
	                        if (oreCount.containsKey(name))
	                        {
	                            final int count = oreCount.get(name);
	                            oreCount.put(name, count + 1);
	                        }
	                        else
	                        {
	                            oreCount.put(name, 1);
	                        }
	                    }

	                }
	            }
	        }
	        
	        final Set<String> names = oreCount.keySet();
	        final String[] sort = names.toArray(new String[names.size()]);
	        Arrays.sort(sort);
	        player.addChatMessage("In Area (" + minX + ", " + minZ + ") to (" + maxX + ", " + maxZ + ")");
	        for (final String name : sort)
	        {
	            final int amount = oreCount.get(name);
	            player.addChatMessage("Found " + amount + " " + name);
	        }
	        	        
			return oreCount;
		}
    	
    }
    
    public synchronized void getOresInArea(World world, EntityPlayer player,int minX, int minY, int minZ, int maxX, int maxY, int maxZ) throws InterruptedException, ExecutionException
    {    
    	OreGeneratorm ore = new OreGeneratorm(world,player, minX, minY, minZ, maxX, maxY, maxZ);
    	exec.submit(ore);
    }
    
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        itemIcon = par1IconRegister.registerIcon("Metallurgy:machines/OreFinder");
    }

}
