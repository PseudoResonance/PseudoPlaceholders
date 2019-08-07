package io.github.pseudoresonance.pseudoplaceholders.hooks;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import io.github.pseudoresonance.pseudoapi.bukkit.playerdata.PlayerDataController;
import io.github.pseudoresonance.pseudoapi.bukkit.utils.Utils;
import me.clip.placeholderapi.PlaceholderHook;

public class PseudoPlayersHooks extends PlaceholderHook {

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		if (Bukkit.getPluginManager().getPlugin("PseudoPlayers").isEnabled()) {
			switch (identifier) {
				case "first_join":
					if (p != null) {
						Object firstJoinO = PlayerDataController.getPlayerSetting(p.getUniqueId().toString(), "firstjoin");
						String firstJoinTime = "";
						if (firstJoinO != null) {
							Timestamp firstJoinTS = new Timestamp(System.currentTimeMillis());
							if (firstJoinO instanceof Timestamp) {
								firstJoinTS = (Timestamp) firstJoinO;
							}
							LocalDate firstJoinDate = firstJoinTS.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
							long firstJoinDays = ChronoUnit.DAYS.between(firstJoinDate, Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.systemDefault()).toLocalDate());
							if (firstJoinDays >= io.github.pseudoresonance.pseudoplayers.Config.firstJoinTimeDifference) {
								firstJoinTime = new SimpleDateFormat(io.github.pseudoresonance.pseudoplayers.Config.firstJoinTimeFormat).format(firstJoinTS);
							} else {
								long diff = System.currentTimeMillis() - firstJoinTS.getTime();
								if (diff < 0) {
									diff = 0 - diff;
								}
								firstJoinTime = Utils.millisToHumanFormat(diff) + " ago";
							}
						} else
							firstJoinTime = "Unknown";
						return firstJoinTime;
					} else {
						return "Not a Player";
					}
				case "online_since":
					if (p != null) {
						Object joinLeaveO = PlayerDataController.getPlayerSetting(p.getUniqueId().toString(), "lastjoinleave");
						String joinLeaveTime = "";
						if (joinLeaveO != null) {
							Timestamp joinLeaveTS = new Timestamp(System.currentTimeMillis());
							if (joinLeaveO instanceof Timestamp) {
								joinLeaveTS = (Timestamp) joinLeaveO;
							}
							LocalDate joinLeaveDate = joinLeaveTS.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
							long joinLeaveDays = ChronoUnit.DAYS.between(joinLeaveDate, Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.systemDefault()).toLocalDate());
							if (joinLeaveDays >= io.github.pseudoresonance.pseudoplayers.Config.joinLeaveTimeDifference) {
								joinLeaveTime = "Since " + new SimpleDateFormat(io.github.pseudoresonance.pseudoplayers.Config.joinLeaveTimeFormat).format(joinLeaveTS);
							} else {
								long diff = System.currentTimeMillis() - joinLeaveTS.getTime();
								if (diff < 0) {
									diff = 0 - diff;
								}
								joinLeaveTime = "For " + Utils.millisToHumanFormat(diff);
							}
						} else
							joinLeaveTime = "Unknown";
						return joinLeaveTime;
					} else {
						return "Not a Player";
					}
				case "playtime":
					if (p != null) {
						Object playtimeO = PlayerDataController.getPlayerSetting(p.getUniqueId().toString(), "playtime");
						if (playtimeO instanceof BigInteger || playtimeO instanceof Long) {
							long playtime = 0;
							if (playtimeO instanceof BigInteger)
								playtime = ((BigInteger) playtimeO).longValueExact();
							else
								playtime = (Long) playtimeO;
							Object o = PlayerDataController.getPlayerSetting(p.getUniqueId().toString(), "lastjoinleave");
							if (o instanceof Timestamp) {
								long joinLeave = ((Timestamp) o).getTime();
								long diff = System.currentTimeMillis() - joinLeave;
								playtime += diff;
							}
							return Utils.millisToHumanFormat(playtime);
						}
						return "0 Seconds";
					} else {
						return "Not a Player";
					}
				default:
					return "";
			}
		}
		return "PseudoPlayers Not Installed";
	}

}
