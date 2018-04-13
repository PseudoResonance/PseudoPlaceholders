package io.github.pseudoresonance.pseudoplaceholders;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import io.github.pseudoresonance.pseudoapi.bukkit.CommandDescription;
import io.github.pseudoresonance.pseudoapi.bukkit.HelpSC;
import io.github.pseudoresonance.pseudoapi.bukkit.MainCommand;
import io.github.pseudoresonance.pseudoapi.bukkit.Message;
import io.github.pseudoresonance.pseudoapi.bukkit.Message.Errors;
import io.github.pseudoresonance.pseudoapi.bukkit.PseudoPlugin;
import io.github.pseudoresonance.pseudoplaceholders.commands.ReloadSC;
import io.github.pseudoresonance.pseudoplaceholders.completers.PseudoPlaceholdersTC;
import io.github.pseudoresonance.pseudoplaceholders.hooks.PseudoAPIHooks;
import io.github.pseudoresonance.pseudoplaceholders.hooks.PseudoMusicHooks;
import io.github.pseudoresonance.pseudoplaceholders.hooks.PseudoPlayersHooks;
import io.github.pseudoresonance.pseudoplaceholders.hooks.PseudoUtilsHooks;

public class PseudoPlaceholders extends PseudoPlugin {

	public static PseudoPlugin plugin;
	public static Message message;
	
	private static MainCommand mainCommand;
	private static HelpSC helpSubCommand;
	
	@Override
	public void onEnable() {
		super.onEnable();
		plugin = this;
		message = new Message(this);
		mainCommand = new MainCommand(plugin);
		helpSubCommand = new HelpSC(plugin);
		initializeCommands();
		initializeTabcompleters();
		initializeSubCommands();
		setCommandDescriptions();
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			registerHooks();
		} else
			PseudoPlaceholders.message.sendPluginError(Bukkit.getConsoleSender(), Errors.CUSTOM, "PlaceholderAPI is not loaded!");
			
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
        Bukkit.getScheduler().cancelTasks(this);
	}
	
	public void reloadHooks() {
		try {
			Class<?> c = Class.forName("me.clip.placeholderapi.PlaceholderAPI");
			Method m = c.getMethod("unregisterPlaceholderHook", String.class);
			m.invoke((Object) null, "pseudoapi");
			m.invoke((Object) null, "pseudomusic");
			m.invoke((Object) null, "pseudoplayers");
			m.invoke((Object) null, "pseudoutils");
			message.sendPluginMessage(Bukkit.getConsoleSender(), "Unregistered all Placeholder Hooks!");
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			message.sendPluginError(Bukkit.getConsoleSender(), Errors.CUSTOM, "Failed to access PlaceholderAPI!");
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
			message.sendPluginMessage(Bukkit.getConsoleSender(), "Registered PseudoAPI Placeholder Hooks!");
			if (Bukkit.getPluginManager().getPlugin("PseudoMusic") != null) {
				m.invoke((Object) null, "pseudomusic", new PseudoMusicHooks());
				message.sendPluginMessage(Bukkit.getConsoleSender(), "Registered PseudoMusic Placeholder Hooks!");
			}
			if (Bukkit.getPluginManager().getPlugin("PseudoPlayers") != null) {
				m.invoke((Object) null, "pseudoplayers", new PseudoPlayersHooks());
				message.sendPluginMessage(Bukkit.getConsoleSender(), "Registered PseudoPlayers Placeholder Hooks!");
			}
			if (Bukkit.getPluginManager().getPlugin("PseudoUtils") != null) {
				m.invoke((Object) null, "pseudoutils", new PseudoUtilsHooks());
				message.sendPluginMessage(Bukkit.getConsoleSender(), "Registered PseudoUtils Placeholder Hooks!");
			}
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			message.sendPluginError(Bukkit.getConsoleSender(), Errors.CUSTOM, "Failed to access PlaceholderAPI!");
			e.printStackTrace();
		}
	}

	private void initializeCommands() {
		this.getCommand("pseudoplaceholders").setExecutor(mainCommand);
	}

	private void initializeSubCommands() {
		subCommands.put("help", helpSubCommand);
		subCommands.put("reload", new ReloadSC());
	}

	private void initializeTabcompleters() {
		this.getCommand("pseudoplaceholders").setTabCompleter(new PseudoPlaceholdersTC());
	}

	private void setCommandDescriptions() {
		commandDescriptions.add(new CommandDescription("pseudoplaceholders", "Shows PseudoPlaceholders information", ""));
		commandDescriptions.add(new CommandDescription("pseudoplaceholders help", "Shows PseudoPlaceholders commands", ""));
		commandDescriptions.add(new CommandDescription("pseudoplaceholders reload", "Reloads PseudoPlaceholders hooks", "pseudoplaceholders.reload"));
	}

}