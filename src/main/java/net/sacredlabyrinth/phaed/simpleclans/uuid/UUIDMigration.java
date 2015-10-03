package net.sacredlabyrinth.phaed.simpleclans.uuid;

import java.util.UUID;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.HardcoreTeamPvP;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 *
 * @author NeT32
 */
public class UUIDMigration {

    public static boolean canReturnUUID() {
        try {
            Bukkit.class.getDeclaredMethod("getPlayer", UUID.class);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    public static UUID getForcedPlayerUUID(String playerDisplayName) {
        Player OnlinePlayer = HardcoreTeamPvP.getInstance().getServer().getPlayerExact(playerDisplayName);
        OfflinePlayer OfflinePlayer = HardcoreTeamPvP.getInstance().getServer().getOfflinePlayer(playerDisplayName);

        if (OnlinePlayer != null) {
            return OnlinePlayer.getUniqueId();
        } else {
            for (ClanPlayer cp : HardcoreTeamPvP.getInstance().getClanManager().getAllClanPlayers()) {
                if (cp.getName().equalsIgnoreCase(playerDisplayName)) {
                    return cp.getUniqueId();
                }
            }
            try {
                return UUIDFetcher.getUUIDOf(playerDisplayName);
            } catch (Exception ex) {
                if (OfflinePlayer != null) {
                    return OfflinePlayer.getUniqueId();
                } else {
                    return null;
                }
            }
        }
    }

}
