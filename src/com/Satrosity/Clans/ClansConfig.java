package com.Satrosity.Clans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;

import org.yaml.snakeyaml.Yaml;

public class ClansConfig {
	
	//General
	private int Currency; //Added
	private boolean AllowTKToggle; //Added
	private boolean TeamTKDefault; //Added
	private boolean AllowCapes;
	
	//Chat
	private boolean UseTags; // Added
	private String TagFormat; //Added
	private String MessageFormat; //Added
	
	//Areas
	private boolean UseAreas; //Added
	private int AreaMaxSize; //Added
	private boolean CapturableAreas; //Added
	private boolean AntiTNTInAreas;
	private boolean EnemyBedReset;
	private boolean AllowUpgrades; //Added
	private boolean UPIntruderAlert; //Added
	private int AlertThreshold; //Added
	private boolean UPOfflineDamage; //Added
	private int OfflineDamageAmount; //Added
	private int ODCooldown;
	private boolean UPBlockResist; //Not Needed Yet
	private int ResistanceBlock; //Not Needed Yet
	private boolean UPCleanse;
	
	//Score
	private boolean UseScore; //Added
	private boolean UseAreaPoints;
	private boolean UsePopulationPoints;
	private int TopRanksDisplay; 
	private boolean outputMySQL;
	private String MySQLHost;
	private String MySQLDB;
	private String MySQLUser;
	private String MySQLPassword;
	
	//Costs
	private int TagCost; //Added
	private int AreaCost; //Added
	private int incSizeCost; //Not Needed Yet
	private int UPAlertsCost; //Not Needed Yet
	private int UPDamageCost; //Not Needed Yet
	private int UPResistCost; //Not Needed Yet
	private int UPCleanseCost;
	
	//Population Reqs
	private int ReqMemColor; //Added
	private int ReqMemArea; //Not Needed Yet
	
	//Score Reqs
	private int ReqScoreColor; //Not Needed Yet
	private int ReqScoreCape; //Not Needed Yet
	
	//Clean Up
	private int CleanPlayerDays; 
	
	private HashSet<String> exemptPlayers;
	
