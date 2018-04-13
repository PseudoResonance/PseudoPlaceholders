package io.github.pseudoresonance.pseudoplaceholders.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.pseudoresonance.pseudoapi.bukkit.Message.Errors;
import io.github.pseudoresonance.pseudoapi.bukkit.SubCommandExecutor;
import io.github.pseudoresonance.pseudoplaceholders.PseudoPlaceholders;

public class ReloadSC implements SubCommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player) || sender.hasPermission("pseudoplaceholders.reload")) {
			if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
				((PseudoPlaceholders) PseudoPlaceholders.plugin).reloadHooks();
				PseudoPlaceholders.message.sendPluginMessage(sender, "Placeholder hooks reloaded!");
				return true;
			}
			PseudoPlaceholders.message.sendPluginError(sender, Errors.CUSTOM, "PlaceholderAPI is not loaded!");
			return false;
		} else {
			PseudoPlaceholders.message.sendPluginError(sender, Errors.NO_PERMISSION, "reload placeholder hooks!");
			return false;
		}
	}

}
