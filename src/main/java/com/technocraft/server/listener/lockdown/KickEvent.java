package com.technocraft.server.listener.lockdown;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public class KickEvent implements Listener {
    @EventHandler
    public void onPlayerKick(PlayerKickEvent e)
    {
        if (e.getReason().contains("white") || e.getReason().contains("lockdown"))
        {
            e.setReason(ChatColor.translateAlternateColorCodes('&', "&cA server lockdown is currently in effect. \nPlease check &6Discord &cfor updates."));
        }
    }
}