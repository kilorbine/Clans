package com.Satrosity.Clans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Server;

public class Team {
	
	private ArrayList<TierList> TeamList;
	
	private ChatColor TeamColor;
	private String TeamMOTD;
	private int TeamScore;
	private String TeamTag;
	private String TeamCapeUrl;
	
	private int OnlineCount;
	
	//Read in from file
	public Team(ArrayList<TierList> TLin, String MOTDin, int Scorein, String Tagin, String Colorin, String Capein)
	{
		TeamList = TLin;
		TeamMOTD = MOTDin;
		TeamScore = Scorein;
		TeamTag = Tagin;
		TeamCapeUrl = Capein;
		TeamColor = interpretColor(Colorin);
		OnlineCount = 0;
	}
	//Read in from file
	public Team(UUID playerUuid)
	{
		TeamList = new ArrayList<TierList> ();
		
		//Make Leader Rank and Add Leader to List
		TeamRank LeaderRank = new TeamRank("Leader");
		LeaderRank.makeTopRank();
		TeamList.add(new TierList(LeaderRank));
		TeamList.get(0).add(playerUuid);
		
		//Make Member List
		TeamRank MemberRank = new TeamRank("Member");
		TeamList.add(new TierList(MemberRank));
		
		TeamMOTD = "";
		TeamCapeUrl = "";
		TeamScore = 0;
		TeamTag = "";
		TeamColor = interpretColor("GRAY");
		
		OnlineCount = 0;
	}
	
	public boolean isLeader(UUID playerUuid)
	{
		return TeamList.get(0).containsMember(playerUuid);
	}
	public int getLeaderCount()
	{
		return TeamList.get(0).getTierSize();
	}
	public void addMember(UUID playerUuid)
	{
		int LastRankNumber = TeamList.size()-1;
		TeamList.get(LastRankNumber).add(playerUuid);
	}
	
	public String getMOTD(){
		return TeamMOTD;
	}
	public void setMOTD(String MOTDin){
		TeamMOTD = MOTDin;		
	}
	
	public void removeMember(UUID playerUuid)
	{
		int RankCount = TeamList.size();
		int i;
		for(i=0; i<RankCount; i++)
		{
			if(TeamList.get(i).containsMember(playerUuid))
				TeamList.get(i).remove(playerUuid);
		}
	}
	public void addRank(TeamRank NewRank)
	{
		TeamList.add(new TierList(NewRank));
	}
	public boolean removeRank(int i)
	{
		if(TeamList.get(i-1).isEmpty())
		{
			TeamList.remove(i-1);
			return true;
		}
		//Members must be removed first
		return false;
	}
	public int getRankCount()
	{
		return TeamList.size();
	}
	public boolean hasTag()
	{
		return !TeamTag.equalsIgnoreCase("");
	}
	public boolean hasMOTD()
	{
		return !TeamMOTD.equalsIgnoreCase("");
	}
	public TeamRank getRank(UUID playerUuid)
	{
		int RankCount = TeamList.size();
		int i;
		
		TeamRank r = new TeamRank("");
		
		for(i=0; i<RankCount; i++)
		{
			if(TeamList.get(i).containsMember(playerUuid))
				return TeamList.get(i).getRank();
		}
		return r;
	}
	public int getRankNumber(UUID playerUuid)
	{
		int RankCount = TeamList.size();
		int i;
		
		for(i=0; i<RankCount; i++)
		{
			if(TeamList.get(i).containsMember(playerUuid))
				return i;
		}
		return -1;
	}
	
	public TeamRank getRank(int RankNumber)
	{
		return TeamList.get(RankNumber-1).getRank();
	}
	
	public void changePlayerRank(UUID uuid,int RankNumber){
		TeamList.get(this.getRankNumber(uuid)).remove(uuid);
		TeamList.get(RankNumber-1).add(uuid);
	}
	
	public void changeRankName(int RankNumber, String newName){
		TeamList.get(RankNumber).getRank().setRankName(newName);
	}
	
	public ChatColor getColor()
	{
		return TeamColor;
	}
	public String getColorName(){
		return reverseInterpretColor(TeamColor);
	}
	public void setColor(String Colorin){
		TeamColor = interpretColor(Colorin);
	}
	
