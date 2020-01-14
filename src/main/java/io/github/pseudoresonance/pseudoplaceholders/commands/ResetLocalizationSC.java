package io.github.pseudoresonance.pseudoplaceholders.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.pseudoresonance.pseudoapi.bukkit.SubCommandExecutor;
import io.github.pseudoresonance.pseudoapi.bukkit.Chat.Errors;
import io.github.pseudoresonance.pseudoapi.bukkit.language.LanguageManager;
import io.github.pseudoresonance.pseudoplaceholders.PseudoPlaceholders;

public class ResetLocalizationSC implements SubCommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player) || sender.hasPermission("pseudoplaceholders.resetlocalization")) {
			try {
				LanguageManager.copyDefaultPluginLanguageFiles(PseudoPlaceholders.plugin, true);
			} catch (Exception e) {
				PseudoPlaceholders.plugin.getChat().sendPluginError(sender, Errors.GENERIC);
				return false;
			}
			PseudoPlaceholders.plugin.getChat().sendPluginMessage(sender, LanguageManager.getLanguage(sender).getMessage("pseudoapi.localization_reset"));
			return true;
		} else {
			PseudoPlaceholders.plugin.getChat().sendPluginError(sender, Errors.NO_PERMISSION, LanguageManager.getLanguage(sender).getMessage("pseudoapi.permission_reset_localization"));
			return false;
		}
	}

}
