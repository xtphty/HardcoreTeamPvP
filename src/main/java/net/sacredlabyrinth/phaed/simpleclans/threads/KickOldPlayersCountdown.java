package net.sacredlabyrinth.phaed.simpleclans.threads;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.sacredlabyrinth.phaed.simpleclans.ChatBlock;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.HardcoreTeamPvP;
import net.sacredlabyrinth.phaed.simpleclans.utils.HardcoreTeamTasks;

public class KickOldPlayersCountdown implements Runnable{
	private int repeatTime=1;
	private int countdown = 10;
	private int countdownTime = 0;
	public KickOldPlayersCountdown(int repeatTime, int countdownTime){
		countdown = countdownTime;
		this.countdownTime = countdownTime;
		this.repeatTime = repeatTime;
	}
	@Override
	public void run() {
		HardcoreTeamPvP plugin = HardcoreTeamPvP.getInstance();
		System.out.println(countdown);
		if(repeatTime!=0 && repeatTime>0){
			while(true){
				try {
					Thread.sleep(repeatTime*60*1000); // sleep for 1 hour by default, then execute checks
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(countdown-1==0){
					try {
						clanChecks(plugin);
					} catch (Exception e) {
						e.printStackTrace();
					}
					countdown = countdownTime;
				}else{
					countdown--;
				}
			}
		}else{
			System.out.println("Lowest kill clan kick disabled ");
		}
	}
	private void clanChecks(HardcoreTeamPvP plugin) throws Exception{
		int lowest=-1;
		Clan lowestClan = null;
		for(Clan c: plugin.getClanManager().getClans()){
			if(lowest==-1 || lowest>=c.getKillCount()){
				if(lowest == c.getKillCount()){
					if(lowestClan.getTotalDeaths() <= c.getTotalDeaths()){
						lowestClan = c;
					}
				}
				lowest = c.getKillCount();
				lowestClan=c;
			}
		}
		System.out.println("Lowest clan is "+lowestClan+" with "+lowest+" kills");
		if(lowestClan != null){
			for(Player p : Bukkit.getOnlinePlayers()){
				ChatBlock.sendMessage(p, ChatColor.RED + lowestClan.getName().toString() + " has been disbanded. They had the fewest kills with a total of " + lowest + " frags.");
			}
		}
		HardcoreTeamTasks.disbandClan=lowestClan;
	}

}