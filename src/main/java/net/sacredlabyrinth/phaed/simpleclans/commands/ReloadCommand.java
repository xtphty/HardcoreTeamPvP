package net.sacredlabyrinth.phaed.simpleclans.commands;

import net.sacredlabyrinth.phaed.simpleclans.ChatBlock;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.HardcoreTeamPvP;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author phaed
 */
public class ReloadCommand
{
    public ReloadCommand()
    {
    }

    /**
     * Execute the command
     *
     * @param sender
     * @param arg
     */
    public void execute(CommandSender sender, String[] arg)
    {
        HardcoreTeamPvP plugin = HardcoreTeamPvP.getInstance();

        if (sender instanceof Player)
        {
            if (!plugin.getPermissionsManager().has((Player)sender, "simpleclans.admin.reload"))
            {
                ChatBlock.sendMessage(sender, ChatColor.RED + "Think you're slick don't ya");
            }
        }

        plugin.getSettingsManager().load();
        plugin.getLanguageManager().load();
        plugin.getStorageManager().importFromDatabase();
        HardcoreTeamPvP.getInstance().getPermissionsManager().loadPermissions();

        for (Clan clan : plugin.getClanManager().getClans())
        {
            HardcoreTeamPvP.getInstance().getPermissionsManager().updateClanPermissions(clan);
        }
        ChatBlock.sendMessage(sender, ChatColor.AQUA + plugin.getLang("configuration.reloaded"));

    }
}
