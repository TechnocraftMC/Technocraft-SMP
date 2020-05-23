package com.technocraft.server.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;

public class SuperChat implements CommandExecutor, Listener {
    public static List<Player> superUsers = new ArrayList();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings)
    {
        Player p = (Player) sender;

        if (superUsers.contains(p))
        {
            superUsers.remove(p);
            p.sendMessage("You are no longer in super chat mode.");
        } else
        {
            superUsers.add(p);
            p.sendMessage("You are now in super chat mode.");
        }
        return true;
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent e)
    {
        if (superUsers.contains(e.getPlayer()))
        {
            e.setFormat(ChatColor.WHITE + "" + ChatColor.BOLD + e.getPlayer().getName() + ChatColor.RESET + " " + ChatColor.AQUA + e.getMessage());
        }
    }
}