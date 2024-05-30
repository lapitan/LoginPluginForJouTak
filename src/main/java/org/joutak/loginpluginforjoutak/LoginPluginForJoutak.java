package org.joutak.loginpluginforjoutak;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.joutak.loginpluginforjoutak.commands.LoginAddAndRemovePlayerCommand;
import org.joutak.loginpluginforjoutak.event.PlayerJoinEventHandler;
import org.joutak.loginpluginforjoutak.utils.JoutakLoginProperties;
import org.joutak.loginpluginforjoutak.utils.ResourcesJsonReader;

@Slf4j
public final class LoginPluginForJoutak extends JavaPlugin {

    @Getter
    private static LoginPluginForJoutak instance;

    @Override
    public void onEnable() {

        instance = this;
        ResourcesJsonReader.getResources();
        if (!JoutakLoginProperties.enabled) {
            log.error("Plugin was disabled in config. Enable it in config file");
            return;
        }

        new LoginAddAndRemovePlayerCommand();
        Bukkit.getPluginManager().registerEvents(new PlayerJoinEventHandler(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
