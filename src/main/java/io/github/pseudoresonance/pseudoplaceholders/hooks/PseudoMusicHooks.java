package io.github.pseudoresonance.pseudoplaceholders.hooks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import io.github.pseudoresonance.pseudomusic.JukeboxController;
import io.github.pseudoresonance.pseudomusic.PlayerType;
import me.clip.placeholderapi.PlaceholderHook;

public class PseudoMusicHooks extends PlaceholderHook {

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		if (Bukkit.getPluginManager().getPlugin("PseudoMusic").isEnabled()) {
			switch(identifier) {
				case "now_playing":
					if (io.github.pseudoresonance.pseudomusic.ConfigOptions.playerType == PlayerType.PRIVATE) {
						if (p != null) {
							if (JukeboxController.isPlaying(p))
								return JukeboxController.getSong(p).getName();
							else
								return "None";
						} else
							return "Not a Player";
					} else {
						if (JukeboxController.getJukebox().isPlaying())
							return JukeboxController.getJukebox().getSong().getName();
						else
							return "None";
					}
				case "next_song":
					if (io.github.pseudoresonance.pseudomusic.ConfigOptions.playerType == PlayerType.PRIVATE) {
						if (p != null) {
							if (JukeboxController.isPlaying(p))
								return JukeboxController.getNextSong(p).getName();
							else
								return "None";
						} else
							return "Not a Player";
					} else {
						if (JukeboxController.getJukebox().isPlaying())
							return JukeboxController.getJukebox().getNextSong().getName();
						else
							return "None";
					}
				case "last_song":
					if (io.github.pseudoresonance.pseudomusic.ConfigOptions.playerType == PlayerType.PRIVATE) {
						if (p != null) {
							if (JukeboxController.isPlaying(p))
								return JukeboxController.getLastSong(p).getName();
							else
								return "None";
						} else
							return "Not a Player";
					} else {
						if (JukeboxController.getJukebox().isPlaying())
							return JukeboxController.getJukebox().getLastSong().getName();
						else
							return "None";
					}
				case "playing":
					if (io.github.pseudoresonance.pseudomusic.ConfigOptions.playerType == PlayerType.PRIVATE) {
						if (p != null) {
							if (JukeboxController.isPlaying(p))
								return "True";
							else
								return "False";
						} else
							return "Not a Player";
					} else {
						if (JukeboxController.getJukebox().isPlaying())
							return "True";
						else
							return "False";
					}
				case "repeating":
					if (io.github.pseudoresonance.pseudomusic.ConfigOptions.playerType == PlayerType.PRIVATE) {
						if (p != null) {
							if (JukeboxController.isRepeating(p))
								return "True";
							else
								return "False";
						} else
							return "Not a Player";
					} else {
						if (JukeboxController.getJukebox().isRepeating())
							return "True";
						else
							return "False";
					}
				case "shuffling":
					if (io.github.pseudoresonance.pseudomusic.ConfigOptions.playerType == PlayerType.PRIVATE) {
						if (p != null) {
							if (JukeboxController.isShuffling(p))
								return "True";
							else
								return "False";
						} else
							return "Not a Player";
					} else {
						if (JukeboxController.getJukebox().isShuffling())
							return "True";
						else
							return "False";
					}
				default:
					return "";
			}
		}
		return "PseudoMusic Not Installed";
	}

}
