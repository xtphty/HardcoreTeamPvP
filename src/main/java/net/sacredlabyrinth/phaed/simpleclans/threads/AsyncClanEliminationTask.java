package net.sacredlabyrinth.phaed.simpleclans.threads;

import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.HardcoreTeamPvP;
import net.sacredlabyrinth.phaed.simpleclans.utils.HardcoreTeamTasks;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;


public class AsyncClanEliminationTask implements Runnable {
    private int repeatTime = 1;
    private int countdown = 10;
    private int countdownTime = 0;

    /**
     * Async thread object for kicking players
     *
     * @param repeatTime    frequency of kicks (in minutes)
     * @param countdownTime grace period before first kick (in minutes)
     */
    public AsyncClanEliminationTask(int repeatTime, int countdownTime) {
        countdown = countdownTime;
        this.countdownTime = countdownTime;
        this.repeatTime = repeatTime;
    }

    @Override
    public void run() {
        HardcoreTeamPvP plugin = HardcoreTeamPvP.getInstance();
        System.out.println(countdown);
        if (repeatTime != 0 && repeatTime > 0) {
            while (true) {
                try {
                    Thread.sleep(repeatTime * 60 * 1000); // sleep for 1 hour by default, then execute checks
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (countdown - 1 == 0) {
                    try {
                        clanChecks(plugin);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    countdown = countdownTime;
                } else {
                    countdown--;
                }
            }
        } else {
            plugin.getLog().info("Lowest kill clan kick disabled ");
        }
    }

    private void clanChecks(HardcoreTeamPvP plugin) throws Exception {
        int lowest = -1;
        Clan lowestClan = null;
        for (Clan c : plugin.getClanManager().getClans()) {
            if (lowest == -1 || lowest >= c.getKillCount()) {
                if (lowest == c.getKillCount()) {
                    if (lowestClan.getTotalDeaths() <= c.getTotalDeaths()) {
                        lowestClan = c;
                    }
                }
                lowest = c.getKillCount();
                lowestClan = c;
            }
        }
        plugin.getLog().info("Lowest clan is " + lowestClan + " with " + lowest + " kills");
        if (lowestClan != null) {
            Bukkit.broadcastMessage(ChatColor.RED + lowestClan.getName().toString() + " has been disbanded. They had the fewest kills with a total of " + lowest + " frags.");
        }
        HardcoreTeamTasks.disbandClan = lowestClan;
    }

}