package com.Satrosity.Clans;

import java.util.HashSet;
import java.util.UUID;
import 	org.bukkit.plugin.java.JavaPlugin;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
//import java.net.MalformedURLException;
//import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map.Entry;
//import java.util.Map;
import java.util.Set;
//import java.util.TreeMap;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
//import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
//import org.bukkit.event.Event;
//import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.yaml.snakeyaml.Yaml;
import org.bukkit.Server;
//Newly Created
//Read in from File
public class TierList {
	
	private TeamRank Rank;
	private HashSet<UUID> RankMembers;
	
	//Newly Created
	public TierList(TeamRank r)
	{
		Rank = r;
		RankMembers = new HashSet<UUID>();
	}
	//Read in from File
	public TierList(TeamRank r, HashSet<UUID> list)
	{
		Rank = r;
		RankMembers = list;
	}
	public HashSet<UUID> getRankMembers() {
		return RankMembers;
	}
	
	public void clearRankMembers() {
		RankMembers = new HashSet<UUID>();
	}

	public void add(UUID playerUuid)
	{
		RankMembers.add(playerUuid);
	}
	public void remove(UUID playerUuid)
	{
		RankMembers.remove(playerUuid);
	}
	public boolean containsMember(UUID playerUuid)
	{
		return RankMembers.contains(playerUuid);
	}
	public boolean isEmpty()
	{
		return RankMembers.isEmpty();
	}
	public TeamRank getRank() {
		return Rank;
	}
	public void setRank(TeamRank rank) {
		Rank = rank;
	}	
	public int getTierSize()
	{
		return RankMembers.size();
	}
	public String getSaveString()
	{
		String save = Rank.getSaveString();
		save += "            Members:\n";
		for(UUID player : RankMembers)
		{
			save += "                - \'" + player.toString() + "\'\n";
		}
		return save;
	}
	
	public String membersToString(Server server)
	{
		String list = "";
		
		if(!RankMembers.isEmpty()) {
			int i = 1;
			for(UUID member : RankMembers) {
				if(i == RankMembers.size())
					list += server.getPlayer(member).getDisplayName();
				else
					list += server.getPlayer(member).getDisplayName() + ", ";
				i++;
			}
		}
		return list;
	}
}
