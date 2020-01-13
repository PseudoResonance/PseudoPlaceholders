package io.github.pseudoresonance.pseudoplaceholders.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.pseudoresonance.pseudoapi.bukkit.language.LanguageManager;
import io.github.pseudoresonance.pseudoapi.bukkit.Chat.Errors;
import io.github.pseudoresonance.pseudoapi.bukkit.SubCommandExecutor;
import io.github.pseudoresonance.pseudoplaceholders.PseudoPlaceholders;

public class ReloadSC implements SubCommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player) || sender.hasPermission("pseudoplaceholders.reload")) {
			if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
				((PseudoPlaceholders) PseudoPlaceholders.plugin).reloadHooks();
				PseudoPlaceholders.plugin.getChat().sendPluginMessage(sender, LanguageManager.getLanguage(sender).getMessage("pseudoplaceholders.reloaded"));
				return true;
			}
			PseudoPlaceholders.plugin.getChat().sendPluginError(sender, Errors.CUSTOM, LanguageManager.getLanguage(sender).getMessage("pseudoplaceholders.placeholderapi_not_loaded"));
			return false;
		} else {
			PseudoPlaceholders.plugin.getChat().sendPluginError(sender, Errors.CUSTOM, LanguageManager.getLanguage(sender).getMessage("pseudoplaceholders.permission_reload"));
			return false;
		}
	}

}
