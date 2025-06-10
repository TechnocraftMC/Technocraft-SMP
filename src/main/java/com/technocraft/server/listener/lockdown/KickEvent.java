package com.technocraft.server.listener.lockdown;

import com.technocraft.server.commands.LockdownCommand;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public class KickEvent implements Listener {
    @EventHandler
    public void onPlayerKick(PlayerKickEvent e)
    {
        if (LockdownCommand.isLocked)
        {
            Player player = e.getPlayer();
            if (!player.isOp() || !player.hasPermission("group.admin"))
            {
                player.kick(Component.text("Server is in maintenance mode. Please check Discord for updates."));
            }
        }
    }
}