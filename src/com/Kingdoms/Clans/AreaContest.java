package com.Kingdoms.Clans;

import org.bukkit.block.BlockState;

public class AreaContest implements Runnable{
    public Clans plugin;

    private String attackingTeam;	
    private String defendingTeam;
    
    private String area;
    
    int hitpoints;
    int timeout;
    
    private int printCount;
    private int printTotal;
    
    
    
    public AreaContest(Clans instance, String a, String def, String att) {
        plugin = instance;
        attackingTeam = att;
        defendingTeam = def;
        area = a;
        
        hitpoints = 99;
        printCount = 0;
        timeout = 0;

    }
    public void run() { //this will be ran every 10 seconds
    	
    	//get difference in area
    	//update hitpoints
    	//if print count is 6 then print scores and set to 0
    	//if hitpoints = 0 or 100i am
    		//declare a winner
    	//else
    		//set to call again another 10 seconds
    		//set printcount to 0
    	
    	int[] res = plugin.countInCapturableArea(area, defendingTeam, attackingTeam);
    	
    	hitpoints += res[0];
    	//System.out.println(hitpoints);
    	
    	if(res[1] == 0 && res[2] == 0)
    		timeout++;
    	if(timeout >= 12)
    		plugin.declareWinner(area, defendingTeam, attackingTeam, defendingTeam);
    	else if(hitpoints <= 0) //attackers win
    	{
    		//declare attackers
    		plugin.declareWinner(area, defendingTeam, attackingTeam, attackingTeam);
    	}
    	else if (hitpoints >= 100) //defenders win
    	{
    		//declare defenders
    		plugin.declareWinner(area, defendingTeam, attackingTeam, defendingTeam);
    	}
    	else
    	{
    		//continue
    		plugin.continueSiege(area, defendingTeam, attackingTeam);
    		printCount++;
    		printTotal += res[0];
    	}
    	if(printCount >= 6)
    	{
    		//print current core
    		plugin.printSiegeProgress(area, defendingTeam, attackingTeam, res[2], res[1], hitpoints, printTotal);
    		printCount = 0;
    		printTotal = 0;
    	}
    	

    }

    
}
