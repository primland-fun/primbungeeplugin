package ru.primland.bungee_plugin.motd;

import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;

import org.jetbrains.annotations.NotNull;

import ru.primland.bungee_plugin.PrimBungeePlugin;
import ru.primland.bungee_plugin.Utils;

public class MotdListener implements Listener {
    @EventHandler
    public void pingEvent(@NotNull ProxyPingEvent event) {
        Configuration config = PrimBungeePlugin.getInstance().getConfig();
        ServerPing response = event.getResponse();

        // Sets a server's MOTD
        String motd = String.join("\n", config.getStringList("motd.description"));
        response.setDescriptionComponent(Utils.translate(motd));

        // Sets a server's protocol name
        ServerPing.Protocol protocol = response.getVersion();
        protocol.setName(config.getString("motd.protocolName"));
        response.setVersion(protocol);

        // Sets a response
        event.setResponse(response);
    }
}
