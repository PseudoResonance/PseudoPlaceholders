package io.github.pseudoresonance.pseudoplaceholders.hooks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import io.github.pseudoresonance.pseudoapi.bukkit.playerdata.ServerPlayerDataController;
import io.github.pseudoresonance.pseudoutils.PlayerBrand;
import io.github.pseudoresonance.pseudoutils.TPS;
import me.clip.placeholderapi.PlaceholderHook;

public class PseudoUtilsHooks extends PlaceholderHook {

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		if (Bukkit.getPluginManager().getPlugin("PseudoUtils").isEnabled()) {
			switch (identifier) {
				case "tps":
					return TPS.getTps() + "TPS";
				case "brand":
					if (p != null)
						return PlayerBrand.getBrand(p.getName());
					else
						return "Not a Player";
				case "god_mode":
					if (p != null) {
						Object o = ServerPlayerDataController.getPlayerSetting(p.getUniqueId().toString(), "godMode");
						if (o instanceof Boolean) {
							boolean b = (Boolean) o;
							if (b)
								return "True";
							else
								return "False";
						}
					} else
						return "Not a Player";
				default:
					return "";
			}
		}
		return "PseudoUtils Not Installed";
	}

}
