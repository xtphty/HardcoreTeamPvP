package net.sacredlabyrinth.phaed.simpleclans.utils;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.getspout.spout.player.SpoutCraftPlayer;

import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.HardcoreTeamPvP;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.HumanEntity;

/**
 * @author Ageudum & firestar
 */

public class HardcoreTeamUtils {
	public static Team staticColor;
	public static void teamColor(Player p){
		HardcoreTeamPvP plugin = HardcoreTeamPvP.getInstance();
		if(plugin.getClanManager()==null || plugin.getClanManager().getClanPlayer(p)==null){
			return;
		}
        Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
        Team playerTeam = board.getPlayerTeam(p);
		Clan playerClan = plugin.getClanManager().getClanPlayer(p).getClan();
	
		if((playerClan!=null && playerTeam==null) || (playerClan.getName() != playerTeam.getName())){
			try {	            
	            if(board.getTeam(playerClan.getTag()) == null){
	            	System.out.println("Team for " + p.getName() + "'s clan does not exist. Creating new team.");
	            	playerTeam = board.registerNewTeam(playerClan.getTag());
	            }
	            else if(board.getTeam(playerClan.getTag()).getName().equalsIgnoreCase(playerClan.getTag())){
	            	playerTeam = board.getTeam(playerClan.getTag());
	            }
	            
	            playerTeam.addPlayer(p);
	            playerTeam.setPrefix(playerClan.getColorTag()+" ");
	            staticColor = playerTeam;
	        }
	        catch (NullPointerException board1) {
	            // empty catch block
	        }
		}
		 p.setScoreboard(board);
    }
}
