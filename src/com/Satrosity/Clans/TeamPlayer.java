package com.Satrosity.Clans;

import java.util.Calendar;


public class TeamPlayer {

	private Calendar LastSeen;

	private String TeamKey;
	private String Invite;
	
	private boolean canTeamKill;
	
	
	//For Loading from a file at start up
	TeamPlayer(Calendar LastSeenin, boolean canTC)
	{
		LastSeen = LastSeenin;
		TeamKey = "";
		Invite = "";
		canTeamKill = canTC;
	}
	//When player joins for the first time
	TeamPlayer(boolean canTC)
	{
		LastSeen = getCurrentDate();
		TeamKey = "";
		Invite = "";
		canTeamKill = canTC;
	}
	public boolean canTeamKill() {
		return canTeamKill;
	}
	public void setCanTeamKill(boolean canTeamKill) {
		this.canTeamKill = canTeamKill;
	}
	public boolean hasTeam()
	{
		return !(TeamKey.equalsIgnoreCase(""));
	}
	public boolean hasInvite()
	{
		return !(Invite.equalsIgnoreCase(""));
	}
	public void setTeamKey(String key)
	{
		TeamKey = key;
	}
	public String getTeamKey() {
		return TeamKey;
	}
	public void clearTeamKey(){
		TeamKey = "";
	}
	public void updateLastSeen()
	{
		LastSeen = getCurrentDate();
	}
	public Calendar getLastSeen()
	{
		return LastSeen;
	}
	private Calendar getCurrentDate()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
        return cal;
	}
	public void setInvite(String invitingTeam){
		Invite = invitingTeam;
	}
	public String getInvite(){
		return Invite;
	}
	public void clearInvite(){
		Invite = "";
	}
	public String getSaveString()
	{
		String save = "";
		int month = LastSeen.get(Calendar.MONTH)+1;
		String date = "LastOnline: '" + month+"/"+LastSeen.get(Calendar.DATE)+"/"+LastSeen.get(Calendar.YEAR)+"'";
		save = "{" + date +"}";
		
		
		return save;
	}
}