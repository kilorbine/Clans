package com.Satrosity.Clans;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.getspout.spoutapi.event.spout.SpoutCraftEnableEvent;

public class ClansPlayerListener implements Listener {
    public Clans plugin;
    Logger log = Logger.getLogger("Minecraft");
    
    public ClansPlayerListener(Clans instance) {
        plugin = instance;
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerChat(PlayerChatEvent event)
    {
    	if(!event.isCancelled())
    	{
    		Player p = event.getPlayer();
    		String fulltag = plugin.getClansConfig().getTagFormat();
    		String format = plugin.getClansConfig().getMessageFormat();
    		event.setFormat(insertData(format,fulltag,p.getDisplayName()));
    	}
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
    	if(plugin.getClansConfig().isAllowCapes())
    		plugin.addCapes();
     }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event){
    	String PlayerName = event.getPlayer().getDisplayName();
    	if(!plugin.hasUser(PlayerName))
    		plugin.makeUser(PlayerName);
    	else
    		plugin.updateUserDate(PlayerName);
    		//add new player
    	
    	//If player has team and motd, print it
    	plugin.doTeamsMOTD(PlayerName);
    	
    	if(plugin.getTeamPlayer(event.getPlayer().getDisplayName()).hasTeam())
    		plugin.IncreaseTeamOnlineCount(plugin.getTeamPlayer(event.getPlayer().getDisplayName()).getTeamKey());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onSpoutCraftEnable(SpoutCraftEnableEvent event) {
    	if(plugin.getClansConfig().isAllowCapes())
    		plugin.addCapes();
     }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onWorldChange(PlayerChangedWorldEvent event) {
    	if(plugin.getClansConfig().isAllowCapes())
    		plugin.addCapes();
     }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerQuit(PlayerQuitEvent event){
    	if(plugin.getTeamPlayer(event.getPlayer().getDisplayName()).hasTeam())
    		plugin.DecreaseTeamOnlineCount(plugin.getTeamPlayer(event.getPlayer().getDisplayName()).getTeamKey());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDamage(EntityDamageEvent event)
	{
	    if (event.getCause().equals(DamageCause.ENTITY_ATTACK)){
	    	EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
	    	if (e.getDamager() instanceof Player && e.getEntity() instanceof Player)//Check to see if the damager and damaged are players
	    	{
	    		Player attacker = (Player)e.getDamager();
	    		Player victim = (Player)e.getEntity();
	    		
	    		TeamPlayer att = plugin.getTeamPlayer(attacker.getDisplayName());
	    		TeamPlayer vic = plugin.getTeamPlayer(victim.getDisplayName());
	    		
	    		if(att.getTeamKey().equalsIgnoreCase(vic.getTeamKey()) && !att.getTeamKey().equalsIgnoreCase(""))
	    		{
	    			if(!att.canTeamKill() && !vic.canTeamKill())
	    			{
	    				attacker.sendMessage(ChatColor.GREEN + "This player is on your team. Use /team tk on to turn on team killing.");
	    				event.setCancelled(true);
	    			}
	    		}
	    	}
	    }
    	else if (event.getCause().equals(DamageCause.MAGIC))
    	{
	    	EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
	        if(e.getDamager() instanceof ThrownPotion)
	        {
	            ThrownPotion potion = (ThrownPotion)e.getDamager();
	            Entity entity = event.getEntity();
	            int damage = event.getDamage();
	            if(potion.getShooter() instanceof Player && entity instanceof Player) {
		    		Player attacker = (Player)potion.getShooter();
		    		Player victim = (Player)entity;
		    		
		    		TeamPlayer att = plugin.getTeamPlayer(attacker.getDisplayName());
		    		TeamPlayer vic = plugin.getTeamPlayer(victim.getDisplayName());
		    		
		    		if(att.getTeamKey().equalsIgnoreCase(vic.getTeamKey()) && !att.getTeamKey().equalsIgnoreCase(""))
		    		{
		    			if(!att.canTeamKill() && !vic.canTeamKill())
		    			{
		    				//attacker.sendMessage(ChatColor.GREEN + "This player is on your team. Use '/team tk on' to turn on team pvp.");
		    				event.setCancelled(true);
		    			}
		    		}
	            }
	        }
    	}
    	else if (event.getCause().equals(DamageCause.PROJECTILE)){
	    	EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
	        if(e.getDamager() instanceof Arrow)
	        {
	            Arrow arrow = (Arrow)e.getDamager();
	            Entity entity = event.getEntity();
	            int damage = event.getDamage();
	            if(arrow.getShooter() instanceof Player && entity instanceof Player) {
		    		Player attacker = (Player)arrow.getShooter();
		    		Player victim = (Player)entity;
		    		
		    		TeamPlayer att = plugin.getTeamPlayer(attacker.getDisplayName());
		    		TeamPlayer vic = plugin.getTeamPlayer(victim.getDisplayName());
		    		
		    		if(att.getTeamKey().equalsIgnoreCase(vic.getTeamKey()) && !att.getTeamKey().equalsIgnoreCase(""))
		    		{
		    			if(!att.canTeamKill() && !vic.canTeamKill())
		    			{
		    				attacker.sendMessage(ChatColor.GREEN + "This player is on your team. Use '/team tk on' to turn on team pvp.");
		    				event.setCancelled(true);
		    			}
		    		}
	            	
	            }
	        }
    	}
	}
    // Disable priming if needed
    @EventHandler(priority = EventPriority.NORMAL)
    public void onExplosionPrime(ExplosionPrimeEvent event)
    {
    	int x = event.getEntity().getLocation().getBlockX();
    	int z = event.getEntity().getLocation().getBlockZ();
    	String world = event.getEntity().getWorld().getName();
        // Only supress TNT
    	String area = plugin.findArea(x, z, world);
    	if(!area.equalsIgnoreCase("")) {
	        if(!(event.getEntity() instanceof Creeper))
	        	if(!plugin.isTeamOnline(area))
	        		event.setCancelled(true);
    	}
    }
    
    // Disable explosion if needed
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityExplode(EntityExplodeEvent event)
    {
    	int x = event.getEntity().getLocation().getBlockX();
    	int z = event.getEntity().getLocation().getBlockZ();
    	String world = event.getEntity().getWorld().getName();
        // Only supress TNT
    	String area = plugin.findArea(x, z, world);
    	if(!area.equalsIgnoreCase("")) {
	        if(!(event.getEntity() instanceof Creeper))
	        	if(!plugin.isTeamOnline(area))
	        		event.setCancelled(true);
    	}
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDeath(EntityDeathEvent event)
    {
    	//if player died in a team area
    	//and team area is not their team area
    	//reset spawn location to default
    	if(event.getEntity() instanceof Player)
    	{
    		Player player = (Player) event.getEntity();
    		String area = plugin.findArea(player.getLocation().getBlockX(), player.getLocation().getBlockZ(), player.getWorld().getName());
    		if(!area.equalsIgnoreCase(""))
    		{
    			String playerTeam = plugin.getTeamPlayer(player.getDisplayName()).getTeamKey();
    			//check if they arent the same team area
    			if(!playerTeam.equalsIgnoreCase(area))
    			{
    				//reset spawn location to default of the first world
    				player.setBedSpawnLocation(plugin.getServer().getWorlds().get(0).getSpawnLocation());
    				//send message
    				player.sendMessage(ChatColor.RED + "You have been killed inside another team's area. Your spawn point has been reset.");
    			}
    		}
    	}
    }
    private String insertData(String format, String tag, String PlayerName)
    {
    	format = format.replace("{PLAYER}", "%1$s");
    	format = format.replace("{MSG}", "%2$s");
    	
    	TeamPlayer tPlayer = plugin.getTeamPlayer(PlayerName);
    	Team team = plugin.getTeam(PlayerName);
    	
    	if(tPlayer.hasTeam()) {
    		if(plugin.getTeam(PlayerName).hasTag()) {
    			tag = tag.replace("{CLANCOLOR}", ""+team.getColor());
    			tag = tag.replace("{CLANTAG}", ""+team.getTeamTag());
    			format = format.replace("{FULLTAG}", tag);
    		}
    		else {
    			format = format.replace("{FULLTAG}", "");
    		}
    	}
    	else {
        	format = format.replace("{FULLTAG}", "");
    	}
    	
    	//COLORS
    	format = format.replace("{WHITE}", ""+ChatColor.WHITE);
    	//add rest later
    	
    	return format;
    }

}