	public ClansConfig()
	{
		//Set Default Values
		//General
		Currency = 41;
		AllowTKToggle = true;
		TeamTKDefault = false;
		AllowCapes = true;
		
		//Chat
		UseTags = true;
		TagFormat = "{CLANCOLOR}[{CLANTAG}] ";
		MessageFormat = "{PLAYER} {FULLTAG}{WHITE}: {MSG}";
		
		//Areas
		UseAreas = true;
		AreaMaxSize = 200;
		CapturableAreas = true;
		AntiTNTInAreas = true;
		EnemyBedReset = true;
		AllowUpgrades = true;
		UPIntruderAlert = true;
		AlertThreshold = 30;
		UPOfflineDamage = true;
		OfflineDamageAmount = 2;
		ODCooldown = 1000;
		UPBlockResist = true;
		ResistanceBlock = 49;
		UPCleanse = true;
		
		//Score
		UseScore = true;
		UseAreaPoints = true;
		UsePopulationPoints = true;
		TopRanksDisplay = 5; 
		outputMySQL = false;
		MySQLHost = "";
		MySQLDB = "";
		MySQLUser = "";
		MySQLPassword = "";
		
		//Costs
		TagCost = 5;
		AreaCost = 10;
		incSizeCost = 10;
		UPAlertsCost = 10;
		UPDamageCost = 25;
		UPResistCost = 50;
		UPCleanseCost = 100;
		
		//Population Reqs
		ReqMemColor = 10;
		ReqMemArea = 10;
		
		//Score Rank Reqs
		ReqScoreColor = 10;
		ReqScoreCape = 10;
		
		//Clean Up
		CleanPlayerDays = 14;
		
		//Exempt From Deletion
		exemptPlayers = new HashSet();
		
		//setConfig
		setConfig();
	}
	private void setConfig()
	{
		HashMap<String,Object> pl = null;
		Yaml yamlCfg = new Yaml();
		Reader reader = null;
        try {
            reader = new FileReader("plugins/Clans/Config.yml");
        } catch (final FileNotFoundException fnfe) {
        	 System.out.println("Config.YML Not Found!");
        	   try{
	            	  String strManyDirectories="plugins/Clans";
	            	  boolean success = (new File(strManyDirectories)).mkdirs();
	            	  }catch (Exception e){//Catch exception if any
	            	  System.err.println("Error: " + e.getMessage());
	            	  }
        } finally {
            if (null != reader) {
                try {
                    pl = (HashMap<String,Object>)yamlCfg.load(reader);
                    reader.close();
                } catch (final IOException ioe) {
                    System.err.println("We got the following exception trying to clean up the reader: " + ioe);
                }
            }
        }
        if(pl != null)
        {
        	HashMap<String,Object> General = (HashMap<String,Object>)pl.get("General");
    		//General
    		Currency = (int) General.get("Currency");
    		AllowTKToggle = (boolean) General.get("Allow TK Toggle");
    		TeamTKDefault = (boolean) General.get("Team TK Default");
    		AllowCapes = (boolean) General.get("Allow Capes");
    		
        	HashMap<String,Object> Chat = (HashMap<String,Object>)pl.get("Chat");
    		//Chat
    		UseTags = (boolean) Chat.get("Use Tags");
    		TagFormat = (String) Chat.get("Tag Format");
    		MessageFormat = (String) Chat.get("Message Format");
    		
        	HashMap<String,Object> Areas = (HashMap<String,Object>)pl.get("Areas");
    		//Areas
    		UseAreas = (boolean) Areas.get("Use Areas");
    		AreaMaxSize = (int) Areas.get("Max Size");
    		CapturableAreas = (boolean) Areas.get("Capturable");
    		AntiTNTInAreas = (boolean) Areas.get("No TNT Damage");
    		EnemyBedReset = (boolean) Areas.get("Enemy Bed Reset");
    		AllowUpgrades = (boolean) Areas.get("Allow Upgrades");
    		UPIntruderAlert = (boolean) Areas.get("UP Intruder Alert");
    		AlertThreshold = (int) Areas.get("Alert Threshold");
    		UPOfflineDamage = (boolean) Areas.get("UP Offline Defense");
    		OfflineDamageAmount = (int) Areas.get("Offline Damage");
    		ODCooldown = (int) Areas.get("Damager Cooldown");
    		UPBlockResist = (boolean) Areas.get("UP Block Resist");
    		ResistanceBlock = (int) Areas.get("Resistance Block");
    		UPCleanse = (boolean) Areas.get("UP Cleanser");
    		
        	HashMap<String,Object> Score = (HashMap<String,Object>)pl.get("Score");
    		//Score
    		UseScore = (boolean) Score.get("Use Score");
    		UseAreaPoints = (boolean) Score.get("Area Points");
    		UsePopulationPoints = (boolean) Score.get("Population Points");
    		TopRanksDisplay = (int) Score.get("Display Top List");
    		outputMySQL = (boolean) Score.get("Output MySQL");
    		MySQLHost = (String) Score.get("MySQL Host");
    		MySQLDB = (String) Score.get("MySQL DB");
    		MySQLUser = (String) Score.get("MySQL User");
    		MySQLPassword = (String) Score.get("MySQL Password");

    		
        	HashMap<String,Object> Costs = (HashMap<String,Object>)pl.get("Costs");
    		//Costs
    		TagCost = (int) Costs.get("Tag");
    		AreaCost = (int) Costs.get("Area");
    		incSizeCost = (int) Costs.get("Increase Size");
    		UPAlertsCost = (int) Costs.get("Alert Upgrade");
    		UPDamageCost = (int) Costs.get("Damage Upgrade");
    		UPResistCost = (int) Costs.get("Resist Upgrade");
    		UPCleanseCost = (int) Costs.get("Cleanse Upgrade");
    		
        	HashMap<String,Object> ReqMem = (HashMap<String,Object>)pl.get("Req Member Counts");
    		//Population Reqs
    		ReqMemColor = (int) ReqMem.get("Tag Color");
    		ReqMemArea = (int) ReqMem.get("Area");
    		
        	HashMap<String,Object> ReqScore = (HashMap<String,Object>)pl.get("Req Score Ranks");
    		//Score Rank Reqs
    		ReqScoreColor = (int) ReqScore.get("Color");
    		ReqScoreCape =  (int) ReqScore.get("Cape");
    		
        	HashMap<String,Object> Cleanup = (HashMap<String,Object>)pl.get("Clean Up");
    		//Clean Up
    		CleanPlayerDays = (int) Cleanup.get("Clear Player Days");
    		
    		ArrayList<String> exempt = (ArrayList<String>)pl.get("Do Not Delete Players");
    		exemptPlayers = new HashSet<String>(exempt);
        }
		
	}
	public boolean isAllowCapes() {
		return AllowCapes;
	}
	public boolean isAntiTNTInAreas() {
		return AntiTNTInAreas;
	}
	public boolean isEnemyBedReset() {
		return EnemyBedReset;
	}
	public int getODCooldown() {
		return ODCooldown;
	}
	public boolean isUseScore() {
		return UseScore;
	}
	public boolean isUseAreaPoints() {
		return UseAreaPoints;
	}
	public boolean isUsePopulationPoints() {
		return UsePopulationPoints;
	}
	public int getTopRanksDisplay() {
		return TopRanksDisplay;
	}
	public boolean isOutputMySQL() {
		return outputMySQL;
	}
	public String getMySQLHost() {
		return MySQLHost;
	}
	public String getMySQLDB() {
		return MySQLDB;
	}
	public String getMySQLUser() {
		return MySQLUser;
	}
	public String getMySQLPassword() {
		return MySQLPassword;
	}
	public boolean isPlayerExempt(String PlayerName)
	{
		return exemptPlayers.contains(PlayerName);
	}
	public int getCurrency() {
		return Currency;
	}
	public boolean UseScore() {
		return UseScore;
	}
	public boolean AllowTKToggle() {
		return AllowTKToggle;
	}
	public boolean TeamTKDefault() {
		return TeamTKDefault;
	}
	public boolean UseTags() {
		return UseTags;
	}
	public String getTagFormat() {
		return TagFormat;
	}
	public String getMessageFormat() {
		return MessageFormat;
	}
	public boolean UseAreas() {
		return UseAreas;
	}
	public int getAreaMaxSize() {
		return AreaMaxSize;
	}
	public boolean CapturableAreas() {
		return CapturableAreas;
	}
	public boolean AllowUpgrades() {
		return AllowUpgrades;
	}
	public boolean isUPIntruderAlert() {
		return UPIntruderAlert;
	}
	public int getAlertThreshold() {
		return AlertThreshold;
	}
	public boolean isUPOfflineDamage() {
		return UPOfflineDamage;
	}
	public int getOfflineDamageAmount() {
		return OfflineDamageAmount;
	}
	public boolean isUPBlockResist() {
		return UPBlockResist;
	}
	public int getTagCost() {
		return TagCost;
	}
	public int getAreaCost() {
		return AreaCost;
	}
	public int getIncSizeCost() {
		return incSizeCost;
	}
	public int getUPAlertsCost() {
		return UPAlertsCost;
	}
	public int getUPDamageCost() {
		return UPDamageCost;
	}
	public int getUPResistCost() {
		return UPResistCost;
	}
	public int getReqMemColor() {
		return ReqMemColor;
	}
	public int getReqMemArea() {
		return ReqMemArea;
	}
	public int getReqScoreColor() {
		return ReqScoreColor;
	}
	public int getReqScoreCape() {
		return ReqScoreCape;
	}
	public int getCleanPlayerDays() {
		return CleanPlayerDays;
	}
	public boolean isUPCleanse() {
		return UPCleanse;
	}
	public int getUPCleanseCost() {
		return UPCleanseCost;
	}
	public int getDamagerKeyCooldown() {
		return ODCooldown;
	}
	public int getResistanceBlock() {
		return ResistanceBlock;
	}
	public void setResistanceBlock(int resistanceBlock) {
		ResistanceBlock = resistanceBlock;
	}
	
}
