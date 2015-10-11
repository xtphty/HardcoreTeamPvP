package net.sacredlabyrinth.phaed.simpleclans.utils;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.HardcoreTeamPvP;

public class HardcoreTeamTasks {
	public static Clan disbandClan=null;
	public static Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
	public static void startTasks(HardcoreTeamPvP plugin){
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
            	HardcoreTeamPvP plugin = HardcoreTeamPvP.getInstance();
            	for(Player p: Bukkit.getServer().getOnlinePlayers()){
            		HardcoreTeamUtils.teamColor(p);
        		}
            }
        }, 100L, 100L);
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
            	Clan toKick = disbandClan;
            	if(toKick!=null){
            		try{
                		HardcoreTeamPvP plugin = HardcoreTeamPvP.getInstance();
                		List<ClanPlayer> clanPlayers = toKick.getMembers();
                		try{
                			for(int i = 0; i < clanPlayers.size(); i++){
                				clanPlayers.get(i).toPlayer().kickPlayer("Your clan had the fewest number of kills and was disbanded. Better luck next time!");
                			}
                		}
                		catch(NullPointerException e){};
                		toKick.disband();
                		HardcoreTeamUtils.disbandTeam(toKick.getTag());
                		disbandClan=null;
            		}
        			catch(IllegalArgumentException e){};
            	}
            }
        }, 20L, 20L);
	}
}