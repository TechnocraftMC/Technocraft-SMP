package com.technocraft.server.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerPingEvent implements Listener {
    @EventHandler
    public void onServerPing(ServerListPingEvent e)
    {
        if (Bukkit.hasWhitelist())
        {
            e.setMotd(ChatColor.translateAlternateColorCodes('&', "&9&l---- &c&lSERVER IS IN MAINTENANCE MODE &9&l---- \n        &6&lCHECK DISCORD FOR UPDATES"));
        }
    }
}
