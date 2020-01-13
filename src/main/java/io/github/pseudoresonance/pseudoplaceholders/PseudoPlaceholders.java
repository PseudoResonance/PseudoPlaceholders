package io.github.pseudoresonance.pseudoplaceholders;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;

import io.github.pseudoresonance.pseudoapi.bukkit.Chat.Errors;
import io.github.pseudoresonance.pseudoapi.bukkit.language.LanguageManager;
import io.github.pseudoresonance.pseudoapi.bukkit.CommandDescription;
import io.github.pseudoresonance.pseudoapi.bukkit.HelpSC;
import io.github.pseudoresonance.pseudoapi.bukkit.MainCommand;
import io.github.pseudoresonance.pseudoapi.bukkit.PseudoPlugin;
import io.github.pseudoresonance.pseudoapi.bukkit.PseudoUpdater;
import io.github.pseudoresonance.pseudoplaceholders.commands.ReloadLocalizationSC;
import io.github.pseudoresonance.pseudoplaceholders.commands.ReloadSC;
import io.github.pseudoresonance.pseudoplaceholders.commands.ResetLocalizationSC;
import io.github.pseudoresonance.pseudoplaceholders.completers.PseudoPlaceholdersTC;
import io.github.pseudoresonance.pseudoplaceholders.hooks.PseudoAPIHooks;
import io.github.pseudoresonance.pseudoplaceholders.hooks.PseudoMusicHooks;
import io.github.pseudoresonance.pseudoplaceholders.hooks.PseudoPlayersHooks;
import io.github.pseudoresonance.pseudoplaceholders.hooks.PseudoUtilsHooks;

public class PseudoPlaceholders extends PseudoPlugin {

	public static PseudoPlugin plugin;
	
	private static MainCommand mainCommand;
	private static HelpSC helpSubCommand;
	
	@SuppressWarnings("unused")
	private static Metrics metrics = null;
	
	public void onLoad() {
		PseudoUpdater.registerPlugin(this);
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		plugin = this;
		mainCommand = new MainCommand(plugin);
		helpSubCommand = new HelpSC(plugin);
		initializeCommands();
		initializeTabcompleters();
		initializeSubCommands();
		setCommandDescriptions();
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			registerHooks();
		} else
			PseudoPlaceholders.plugin.getChat().sendConsolePluginError(Errors.CUSTOM, LanguageManager.getLanguage().getMessage("pseudoplaceholders.placeholderapi_not_loaded"));
		initializeMetrics();
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
        Bukkit.getScheduler().cancelTasks(this);
	}
	
	private void initializeMetrics() {
		metrics = new Metrics(this);
	}
	
	public void reloadHooks() {
		try {
			Class<?> c = Class.forName("me.clip.placeholderapi.PlaceholderAPI");
			Method m = c.getMethod("unregisterPlaceholderHook", String.class);
			m.invoke((Object) null, "pseudoapi");
			m.invoke((Object) null, "pseudomusic");
			m.invoke((Object) null, "pseudoplayers");
			m.invoke((Object) null, "pseudoutils");
			PseudoPlaceholders.plugin.getChat().sendConsolePluginMessage(LanguageManager.getLanguage().getMessage("pseudoplaceholders.unregistered_all"));
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			PseudoPlaceholders.plugin.getChat().sendConsolePluginError(Errors.CUSTOM, LanguageManager.getLanguage().getMessage("pseudoplaceholders.error_failed_access_placeholderapi"));
			e.printStackTrace();
		}
		registerHooks();
	}
	
	private void registerHooks() {
		try {
			Class<?> c = Class.forName("me.clip.placeholderapi.PlaceholderAPI");
			Class<?> hook = Class.forName("me.clip.placeholderapi.PlaceholderHook");
			Method m = c.getMethod("registerPlaceholderHook", String.class, hook);
			m.invoke((Object) null, "pseudoapi", new PseudoAPIHooks());
			PseudoPlaceholders.plugin.getChat().sendConsolePluginMessage(LanguageManager.getLanguage().getMessage("pseudoplaceholders.registered_hook", "PseudoAPI"));
			if (Bukkit.getPluginManager().getPlugin("PseudoMusic") != null) {
				m.invoke((Object) null, "pseudomusic", new PseudoMusicHooks());
				PseudoPlaceholders.plugin.getChat().sendConsolePluginMessage(LanguageManager.getLanguage().getMessage("pseudoplaceholders.registered_hook", "PseudoMusic"));
			}
			if (Bukkit.getPluginManager().getPlugin("PseudoPlayers") != null) {
				m.invoke((Object) null, "pseudoplayers", new PseudoPlayersHooks());
				PseudoPlaceholders.plugin.getChat().sendConsolePluginMessage(LanguageManager.getLanguage().getMessage("pseudoplaceholders.registered_hook", "PseudoPlayers"));
			}
			if (Bukkit.getPluginManager().getPlugin("PseudoUtils") != null) {
				m.invoke((Object) null, "pseudoutils", new PseudoUtilsHooks());
				PseudoPlaceholders.plugin.getChat().sendConsolePluginMessage(LanguageManager.getLanguage().getMessage("pseudoplaceholders.registered_hook", "PseudoUtils"));
			}
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			PseudoPlaceholders.plugin.getChat().sendConsolePluginError(Errors.CUSTOM, LanguageManager.getLanguage().getMessage("pseudoplaceholders.error_failed_access_placeholderapi"));
			e.printStackTrace();
		}
	}

	private void initializeCommands() {
		this.getCommand("pseudoplaceholders").setExecutor(mainCommand);
	}

	private void initializeSubCommands() {
		subCommands.put("help", helpSubCommand);
		subCommands.put("reload", new ReloadSC());
		subCommands.put("reloadlocalization", new ReloadLocalizationSC());
		subCommands.put("resetlocalization", new ResetLocalizationSC());
	}

	private void initializeTabcompleters() {
		this.getCommand("pseudoplaceholders").setTabCompleter(new PseudoPlaceholdersTC());
	}

	private void setCommandDescriptions() {
		commandDescriptions.add(new CommandDescription("pseudoplaceholders", "pseudoplaceholders.pseudoplaceholders_help", ""));
		commandDescriptions.add(new CommandDescription("pseudoplaceholders help", "pseudoplaceholders.pseudoplaceholders_help_help", ""));
		commandDescriptions.add(new CommandDescription("pseudoplaceholders reload", "pseudoplaceholders.pseudoplaceholders_reload_help", "pseudoplaceholders.reload"));
		commandDescriptions.add(new CommandDescription("pseudoplaceholders reloadlocalization", "pseudoplaceholders.pseudoplaceholders_reloadlocalization_help", "pseudoplaceholders.reloadlocalization"));
		commandDescriptions.add(new CommandDescription("pseudoplaceholders resetlocalization", "pseudoplaceholders.pseudoplaceholders_resetlocalization_help", "pseudoplaceholders.resetlocalization"));
	}

}