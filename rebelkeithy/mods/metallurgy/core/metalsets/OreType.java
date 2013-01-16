package rebelkeithy.mods.metallurgy.core.metalsets;

public enum OreType { 
	
	ORE(true), CATALYST(true), ALLOY(false), RESPAWN(true); 
	
	private boolean generates;

	OreType(boolean generates)
	{
		this.generates = generates;
	}
	
	public boolean generates()
	{
		return generates;
	}
}
