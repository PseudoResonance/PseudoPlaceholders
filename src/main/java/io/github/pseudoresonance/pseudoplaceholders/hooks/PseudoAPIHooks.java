package io.github.pseudoresonance.pseudoplaceholders.hooks;

import org.bukkit.entity.Player;

import io.github.pseudoresonance.pseudoapi.bukkit.PluginController;
import io.github.pseudoresonance.pseudoapi.bukkit.data.Data;
import me.clip.placeholderapi.PlaceholderHook;

public class PseudoAPIHooks extends PlaceholderHook {

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		switch(identifier) {
			case "plugins_loaded":
				return String.valueOf(PluginController.getPlugins().length);
			case "global_backend":
				return Data.getGlobalBackend().getName();
			case "server_backend":
				return Data.getServerBackend().getName();
			default:
				return "";
		}
	}

}
