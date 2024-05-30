package org.joutak.loginpluginforjoutak.commands;

import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joutak.loginpluginforjoutak.LoginPluginForJoutak;

import java.util.List;

public abstract class AbstractCommand implements CommandExecutor {

    public AbstractCommand(String command) {
        PluginCommand pluginCommand = LoginPluginForJoutak.getInstance().getCommand(command);
        System.out.println("pluginCommand:" + pluginCommand);
        if (pluginCommand != null) {
            pluginCommand.setExecutor(this);
        }
    }

    public abstract void execute(CommandSender commandSender, Command command, String string, String[] args);

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String string, @NotNull String[] args) {
        execute(commandSender, command, string, args);
        return true;
    }
}
