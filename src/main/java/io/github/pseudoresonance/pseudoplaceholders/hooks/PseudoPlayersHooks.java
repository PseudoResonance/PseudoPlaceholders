package io.github.pseudoresonance.pseudoplaceholders.hooks;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import io.github.pseudoresonance.pseudoapi.bukkit.language.LanguageManager;
import io.github.pseudoresonance.pseudoapi.bukkit.playerdata.PlayerDataController;
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
							if (firstJoinO instanceof Date) {
								firstJoinTS = new Timestamp(((Date) firstJoinO).getTime());
							}
							LocalDate firstJoinDate = firstJoinTS.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
							long firstJoinDays = ChronoUnit.DAYS.between(firstJoinDate, Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.systemDefault()).toLocalDate());
							if (firstJoinDays >= io.github.pseudoresonance.pseudoplayers.Config.firstJoinTimeDifference) {
								firstJoinTime = LanguageManager.getLanguage(p).formatDateTime(firstJoinTS);
							} else {
								firstJoinTime = LanguageManager.getLanguage(p).formatTimeAgo(firstJoinTS, ChronoUnit.SECONDS, ChronoUnit.DAYS);
							}
						} else
							firstJoinTime = LanguageManager.getLanguage(p).getMessage("pseudoplaceholders.unknown");
						return firstJoinTime;
					} else {
						return LanguageManager.getLanguage(p).getMessage("pseudoplaceholders.not_player");
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
							if (joinLeaveO instanceof Date) {
								joinLeaveTS = new Timestamp(((Date) joinLeaveO).getTime());
							}
							LocalDate joinLeaveDate = joinLeaveTS.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
							long joinLeaveDays = ChronoUnit.DAYS.between(joinLeaveDate, Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.systemDefault()).toLocalDate());
							if (joinLeaveDays >= io.github.pseudoresonance.pseudoplayers.Config.joinLeaveTimeDifference) {
								joinLeaveTime = LanguageManager.getLanguage(p).formatDateTime(joinLeaveTS);
							} else {
								joinLeaveTime = LanguageManager.getLanguage(p).formatTimeAgo(joinLeaveTS, false, ChronoUnit.SECONDS, ChronoUnit.DAYS);
							}
						} else
							joinLeaveTime = LanguageManager.getLanguage(p).getMessage("pseudoplaceholders.unknown");
						return joinLeaveTime;
					} else {
						return LanguageManager.getLanguage(p).getMessage("pseudoplaceholders.not_player");
					}
				case "playtime":
					if (p != null) {
						Object playtimeO = PlayerDataController.getPlayerSetting(p.getUniqueId().toString(), "playtime");
						long playtime = 0;
						if (playtimeO instanceof BigInteger || playtimeO instanceof Long || playtimeO instanceof Integer) {
							if (playtimeO instanceof BigInteger)
								playtime = ((BigInteger) playtimeO).longValueExact();
							else if (playtimeO instanceof Long)
								playtime = (Long) playtimeO;
							else
								playtime = (Integer) playtimeO;
							Object o = PlayerDataController.getPlayerSetting(p.getUniqueId().toString(), "lastjoinleave").join();
							Timestamp joinLeaveTS = null;
							if (o instanceof Timestamp) {
								joinLeaveTS = (Timestamp) o;
							}
							if (o instanceof Date) {
								joinLeaveTS = new Timestamp(((Date) o).getTime());
							}
							if (joinLeaveTS != null) {
								long joinLeave = joinLeaveTS.getTime();
								long diff = System.currentTimeMillis() - joinLeave;
								playtime += diff;
							}
							return LanguageManager.getLanguage(p).formatTimeAgo(new Timestamp(System.currentTimeMillis() - playtime), false, ChronoUnit.SECONDS, ChronoUnit.YEARS);
						}
						return LanguageManager.getLanguage(p).getMessage("pseudoplaceholders.unknown");
					} else {
						return LanguageManager.getLanguage(p).getMessage("pseudoplaceholders.not_player");
					}
				default:
					return "";
			}
		}
		return LanguageManager.getLanguage(p).getMessage("pseudoplaceholders.plugin_not_installed", "PseudoPlayers");
	}

}
