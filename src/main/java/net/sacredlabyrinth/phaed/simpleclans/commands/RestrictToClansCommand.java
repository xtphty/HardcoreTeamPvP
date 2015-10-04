package net.sacredlabyrinth.phaed.simpleclans.commands;

import net.sacredlabyrinth.phaed.simpleclans.ChatBlock;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.HardcoreTeamPvP;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * @author xtphty
 */
public class RestrictToClansCommand {
    HardcoreTeamPvP plugin;

    public RestrictToClansCommand() {
        plugin = HardcoreTeamPvP.getInstance();
    }

    /**
     * Restricts the servers to only players on a clan
     *
     * @param player the command runner
     * @param arg command arguments
     */
    public void execute(Player player, String[] arg) {
        if ((arg == null) || (arg.length != 1)){
            ChatBlock.sendMessage(player, ChatColor.RED + plugin.getLang(""));
        }

        boolean restrictionState = plugin.isRestrictedToClans();
        if (StringUtils.equalsIgnoreCase(arg[0], "enable")){
            restrictionState = true;
            ChatBlock.sendMessage(player, ChatColor.GREEN + plugin.getLang("message.restrict.enable"));
        } else if (StringUtils.equalsIgnoreCase(arg[0], "disable")) {
            restrictionState = false;
            ChatBlock.sendMessage(player, ChatColor.GREEN + plugin.getLang("message.restrict.disable"));
        }

        plugin.setRestrictedToClans(restrictionState);

        if (plugin.getPermissionsManager().has(player, "simpleclans.admin.restrict-to-clans")) {
            // Kick all online players that are not on a clan, except OPs
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.isOp()) {
                    continue;
                }

                if (plugin.getClanManager().getClanByPlayerUniqueId(player.getUniqueId()) == null) {
                    player.kickPlayer("You were kicked for not joining a clan before the round started, solo players not allowed.");
                }
            }

        } else {
            ChatBlock.sendMessage(player, ChatColor.RED + plugin.getLang("insufficient.permissions"));
        }
    }
}
