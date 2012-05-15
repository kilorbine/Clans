package com.Satrosity.Clans;

public class BlackArea {
	private int minX;
	private int minY;
	private int minZ;
	private int maxX;
	private int maxY;
	private int maxZ;
	private String world;
	
	public BlackArea(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, String world) 
	{
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
		this.world = world;
	}
	public boolean inArea(int x, int z, String worldname)
	{		        
		boolean isInArea = false;
		if(world.equalsIgnoreCase(worldname)) {
			if(minX <= x && x <= maxX) {
	    		if(minZ <= z && z <= maxZ) {
	    			isInArea = true;
	    		}
			}
		}
		return isInArea;
	}

}