	public ArrayList<String> getAllMembers(Server server)
	{
		//Super inefficient! But only needed for disband...
		ArrayList<String> members = new ArrayList<String>();
		for(TierList tl : TeamList)
		{
			String list = tl.membersToString(server);
			if(!list.equalsIgnoreCase("")) {
					String[] RankMembers = list.split(", ");
					for(String s : RankMembers)
						members.add(s);
			}
		}
		return members;
	}
	public ArrayList<String> getTeamInfo(Server server)
	{
		ArrayList<String> teamInfo = new ArrayList<String>();
		teamInfo.add(TeamColor + "Team Members: " + getTeamSize());
		int rankNum = 1;
		for(TierList tl : TeamList)
		{
			teamInfo.add(TeamColor + "" + rankNum + ". "+ tl.getRank().getRankName() + ": " + ChatColor.GRAY + tl.membersToString(server));
			rankNum++;
		}
		return teamInfo;
	}
	public int getTeamSize()
	{
		int TeamSize = 0;
		for(TierList tl : TeamList)
			TeamSize += tl.getTierSize();
		return TeamSize;
	}
	public String getTeamTag(){
		return TeamTag;
	}
	public void leaderInactivePromotionCascade()
	{
		if(TeamList.get(0).getTierSize() <= 1)
		{
			int i = TeamList.size();
			while(i > 1)
			{
				massRankMove(i,i-1);
				i--;
			}
			
		}	
	}
	public void massRankMove(int start, int finish){
		HashSet<UUID> temp = new HashSet<UUID>();
		temp = TeamList.get(start -1).getRankMembers();
		TeamList.get(start - 1).clearRankMembers();
		for(UUID member : temp){
			TeamList.get(finish-1).add(member);
		}
	}
	public void setTeamTag(String Tagin){
		TeamTag = Tagin;
	}
	public String getSaveString()
	{
		String save = "";
		save += "    Tag: '" + TeamTag + "'\n";
		save += "    Color: '" + reverseInterpretColor(TeamColor) + "'\n";
		save += "    Cape: '" + TeamCapeUrl + "'\n";
		save += "    Motd: '" + TeamMOTD + "'\n";
		save += "    Score: '" + TeamScore + "'\n";
		save += "    List:\n";
		int i = 0;
		for(i=0; i<TeamList.size(); i++)
		{
			int r = i+1;
			save += "        Rank " + r + ":\n";
			save += TeamList.get(i).getSaveString();
		}
		
		return save;
	}
	
public boolean validateColor(String Colorin){
	switch(Colorin){
		case "DARK_RED":
			return true;
		case "RED":
			return true;
		case "DARK_AQUA":
			return true;
		case "AQUA":
			return true;
		case "DARK_GREEN":
			return true;
		case "GREEN":
			return true;
		case "DARK_BLUE":
			return true;
		case "BLUE":
			return true;
		case "DARK_PURPLE":
			return true;
		case "PURPLE":
			return true;
		case "GOLD":
			return true;
		case "YELLOW":
			return true;
		case "BLACK":
			return true;
		case "GRAY":
			return true;
		default:
			return false;
	}
}
	
	private ChatColor interpretColor(String Colorin) {

		ChatColor c;
		     switch(Colorin.toUpperCase())
		     {
		     case "DARK_RED":
		     c = ChatColor.DARK_RED;
		     break;
		     case "RED":
		     c = ChatColor.RED;
		     break;
		     case "DARK_AQUA":
		     c = ChatColor.DARK_AQUA;
		     break;
		     case "AQUA":
		     c = ChatColor.AQUA;
		     break;
		     case "DARK_GREEN":
		     c = ChatColor.DARK_GREEN;
		     break;
		     case "GREEN":
		     c = ChatColor.GREEN;
		     break;
		     case "DARK_BLUE":
		     c = ChatColor.DARK_BLUE;
		     break;
		     case "BLUE":
		     c = ChatColor.BLUE;
		     break;
		     case "DARK_PURPLE":
		     c = ChatColor.DARK_PURPLE;
		     break;
		     case "LIGHT_PURPLE":
		     c = ChatColor.LIGHT_PURPLE;
		     break;
		     case "GOLD":
		     c = ChatColor.GOLD;
		     break;
		     case "YELLOW":
		     c = ChatColor.YELLOW;
		     break;
		     case "BLACK":
		     c = ChatColor.BLACK;
		     break;
		     default:
		     c = ChatColor.GRAY;
		     break;
		     }
		return c;
		}
	public String getTeamCapeUrl() {
		return TeamCapeUrl;
	}
	public void setTeamCapeUrl(String teamCapeUrl) {
		TeamCapeUrl = teamCapeUrl;
	}
	private String reverseInterpretColor(ChatColor Colorin) {

		String c = "";
    	switch(Colorin)
    	{
    		case DARK_RED: 
    			c = "DARK_RED";
    			break;
    		case RED: 
    			c = "RED";
    			break;
    		case DARK_AQUA: 
    			c = "DARK_AQUA";
    			break;
    		case AQUA: 
    			c = "AQUA";
    			break;
    		case DARK_GREEN: 
    			c = "DARK_GREEN";
    			break;
    		case GREEN: 
    			c = "GREEN";
    			break;
    		case DARK_BLUE: 
    			c = "DARK_BLUE";
    			break;
    		case BLUE: 
    			c = "BLUE";
    			break;
    		case DARK_PURPLE: 
    			c = "DARK_PURPLE";
    			break;
    		case LIGHT_PURPLE: 
    			c = "LIGHT_PURPLE";
    			break;
    		case GOLD: 
    			c = "GOLD";
    			break;
    		case YELLOW: 
    			c = "YELLOW";
    			break;
    		case BLACK: 
    			c = "BLACK";
    			break;
    		default:
    			c =  "GRAY";
    			break;
    	}
		return c;
	}
	public void IncreaseOnlineCount() {
		OnlineCount++;
	}
	public void DecreaseOnlineCount() {
		OnlineCount--;
	}
	public int getOnlineCount() {
		return OnlineCount;
	}
	public void addScore(int score) {
		TeamScore += score;
	}
	public int getTeamScore() {
		return TeamScore;
	}

}
