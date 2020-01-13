package io.github.pseudoresonance.pseudoplaceholders.hooks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import io.github.pseudoresonance.pseudoapi.bukkit.language.LanguageManager;
import io.github.pseudoresonance.pseudomusic.JukeboxController;
import io.github.pseudoresonance.pseudomusic.PlayerType;
import me.clip.placeholderapi.PlaceholderHook;

public class PseudoMusicHooks extends PlaceholderHook {

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		if (Bukkit.getPluginManager().getPlugin("PseudoMusic").isEnabled()) {
			switch(identifier) {
				case "now_playing":
					if (io.github.pseudoresonance.pseudomusic.Config.playerType == PlayerType.PRIVATE) {
						if (p != null) {
							if (JukeboxController.isPlaying(p))
								return JukeboxController.getSong(p).getName();
							else
								return LanguageManager.getLanguage(p).getMessage("pseudoplaceholders.none");
						} else
							return LanguageManager.getLanguage(p).getMessage("pseudoplaceholders.not_player");
					} else {
						if (JukeboxController.getJukebox().isPlaying())
							return JukeboxController.getJukebox().getSong().getName();
						else
							return LanguageManager.getLanguage(p).getMessage("pseudoplaceholders.none");
					}
				case "next_song":
					if (io.github.pseudoresonance.pseudomusic.Config.playerType == PlayerType.PRIVATE) {
						if (p != null) {
							if (JukeboxController.isPlaying(p))
								return JukeboxController.getNextSong(p).getName();
							else
								return LanguageManager.getLanguage(p).getMessage("pseudoplaceholders.none");
						} else
							return LanguageManager.getLanguage(p).getMessage("pseudoplaceholders.not_player");
					} else {
						if (JukeboxController.getJukebox().isPlaying())
							return JukeboxController.getJukebox().getNextSong().getName();
						else
							return LanguageManager.getLanguage(p).getMessage("pseudoplaceholders.none");
					}
				case "last_song":
					if (io.github.pseudoresonance.pseudomusic.Config.playerType == PlayerType.PRIVATE) {
						if (p != null) {
							if (JukeboxController.isPlaying(p))
								return JukeboxController.getLastSong(p).getName();
							else
								return LanguageManager.getLanguage(p).getMessage("pseudoplaceholders.none");
						} else
							return LanguageManager.getLanguage(p).getMessage("pseudoplaceholders.not_player");
					} else {
						if (JukeboxController.getJukebox().isPlaying())
							return JukeboxController.getJukebox().getLastSong().getName();
						else
							return LanguageManager.getLanguage(p).getMessage("pseudoplaceholders.none");
					}
				case "playing":
					if (io.github.pseudoresonance.pseudomusic.Config.playerType == PlayerType.PRIVATE) {
						if (p != null) {
							if (JukeboxController.isPlaying(p))
								return LanguageManager.getLanguage(p).getMessage("pseudoplaceholders.true");
							else
								return LanguageManager.getLanguage(p).getMessage("pseudoplaceholders.false");
						} else
							return LanguageManager.getLanguage(p).getMessage("pseudoplaceholders.not_player");
					} else {
						if (JukeboxController.getJukebox().isPlaying())
							return LanguageManager.getLanguage(p).getMessage("pseudoplaceholders.true");
						else
							return LanguageManager.getLanguage(p).getMessage("pseudoplaceholders.false");
					}
				case "repeating":
					if (io.github.pseudoresonance.pseudomusic.Config.playerType == PlayerType.PRIVATE) {
						if (p != null) {
							if (JukeboxController.isRepeating(p))
								return LanguageManager.getLanguage(p).getMessage("pseudoplaceholders.true");
							else
								return LanguageManager.getLanguage(p).getMessage("pseudoplaceholders.false");
						} else
							return LanguageManager.getLanguage(p).getMessage("pseudoplaceholders.not_player");
					} else {
						if (JukeboxController.getJukebox().isRepeating())
							return LanguageManager.getLanguage(p).getMessage("pseudoplaceholders.true");
						else
							return LanguageManager.getLanguage(p).getMessage("pseudoplaceholders.false");
					}
				case "shuffling":
					if (io.github.pseudoresonance.pseudomusic.Config.playerType == PlayerType.PRIVATE) {
						if (p != null) {
							if (JukeboxController.isShuffling(p))
								return LanguageManager.getLanguage(p).getMessage("pseudoplaceholders.true");
							else
								return LanguageManager.getLanguage(p).getMessage("pseudoplaceholders.false");
						} else
							return LanguageManager.getLanguage(p).getMessage("pseudoplaceholders.not_player");
					} else {
						if (JukeboxController.getJukebox().isShuffling())
							return LanguageManager.getLanguage(p).getMessage("pseudoplaceholders.true");
						else
							return LanguageManager.getLanguage(p).getMessage("pseudoplaceholders.false");
					}
				default:
					return "";
			}
		}
		return LanguageManager.getLanguage(p).getMessage("pseudoplaceholders.plugin_not_installed", "PseudoMusic");
	}

}
